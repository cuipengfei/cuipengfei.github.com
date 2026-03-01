# 重写 writing-style-profile.md：从泛化教科书到实证风格档案

## TL;DR

> **Quick Summary**: 对 184 篇博客（2008-2026）进行全量文体学扫描，提取贯穿始终的写作共性特征，用 voice-analysis + style-modeler 融合框架重写 `.memory-bank/writing-style-profile.md`，使其成为一份 AI 可执行的个人写作风格指南。
>
> **Deliverables**:
>
> - 重写后的 `.memory-bank/writing-style-profile.md`
> - 基于 184 篇博客的实证分析，非凭空编造
>
> **Estimated Effort**: Medium
> **Parallel Execution**: YES - 4 waves
> **Critical Path**: Task 1 → Task 2-7 → Task 8-9 → Task 10-11

---

## Context

### Original Request

用户认为当前的 `writing-style-profile.md` 写得不好——它是一份泛化的"技术博客写作通用指南"，而非基于实际文章的个人风格档案。要求用文体学方法论（voice-analysis + style-modeler 技能）对所有 184 篇博客进行分析后重写。注意：文章分布在 `.md` 和 `.markdown` 两种扩展名下（127 篇 .md + 57 篇 .markdown），`source/_posts/verify.md` 是 Folo RSS 验证文件，非博客文章，应排除在分析范围外。

### Interview Summary

**Key Discussions**:

- **目标受众**: 给 AI 用 → 输出可执行规则和模板，让 AI 精准模仿风格
- **分析范围**: 全量 184 篇扫描（127 篇 .md + 57 篇 .markdown，排除 verify.md），只取贯穿始终的共性（不追踪演变）
- **框架选择**: 融合 voice-analysis（8阶段）+ style-modeler（15维度）自创框架

**Research Findings**:

- 当前文件 334 行，零实证基础，量化指标编造
- 博客跨 2008-2026，从 C# 设计模式到 AI Agent
- 抽样发现的招牌动作：军事类比、口语化转折、递进式质疑、生活切入技术、译者注模式、自嘲式幽默

---

## Work Objectives

### Core Objective

用系统化文体学分析方法，从 184 篇博客中提取作者真实的写作风格特征，生成一份 AI 可执行的个人写作风格档案。

### Concrete Deliverables

- `.memory-bank/writing-style-profile.md`（重写）

### Definition of Done

- [x] 新文件中每个特征声明都有原文摘录佐证（轻量引用关键句，不大段复制）
- [x] 包含招牌动作 + 原文示范（有多少写多少，不设数量限制）
- [x] 包含词汇指纹（高频词、禁用词）
- [x] 包含段落模板（有多少写多少，不设数量限制）
- [x] 包含禁忌清单
- [x] AI 使用此 profile 写出的文本能让作者认可“像我写的”

### Must Have

- 基于实际文章的实证分析（非编造）
- 可执行的写作规则（非学术描述）
- 原文摘录作为每个特征的佐证（轻量引用关键句）
- 招牌动作、词汇指纹、段落模板、禁忌清单

### Must NOT Have (Guardrails)

- ❌ 编造的量化指标（如"术语密度 15-25 个/百字"）
- ❌ 学术黑话（如"概念操作化"、"语义场建构"）
- ❌ 泛化的"技术博客通用建议"
- ❌ AI 味重的分类体系
- ❌ 追踪风格演变（用户明确只要共性）

---

## Verification Strategy

> **ZERO HUMAN INTERVENTION** — ALL verification is agent-executed.

### Test Decision

- **Infrastructure exists**: N/A（文档重写任务）
- **Automated tests**: None
- **Framework**: None

### QA Policy

每个分析任务的输出会作为下一阶段的输入。最终验证通过对比测试完成：用新 profile 指导 AI 写一段文字，与原文盲比。

---

## Execution Strategy

### Parallel Execution Waves

```
Wave 1 (Start Immediately — 元数据收集 + 分批扫描):
├── Task 1: 元数据统计与按内容量均衡分组 [quick]
├── Task 2: 批次A 文体学扫描（按内容量均衡分组，~101K字节） [deep]
├── Task 3: 批次B 文体学扫描（~101K字节） [deep]
├── Task 4: 批次C 文体学扫描（~101K字节） [deep]
├── Task 5: 批次D 文体学扫描（~101K字节） [deep]
├── Task 6: 批次E 文体学扫描（~101K字节） [deep]
└── Task 7: 批次F 文体学扫描（~101K字节） [deep]

Wave 2 (After Wave 1 — 汇总 + 深度分析):
├── Task 8: 汇总6份报告，提取高频共性特征 (depends: 1-7) [deep]
└── Task 9: 深度分析10-15篇代表作（逐段拆解） (depends: 8) [deep]

Wave 3 (After Wave 2 — 撰写输出):
└── Task 10: 用融合框架撰写新 writing-style-profile.md (depends: 8, 9) [deep]

Wave 4 (After Wave 3 — 验证):
└── Task 11: 验证——用新 profile 写测试段落并对比 (depends: 10) [deep]
```

### Dependency Matrix

| Task | Blocked By | Blocks |
| ---- | ---------- | ------ |
| 1    | —          | 2-7    |
| 2-7  | 1          | 8      |
| 8    | 2-7        | 9, 10  |
| 9    | 8          | 10     |
| 10   | 8, 9       | 11     |
| 11   | 10         | —      |

### Agent Dispatch Summary

- **Wave 1**: 7 tasks — T1 → `quick`, T2-T7 → `deep`
- **Wave 2**: 2 tasks — T8 → `deep`, T9 → `deep`
- **Wave 3**: 1 task — T10 → `deep`
- **Wave 4**: 1 task — T11 → `deep`

---

## TODOs

- [x] 1. 元数据统计与按内容量均衡分组

  **What to do**:
  - 扫描 `source/_posts/` 下所有 `.md` 和 `.markdown` 文件，排除 `verify.md`（Folo RSS 验证文件，非博客）
  - 统计每篇的：文件名、标题、日期、tags、categories、字数
  - 按内容量（字节数）均衡分成 6 组，每组目标约 151K 字节（总量 ~905K / 6），而非简单按篇数均分——大文件多的组篇数少，小文件多的组篇数多，确保每个扫描 agent 的实际阅读工作量相当
  - 每个分组中的文章列表格式为 `- source/_posts/YYYY-MM-DD-slug.md`（或 `.markdown`），方便后续 T2-T7 覆盖率校验
  - 保存到 `.sisyphus/evidence/task-1-metadata.md`

  **Must NOT do**:
  - 不读取文章正文（只提取 front matter 和基本统计）
  - 不做任何风格分析

  **Recommended Agent Profile**:
  - **Category**: `quick`
  - **Skills**: []

  **Parallelization**:
  - **Can Run In Parallel**: NO
  - **Blocks**: Tasks 2-7
  - **Blocked By**: None

  **References**:
  - `source/_posts/` — 博客文章目录，命名格式 `YYYY-MM-DD-slug.md`

  **Acceptance Criteria**:
  - [ ] 统计了所有文章的元数据
  - [ ] 按内容量（字节数）均衡输出 6 个分组（每组约 101K 字节），而非按篇数均分
  - [ ] 保存到 `.sisyphus/evidence/task-1-metadata.md`

  **QA Scenarios**:

  ```
  Scenario: 元数据完整性
    Tool: Bash
    Steps:
      1. wc -l .sisyphus/evidence/task-1-metadata.md → 非空
      2. grep -c 'source/_posts/' .sisyphus/evidence/task-1-metadata.md → ≥ 184
    Expected Result: 184 篇全覆盖（排除 verify.md），6 组各约 151K 字节（允许 ±15% 偏差）
    Evidence: .sisyphus/evidence/task-1-metadata.md
  ```

  **Commit**: NO

- [x] 2. 批次A 文体学扫描（按内容量分组，~101K字节）

  **What to do**:
  - 报告开头必须包含「已扫描文章清单」，逐一列出每篇文章的文件名，格式为 `- [x] source/_posts/YYYY-MM-DD-slug.md`（打勾表示已读）
  - 读取 Task 1 输出的批次A 文件列表
  - 逐篇阅读每篇文章的完整正文，提取以下文体学特征：
    - **词汇层**: 高频动词、口语化连接词、程度副词、中英混用模式
    - **句式层**: 典型句式、长短句比例感受、标点习惯
    - **修辞层**: 类比来源域、反问频率、排比使用
    - **结构层**: 开头模式、段落过渡方式、结尾模式
    - **互动层**: 读者称呼、设问策略、自嘲/幽默
    - **招牌动作**: 最具辨识度的表达（摘录原文）
  - 输出汇总报告到 `.sisyphus/evidence/task-2-batch-a.md`

  **Must NOT do**:
  - 不写泛化的“技术博客应该怎样”的建议
  - 不编造量化指标——只描述观察到的模式
  - 不用学术黑话

  **Recommended Agent Profile**:
  - **Category**: `deep`
  - **Skills**: [`voice-analysis`, `style-modeler`]
    - `voice-analysis`: 提供系统化文体分析框架
    - `style-modeler`: 提供 15 维度解构清单确保分析全面

  **Parallelization**:
  - **Can Run In Parallel**: YES
  - **Parallel Group**: Wave 1 (with Tasks 3-7)
  - **Blocks**: Task 8
  - **Blocked By**: Task 1

  **References**:
  - `.sisyphus/evidence/task-1-metadata.md` — 批次A 文件列表
  - `source/_posts/` — 实际文章文件
  - voice-analysis 技能: 语言分析、词汇指纹、概忻DNA、情感质地等分析框架
  - style-modeler 技能: 15 维度解构清单

  **Acceptance Criteria**:
  - [ ] 报告开头包含「已扫描文章清单」，每篇文章文件名都列出
  - [ ] 清单中的文章数量与 Task 1 分组一致（非偏发）
  - [ ] 报告覆盖批次A 所有文章
  - [ ] 每个文体学维度都有观察结果
  - [ ] 包含至少 10 处原文摘录
  - [ ] 保存到 `.sisyphus/evidence/task-2-batch-a.md`

  **QA Scenarios**:

  ```
  Scenario: 扫描报告质量 + 覆盖率验证
    Tool: Bash
    Steps:
      1. test -f .sisyphus/evidence/task-2-batch-a.md → 文件存在
      2. wc -l .sisyphus/evidence/task-2-batch-a.md → > 100 行
      3. grep -c '\[x\] source/_posts/' .sisyphus/evidence/task-2-batch-a.md → 等于 Task 1 分组中批次A 的文章数
    Expected Result: 报告完整，已扫描清单与 Task 1 分组一致，无遗漏
    Evidence: .sisyphus/evidence/task-2-batch-a.md
  ```

  **Commit**: NO

- [x] 3. 批次B 文体学扫描（~101K字节）
     **What to do**: 与 Task 2 相同流程，针对批次B。输出到 `.sisyphus/evidence/task-3-batch-b.md`
     **Must NOT do**: 同 Task 2
     **Recommended Agent Profile**: Category `deep`, Skills [`voice-analysis`, `style-modeler`]
     **Parallelization**: Wave 1, Blocks Task 8, Blocked By Task 1
     **Acceptance Criteria**: 同 Task 2，输出路径 `.sisyphus/evidence/task-3-batch-b.md`
     **QA Scenarios**: 同 Task 2，路径替换
     **Commit**: NO

- [x] 4. 批次C 文体学扫描（~101K字节）
     **What to do**: 与 Task 2 相同流程，针对批次C。输出到 `.sisyphus/evidence/task-4-batch-c.md`
     **Must NOT do**: 同 Task 2
     **Recommended Agent Profile**: Category `deep`, Skills [`voice-analysis`, `style-modeler`]
     **Parallelization**: Wave 1, Blocks Task 8, Blocked By Task 1
     **Acceptance Criteria**: 同 Task 2，输出路径 `.sisyphus/evidence/task-4-batch-c.md`
     **QA Scenarios**: 同 Task 2，路径替换
     **Commit**: NO

- [x] 5. 批次D 文体学扫描（~101K字节）
     **What to do**: 与 Task 2 相同流程，针对批次D。输出到 `.sisyphus/evidence/task-5-batch-d.md`
     **Must NOT do**: 同 Task 2
     **Recommended Agent Profile**: Category `deep`, Skills [`voice-analysis`, `style-modeler`]
     **Parallelization**: Wave 1, Blocks Task 8, Blocked By Task 1
     **Acceptance Criteria**: 同 Task 2，输出路径 `.sisyphus/evidence/task-5-batch-d.md`
     **QA Scenarios**: 同 Task 2，路径替换
     **Commit**: NO

- [x] 6. 批次E 文体学扫描（~101K字节）
     **What to do**: 与 Task 2 相同流程，针对批次E。输出到 `.sisyphus/evidence/task-6-batch-e.md`
     **Must NOT do**: 同 Task 2
     **Recommended Agent Profile**: Category `deep`, Skills [`voice-analysis`, `style-modeler`]
     **Parallelization**: Wave 1, Blocks Task 8, Blocked By Task 1
     **Acceptance Criteria**: 同 Task 2，输出路径 `.sisyphus/evidence/task-6-batch-e.md`
     **QA Scenarios**: 同 Task 2，路径替换
     **Commit**: NO

- [x] 7. 批次F 文体学扫描（~101K字节）
     **What to do**: 与 Task 2 相同流程，针对批次F（剩余文章）。输出到 `.sisyphus/evidence/task-7-batch-f.md`
     **Must NOT do**: 同 Task 2
     **Recommended Agent Profile**: Category `deep`, Skills [`voice-analysis`, `style-modeler`]
     **Parallelization**: Wave 1, Blocks Task 8, Blocked By Task 1
     **Acceptance Criteria**: 同 Task 2，输出路径 `.sisyphus/evidence/task-7-batch-f.md`
     **QA Scenarios**: 同 Task 2，路径替换
     **Commit**: NO

---

- [x] 8. 汇总6份报告，提取高频共性特征

  **What to do**:
  - **前置覆盖率校验（必须先做）**：从 6 份扫描报告中提取所有「已扫描文章清单」中的文件名，与 Task 1 的 184 篇全量列表对比，找出遗漏的文章。如有遗漏，在报告开头标红并列出遗漏清单
  - 读取 Tasks 2-7 的 6 份扫描报告
  - 交叉比对，找出在 **≥3 个批次**中反复出现的共性特征（仅出现在1-2个批次的不算共性）
  - 按维度汇总：词汇指纹、句式节奏、修辞手法、结构模式、互动设计、招牌动作
  - 标注每个特征的出现频次和跨批次覆盖率
  - 输出到 `.sisyphus/evidence/task-8-synthesis.md`

  **Must NOT do**:
  - 不收录只在 1-2 个批次出现的特征（非共性）——注意 2008 年文章占 73%，防止早期短文风格压倒近期风格，特征需在多个时期出现才算共性
  - 不编造量化指标

  **Recommended Agent Profile**:
  - **Category**: `deep`
  - **Skills**: [`voice-analysis`, `style-modeler`]

  **Parallelization**:
  - **Blocks**: Tasks 9, 10
  - **Blocked By**: Tasks 2-7

  **References**:
  - `.sisyphus/evidence/task-2-batch-a.md` 到 `task-7-batch-f.md`

  **Acceptance Criteria**:
  - [ ] 前置覆盖率校验通过：6 份报告合计覆盖 184 篇文章，0 篇遗漏
  - [ ] 每个共性特征标注了跨批次覆盖率
  - [ ] 包含原文摘录佐证
  - [ ] 保存到 `.sisyphus/evidence/task-8-synthesis.md`

  **QA Scenarios**:

  ```
  Scenario: 覆盖率校验 + 汇总完整性
    Tool: Bash
    Steps:
      1. test -f .sisyphus/evidence/task-8-synthesis.md
      2. wc -l .sisyphus/evidence/task-8-synthesis.md → > 50 行
      3. grep -c '覆盖率校验' .sisyphus/evidence/task-8-synthesis.md → ≥ 1（确认校验段存在）
      4. grep '遗漏' .sisyphus/evidence/task-8-synthesis.md → 应为空或显示 0 篇遗漏
    Expected Result: 覆盖率 184/184，汇总报告存在且内容充实
    Evidence: .sisyphus/evidence/task-8-synthesis.md
  ```

  **Commit**: NO

- [x] 9. 深度分析 10-15 篇代表作

  **What to do**:
  - 根据 Task 8 的汇总，选取 10-15 篇最能体现共性特征的代表作
  - 逐段拆解每篇，提取可复用的段落模板
  - 记录招牌动作的具体原文 + 上下文
  - 提取禁忌清单（作者从不用的表达）
  - 输出到 `.sisyphus/evidence/task-9-deep-analysis.md`

  **Must NOT do**:
  - 不写文学赏析，只提取可执行的写作规则

  **Recommended Agent Profile**:
  - **Category**: `deep`
  - **Skills**: [`voice-analysis`, `style-modeler`]

  **Parallelization**:
  - **Blocks**: Task 10
  - **Blocked By**: Task 8

  **References**:
  - `.sisyphus/evidence/task-8-synthesis.md` — 共性特征汇总
  - `source/_posts/` — 代表作原文

  **Acceptance Criteria**:
  - [ ] 段落模板（含原文，有多少写多少）
  - [ ] 招牌动作 + 原文示范（有多少写多少）
  - [ ] 禁忌清单
  - [ ] 保存到 `.sisyphus/evidence/task-9-deep-analysis.md`

  **QA Scenarios**:

  ```
  Scenario: 深度分析质量
    Tool: Bash
    Steps:
      1. test -f .sisyphus/evidence/task-9-deep-analysis.md
      2. grep -c '招牌动作' .sisyphus/evidence/task-9-deep-analysis.md → ≥ 1
    Expected Result: 包含充分的模板和招牌动作
    Evidence: .sisyphus/evidence/task-9-deep-analysis.md
  ```

  **Commit**: NO

- [x] 10. 撰写新的 writing-style-profile.md

  **What to do**:
  - 读取 Task 8 汇总 + Task 9 深度分析
  - 用融合框架撰写新的 `.memory-bank/writing-style-profile.md`
  - 框架结构融合 voice-analysis + style-modeler：
    1. 作者画像与核心人格
    2. 思维内核与论证逻辑
    3. 词汇指纹（高频词 + 禁用词）
    4. 句式与节奏
    5. 修辞手法与概念DNA
    6. 开头与结尾配方
    7. 段落过渡模式
    8. 读者互动设计
    9. 情感质地与语调
    10. 招牌动作（含原文示范）
    11. 段落模板库（含原文）
    12. 禁忌清单
    13. AI 写作用指南（可执行规则摘要）
  - 每个特征必须有原文摘录佐证

  **Must NOT do**:
  - 不编造量化指标
  - 不用学术黑话
  - 不写泛化建议
  - 不追踪风格演变（只写共性）

  **Recommended Agent Profile**:
  - **Category**: `deep`
  - **Skills**: [`voice-analysis`, `style-modeler`, `beautiful-prose`]

  **Parallelization**:
  - **Blocks**: Task 11
  - **Blocked By**: Tasks 8, 9

  **References**:
  - `.sisyphus/evidence/task-8-synthesis.md`
  - `.sisyphus/evidence/task-9-deep-analysis.md`
  - `.memory-bank/writing-style-profile.md` — 要被替换的旧文件

  **Acceptance Criteria**:
  - [ ] 新文件包含上述 13 个维度
  - [ ] 每个维度有原文摘录佐证
  - [ ] 招牌动作（有多少写多少）
  - [ ] 段落模板（有多少写多少）
  - [ ] 禁忌清单完整
  - [ ] 无编造指标、无学术黑话

  **QA Scenarios**:

  ```
  Scenario: 新 profile 完整性
    Tool: Bash
    Steps:
      1. test -f .memory-bank/writing-style-profile.md
      2. grep -c '招牌动作' .memory-bank/writing-style-profile.md → ≥ 1
      3. grep -c '禁忌' .memory-bank/writing-style-profile.md → ≥ 1
      4. grep -c '模板' .memory-bank/writing-style-profile.md → ≥ 1
      5. grep -cE '(原文|摘录|「|『|“)' .memory-bank/writing-style-profile.md → ≥ 10
    Expected Result: 文件存在且包含所有关键维度
    Evidence: .memory-bank/writing-style-profile.md
  ```

  **Commit**: YES
  - Message: `docs: rewrite writing-style-profile based on empirical analysis of 184 posts`
  - Files: `.memory-bank/writing-style-profile.md`

- [x] 11. 验证——用新 profile 写测试段落并对比

  **What to do**:
  - 读取新的 `.memory-bank/writing-style-profile.md`
  - 以其为指南，写 2-3 段测试文字（主题自选）
  - 随机抽取 2-3 篇原文的对应段落
  - 逐项对比：招牌动作是否出现、词汇风格是否一致、禁忌是否规避
  - 输出对比报告到 `.sisyphus/evidence/task-11-validation.md`
  - 如有明显偏差，列出具体问题和建议修正

  **Must NOT do**:
  - 不自行修改 profile 文件（只出报告）

  **Recommended Agent Profile**:
  - **Category**: `deep`
  - **Skills**: [`voice-analysis`, `style-modeler`]

  **Parallelization**:
  - **Blocks**: None
  - **Blocked By**: Task 10

  **References**:
  - `.memory-bank/writing-style-profile.md` — 新写的风格档案
  - `source/_posts/` — 原文对比材料

  **Acceptance Criteria**:
  - [ ] 生成了 2-3 段测试文字
  - [ ] 与原文逐项对比
  - [ ] 输出对比报告

  **QA Scenarios**:

  ```
  Scenario: 验证报告存在
    Tool: Bash
    Steps:
      1. test -f .sisyphus/evidence/task-11-validation.md
    Expected Result: 报告存在
    Evidence: .sisyphus/evidence/task-11-validation.md
  ```

  **Commit**: NO

## Final Verification Wave

- [x] F1. **Plan Compliance Audit** — `deep`
      读取最终的 writing-style-profile.md，检查：每个特征声明是否都有原文摘录？是否包含招牌动作、词汇指纹、段落模板、禁忌清单？是否有编造指标或学术黑话？
      Output: `Must Have [N/N] | Must NOT Have [N/N] | VERDICT`

---

## Commit Strategy

- **Task 10**: `docs: rewrite writing-style-profile based on empirical analysis of 184 posts` — `.memory-bank/writing-style-profile.md`

---

## Success Criteria

### Final Checklist

- [x] 新 profile 基于 184 篇实际文章分析（排除 verify.md）
- [x] 每个特征都有原文摘录佐证
- [x] 招牌动作 + 原文示范（有多少写多少）
- [x] 词汇指纹完整（高频词 + 禁用词）
- [x] 段落模板（有多少写多少）
- [x] 禁忌清单完整
- [x] 无编造量化指标
- [x] 无学术黑话
- [x] AI 用此 profile 写出的文字风格一致
