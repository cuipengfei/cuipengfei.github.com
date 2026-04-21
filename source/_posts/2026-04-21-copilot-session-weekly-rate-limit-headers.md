---
title: GitHub Copilot 这两个 rate limit header，我追了一晚上
date: 2026-04-21 22:00:00
categories:
  - [AI]
  - [技术研究]
tags:
  - GitHub Copilot
  - Rate Limit
  - Headers
  - VS Code
  - Reverse Engineering
description: 整理 GitHub Copilot 的 x-usage-ratelimit-session / x-usage-ratelimit-weekly 两个响应头：公开代码证据、官方文档证据，以及 rem 字段目前能确认与不能确认的部分。
---

前几天盯 Copilot 的一次响应，看到里面多了两行之前没留意的 header：

```
x-usage-ratelimit-session: ...rem=5.7&rst=...
x-usage-ratelimit-weekly: ...rem=74.9&rst=...
```

`rem=5.7`。这个数在掉。我不知道它按什么规律掉，也不知道掉到几要出事。

晚上花了点时间去追，想搞清楚四件事：这俩 header 啥时候冒出来的，`rem` 到底是什么，它怎么往下走，单次调用扣多少。

追完的感觉是：能坐实的比想的多，不能坐实的那块也比想的硬。`rem` 是什么，有代码作证；具体怎么扣的公式，公开世界里就是个洞。

<!-- more -->

## 先把范围划清楚

这篇只聊 `x-usage-ratelimit-session` 和 `x-usage-ratelimit-weekly`。

不聊 premium quota。`x-quota-snapshot-premium_interactions` 是另一码事，是月度 premium request 额度。两套都叫"限制"但不在一个层，混着算就像把"通话时长上限"和"每月套餐分钟数"加在一起——加出来的数没意义。

## 这俩 header 是什么时候冒出来的

先去搜 GitHub 文档。没搜到。

接着去 GitHub code search，全网精确字符串搜 `x-usage-ratelimit-session`。命中很少，少到只有一个 repo：`microsoft/vscode`。

打开看，命中位置是 `extensions/copilot/src/platform/chat/common/chatQuotaServiceImpl.ts`，对应 PR `microsoft/vscode` #310836，标题就叫 `Handle showing weekly and session rate limit data`，时间 2026-04-16。

代码里直接读：

```ts
const sessionRateLimitHeader = headers.get("x-usage-ratelimit-session");
const weeklyRateLimitHeader = headers.get("x-usage-ratelimit-weekly");
this._rateLimitInfo.session = sessionRateLimitHeader
  ? this._processHeaderValue(sessionRateLimitHeader)
  : undefined;
this._rateLimitInfo.weekly = weeklyRateLimitHeader
  ? this._processHeaderValue(weeklyRateLimitHeader)
  : undefined;
```

为了多踏实一步，我顺着 PR 往父提交追了一下：这两个 header 在该提交里有，在父提交里没有。

所以我能说出口的最稳一句话是：公开可核实的最早代码证据落在 2026-04-16。

但这话有个边界：它不等于"GitHub 内部一定是那天才加的"。内部分支什么时候开始拼这套，我没证据。

再过四天，2026-04-20，GitHub 自己开口了。官方博客 `Changes to GitHub Copilot Individual plans` 写：

> GitHub Copilot has two usage limits today: session and weekly (7 day) limits. Both limits depend on two distinct factors—token consumption and the model's multiplier.

文档 `Monitoring your GitHub Copilot usage and entitlements` 又补了一刀：

> Starting April 20, 2026, Copilot Pro, Copilot Pro+, and student plans have tighter usage limits: Session limits and weekly (7 day) limits.

机制本身坐实了。但奇怪的是——官方既没写出 header 名，也没给出 `rem` 怎么算。

## `rem` 是什么——这一段最容易坐实

回到 VS Code 那段代码继续往下读。它把 header 的 value 用 `URLSearchParams` 解析，然后做了这一步：

```ts
const percentRemaining = parseFloat(params.get("rem") || "0.0");
```

紧接着：

```ts
const percentUsed = 100 - info.percentRemaining;
```

到这一行就放心了。`rem` 就是 remaining percentage，剩余百分比。`rem=74.9` 就是剩 74.9%，`rem=5.7` 就是只剩 5.7%。

不是猜，是 VS Code 客户端代码自己这么解释的。

完整的 header value 长这样：

```
ent=0&ov=0.0&ovPerm=false&rem=74.9&rst=2026-04-27T00%3A00%3A00Z
```

VS Code 把这几个字段对应成：

- `ent` → entitlement（整型）
- `ov` → overageUsed（浮点）
- `ovPerm` → overageEnabled（布尔）
- `rem` → percentRemaining（浮点）
- `rst` → resetDate（日期）

这里要补一句免责：我没找到 GitHub 官方公开写过 header schema 的任何文档。上面这套字段含义全是从 VS Code 实现反推的。VS Code 是这么读的，不代表官方就是这么定义的——只是目前没有第二个独立来源能交叉验证。

## 知道 rem 是百分比之后呢

`rem` 是百分比，OK。但我想搞清楚的是——它是怎么掉的。

每次调用扣多少？不同模型一样吗？长 prompt 比短 prompt 扣得多？

到这里就尴尬了。盘是真的，公式是空的。

官方文档只给了一句方向性的话：

> Both limits depend on two distinct factors—token consumption and the model's multiplier.

也就是说，`rem` 不是按"请求次数"扣的，是按 token 消耗 × 模型倍率算的。Usage limits 那篇还顺嘴说，如果接近上限，可以换 multiplier 更小的模型撑久一点。

至于具体公式，我什么都没找到：

- 没有 `rem` 的精确算法
- 没有公开 schema 文档
- 客户端源码里没有扣减算法（VS Code 只读不算）
- 没有 "GPT-5.4 每次扣多少、Haiku 每次扣多少" 那种表

所以"每次请求固定扣 0.1"、"GPT-5.4 一次扣 0.6" 这种说法我现在都不能当事实写。现在能说死的也就这一句：它跟 token 用量和模型倍率有关，至于怎么算，我没找到。

## 那就自己跑两条样本看看

公式查不到，那就实测。

跑了两条不同链路的连续观测，下面记成样本 A 和样本 B。

样本 A 像快照——session `rem` 基本不动、weekly `rem` 基本不动、`rst` 也不动。我没法判断它是某种稳定口径，还是某条不敏感路径，所以也没继续外推。一句话：能记的就一句"看起来没动"。

样本 B 比较有意思。

样本 B 里，一共拿到了 17 次成功且带限额头的请求：

- session `rem`：`5.7 → 3.0`，总共掉了 `2.7`
- weekly `rem`：`74.9 → 74.2`，总共掉了 `0.7`

硬算平均，session 大约每次掉 `0.16`，weekly 每次掉 `0.04`。

但平均数在这种地方很坑——盯着原始时间序列看会发现：它不是每次都降的，也不是每次降一样的量。有的请求过去 `rem` 完全没动，有的请求一下降一截。

这反过来正好对上官方那句话——按 token × multiplier 在走，所以同样数量的请求，消耗会差很多。

样本 B 还能看出一件事：session 降得明显比 weekly 快。这其实合理，session 是短窗口，weekly 是 7 天累计窗口，本来就不是一个速率。

样本 B 跑到后段我还碰上一次 rollover：

- 之前：`rem=3.0`，`rst=2026-04-21T06:35:37Z`
- 之后：`rem=98.2`，`rst=2026-04-21T11:36:27Z`

时间差大概是 5 小时 50 秒。这跟"session 大约 5 小时窗口"的粗估对得上。

## 到这里，哪些能说死，哪些还不能

确认的：

1. 这俩 header 至少 2026-04-16 已经进了公开代码链路。
2. 公开 GitHub code 里只命中 `microsoft/vscode` 一个 repo。
3. VS Code 把 `rem` 当 `percentRemaining`（浮点百分比）解析。
4. 官方公开说过：session / weekly limits 取决于 token consumption 和 model multiplier。
5. session / weekly 跟 premium requests 是两套东西。

不能确认的：

1. 这俩 header 第一次在 GitHub 内部上线的具体时间。
2. `rem` 的精确公式。
3. 单次调用扣多少。
4. 不同模型分别扣多少。
5. `/v1/responses`、`/chat/completions`、`/v1/messages` 这几条路径之间，`rem` 成本有没有稳定差异。

## 顺便提一下 VS Code 怎么用这两个 header

回到 `chatQuotaServiceImpl.ts`。这不是 README，不是测试 fixture，是正式运行时代码。

它读 header，解析字段，按一组阈值算 warning：

```ts
private static readonly _RATE_LIMIT_THRESHOLDS = [50, 75, 90, 95];
```

百分比剩 50、25、10、5 的时候提醒。注释还写了一句很关键：

```ts
// Session rate limit takes priority over weekly
```

session 优先提示。session 一旦快没，就不管 weekly 还剩多少——毕竟 session 5 小时一轮，先躲过这一波再说。

## 后面还能再挖什么

如果还要继续追，我想做的不是再泛搜一轮，而是几个更窄的方向：

- 把现有实测样本按模型分桶，整成一张观测表（要标清楚是 observational，不是官方规则）。
- 看 Copilot CLI 或者 VS Code 之外的客户端，有没有另一处也在处理这俩 header。能找到第二个来源就能交叉验证字段含义。
- 看有没有人公开贴过带模型、prompt 长度、成功/失败状态的连续 header 样本。
- 等 GitHub 哪天补了 schema 文档，再回来把"根据客户端推断"改成"根据官方文档"。

## 我用到的来源

**公开代码**：`microsoft/vscode` PR #310836，`extensions/copilot/src/platform/chat/common/chatQuotaServiceImpl.ts`

**GitHub 官方文档 / 博客**：`Usage limits for GitHub Copilot`、`Monitoring your GitHub Copilot usage and entitlements`、`Changes to GitHub Copilot Individual plans`、`AI model comparison`

**实测样本**：本地两条链路（文中的样本 A / 样本 B）直连 `https://api.business.githubcopilot.com`，固定 `Host: api.githubcopilot.com`，固定 `X-Initiator: agent`，对 `/v1/responses`、`/chat/completions`、`/v1/messages` 的小样本探测

---

`rem` 是不是百分比？是，VS Code 的代码替我答了。GPT-5.4 一次到底扣多少？不知道，公开证据还不够。

前者够我看懂这个数字到底在说什么；后者意味着，下一次调用之后它会掉多少，我现在还是没法预测。
