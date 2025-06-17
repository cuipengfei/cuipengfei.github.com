---
title: 如何给 GitHub Copilot 洗脑，让它乖乖听话 (How to Brainwash GitHub Copilot to Follow Your Orders)
date: 2025-06-17 10:00:00
tags:
  - AI
  - GitHub Copilot
  - Prompts
  - Software Development
  - Code Quality
categories:
  - 技术思考
---

## 前言 (Foreword)

最近，AI 编程助手如 GitHub Copilot 真是火得一塌糊涂。它们能极大地提升我们的开发效率，但有时候，这些 AI 小伙伴们也会有点“皮”，生成的代码不那么“听话”，甚至有点“放飞自我”，产出一些让人哭笑不得的“代码意大利面”。

你是否也曾对着 Copilot 生成的“神来之笔”摇头叹息？是否也曾希望它能更懂你的心思，写出更规范、更健壮、更符合项目要求的代码？

别急，今天我给大家介绍一个“秘密武器”——一个能让你把 GitHub Copilot (以及其他 AI 助手) “洗脑”成纪律严明、注重质量的代码忍者的指令库：[prompts](https://github.com/cuipengfei/prompts) (请将此替换为您的实际仓库链接)。

这可不是什么黑魔法，而是一套精心设计的系统指令，通过“谆谆教导”，让 AI 助手在编码时能够“循规蹈矩”，产出高质量的代码。

<!-- more -->

## 这个“洗脑手册”是啥玩意儿？ (What's This "Brainwashing Manual" About?)

简单来说，这个 `prompts` 仓库里装的不是代码，而是一系列 Markdown 文件，每个文件都是一条给 AI 的“指令”或“行为准则”。你可以把它们想象成给 AI 的“培训教材”或者“军规”。

目的是啥？就是告别那些“差不多就行”的代码，告别那些“在我机器上能跑”的尴尬。我们要的是 AI 能够像一个经验丰富、训练有素的工程师一样，写出漂亮、易维护、高标准的代码。

## 哪些人需要这份“洗脑手册”？ (Who Needs This Brainwashing Manual?)

我觉得，以下几类同学可能会对这个仓库非常感兴趣：

- **厌倦了 AI 生成“代码意大利面”的开发者**：希望你的 AI 助手能真正帮你，而不是给你添乱。
- **希望在团队中引入 AI，但又担心代码库变成“数字犯罪现场”的团队**：规范是第一生产力！
- **对 AI 生成代码的可靠性和可维护性有执念的研究者和工程师**：追求卓越，AI 也不能例外。
- **希望 AI 助手能准确理解需求，而不是“脑补”功能的业务分析师 (BA) 和产品负责人 (PO)**：沟通，从精确的指令开始。

## “指令库”里都有啥？ (The Instruction Arsenal)

这个仓库里的指令覆盖了 AI 行为的方方面面，就像一个小型“AI 行为改造中心”：

- **核心行为定义 (Core Behavior Definition)**：塑造 AI 思考和运作的基本原则。
- **标准与质量规范 (Standards & Quality Specs)**：一套铁律，防止 AI 写出让资深工程师看了想哭的代码。
- **流程模板 (Process Templates)**：将模糊的想法转化为坚不可摧的实施方案的工作流。
- **工具使用指南 (Tool Usage Guides)**：更聪明地使用 AI 的高级技巧，而不是更费力。

具体来说，在 `[.github/instructions/](.github/instructions/)` 目录下，你会找到这些“调教”AI 的法宝：

- `overview.md`: AI 编码小黄人的总体规划。
- `memory-bank.instructions.md`: 给 AI 装上持久化“大脑”，对抗数字失忆症。
- `response-and-prompt-guidelines.md`: 要求 AI 专业沟通，而不是陷入聊天机器人的存在危机。核心是那个神圣的“八段式回应结构”。
- `programming-workflow.md`: TDD (测试驱动开发) 的福音，防止 AI 进行牛仔式灾难编码。
- `workflow-and-task-splitting.md`: 教 AI 如何分解复杂问题而不会崩溃 (MECE 原则万岁)。
- `code-standards.md`: 清洁代码的神圣戒律 (SOLID, DRY 等)。
- `avoid-bad-smells.md`: 代码异味和反模式的黑名单。你的 AI 将学会像躲避放射性废物一样避开它们。
- `testing-guidelines.md`: 如何编写真正能测试东西的测试。覆盖所有基础，这样你的代码就不会在生产环境中爆炸。
- `req.md`: 将模糊的想法一步步转化为具体的计划，每次一个 Markdown 段落。专为希望 AI *理解*需求的 BA 和 PM 设计。
- `ba.md`: 为业务分析师量身定制的 AI 辅助用户故事编写流程。从史诗到故事，BA 认可的精度。
- `sequential-thinking.md`: 为 `sequentialthinking` MCP 工具解锁动态问题解决能力。
- `shortcut-system-instruction.md`: 战术效率的命令快捷方式。`r!`, `d!`, `t!` – 启动！

## 如何使用这些“指令”？ (How to Use This Stuff?)

使用方法其实很简单：

1.  **集成 (Integrate)**：将这些指令喂给你的 AI 代理 (比如 GitHub Copilot, 自定义的 GPT 等)。通常是通过自定义系统提示 (custom system prompts) 或知识库注入 (knowledge base feeds) 的方式。
2.  **观察 (Observe)**：见证你的 AI 从一个狂野的代码投掷野兽，转变成一个专注、注重质量的合作伙伴。
3.  **改进 (Refine)**：这些是活的文档。根据你项目的具体“神经质”进行调整。

## 如何在 VS Code 中正确“洗脑” GitHub Copilot (重点来了！)

想在 VS Code 里直接给 Copilot “洗脑”？姿势要正确：

1.  打开 VS Code 设置 (快捷键 `Ctrl+,` 或 `Cmd+,`)。
2.  搜索 `github.copilot.chat.codeGeneration.instructions`。
3.  将你的指令添加为一个对象数组，每个对象包含 `text` 或 `file` 属性。例如：

    ```jsonc
    "github.copilot.chat.codeGeneration.instructions": [
        { "text": "Avoid generating code that matches public code exactly." },
        { "file": "../prompts/.github/instructions/req.md" },
        { "file": "../prompts/.github/instructions/ba.md" },
        // ... 其他指令文件
        { "file": "../prompts/.github/instructions/shortcut-system-instruction.md" }
    ]
    ```

    **注意路径！** 确保这里的 `../prompts/` 路径相对于你的 VS Code 设置文件 (通常是 `.vscode/settings.json`) 是正确的，能够准确指向 `prompts` 仓库中的指令文件。如果你的 `prompts` 仓库和你的项目不在同一个父目录下，你可能需要使用绝对路径或者调整相对路径。

4.  保存设置，如果需要，重启 Copilot Chat。
5.  然后，就等着看 Copilot 是不是更“听话”了吧 (至少它会努力尝试的)。

**专业提示**：使用相对路径指向你的指令文件，或者添加自定义文本规则。指令越具体，“洗脑”效果越强。

## 免责声明 (Disclaimer)

“洗脑”AI 是一门艺术，而非精密科学。结果可能因 AI 而异。可能会导致你的 AI 产生优越感。请负责任地使用。

---

希望这份“洗脑手册”能帮助大家更好地与 AI 协作，共同创造出更美好的数字世界！如果你有任何好的想法或者改进建议，欢迎来 [prompts](https://github.com/cuipengfei/prompts) 仓库提 PR 或者 Issue！
