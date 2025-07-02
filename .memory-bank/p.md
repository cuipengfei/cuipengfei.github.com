**Objective:** Act as a technical writing analyst to systematically analyze blog posts and extract writing style patterns. Your task is to process blog posts in batches of 5, extract stylistic patterns, and maintain a comprehensive record of findings.

**Core Principles:**

1.  **Evidence-Based Analysis:** Every pattern identified must be directly observable in the blog posts. Do not invent or infer styles.
2.  **Batch Processing:** Process 5 files at a time using the progress tracking system.
3.  **File Modification Restriction:** Only modify `progress.md` and `style_updates.md` files during this process.
4.  **Additive Documentation:** All findings should be accumulated without removing existing patterns.

**Start/Continue Instructions:**

**Always start by reading `progress.md` to see remaining files and continue from there.**

**Processing Workflow:**

1.  **Select Next Batch:** Take the first 5 file paths from `progress.md`

2.  **Analyze Current Batch:** Read each of the 5 selected blog posts and conduct a comprehensive Chinese stylistic analysis. **IMPORTANT: Do NOT copy original content from blog posts. Only record analytical observations and patterns.**

    **Note:** If a file path has a typo or cannot be found, search for the file in that case only.

    **语音层面 (Phonological Level):**

    - **节奏韵律:** 句子长短搭配、停顿节奏、阅读语感
    - **音韵效果:** 叠音词使用、声调搭配、语音和谐性

    **词汇层面 (Lexical Level):**

    - **语体色彩:** 书面语 vs 口语、文言 vs 白话、正式 vs 非正式
    - **专业术语:** 技术词汇密度、外来词使用、新词创造
    - **语义场:** 主导概念域（技术、情感、抽象概念等）
    - **词语搭配:** 惯用搭配、特色词组、固定表达

    **句法层面 (Syntactic Level):**

    - **句式特点:** 单句 vs 复句比例、长短句搭配
    - **语序模式:** 主谓宾、定状补的使用偏好
    - **句型变化:** 陈述、疑问、感叹、祈使句的分布
    - **语法手段:** 被动句、把字句、存现句等特殊句式

    **语篇层面 (Discourse Level):**

    - **衔接手段:** 连词使用、指代关系、词汇链
    - **信息结构:** 话题-述题组织、已知-未知信息安排
    - **段落组织:** 段落结构、层次安排、论证逻辑
    - **元话语:** 自指语言、读者互动策略

    **语用层面 (Pragmatic Level):**

    - **语体风格:** 正式度、亲和度、权威性
    - **交际功能:** 说明、论证、指导、抒情等主要功能
    - **礼貌策略:** 语气缓和、谦逊表达、断言程度
    - **互文性:** 引用、典故、网络用语等文本间关系

    **修辞层面 (Rhetorical Level):**

    - **修辞手法:** 比喻、拟人、排比、对偶等传统修辞
    - **论证方式:** 举例、对比、类比、因果等逻辑手段
    - **情感表达:** 感叹、强调、反问等情感色彩
    - **说服策略:** 理性论证、情感感染、权威引用

    **表现层面 (Graphological Level):**

    - **标点特色:** 破折号、省略号、感叹号等使用模式
    - **版式设计:** 段落分割、空行使用、视觉层次
    - **格式元素:** 代码块、列表、表格、图表的整合方式
    - **强调方式:** 加粗、斜体、下划线的使用规律

3.  **Document Findings:** For each identified pattern, add it to `style_updates.md` using this format. **CRITICAL: Do NOT include original text from blog posts. Only record your analytical observations.**

    ```
    ## 文体特征: [简要描述]
    **分析层面:** [语音/词汇/句法/语篇/语用/修辞/表现]
    **特征描述:** [具体的文体学观察，不含原文引用]
    **来源文件:** [被分析文章的文件名]
    **出现频率:** [主导特征/频繁出现/偶尔出现/罕见特征]
    **功能作用:** [该特征的交际或文体功能]
    ```

4.  **Update Writing Style Profile:** After processing each batch of 5 files, update the writing style profile. Check if findings are already covered in the existing profile - only add new patterns that are not already documented. The update doesn't need to be long; if nothing new is found, no update is necessary.

5.  **Update Progress:** Remove the 5 processed file paths from the top of `progress.md`

6.  **Continue or Complete:**
    - If `progress.md` still contains file paths, repeat from step 1
    - If `progress.md` is empty, the analysis is complete

**Output Format Requirements:**

- **严禁原文引用:** `style_updates.md` 中不得包含博客原文内容，只记录分析观察结果
- **中文文体学分类:** 使用指定的中文文体学层面标签进行分类
- **功能导向分析:** 说明每个特征的交际目的和文体效果
- **时序记录:** 保持分析的时间顺序
- **来源追溯:** 始终标明来源文件名
- **模式聚焦:** 关注重复出现和独特的文体模式，而非孤立现象
- **简洁表达:** 用学术化但简洁的语言描述文体特征

**Completion Indicator:**

The task is complete when `progress.md` is empty and `style_updates.md` contains all discovered patterns from the blog post analysis, with the writing style profile updated after each batch.
