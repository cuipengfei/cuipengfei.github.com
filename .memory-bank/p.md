**Objective:** Your task is to act as a technical writing analyst. Your goal is to analyze all blog posts and update the `writing-style-profile.md` to ensure it comprehensively reflects the author's writing style.

**Core Principles:**

1.  **Additive Only:** You must only add new, observed patterns. Do not remove or modify any existing content in `writing-style-profile.md`.
2.  **Evidence-Based:** Every new pattern added must be directly observable in the blog posts. Do not invent or infer styles.
3.  **Atomic Update:** Do not modify the target `writing-style-profile.md` file until your analysis of _all_ blog posts is complete.

**Workflow:**

1.  **Load Profile:** Read the content of `D:\code\cuipengfei.github.com\.memory-bank\writing-style-profile.md` to understand the currently documented writing styles.

2.  **Locate Posts:** Identify all markdown files (`.md` or `.markdown`) within the `D:\code\cuipengfei.github.com\source\_posts\` directory.

3.  **Analyze and Collect (Iterative Process):**

    - To manage context and ensure thoroughness, analyze the blog posts one by one or in small batches.
    - For each post, identify stylistic patterns based on the principles of stylistics. Pay attention to:
      - **A. Lexical Features (词汇特征):**
        - **Vocabulary Choice:** (e.g., formal vs. informal, abstract vs. concrete, technical jargon, neologisms).
        - **Recurring Phrases:** (e.g., idiomatic expressions, specific collocations).
      - **B. Syntactic Features (句法特征):**
        - **Sentence Structure:** (e.g., length, complexity: simple, compound, complex).
        - **Sentence Type:** (e.g., declarative, interrogative, imperative, exclamatory).
        - **Voice:** (e.g., active vs. passive).
      - **C. Discourse Features (篇章特征):**
        - **Cohesion & Coherence:** (e.g., use of conjunctions, transitions, paragraph structure).
        - **Overall Structure:** (e.g., narrative, descriptive, argumentative, problem-solution).
      - **D. Graphological & Formatting Features (字形与格式特征):**
        - **Punctuation:** (e.g., frequent use of em-dashes, ellipses, etc.).
        - **Text Formatting:** (e.g., use of bolding, italics, lists, blockquotes).
        - **Markdown Conventions:** (e.g., specific ways of formatting headings, links, images).
        - **Code Blocks:** (e.g., inclusion of language identifiers, commenting style within code).
      - **E. Rhetorical Devices (修辞手法):**
        - (e.g., metaphors, similes, analogies, personification, rhetorical questions).
    - Compare these observed patterns against the styles already documented in `writing-style-profile.md`.

4.  **Stage New Patterns:**

    - If you identify a new stylistic pattern not yet documented in the profile, add a clear description of it to a local temporary file (e.g., `style_updates.tmp`).
    - Do not modify `writing-style-profile.md` during this analysis phase.

5.  **Consolidate and Update:**
    - After analyzing all blog posts, review the full list of new patterns you have collected. Remove any duplicates and refine the descriptions for clarity.
    - Append the consolidated, new style patterns to the end of the `D:\code\cuipengfei.github.com\.memory-bank\writing-style-profile.md` file.
