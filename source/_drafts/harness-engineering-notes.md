---
title: Harness Engineering 阅读笔记（原始素材）
tags:
  - draft
  - raw-material
  - ai-agents
  - harness-engineering
---

> **状态**：纯原始素材。来自读 Martin Fowler 站点上 Birgitta Böckeler 的 *Harness engineering for coding agent users*（2026-04-02）一文以及随后的逐项追问。**未经成稿编辑**，仅作为后续博客写作的原料堆放在此。
>
> 原文链接：<https://martinfowler.com/articles/harness-engineering.html>

---

## 一、文章核心论点（一句话版）

**Agent = Model + Harness**。模型本身能力有限且难以驯服；要让用户在更少监督下信任 agent 输出，必须在模型外面套一层"harness"——即引导（guides，前馈）+ 传感器（sensors，反馈）+ 反馈回路。Harness engineering 就是设计、迭代、维护这层外壳的工程实践。

三层同心结构：
1. **模型核心**（厂商训练的 LLM）
2. **厂商内置 harness**（Claude Code / Cursor 等 agent 框架自带的工具调用、审批、上下文管理）
3. **用户外层 harness**（AGENTS.md、Skills、自定义 lint、CI 检查、AI judge 等）

---

## 二、关键框架（按文章顺序）

### 1. 两个控制方向

| 方向 | 时机 | 例子 |
|------|------|------|
| **Feedforward / Guides** | agent 行动前 | AGENTS.md、Skills、LSP 类型提示、文档、模板 |
| **Feedback / Sensors** | agent 行动后 | linter、test、AI review、SLO 监控 |

两者必须共存。只前馈无反馈 → 漂移；只反馈无前馈 → 反复试错耗成本。

### 2. 两种执行类型

| 类型 | 特点 | 例子 |
|------|------|------|
| **Computational** | 确定性、毫秒级、零幻觉、零成本可重复 | 测试、linter、type checker、ArchUnit、OpenRewrite |
| **Inferential** | 非确定性、慢、贵、需选择性使用 | LLM-as-judge、AI code reviewer |

原则：能用 computational 的就别用 inferential。Inferential 留给"语义对不对"这种 computational 永远做不到的判断。

### 3. Steering loop（人类角色变迁）

人不再是逐行 code reviewer，而是 **harness 迭代者**：观察 agent 反复犯哪类错 → 把这类错对应的检查写成新规则/skill/sensor → 让下一次 agent 自己被 harness 拦下。

AI 也能反过来帮人造 harness：自动生成 lint 规则、结构测试、从历史 PR 反推规范。

### 4. Quality left-shift（按成本分布传感器）

| 阶段 | 传感器特征 |
|------|-----------|
| Pre-commit hook | 最快最便宜，必须秒级 |
| CI pipeline | 中等成本，可跑集成测试、契约测试 |
| Post-merge / 持续扫描 | 慢但有价值，drift 检测、依赖扫描、死代码 |
| Runtime | SLO 监控、AI judge 抽样 production 日志 |

### 5. 三个治理维度（regulation categories）

借自控制论中的 governor（调速器）隐喻：

1. **Maintainability harness**（可维护性）
   - 最容易、工具最丰富
   - Computational 可靠捕获：重复代码、圈复杂度、覆盖率不足、架构偏移、风格违规
   - LLM 部分捕获：语义重复、冗余测试、过度工程
   - 都难捕获：需求误解、设计错位

2. **Architecture fitness harness**（架构适配性）
   - 用 fitness functions 持续校验性能、可观测性、可伸缩等架构特性
   - 出处：Neal Ford / Rebecca Parsons / Patrick Kua 的 *Building Evolutionary Architectures*（O'Reilly, 第 1 版 2017，第 2 版 2023 加 Pramod Sadalage）

3. **Behaviour harness**（行为正确性）
   - 文章称之为 "elephant in the room"
   - 验证功能正确性目前仍主要依赖 AI 生成的测试 + 人工测试
   - **Approved fixtures pattern** 是潜在方向，但不是万能解
     - （细节未验证：仅基于命名与 lexler.github.io 链接推断——人工先批准一份"已知正确的输入/输出 fixture"，agent 写代码必须复现这份 fixture 而不是自编测试）

### 6. Harnessability（值得收藏的词）

代码库**被 harness 工程驯服的难易程度**。是 testability 的孪生兄弟。

提升 harnessability 的因素：
- 强类型语言
- 清晰模块边界
- 有约束的框架（opinionated framework）
- 规范化目录结构
- 声明式配置

残酷推论：**harness 最需要的地方，恰好最难建立**——遗留烂摊子最该上 agent，但烂摊子的 harnessability 最低。

相关概念：Ned Letcher 的 **"ambient affordances"**——环境本身的结构化属性，让 agent 能"读懂"和"导航"代码库。

### 7. Harness templates（未来形态）

类似今天的 service template，但内置一整套 guides + sensors。每种拓扑一个模板：

| 拓扑 | 典型 guides | 典型 sensors |
|------|------------|-------------|
| Data dashboard (Node) | 图表库选型、设计令牌、a11y 要求 | ESLint + axe-core、Lighthouse budget、视觉回归（Playwright screenshot diff）、bundle size guard |
| CRUD service (JVM) | 六边形架构分层、OpenAPI 模板、错误响应约定 | ArchUnit、Spectral lint OpenAPI、Testcontainers、Pact 契约测试、SQL 注入扫描 |
| Event processor (Go) | 消息 schema 演进、idempotency 模式、重试策略 | Schema registry 兼容性、DLQ 监控、消费 lag SLO、go vet + golangci-lint |
| ML 推理服务 | （可延伸） | |
| 批处理 ETL | （可延伸） | |
| 内部 admin tool | （可延伸） | |

### 8. Ashby's Law of Requisite Variety（必要多样性定律）

- 出处：W. Ross Ashby, *An Introduction to Cybernetics*, **1956**
- 原话：**"Only variety can destroy variety"**
- 直觉：象棋对手有 N 种变招，你的应招种类必须 ≥ N，否则必输
- 对 harness 工程的意义：LLM agent 输出空间近乎无限（variety 极大），单一传感器永远治不住。两条路：
  1. 扩大治理器多样性（堆更多种类的传感器）
  2. **缩小被治理系统多样性**（用 harness templates 限制 agent 输出形态）—— Birgitta 强调这一条，"defining topologies is a variety-reduction move"

### 9. 人类自带的"隐含 harness"（值得展开）

人类开发者把以下五样东西当作内嵌治理系统带进每个代码库，agent 没有：

| 维度 | 人类有 | agent 无 |
|------|--------|---------|
| **审美直觉** | 看到 300 行函数会皱眉 | 1000 行也心安理得 |
| **社会问责** | commit 挂名、PR 被 review、半夜被 on-call 叫醒 | 没有名字、没有同事、没有半夜电话 |
| **品味** | 阅读大量好/烂代码后形成的隐性判断 | 难以完全规则化 |
| **组织记忆** | 知道"这块债是上季度欠的，三个月后重写"——会议、Slack、走廊对话累积 | 文档查不到 |
| **节奏带来的思考空间** | 写一行几秒到几分钟，间隔触发经验回路 | 一秒生成 200 行，没有窗口 |

Meta 含义：harness 工程的本质是**把这五种隐性能力中可显式化的部分挤出来**。但作者明确承认——**永远挤不完**。终极目标不是消除人类输入，而是把人类输入引导到最值钱的地方（品味、组织上下文、架构权衡）。

### 10. Harness 自身的元评估（很 meta）

文章列为开放问题。属于"测试的测试"那一层。

为什么是真问题：harness 本身是代码 + 配置 + prompt + skill 的混合体，会随时间增长。50 条 lint + 20 个 skill + 10 个 AI judge 的 harness，如果没人评估它**漏了什么 / 互相打架 / 哪些规则从没触发过**，会和老旧测试套件一样腐烂。

可借鉴的类比物：
- **代码覆盖率**（line / branch coverage）
- **Mutation testing**（PIT、Stryker、mutmut）：故意植入 bug，看测试套件能不能抓到
- **LLM eval framework**（Ragas、DeepEval、promptfoo）

可能的 harness 评估维度（推断方向，业内尚未定型）：
1. **规则覆盖率**：每条规则有没有对应"反例 fixture" 证明它真能拦下违规
2. **传感器召回率/精确率**：植入已知违规，看哪些 sensor 触发、漏报、误报
3. **触发频率分布**：一年都没 fire 过的规则——是代码完美还是规则过时？
4. **冲突检测**：guides 之间有没有矛盾指令
5. **修复有效性**：sensor 报错后 agent 自我修正成功率

可预期演化路径：未来出现 "harness coverage tool" / "harness mutation framework"——往项目里植入已知反模式，看 harness 能逮住几个。

### 11. 工业界现状例子（文章引用）

- **OpenAI**：分层架构 + 自定义 linter + "garbage collection" agents
- **Stripe**：pre-push hooks + "minions"（小型自动化 agent）+ blueprints
- **Thoughtworks 各团队**：computational + inferential 传感器混合使用

---

## 三、术语速查（写博客时可能直接用）

| 术语 | 解释 |
|------|------|
| **Harness** | agent 外壳：guides + sensors + feedback loops |
| **Feedforward / Feedback** | 控制论正式术语，Wiener 1948 *Cybernetics* 推广 |
| **Cybernetics** | 1948 Wiener，跨学科（动物 + 机器），希腊语 κυβερνήτης（舵手），不只是工业控制 |
| **Governor** | 控制论中的调速器隐喻 |
| **OpenRewrite** | Moderne 公司开源，基于 Lossless Semantic Tree（LST），用 recipe 做大规模确定性重构（Spring Boot 升级、JUnit 4→5） |
| **Fitness function** | 出自 *Building Evolutionary Architectures*（Ford / Parsons / Kua, 2017） |
| **ArchUnit / ts-arch / dependency-cruiser / NetArchTest / import-linter / Sonargraph** | 架构偏移检测工具族 |
| **Prettier / Black / gofmt / rustfmt / Spotless / ESLint / Checkstyle / Pylint / golangci-lint / clippy** | 风格违规检测工具族 |
| **Approved fixtures pattern** | （未验证细节）approval testing 家族；人工批准 fixture，agent 写代码必须匹配 |
| **Harnessability** | 代码库被 harness 工程驯服的难易程度 |
| **Ambient affordances** | Ned Letcher 提出，环境结构属性让 agent 能读懂/导航 |
| **Ashby's Law / Requisite Variety** | "Only variety can destroy variety", Ashby 1956 |
| **Variety-reduction move** | 用 harness template 收窄 agent 输出空间 |

---

## 四、可以发展成的博客角度（脑暴）

> 仅作素材，不一定都写

1. **"Agent = Model + Harness" 这个公式为什么是当下最有用的心智模型** —— 把"AI 编程不行 / AI 编程很强"两派拉到同一个尺子上：拼的是 harness。
2. **从 testability 到 harnessability：可治理性是新一代代码质量指标** —— 类比 testability 的历史，预测 harnessability 会变成架构师的口头禅。
3. **Harness 模板 vs Service 模板：谁会取代谁** —— 企业平台工程的下一步。
4. **Ashby's Law 在 AI 时代的复活** —— 控制论老定律为什么突然在 LLM 治理上变得相关。
5. **人类的"隐含 harness"：那些永远挤不出来的能力** —— 偏感性的散文，写组织记忆 / 节奏 / 社会问责。
6. **Harness 自身的覆盖率：meta 一层的工程问题** —— 行业开放问题，可以自己提一个雏形方案。
7. **OpenRewrite + AI agent 的化学反应** —— computational feedforward 的极致案例。
8. **从 AGENTS.md 到 harness：我自己代码库的演化路径** —— 个人实践帖，可结合 OMO、oc-tweaks 的真实例子。

---

## 五、原始追问 Q&A（备查）

> 这部分是和助手对话时的逐项追问输出，未编辑，原样保留。

1. **OpenRewrite**：Moderne 公司开源，LST + recipe，确定性大规模重构。
2. **Feedforward / Feedback**：控制理论正式术语，被控制论继承。
3. **Cybernetics**：1948 Wiener，跨学科起源，不只工业。
4. **三个治理维度**：Maintainability / Architecture fitness / Behaviour。
5. **架构偏移检测**：ArchUnit / ts-arch / dependency-cruiser / NetArchTest / import-linter / Sonargraph。**风格违规**：Prettier / ESLint / 各语言原生 formatter / Spotless / pre-commit hook。
6. **Fitness functions 出处**：*Building Evolutionary Architectures*, Ford/Parsons/Kua, O'Reilly 2017，2023 第二版加 Sadalage。
7. **Approved fixtures**：未验证细节，approval testing 家族变体。
8. **Harnessability**：代码库被治理的难易程度，testability 的孪生概念。
9. **Harness templates**：data dashboard / CRUD service / event processor 三种拓扑各自的 guides + sensors 组合。
10. **Ashby's Law**：Ashby 1956，*An Introduction to Cybernetics*，"only variety can destroy variety"。
11. **人类隐含 harness**：审美 / 社会问责 / 品味 / 组织记忆 / 节奏空间。
12. **Harness 元评估**：类比 mutation testing / 代码覆盖率，行业开放问题。

---

**END of raw material（第一轮）。**

---

## 六、Chad Fowler《Relocating Rigor》— 8 段金句素材

> 来源：https://aicoding.leaflet.pub/3mbrvhyye4k2e（Chad Fowler，2026-01-07）。
> Chad Fowler ≠ Martin Fowler；Chad 是 Ruby 社区早期人物、ex-Wunderlist CTO、ex-Thoughtworks DevRel。
> 这一节是 8 段被标记为 "i like / good" 的引文，沉淀为可复用素材片段。
> 备注：每条标了**这段为什么可以独立被引用**，不重述论点本身。

### 1. 重新安置纪律 / 评估系统更难被愚弄 / 拒绝把速度误认为进步

> 在这个环境中成功的工程师，会是那些重新安置纪律而非放弃纪律的人。他们把代码生成视为一种要求更高规格精度（而非更低）的能力。他们会建立比旧系统更难被愚弄的评估系统。他们会抵制把速度误认为进步的诱惑。

**用法**：三句话排比，可直接做某节的小标题三连，或博客结尾的 manifesto。压缩了 "AI 时代谁是合格工程师" 的判定标准。

### 2. 范式转移看起来乱，其实是纪律搬家了

> 每一次技术范式转移，旁观者的第一反应都是"太乱了"，但实际上是"纪律搬家了"。

**用法**：可作 slogan。
**注意**：这句是阅读稿 commentary 加的话，**不是 Chad 原文**；引用时要标注 "改写自 Chad Fowler 的论点"，不能直接打引号挂他名下。

### 3. The Curse of a Name —— 命名的诅咒

> *"The Curse of a Name"* —— 一个理念一旦有了名字，就容易被包装成可以卖的"方法论"，购买者买的是符号，不是纪律。这个观察对 harness engineering 有直接警示：如果 harness 变成"装一个工具就算完成"，它会重蹈 Agile 的覆辙。

**用法**：整篇 harness engineering 博客可借力的最强外部锚点。把 Chad 对 Agile 的诊断平移到 "harness 不能只是装个工具"，警示性强。
**外延**：Chad 的 "The Curse of a Name" 是 Medium 上更早的一篇文章，本节子代理在查相关上下文。

### 4. 控制的表象 vs 运行时真相

> 它看起来混乱，是因为它移除了"控制的表象"。替代它的是运行时真相。你知道你在哪里，是因为代码告诉你，而不是因为项目经理更新了甘特图。

**用法**：开篇钩子或某节首句，画面感强。"代码告诉你 vs 甘特图告诉你" 这一对照可同时罩住 agile / harness / AI 三种语境，无术语门槛。

### 5. 你会失去迫使你理解的摩擦力

> 你会失去迫使你理解的摩擦力。

**用法**：句子级别金句，反消费主义味道正。可直接做章节标题，例如《摩擦力是工程的特征，不是缺陷》。

### 6. 纪律从"谁写的代码"位移到"代码必须满足什么"

> 我在 1999 年学到的纪律，恰好就是让 AI 辅助开发能工作的纪律。*纪律从"谁写的代码"位移到了"代码必须满足什么"*。

**用法**：把 "relocating rigor" 抽象命题落到一个可执行结论上的句子。可作为 "为什么 harness engineering 的核心是 sensor 而非 model 选型" 那段的论据。

### 7. 廉价的生成 + 不严格的判断 = 弃权

> 廉价的生成加上不严格的判断，不是新范式。是弃权。

**用法**：攻击性最强的一句，适合放在反方观点之后做收尾打击。"abdication" 中文译 "弃权" 已够锋利，不必再润色。

### 8. 双联金句：判断必须更严 + 控制更靠近现实

> 如果生成变得更容易，判断就必须变得更严格。否则，你就不是在做工程了。
> Control doesn't disappear. It moves closer to reality.

**用法**：双联金句。前半句给读者义务，后半句给世界观。可并排放在博客末尾收束。
**额外**："Control moves closer to reality" 适合做整篇 harness engineering 博客的封面引言或卷尾印章。

---

## 七、Uncle Bob × Agile 词义稀释 —— Chad Fowler "The Curse of a Name" 的前辈互文链

> **设计意图**：把 Chad Fowler "The Curse of a Name" 的论点放回它的前辈互文链中，证明 "名字一旦流行，纪律必然流失" 不是 Chad 一个人的观察，而是一条十几年的批判主线 —— 这条主线对 harness engineering 是直接警示。
> **取证状态**：以下 7 个来源全部由子代理通过 webfetch / websearch 实际取证；个别失效链接已注明。

### 7.1 The Land that Scrum Forgot

- **作者**: Robert C. Martin (Uncle Bob)
- **年份**: 2010（Scrum Alliance 文章版）/ 2011（NDC Oslo 演讲版）
- **链接**: 原始 `https://www.scrumalliance.org/community/articles/2010/december/the-land-that-scrum-forgot`（**已失效**，Scrum Alliance 改版替换内容）；演讲笔记存档 https://max.hn/good-talks/the-land-that-scrum-forgot
- **核心观点**: Scrum 推广过程中，CSM 认证体系完全跳过了 XP 的所有技术实践（TDD、CI、重构等），只教流程管理。结果是团队在高频迭代中积累技术债，速度越来越慢，最终 Scrum 变成对开发者的压榨工具。名字（Scrum/Agile）保住了，技术核心被悄悄遗弃。
- **关键引文**:
  > What Scrum forgot is that you can not have speed without (technical) quality. The more technical debt you carry the slower you go.
  >
  > If agile is going to survive it has to remember its roots. It was a set of technical practices conjoined with a set of management practices. You could not separate them and yet it was done.

### 7.2 The True Corruption of Agile

- **作者**: Robert C. Martin (Uncle Bob)
- **年份**: 2014
- **链接**: https://blog.cleancoder.com/uncle-bob/2014/03/28/The-Corruption-of-Agile.html
- **核心观点**: 敏捷的腐化不是因为某人作恶，而是因为 "name over substance" —— "敏捷文化" 的标签被保留，技术实践（XP 的十三条）被一条条剥离。证书泡沫 → 技术实践稀释 → 项目管理占据中心，开发者被边缘化。软件工匠运动正是这次分裂的直接产物。
- **关键引文**:
  > The biggest problem I have seen within the Agile movement is the elimination of the practices. One by one, over the years, the practices have been de-emphasized, or even stripped away. This loss of practice has diluted and changed the Agile culture into something that I don't recognize as Agile any more. It has been a shift away from excellence towards mediocrity, away from hard realities, towards feel-good platitudes.
  >
  > While project managers have flocked into the Agile movement, developers have fled out of it.

### 7.3 The Tragedy of Craftsmanship

- **作者**: Robert C. Martin (Uncle Bob)
- **年份**: 2018
- **链接**: http://blog.cleancoder.com/uncle-bob/2018/08/28/CraftsmanshipMovement.html
- **核心观点**: 对 Martin Fowler 在 Agile Australia 2018 演讲的回应。Uncle Bob 接受 "Software Craftsmanship 的诞生本身是一场悲剧" 这一定性 —— 不是说工匠精神不好，而是说它的 "必须诞生" 本身就证明了 Agile 运动从内部失败。地铁检票员把乘客推进车厢却把司机挤了出去 —— 项目经理涌入，程序员被推出。
- **关键引文**:
  > There's an old song... It's about a subway conductor who did such a great job at pushing people into the train cars, that he pushed the engineer out. This is what happened to the Agile movement. They pushed so many project managers in, they pushed the programmers out.
  >
  > The tragedy is that the Agile movement was supposed to promote the ideals of Craftsmanship; and it failed. Horribly.
  >
  > The tragedy was that the Agile movement became a business that left the original values and disciplines of Agile behind.

### 7.4 Thankyou Kent

- **作者**: Robert C. Martin (Uncle Bob)
- **年份**: 2013
- **链接**: https://blog.cleancoder.com/uncle-bob/2013/12/10/Thankyou-Kent.html
- **核心观点**: 以感谢 Kent Beck / XP 出版 14 周年为切口，记录 Agile 这个 "meme" 如何被稀释的历史：认证泡沫 → Flaccid Scrum → Kanban / Lean 不断涌现的项目管理前缀。名字不断演变，原始技术精神逐渐消散。
- **关键引文**:
  > The Agile movement, which was spawned out of the controversy over Extreme Programming, skyrocketed into success, and was subsequently taken over by the project managers who all but pushed the programmers out... We've seen the growth of the software craftsmanship movement, and the slow degradation and dilution of the Agile meme.

### 7.5 Manifesto for Software Craftsmanship

- **作者**: 集体签署（含 Uncle Bob、Sandro Mancuso、Pete McBreen 等；公开征集，现有数千签署者）
- **年份**: 2009
- **链接**: https://manifesto.softwarecraftsmanship.org/（网站存在，本次 fetch 返回空，内容通过 Uncle Bob 多篇文章间接确认）
- **核心观点**: 这份宣言本身就是对 Agile Manifesto 的技术回应 —— 在 Agile 只剩管理外壳之时，用与 Agile Manifesto 平行的 "not only X, but also Y" 格式，把 "well-crafted software"、"steadily adding value" 等技术 / 匠人价值观重新写入旗帜。**宣言的存在本身就是在说：光靠 "Agile" 这个名字已经不够了。**
- **关键引文**（间接转述自 Uncle Bob 2018 文章）:
  > It's very clear from the Software Craftsmanship Manifesto that the goal of Craftsmanship is to continue and expand the Agile message. Software Craftsmanship is not some kind of Techie Nocturnal Emission. Software Craftsmanship is simply a continuation of the original goals of Agile.

### 7.6 Developers Should Abandon Agile

- **作者**: Ron Jeffries（XP 创始人之一，Agile Manifesto 签署者）
- **年份**: 2018
- **链接**: https://ronjeffries.com/articles/018-01ff/abandon-1/
- **核心观点**: Jeffries 比 Uncle Bob 走得更远 —— 主张开发者干脆放弃 "Agile" 这个词，因为标签已经被 SAFe、Scrum 认证工厂彻底绑架，继续用这个名字只会让开发者成为被组织榨取产出的工具。**名字本身已经有毒**，不如改从 XP 的具体实践（TDD、CI）出发，不冠 "Agile" 之名。
- **关键引文**:
  > When "Agile" ideas are applied poorly, they often lead to more interference with developers, less time to do the work, higher pressure, and demands to "go faster". This is bad for the developers...
  >
  > I really am coming to think that software developers of all stripes should have no adherence to any "Agile" method of any kind. As those methods manifest on the ground, they are far too commonly the enemy of good software development rather than its friend.

### 7.7 The State of Agile Software in 2018

- **作者**: Martin Fowler（Agile Manifesto 签署者，Thoughtworks）
- **年份**: 2018
- **链接**: https://martinfowler.com/articles/agile-aus-2018.html
- **核心观点**: Fowler 创造 **"Agile Industrial Complex"** 这一术语，精确描述 "Agile" 品牌被咨询公司和认证机构产业化的过程。当前状态被定义为 "faux-agile"（伪敏捷）—— 名字在，价值观和实践全无。三大呼吁中两条直指技术：提升技术卓越性的重要地位 / 停止向团队强加方法论。
- **关键引文**:
  > Our challenge at the moment isn't making agile a thing that people want to do, it's dealing with what I call faux-agile: agile that's just the name, but none of the practices and values in place. Ron Jeffries often refers to it as "Dark Agile", or specifically "Dark Scrum".
  >
  > The Agile Industrial Complex imposing methods upon people... is an absolute travesty.

---

## 八、互文链总结：Chad Fowler "The Curse of a Name" 的论点结构对位

上述 7 个来源与 Chad Fowler 的 "The Curse of a Name" **在核心诊断上高度同构**：一旦某个运动的名字变成可消费品牌，品牌利益相关方就会为维持商业活力而系统性剔除门槛高的技术实践，只保留容易销售的流程仪式，最终名字与内涵彻底脱钩。

**关键差异**：

1. **解法路径不同**:
   - Uncle Bob / Software Craftsmanship 阵营 = **名字内部另起炉灶**，用 Craftsmanship 这个新词重新锚定技术核心，本质是名字分裂。
   - Ron Jeffries = **更激进，直接弃用标签**，从具体实践（TDD/CI）出发不冠 Agile 之名。
   - Martin Fowler = 不放弃名字但承认 "Agile Industrial Complex" 已成事实，呼吁回到技术实践和团队自决。
   - Chad Fowler = 不主张任何具体解法，把 "naming as corruption" 上升为**结构性规律**。

2. **归因层级不同**:
   - Uncle Bob 等人的批评是 **"谁的错"**（项目经理入侵 / 认证机构作恶），落脚点在历史归因。
   - Chad Fowler 的论点是 **"名字本身为何必然腐化"**，落脚点在结构必然性。

**一句话对位**：同一个病 —— Uncle Bob 指 "谁下的毒"，Chad Fowler 指 "名字本身就是毒"。

**对 harness engineering 的直接警示**：harness 一旦被命名、被产品化、被认证化（"Certified Harness Engineer"、"Harness-as-a-Service" 厂商），就会重蹈 Agile 老路 —— 工具留下，纪律流失。Birgitta Böckeler 在原文里反复强调 "harness 是不断演化的工程实践，不是一次性配置"，正是在提前防御这条命名诅咒。

---

**END of raw material（第二轮完整版）。**

---

## 8. Mitchell Hashimoto: My AI Adoption Journey

**来源**: https://mitchellh.com/writing/my-ai-adoption-journey
**作者**: Mitchell Hashimoto（HashiCorp 创始人，Ghostty 终端作者）
**发表时间**: 2026-02-05
**利益声明**: "I don't work for, invest in, or advise any AI companies"

### 8.1 术语溯源关键证据

**"Harness engineering" 这个术语的早期公开使用者是 Mitchell，不是 Birgitta**：

| 来源 | 时间 |
|---|---|
| Mitchell Hashimoto 个人博客 | 2026-02-05 |
| Birgitta Böckeler internal memo | 2026-02-17 |
| Birgitta Böckeler 正式文章 | 2026-04-02 |

Mitchell 原话：
> *"I don't know if there is a broad industry-accepted term for this yet, but I've grown to calling this 'harness engineering.' It is the idea that anytime you find an agent makes a mistake, you take the time to engineer a solution such that the agent never makes that mistake again."*

**未验证**: 两人是否独立发明，或谁先谁后影响了对方。但时间线明确：Mitchell 早 12 天公开使用此术语。

**博客可用**: 写一句"这个术语的早期公开使用者是 Mitchell Hashimoto，Birgitta 把它发展成了完整的控制系统论框架"——还原谱系，避免单点归因。

### 8.2 独特金句（可直接引用）

1. **"kind of stupid and yet mysteriously productive robot friend"**
   - 全网最接地气的 agent 比喻，破解"AI = 神 / AI = 工具"的两极对立。
   
2. **"Instead of trying to do more in the time I have, try to do more in the time I don't have."**
   - End-of-day agents 哲学。对"速度陷阱"的另一种解法：不优化生成速度，优化时间利用拓扑。
   
3. **"Negative space"** （反面空间）
   - 知道什么不做和知道什么做同等重要。可与 Birgitta 的 harnessability 配合使用。
   
4. **"Bad Thing / Good Thing"**
   - 用日常语言定义 harness 目标。和 Birgitta 的 "regulate the codebase towards its desired state" 说同一件事，但破解技术腔。

### 8.3 独特方法论：6 步 adoption journey（纵向时间线）

Mitchell 提供的是其他三人没有的**纵向个人进化路径**，而 Birgitta/Rahul 给的是**横向框架**。

1. **Drop the Chatbot** — 从 chatbot 切换到 agent（loop + tool-use）
2. **Reproduce Your Own Work** — 用 agent 重做自己已完成的任务，校准能力边界
3. **End-of-Day Agents** — 每天最后 30 分钟启动 agent，利用非工作时间
4. **Outsource the Slam Dunks** — 高置信度任务全权委托，自己做别的
5. **Engineer the Harness** — 防止 agent 重复犯同一个错
6. **Always Have an Agent Running** — 后台常驻 agent（自承只达成 10-20%）

**博客可用对照**:
- 横轴（Birgitta/Rahul）：harness 由什么组成
- 纵轴（Mitchell）：人怎么一步步学会用 harness

### 8.4 独特的实践细节

1. **关闭 desktop notifications**——人控制中断 agent 的时机，不是反过来。"Context switching is very expensive."
2. **一次只跑一个 agent**——Mitchell 明确反对当前的 agent farm 风潮："I'm not [yet?] running multiple agents, and currently don't really want to."
3. **Triage agent 只读不写**——"I would NOT allow agents to respond, I just wanted reports the next day"。Safety guardrail 早期形态。
4. **Ghostty AGENTS.md 作为具体可点击的 harness 实例**——
   `https://github.com/ghostty-org/ghostty/blob/ca07f8c3f775fe437d46722db80a755c2b6e6399/src/inspector/AGENTS.md`
   Mitchell 原话：*"Each line in that file is based on a bad agent behavior, and it almost completely resolved them all."*——比 Birgitta 抽象描述强一个数量级。

### 8.5 Mitchell vs Birgitta 的 harness 定义差异

| 维度 | Mitchell（工匠视角） | Birgitta（架构视角） |
|---|---|---|
| 定义 | 防止 agent 重复犯同一个错 | guides + sensors + feedback loops 完整控制系统 |
| 形式 | 2 种（AGENTS.md + programmed tools） | 4 种组合（feedforward × feedback × computational × inferential） |
| 理论根基 | 经验观察 | 控制论（Ashby's Law） |
| 适用范围 | 个人 / 小团队 | 团队 / 组织 |

**博客可用**: Mitchell 是从底向上从实践归纳出概念，Birgitta 是从顶向下用理论框架收编实践。两者互补，不冲突。

### 8.6 Software Craftsman 身份谱系（重要线索）

Mitchell 自我定位：
> *"I'm a software craftsman that just wants to build stuff for the love of the game."*

这给博客提供一条**身份谱系线索**：

```
1999  XP / 极限编程（Kent Beck, Chad Fowler 入行）
  ↓
2001  Agile Manifesto
  ↓
2009  Software Craftsmanship Manifesto（Uncle Bob, Sandro Mancuso）
  ↓
2014-2018  Uncle Bob "True Corruption of Agile" / Ron Jeffries "Abandon Agile" / Chad Fowler "Curse of a Name"
  ↓
2026  Mitchell "software craftsman" + Birgitta "harness engineering"
```

**核心论点**: harness engineering 不是 AI 时代的新发明，是 software craftsmanship 传统在 LLM 协作场景下的自然延伸。Mitchell 的自我定位明确把这条线索接上。

### 8.7 关于 junior 的担忧（脚注 3）

Mitchell 在脚注里明确表达：
> *"The skill formation issues particularly in juniors without a strong grasp of fundamentals deeply worries me, however."*

并暗示了 partial mitigation 策略：**outsource slam dunks, keep deep work for human**——把高价值挑战性任务留给人，把确定性低价值任务给 agent，避免人类技能在所有维度同时退化。

**注**: 引用的 Anthropic skill formation paper 链接需独立验证，博客如使用要重新查证。

### 8.8 三篇文章互文对照表

| 维度 | Birgitta Böckeler | Rahul Garg | Mitchell Hashimoto |
|---|---|---|---|
| **视角** | 架构 / 控制系统论 | 开发者体验 / 协作 | 工匠 / 个人工作流 |
| **核心隐喻** | Harness = guides + sensors | Teammate = onboarding + whiteboarding + guardrails | Robot friend that's stupid yet productive |
| **发表时间** | 2026-04-02 | 2026-04-08 | 2026-02-05 |
| **harness 术语** | 完整框架化 | 未直接使用 | 自称独立命名 |
| **关键贡献** | Computational/Inferential 二分；Ashby's Law；harnessability | 5 patterns；首次通过验收率指标 | 6 步 adoption journey；AGENTS.md 实践 |
| **对 AI 态度** | 信任但需控制 | 协作伙伴 | 有点蠢但 mysteriously 有成效的机器人朋友 |
| **对 junior 担忧** | 隐含（harness 替代人类经验） | 未提及 | 明确担忧（脚注 3） |
| **与 Chad Fowler 共振** | "steering loop" = relocating rigor | "shared mental model" = control closer to reality | "work on something else" = 纪律搬家 |
| **身份标签** | Thoughtworks Distinguished Engineer | Engineer @ Thoughtworks | Software craftsman (no AI affiliation) |

### 8.9 略过的部分（不值得收录）

- **Step 1 (Drop the Chatbot)**: 常识，2026 年的读者不需要再被说服。
- **Step 6 (Always Have an Agent Running)**: 愿景多于实践，作者自承只达成 10-20%。
- **Anthropic skill formation paper 引用**: 需独立验证才能在博客引用。

---

**END Section 8 — Mitchell Hashimoto 收割**

