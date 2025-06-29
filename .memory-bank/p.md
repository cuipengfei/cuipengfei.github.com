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
    - For each post, identify stylistic patterns. Pay attention to:
      - **Tone and Voice:** (e.g., formal, informal, humorous, technical)
      - **Common Phrases & Vocabulary:** (e.g., recurring expressions, specific technical jargon)
      - **Sentence Structure:** (e.g., preference for short, declarative sentences or complex, compound sentences)
      - **Formatting:** (e.g., use of bolding, italics, lists, blockquotes)
      - **Code Blocks:** (e.g., inclusion of language identifiers, comments within code)
      - **Markdown Usage:** (e.g., how headings, links, and images are formatted)
    - Compare these observed patterns against the styles already documented in `writing-style-profile.md`.

4.  **Stage New Patterns:**

    - If you identify a new stylistic pattern not yet documented in the profile, add a clear description of it to a local temporary file (e.g., `style_updates.tmp`).
    - Do not modify `writing-style-profile.md` during this analysis phase.

5.  **Consolidate and Update:**
    - After analyzing all blog posts, review the full list of new patterns you have collected. Remove any duplicates and refine the descriptions for clarity.
    - Append the consolidated, new style patterns to the end of the `D:\code\cuipengfei.github.com\.memory-bank\writing-style-profile.md` file.
