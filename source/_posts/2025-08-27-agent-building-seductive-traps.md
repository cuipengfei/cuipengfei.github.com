---
title: "Agent 构建中的三个诱人陷阱"
date: 2025-08-27 10:00:00 +0800
tags: [AI Agent, Multi-Agent, RAG, Prompt Engineering, Cline]
---

> 在构建 Cline AI agent 的过程中，我们发现最危险的不是那些一眼就能看出来的坏想法，而是那些理论上听起来完美、实践中却一败涂地的诱人陷阱。

![诱人陷阱](https://cline.ghost.io/content/images/2025/08/u9318423161_tempting_trap_interpreted_in_nature_in_the_style__26705fd3-2f3e-430c-a10d-aae540aa1853_2-1.png)

## 引言

在 Cline 构建 AI agent 的征程中，我们发现最危险的其实不是那些一眼就能识破的馊主意，而是那些理论上完美无缺、实践中却惨败收场的"陷阱"。这些思维病毒早已蔓延整个行业，吞噬了数百万工程师小时，把一个又一个团队拖进了架构死胡同。

以下是我们见过的三大最常见陷阱：

1. **Multi-Agent Orchestration**（多智能体编排）
2. **RAG (Retrieval Augmented Generation)** 索引代码库
3. **指令越多 = 效果越好**

咱们一个个来看！

---

## (1) Multi-Agent Orchestration

那种科幻电影般的 agent 愿景——'后勤 agent、分片 agent、分析器 agent、编排器 agent'——听起来确实很酷，派出一群子 agent 然后整合它们的结果。但现实是：大部分有用的 agent 工作其实都是单线程的。

目前多智能体系统的最大突破来自 Anthropic，但就连他们也坦言，构建和调教多个 agent 是[极其困难的](https://www.anthropic.com/engineering/multi-agent-research-system)。他们的团队这样说：

_"Agent 系统中的错误会叠加放大，传统软件里的小毛病到了这里可能让整个 agent 彻底偏轨。一步走错，agent 就可能走上完全不同的路径，结果变得完全不可预测。基于我们文章中提到的种种原因，从原型到生产的鸿沟往往比想象中要大得多。"_

当然，我们也不是完全否定多 agent 方案。对于一些小而具体的用例，用些功能有限的子 agent 还是挺合理的。

比如说，主 agent 线程创建几个子 agent 来并行读取文件。或者用子 agent 来处理从网络抓取数据这种简单任务。但说白了，这些跟并行调用工具没什么本质区别，我甚至怀疑这算不算得上"真正的"多智能体编排。

![多智能体微服务架构](https://cline.ghost.io/content/images/2025/08/image-27.png)

_来源：[AI Agents are Microservices with Brains](https://seanfalconer.medium.com/ai-agents-are-microservices-with-brains-ccb42d1504d7)_

![多智能体系统](https://cline.ghost.io/content/images/2025/08/image-33.png)

---

## (2) RAG (Retrieval Augmented Generation)

RAG 是另一个诱人的坑。这个概念延续自上下文窗口还很小的时代，那时候给 agent 提供查询"整个代码库"的能力确实很有吸引力。但 RAG 的炒作并没有转化为实际可用的编码 agent 工作流，因为它经常会产生零散的代码片段，完全无法为模型提供真正的"上下文理解"。纸面上看起来很强大，但实际上，就连 GREP 这样的简单工具都比它好用，特别是对 agent 来说。

让 agent 像人类一样工作——列出文件、用 grep 搜索、然后打开完整的文件来阅读——这样的效果几乎总是更好。Cline 从一开始就确立了这套方法的标准，后来 Amp Code 和 Cursor 都跟着采用了这种做法。

![RAG介绍](https://cline.ghost.io/content/images/2025/08/image-32.png)

**RAG 及其复杂变种介绍**

大多数公司最初都选择了向量数据库，因为 2023 年那些"与代码聊天"的 VS Code 扩展刚出现时，模型只有 8,092 个 token 的上下文窗口，所以每一行进入模型的代码都必须精心挑选。那时候这样做确实说得通，这也解释了为什么那么多基础设施和热钱涌入了向量数据库公司，有些公司甚至筹集了数亿美元，比如 [Pinecone](https://www.pinecone.io/)。但 Cline 于 2024 年 7 月发布时，当时领先的编码模型是拥有 200K token 上下文窗口的 Claude 3.5 Sonnet，所以它从来没有受到需要拼接无关上下文片段的限制。

---

## (3) 指令越多 = 效果越好

觉得在系统提示里堆砌越来越多"指令"就能让模型变聪明？这完全是个错误观念。过载的提示只会把模型搞糊涂，因为额外的指令经常会相互冲突，产生噪音。最后你只是在行为上打地鼠，而不是获得有用的输出。对今天的大多数前沿模型来说，最好是退一步让它们自己发挥，而不是通过提示不断对它们大吼大叫。措辞要仔细斟酌。

![信号与噪声](https://cline.ghost.io/content/images/2025/08/image-31.png)

_来源：[Signal vs. Noise](https://nolongerset.com/signal-vs-noise/)_

**信号 vs. 噪声**

Cline 在 2024 年中期发布时，Sonnet 3.5 是当时的顶级模型，那个时候用大量示例和想法填充提示确实有意义。但当 Sonnet 4 系列出现时，这套方法彻底失效了，其他所有 agent 系统也跟着翻车。经过反复测试，我们意识到核心问题：指令太多会产生矛盾，矛盾会产生混乱。

新一代前沿模型——Claude 4、Gemini 2.5 和 GPT-5——在遵循简洁指令方面要强得多。它们不需要长篇大论，它们需要的是最精炼的内容。这就是新的现实。

---

## 结论

多智能体编排、RAG 和塞满内容的提示在纸面上看起来很诱人，但没有一个能在真正的开发工作流中存活下来。真正获胜的 agent 是那些拥抱简单性的：像开发者一样阅读代码，相信模型的能力，让路走得通畅。

虽然整个行业还在追逐架构上的复杂性，但基本面已经发生了根本转变。少即是多，清晰胜过聪明，最好的 AI agent 往往是最简单的。

**— Ara**

_让你的工程团队拥有一个完全协作的 AI 搭档。开源、完全可扩展，专为放大开发者影响力而设计。_

---

> 原文链接：[3 Seductive Traps in Agent Building](https://cline.bot/blog/3-seductive-traps-in-agent-building)  
> 作者：Ara Khan (@arafatkkatze)  
> 发布日期：2025 年 8 月 26 日
