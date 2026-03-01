---
title: 写了一年 Agent，我做了个教程站
date: 2026-03-01 10:00:00
categories:
  - [AI]
  - [技术思考]
tags:
  - AI Agent
  - Context Engineering
  - 上下文
---

过去一年我写了四篇跟 AI Agent 有关的文章。

[洗脑 Copilot](/blog/2025/06/17/how-to-brainwash-copilot-to-follow-orders/) 讲怎么用指令让 agent 按你的方式工作。

[Agent 陷阱](/blog/2025/08/27/agent-building-seductive-traps/) 讲构建 agent 时那些看起来对实际上坑的模式。

[AI 育儿博主](/blog/2025/12/18/因为懒得看书，我把-AI-变成了育儿博主/) 讲怎么把大量领域知识喂给 AI。

[MCP 启动优化](/blog/2026/01/24/claude-code-mcp-startup-optimization/) 讲逆向工程 Claude Code 的 MCP 初始化流程。

四个完全不同的话题，写的时候也没想过它们之间有什么关系。

但写完 MCP 那篇之后，我发现它们在讲同一件事。

<!-- more -->

## 都是上下文

上下文（Context）。

不是什么玄学概念。就是你的 agent 发给 LLM API 的那个 HTTP request body 里的 JSON：

```json
{
  "messages": [
    {"role": "system", "content": "你是一个...（系统指令）"},
    {"role": "user", "content": "帮我重构这个函数"},
    {"role": "assistant", "content": "好的，我先看看...", "tool_calls": [...]},
    {"role": "tool", "content": "（文件内容、命令输出、搜索结果...）"},
    {"role": "assistant", "content": "根据代码结构，我建议..."}
  ]
}
```

每一轮对话，agent 都把完整的历史重新发送一遍。LLM 没有记忆——它每次都是从头读这个 JSON，然后生成下一条回复。

回头看那四篇文章：

洗脑 Copilot？你操纵的是 `system` 里的指令。它被注入到每个 request 的最前面，是 LLM 看到的第一样东西。

Agent 陷阱？你搭的工具链每调用一次就往 `messages` 里塞一条 `tool` 结果。塞太多，窗口满了，模型就开始丢信息。

育儿博主？你喂的知识在占用 context window。窗口就那么大，选什么喂、不选什么喂，是个决策问题。

MCP 优化？每个 MCP server 启动时注册的 tool definitions 会被塞进 system prompt。工具越多，留给实际对话的空间越少。

全是上下文。

## 所以做了个站

想明白这条线之后，我把它写成了一个教程站。

> **[Adopt Agentic — 采用 Agentic AI 工作流](https://cuipengfei.is-a.dev/adopt-agentic/)**

15 个概念节点，从"上下文是什么"一直讲到"多 Agent 协作"。中英双语。

每个概念都用 HTTP request/response 拆解——因为 agent 和 LLM 之间的交互，归根到底就是 HTTP 调用，没什么神秘的。

但有意思的不是产出。是过程。

这个站本身就是用 agentic workflow 做的。

Agent 写内容，agent 爬了 62 篇社区文章做覆盖度分析，agent 生成 38 个 SVG 动画插图，agent 做五轮交叉审校（甚至用了不同模型交叉校验彼此的盲区）。

人做什么？人写规则。人审结果。人拍板。

过程中最大的收获：**项目的规则文件从零长到了几百行，每一条都是踩坑长出来的。**

举个例子。最开始 agent 写出来的内容充斥着"本质上"、"从根本上说"、"essentially"——这些是 AI 生成文本的高频指纹。我们花了五轮审校才把这些清干净，然后把"AI filler 词密度控制"写成了一条显式规则：同一篇文档里"本质上"不超过两处，超了就删。

再比如。Agent 写技术教程时爱用文艺比喻——"给 Agent 请家教"、"把你的大脑装进 Agent"。读者看标题猜不到这节在讲什么。于是加了一条：标题禁止文艺化，用直白有信息量的表述。

规则不是一开始就有的。它们是在反复的"agent 写 → 人审 → 发现问题 → 加规则 → agent 重写"循环里长出来的。最后发现，人花在"改流程"上的时间远超"改内容"。

用 agentic workflow 来写教 agentic workflow 的教程——这事本身就挺 meta 的。

---

## Agent 是胶水代码，不是 AI

你每天用的那些 coding agent，不管是哪家的，90% 的代码在做这些事：拼 HTTP request、解析 LLM response、管理 tool calls、读写文件、跑 shell 命令。LLM 只是其中一个 API 调用。

理解这个，你对 agent 的期待会回到地面。它不是"更聪明的 AI"，是一个围绕 LLM API 构建的自动化流程。聪明与否取决于这个流程怎么编排上下文。

## 上下文像牛奶，会过期

长对话里的早期信息会被后续内容稀释。这不是 bug，是 attention 机制的物理限制——模型对最近的内容关注度更高，早期内容的影响力会衰减。

所以高手会主动切新会话。不是因为强迫症，是因为一个干净的新会话比一个拖了几十轮的旧会话更可控。上下文不是越多越好，是越相关越好。

## 认知债务

你让 agent 自动化的每个决策，如果你不理解它为什么这样决策，就欠下一笔认知债。短期看效率很高——agent 帮你做了一堆事。长期看，债积累到一定程度，你连 agent 出错都发现不了。

这跟技术债务一个道理。区别是技术债务在代码里，你至少看得见；认知债务在你脑子里，你甚至不知道自己欠了多少。

---

这三个点，加上更多一篇博客展开不了的内容，都在这里：

## **[Adopt Agentic — 采用 Agentic AI 工作流](https://cuipengfei.is-a.dev/adopt-agentic/)**

中文版直接进，英文版点右上角切换。

如果你每天在用 AI coding agent，但偶尔会想"这东西到底是怎么工作的"——这个站试图回答这个问题。
