---
title: "如何驯化 GitHub Copilot，让 AI 生成高质量代码"
date: 2025-06-17 10:00:00
tags:
  - AI
  - GitHub Copilot
  - 软件开发
  - 代码质量
categories:
  - 技术思考
---

## 引子

最近在项目中使用 GitHub Copilot 的过程中，我发现了一个很有趣的现象。

当我让 Copilot 帮我写代码时，它总是热情满满地给出一大段实现。但当我仔细审视这些代码时，却经常会发现各种小问题——缺少错误处理、异常情况没考虑、命名不规范、没有单元测试，有时甚至会出现一些"魔法数字"。

这让我想起了那些刚入行的程序员，技术不错，但是对软件工程的规范和最佳实践还不够了解。他们写出来的代码能跑，但总让资深工程师皱眉头。

这些 AI 工具确实能提高效率，但如何让它们生成出更符合工程实践的高质量代码，成了一个值得思考的问题。

<!-- more -->

## 不是魔法，是系统指令

经过一段时间的研究和实践，我发现 Copilot 这类 AI 工具实际上可以被"训练"或者说"驯化"。

它们并不是魔术盒子，而是遵循一套输入-输出原则的系统。如果我们能给它提供明确的指导和原则，它就能相应地调整自己的输出。

这个思路促使我整理了一个专门用来"驯化" GitHub Copilot 的指令集：[prompts](https://github.com/cuipengfei/prompts)。

这个仓库里不是代码，而是一系列指导 AI 行为的 Markdown 文件。每个文件就像是给 AI 的一份规范或指南，告诉它应该怎样思考和行动。

## 这套指令能解决什么问题？

使用 AI 编程助手时，我们通常会遇到这些问题：

- 生成的代码能运行，但结构混乱，难以维护
- 没有考虑边界情况和异常处理
- 代码风格不一致，命名随意
- 缺乏适当的测试覆盖
- 不遵循项目已有的架构模式

这套指令集就是为了解决这些问题而设计的。它告诉 AI 该如何思考软件设计、如何编写清晰的代码、如何进行测试驱动开发，以及如何分解复杂问题。

## 指令集的构成

整个指令集分为几个主要部分：

1. **核心行为定义**：这部分告诉 AI 应该如何进行思考和工作，包括：

   - 如何保持项目知识的连贯性（memory-bank）
   - 如何有条理地回应用户（response-and-prompt-guidelines）
   - 如何遵循 TDD 工作流（programming-workflow）
   - 如何分解复杂任务（workflow-and-task-splitting）

2. **代码质量规范**：这部分告诉 AI 什么是好代码，什么是坏代码：

   - 代码标准和最佳实践（code-standards）
   - 代码异味和应避免的反模式（avoid-bad-smells）
   - 如何编写有效的测试（testing-guidelines）

3. **流程模板**：这部分提供了从需求到实现的结构化方法：

   - 如何将模糊的想法转化为明确的计划（req）
   - 如何协助业务分析师编写用户故事（ba）

4. **工具使用指南**：这部分包含了一些高级技巧：
   - 如何使用顺序思考解决问题（sequential-thinking）
   - 快捷指令系统（shortcut-system-instruction）

## 如何在实际工作中使用这套指令

经过实践，我发现在 VS Code 中配置 Copilot 使用这些指令非常简单：

1. 打开 VS Code 设置（Ctrl+, 或 Cmd+,）
2. 搜索 `github.copilot.chat.codeGeneration.instructions`
3. 添加指向指令文件的配置，例如：

```jsonc
"github.copilot.chat.codeGeneration.instructions": [
    { "text": "避免生成与公共代码完全匹配的代码" },
    { "file": "../prompts/.github/instructions/req.md" },
    { "file": "../prompts/.github/instructions/ba.md" },
    // 其它指令文件...
    { "file": "../prompts/.github/instructions/shortcut-system-instruction.md" }
]
```

需要注意的是，文件路径要正确。这里的路径是相对于你的 VS Code 设置文件的，通常是工作区中的 `.vscode/settings.json`。如果你的 prompts 仓库和当前项目不在同一位置，可能需要调整路径。

设置完成后，你会发现 Copilot 生成的代码质量明显提升：更规范、更健壮、考虑更周全。

## 一点思考

驯化 AI 工具其实和带新人有些相似 —— 你需要清晰地表达期望，提供良好的指导和范例，然后持续进行纠正和反馈。

这不是一项精确的科学，而是一个持续改进的过程。不同项目可能需要对指令进行一些定制和调整，以适应特定的技术栈和团队风格。

对我来说，这套指令系统的价值不仅在于提升了 AI 工具的输出质量，更在于它促使我思考：什么是好的代码？什么是好的软件工程实践？这些思考反过来又能帮助我们在日常工作中做得更好。

---

如果你也在使用 AI 编程助手，不妨试试这套指令。如果有任何想法或改进建议，欢迎到 [prompts](https://github.com/cuipengfei/prompts) 仓库提交 PR 或 Issue。
