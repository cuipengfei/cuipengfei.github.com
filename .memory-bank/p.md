**Objective:** Act as a technical writing analyst to systematically analyze blog posts and extract writing style patterns. Your task is to process blog posts one by one, extract stylistic patterns, and maintain a comprehensive record of findings.

**Core Principles:**

1.  **Evidence-Based Analysis:** Every pattern identified must be directly observable in the blog posts. Do not invent or infer styles.
2.  **Progressive Processing:** Process one file at a time using the progress tracking system.
3.  **File Modification Restriction:** Only modify `progress.md` and `style_updates.md` files during this process.
4.  **Additive Documentation:** All findings should be accumulated without removing existing patterns.

**Start/Continue Instructions:**

**IF this is your first time running this task:**

1. Check if `D:\code\cuipengfei.github.com\.memory-bank\progress.md` exists
2. If it doesn't exist, create it with a complete list of all `.md` and `.markdown` files from `D:\code\cuipengfei.github.com\source\_posts\` directory
3. If `D:\code\cuipengfei.github.com\.memory-bank\style_updates.md` doesn't exist, create it with a header: "# Writing Style Patterns Discovered"

**IF continuing from previous work:**

1. Read `D:\code\cuipengfei.github.com\.memory-bank\progress.md` to see remaining files
2. Take the first file from the list and proceed with analysis

**Processing Workflow:**

1.  **Select Next File:** Take the first file path from `progress.md`

2.  **Analyze Current Post:** Read the selected blog post and identify stylistic patterns based on:

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

3.  **Document Findings:** For each identified pattern, add it to `style_updates.md` using this format:

    ```
    ## Pattern: [Brief Description]
    **Category:** [A/B/C/D/E based on classification above]
    **Evidence:** [Specific example from the post]
    **Source File:** [Filename of the analyzed post]
    **Frequency:** [How often this pattern appears in the post]
    ```

4.  **Update Progress:** Remove the processed file path from the top of `progress.md`

5.  **Continue or Complete:**
    - If `progress.md` still contains file paths, repeat from step 1
    - If `progress.md` is empty, the analysis is complete

**Output Format Requirements:**

- Each pattern entry in `style_updates.md` must include concrete evidence
- Use the exact category labels (A through E) for consistent classification
- Maintain chronological order of analysis in the updates file
- Always include the source filename for traceability

**Completion Indicator:**

The task is complete when `progress.md` is empty and `style_updates.md` contains all discovered patterns from the blog post analysis.
