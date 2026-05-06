---
title: Harness Engineering：把纪律搬到模型外面
date: 2026-05-05 23:30:00
categories:
  - AI
tags:
  - ai-agents
  - harness-engineering
  - software-craftsmanship
  - testing
description: 代码生成变快以后，工程纪律没有消失，只是从人身上搬到了模型外面的 guides、sensors 和 feedback loops 里。
---

为什么 AI coding 让人一边兴奋，一边心里发毛？

不是因为它写不出代码。恰恰相反，是因为它太能写了。你说一句，它回你一屏；你开个 agent，它能自己读文件、改代码、跑命令、再解释一遍自己做了什么。

问题是：生成变快了，判断有没有变严？

如果没有，那就不是工程效率提升，而是把风险生成得更快。

<!-- more -->

## 速度不是纪律

以前我们说一个工程师靠谱，通常不是说他打字快。

靠谱意味着很多隐性的东西：知道什么代码不该写，知道一个测试是不是只是在凑 coverage，知道一个依赖跨层了会在三个月后变成债，知道某个“先这样吧”其实已经在召唤未来的事故。

这些东西很难完全写进流程。它们长在人身上，长在 review 习惯里，长在团队的组织记忆里。

这就是 software craftsmanship 里最硬的一部分：不是姿态，而是纪律。

AI agent 进来以后，这些纪律没有自动继承。它不会因为 commit 挂名而睡不好，不会因为半夜 on-call 记住某个设计债，也不会因为看见 800 行函数而本能地皱眉。

所以纪律要搬家。

从人的隐性判断，搬到模型外面的结构里。

这就是我理解的 harness engineering。

## 纪律搬到哪里去了？

[Birgitta Böckeler 在 Martin Fowler 网站那篇 *Harness engineering for coding agent users*](https://martinfowler.com/articles/harness-engineering.html) 里，给了一个能直接拿来拆问题的模型：

> Agent = Model + Harness

这里的 harness 不是某个神奇配置，也不是某个工具的高级功能。它是套在模型外面的外壳：guides、sensors、feedback loops。

先别急着翻译。我们把它拆开看。

**Guide** 是行动前的东西：项目约束、代码风格、架构规则、写作风格、任务模板、分层规程。它告诉 agent：这个项目里什么叫“对”。

**Sensor** 是行动后的东西：lint、typecheck、test、架构规则、契约测试（验服务接口约定）、视觉回归（截图比对看 UI 有没有变形）、AI review。它告诉 agent：你刚才做的事有没有偏。

**Feedback loop** 是把两者接起来的回路：发现一类错误反复出现，就把它变成新的 guide 或 sensor，让下一次不再靠人肉提醒。

这不是抽象口号。我们看一些 agent 工具的高级用户配置，会发现它们最后都在拼同一套零件：项目指令、分层规程、行动前 gate、行动后 sensor、权限边界、专门角色 agent、持久状态。叫法各异——有的叫 rules / instructions，有的把关键逻辑放进 plugin——但做的是同一件事。

每一件具体做什么、在不同工具里落到哪儿，文末会给一张映射表，这里先按下不表。

重点是：纪律被拆成了可执行的结构。

这件事听起来像新词，其实不是新问题。

以前 craftsmanship 要求人把纪律带进代码库。现在 agent 没有那套隐性纪律，我们就要把可显式化的部分挤出来，变成模型外面的 harness。

纪律没有消失。

它只是换了位置。

## 能计算的，先交给计算机

到这里容易走偏。一听 harness，很多人会下意识开始堆 prompt、堆 rules、堆「请你务必认真」。

我们顺手把这条岔路先封掉。

Harness 里有一个很关键的区分：computational 和 inferential。

这两个词不是行业通用术语，是 Birgitta 文章里用来切分 sensor 类型的一对划分。直译是「计算型」和「推断型」，但更直白的说法是：**一种是机器算得出对错的检查，一种是要靠判断的检查**。

**Computational sensor** 是「算得出对错」的那种，结果确定。

像 formatter（自动整理代码格式）、linter（静态代码检查）、type checker（类型检查）、unit test、contract test（验证服务之间接口约定的测试）、架构依赖检查。这类工具便宜、快、可重复，不会今天心情好就放过你一次。

**Inferential sensor** 是「要靠判断」的那种，结果会飘。

像 LLM-as-judge（让模型当裁判去评分）、AI code reviewer、语义重复检测、设计合理性点评。这类工具有用，但慢、贵、不稳定，还需要被校准。

所以顺序应该是：能用 computational 的，就别急着用 inferential。

但「拿什么工具」只是问题的一半。

另一半是「在 agent 工作流的哪一刻让它跑」。

大部分 agent 工具都给了一组生命周期事件——有的叫 hook，有的叫 lifecycle event，有的叫 plugin point。名字不重要，含义都差不多：会话开始、用户输入提交、工具调用之前、工具调用之后、上下文压缩之前（agent 把过长会话压缩成摘要的那一刻）、会话结束。

这一排时间点，就是 sensor 真正能落地的地方。

所以一张更有用的表是这样的——左边是想防的事，中间是工具，右边是这工具该挂在哪个生命周期点上：

| 想防什么 | 用什么 | 挂在哪个生命周期点 |
|---|---|---|
| 写命令前手滑 | bash 前缀白名单 / 危险命令黑名单 | 行动前 gate（工具调用之前） |
| 改完代码留下 lint / type 错 | formatter、linter、type checker | 行动后 sensor（工具调用之后） |
| 架构层乱穿 | 架构依赖检查工具：ArchUnit（JVM 系）、dependency-cruiser（Node 系）、import-linter（Python 系）、ts-arch（TypeScript 系） | 行动后 sensor + CI 门禁 |
| 测试只是绿但没用 | mutation testing（故意往代码里植入 bug，看测试能不能抓到）：PIT、Stryker | CI 门禁（成本高，不适合每次工具调用都跑） |
| 服务契约漂移 | contract test 工具：Pact、Spring Cloud Contract | CI 门禁 / 发布前 |
| UI 悄悄变形 | visual regression（截图比对，看像素级别变化）：Playwright snapshot、Percy、Chromatic | CI 门禁 / 发布前 |
| 已知迁移 / 禁用 API | codemod（按规则批量改写源代码的工具）：OpenRewrite 这类 recipe | 行动后 sensor（自动修） + CI 门禁（兜底报） |
| skill / 规则没被自动加载 | 项目指令注入 | 用户输入提交时（prompt 进入模型之前） |
| 一次 compact（上面说的"上下文压缩"动作）把教训洗掉 | 把当前计划、决定、blocker 写到外部文件 | 上下文压缩之前 |

同一个工具可以挂在不同生命周期点，效果完全不同。

formatter 挂在「工具调用之后」，是给 agent 即时反馈；挂在 CI，是给团队兜底。mutation testing 跑得慢，硬塞进每次工具调用之后会把 agent 卡死，更适合放在 CI 门禁。

生命周期点选错，再好的 sensor 也会被嫌弃然后被关掉。

这些工具不是为了显得工具箱很豪华。

它们是在把 craftsmanship 里的隐性判断拆出来。

以前 reviewer 看到跨层 import 会说：「这不该依赖这里。」现在可以把这句话写成 dependency rule。这类把架构性质翻译成可执行测试的做法，业内有个统称叫 **architecture fitness functions**（架构适配性函数——把"这个系统该长什么样"写成可以自动跑的检查），*Building Evolutionary Architectures* 那本书把它系统化了。ArchUnit、dependency-cruiser 这些工具只是它的具体载体。

以前 senior 看到测试只覆盖 happy path（只测正常流程，不测异常和边界）会说：「这个测试没测出什么。」现在可以用 mutation testing 压一下，看测试能不能杀死故意植入的错误。

以前团队靠经验知道某个服务契约不能乱改。现在可以把契约放进发布门禁。

这就是 technical harness 的价值：把「好工程师会皱眉」的地方，尽量变成机器会报警的地方。

这不是说 AI review 没价值。它能补 computational sensor 看不到的语义判断。

只是别让一个会飘的判断，替代一个本来可以确定执行的检查。

这句话很土，但很好用：

能跑的，就不要靠猜。

## 顺手补一刀：harnessability

讲到这里要插一个边界澄清。

不是所有代码库都同样好治。有的项目你刚把 harness 接上去，sensor 立刻就能咬住代码；有的项目你装一圈下来，规则跑不动、报警全是噪音、agent 改两行就触发连锁反应。

这件事其实有个名字：**harnessability**——一个代码库被 harness 工程驯服的难易程度。

这个词也是新造的，不是已有术语。它对标的是大家比较熟的 testability（可测试性）：testability 衡量代码好不好被测试，harnessability 衡量代码好不好被 harness 套住。

提升 harnessability 的因素，和提升 testability 的因素高度重合：强类型、清晰模块边界、有约束的框架、规范化目录结构、声明式配置。

这里有一条让人不太舒服的推论：**最该上 agent 的烂摊子，harnessability 往往最低**。遗留代码、隐式耦合、跨层乱穿、配置散落各处的项目，恰恰是最想让 agent 帮忙啃的，也是最难给它套 harness 的。

所以 harness engineering 不止是「给现在的项目加护栏」。它还反过来要求我们写新代码时，留出能被治理的形状。

不然 harness 装得再多，也只是装在沙地上。

## 从真实配置里能看出的几件事

前面讲的都是机制。我们落地一下——把 Claude Code、Codex、OpenCode 这些工具里那些用得比较深的配置摊开来看，会反复看到几个共同模式。这些模式跟具体工具关系不大，可以直接拿来做工程纪律。

**第一，项目指令越长越没人看，于是被拆成「短入口 + 按需加载」。**

根目录那份指令文件（叫 `CLAUDE.md` 也好，叫 `AGENTS.md` 也好）只放快事实、硬规则、和一张「什么时候去翻哪份详细规程」的路由表。

详细规程下沉到一个 skills / commands / prompts 目录里，按 keyword、路径、文件内容自动激活。

一份大而全的指令文件没人维护得动，三个月就开始撒谎；短入口加分层规程才扛得住时间。

**第二，「靠模型自觉调用规则」是不可靠的，于是用生命周期事件硬激活。**

你写在指令文件里的「请你写代码前先看一下 X」，模型有时候看，有时候忘。

靠谱的做法是在用户输入提交那一刻，由代码（不是模型）判断这次请求是不是命中某条规则，命中就把对应内容硬塞进上下文。

这一步从「请模型帮忙」变成了「不允许模型不看」。

**第三，权限不是劝告，是声明。**

「请小心不要 rm -rf」是没用的。

可用的是一份白名单：bash 只允许这几个前缀、写文件只允许这几个目录、`.env` 一律 deny、main 分支禁止 commit。

这份白名单越具体越好，模糊的「请你判断一下安不安全」基本等于没设。

**第四，行动后 sensor 的失败信号，必须能回灌到 agent。**

只是把 lint 报错打印到终端没用——agent 看不到，也就不会自己修。

可用的做法是，在工具调用之后跑 formatter / typecheck / 相关测试，把 stderr 喂回给 agent，让它自己看到、自己修。

这一步把「人去 review agent 的输出」转成了「agent 自己被自己刚刚搞坏的东西打脸」。

**第五，上下文压缩之前要主动存盘。**

长会话总会撞上 context limit（模型能记住的上下文长度上限）。如果不在压缩之前把当前计划、已经做的决定、还没解决的 blocker 写到外部文件里，下一轮 agent 醒来就只剩一份它自己生成的、可能已经失真的总结。

可用的做法是：压缩前 hook 触发一次「快照写盘」，下一轮启动时再把它读回来。

**第六，一个 agent 不该什么都干。**

让同一个上下文同时负责探索代码、修改代码、review 自己的修改、再 debug 自己的 bug，会出现一种很微妙的失败：它会下意识维护「我刚才那个判断是对的」。

把这些角色拆给不同的 subagent / role（同一个工具里启动的、上下文互相隔离的子 agent），每个角色拿到干净上下文，互相不背包袱，重复错误明显减少。

这六件事，没有一件是某个工具独有的。

Claude 的 hook、Codex 的 lifecycle dispatcher、OpenCode 的 plugin point，名字不同，做的就是这几件事。

换句话说：你换工具，但你不换纪律。

## 为什么单一规则治不住 agent？

这里可以借一下 Ashby 的必要多样性定律。

W. Ross Ashby 在 *An Introduction to Cybernetics* 里那句常被引用的话是：

> Only variety can destroy variety.

直觉版本是：对手有多少种变化，你的应对也得有足够多的变化。否则你不是在控制系统，你是在许愿。

Agent 的输出空间很大。它可以从十种路径读文件，用十种方式改代码，用十种理由解释自己为什么这么做。你只写一条“请保持代码质量”，基本没什么用。

那怎么办？两条路。

第一，增加治理器的多样性。

也就是把 harness 做成组合：项目指令、分层规程、行动前 gate、行动后 sensor、权限边界、专门角色 agent、外部验证器。

第二，减少被治理对象的多样性。

也就是不要让 agent 每次都从无限可能里自由发挥，而是给它 topology（拓扑——把项目按形态归类）：这个项目是 CRUD service（增删改查后端服务）、data dashboard、event processor（处理消息流的服务），还是 internal admin tool？每种拓扑有自己的 guides 和 sensors。

这也是 harness template 值得单独拎出来的地方。

它不是模板代码那么简单，而是在收窄 agent 的输出形态。

不是让模型更聪明。

是让战场更可控。

## 不过再想一下：harness 本身谁来测？

到这里，很多团队会自然走向一个方案：那就多加规则吧。

多写几个项目指令，多配几个 hooks，多跑几个检查，多接一个 AI reviewer。

这有用，但它会带来一个新问题：harness 自己也会腐烂。

50 条规则里，有多少真的拦过错？

20 个 sensor 里，哪些在漏报？哪些在误报？

两个 guide 互相打架的时候，agent 听谁的？

一个 hook 一年没触发，是因为团队做得好，还是因为规则已经过时？

这才是 harness engineering 最容易被忽略、但最值钱的一层：它不只是“给 agent 加更多护栏”，而是要继续往上一层问：

我们怎么知道这些护栏有用？

这很像测试的测试。

代码测试有 coverage，有 mutation testing。那 harness 能不能也有类似的东西？

- **规则覆盖率**：每条规则有没有一个反例 fixture（预先准备好的"违规样本"），证明它真的能拦下违规？
- **sensor 召回率 / 精确率**：故意植入已知问题，看哪些 sensor 抓到了（召回率：该抓的抓到多少），哪些漏了，哪些乱报（精确率：报警里有多少是真问题）。
- **触发频率分布**：一年没响过的规则，要么项目完美，要么规则已经死了。通常不是前者。
- **冲突检测**：guides 之间有没有互相矛盾？
- **修复有效性**：sensor 报错以后，agent 自我修正成功率是多少？

这一步很 meta。

但工程里最容易坏掉的，往往就是那些“大家以为它还在工作”的东西。

## 工具只是映射，不是主语

最后落到工具。

我不太想把这篇写成具体工具对比。那会很快过期，也会把问题讲窄。

更稳的方式是先用通用机制，再在括号里映射到具体工具。

| 通用机制 | 它做什么 | 典型生命周期点 | 常见落点 |
|---|---|---|---|
| 项目指令 | 给 agent 前馈约束（行动前的引导，不是事后纠错）、项目事实、风格边界 | 会话开始 / 用户输入提交 | `CLAUDE.md`、`AGENTS.md`、rules / instructions |
| 分层规程 | 把长规则拆成按需加载的领域规程 | 用户输入提交（按 keyword / 路径触发） | skills、commands、prompt files |
| 行动前 gate | 在写文件、跑命令、访问敏感路径前阻断或询问 | 工具调用之前 | hooks、permissions、approval rules |
| 行动后 sensor | 工具调用后跑格式化、typecheck、test、审计 | 工具调用之后 | hooks、CI、local scripts |
| 权限边界 | 限制工具、目录、命令、外部访问 | 工具调用之前（声明式静态约束） | sandbox、permissions、allow / deny rules |
| 专门角色 agent | 把探索、review、debug、执行拆给不同角色 | 任务分发时 | subagents、agents、orchestrators |
| 持久状态 | 跨 session 保存计划、决策、blocker、踩过的坑 | 上下文压缩之前 / 会话结束 | progress files、memory、compaction preservation（压缩前把关键信息存到外部，压缩后再读回来） |
| 外部验证器 | 用确定性工程工具治理输出 | 工具调用之后 + CI 门禁 | lint、typecheck、test、contract test、mutation test |

真实的深度用法里，重点也不是「某工具支持某功能」，而是这些机制怎么组合。

短项目指令负责定边界，分层规程负责装经验，行动前 gate 负责硬阻断，行动后 sensor 负责把失败信号回灌，持久状态负责保存已经踩过的坑。

这些都是 harness。

不是因为它们叫同一个名字，而是因为它们在做同一件事：把重复出现的工程判断，变成下一次自动生效的结构。

所以 hooks 是什么？

它不是 harness 本身。它更像 harness 的行动现场：agent 真要动手时，在那里拦一下；动完手后，在那里查一下；上下文要丢之前，在那里存一下。

我们要抽象的是共性：行动前 gate、行动后 sensor、上下文 preservation。

但“什么是好代码”“这个项目边界在哪里”“这次该走哪条 workflow”，这些不该都塞进 hook 里。

那会把 hook 变成新的泥球（big ball of mud——什么都往里塞、最后没人敢动的那种代码）。

## 回到 craftsmanship

[Software Craftsmanship Manifesto](https://manifesto.softwarecraftsmanship.org/) 里有一个很朴素的表达：不只是能工作的软件，还要 well-crafted software；不只是响应变化，还要 steadily adding value。

AI agent 没有让这件事过时。

它只是把问题重新摆到我们面前：当代码生成变得廉价，判断就必须变得更严格；当执行可以交给 agent，纪律就必须被外化、被验证、被维护。

所以 harness engineering 不是给 AI coding 镀一层新名词。

它更像 software craftsmanship 在 AI 时代的一次搬家。

以前纪律住在人身上。

现在，它也得住在系统里。

而且这套系统不能只被信任。

它也要被测试。

## 参考

- Birgitta Böckeler, [Harness engineering for coding agent users](https://martinfowler.com/articles/harness-engineering.html)
- Chad Fowler, [Relocating Rigor](https://aicoding.leaflet.pub/3mbrvhyye4k2e)
- W. Ross Ashby, [An Introduction to Cybernetics](http://pespmc1.vub.ac.be/books/IntroCyb.pdf)
- [Manifesto for Software Craftsmanship](https://manifesto.softwarecraftsmanship.org/)
