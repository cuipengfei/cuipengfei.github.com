<!-- OPENSPEC:START -->
# OpenSpec Instructions

These instructions are for AI assistants working in this project.

Always open `@/openspec/AGENTS.md` when the request:
- Mentions planning or proposals (words like proposal, spec, change, plan)
- Introduces new capabilities, breaking changes, architecture shifts, or big performance/security work
- Sounds ambiguous and you need the authoritative spec before coding

Use `@/openspec/AGENTS.md` to learn:
- How to create and apply change proposals
- Spec format and conventions
- Project structure and guidelines

Keep this managed block so 'openspec update' can refresh the instructions.

<!-- OPENSPEC:END -->

# Repository Guidelines

本仓库当前采用 Hexo + NexT（legacy Octopress/Jekyll 仅保留参考，不再主动维护）。目标：最小变更、可重复构建、内容与写作风格一致。修改前请先阅读；字数控制以便快速记忆。

## Project Structure
- Core: `_config.yml` (站点)，`_config.next.yml` / `themes/next-old/_config.yml` (主题)。
- Optional override: `source/_data/next.yml` / `source/_data/*.swig|*.styl`。
- Content: `source/_posts/`, `source/_drafts/`(草稿), `source/images/`, `source/javascripts/`, `source/assets/`。
- Slides: 文章 front matter `slidehtml: true` 生成 Reveal.js 页面 `.../slide.html`。
- Output (只读): `public/` (生成)，`.deploy_git/` (部署缓存)。

## Build & Dev (Bun Only)
```bash
bun install          # 安装依赖（唯一包管理器）
bun run clean        # hexo clean
bun run build        # hexo generate
bun run server       # 预览 (http://localhost:4000)
bun run deploy       # 构建+部署 (hexo-deployer-git)
```
推荐在 package.json 添加脚本:
```json
"scripts": {
  "new:post": "hexo new post"
}
```
然后统一使用 `bun run new:post "Title"` 创建文章。禁止使用 `npm` / `yarn` / `pnpm` / `npx` / 直接调用 `hexo` 二进制（保持环境一致）。变更主题/配置后先 `bun run clean` 再 build。

## Coding & Naming
- Post 文件: `YYYY-MM-DD-slug.md`（slug 小写短横线）。
- 必需 front matter: `title`, `date`, `categories`；常用：`tags`, `updated`, `description`, `slidehtml`。
- 摘要分隔: `<!-- more -->`；代码：fenced ```lang。
- 自定义样式 / 变量优先放 `_data/`，避免直接修改主题核心文件以便升级。

## Content & Quality
- 参考写作风格: `.memory-bank/writing-style-profile.md`。
- 中文主文 + English 技术术语原文；避免绝对化语气。
- 鼓励提出问题/多视角：适度使用 “从…角度 / 另一方面”。
- 图片置于 `source/images/<post-slug>/`，引用相对路径；必要时优化尺寸。
- Slides: 分节 `---`（横向）/ `--`（纵向）。

## Commit & PR
- 格式: `<type>: <summary>`（feat|fix|docs|refactor|style|perf|chore|build|ci）。
- PR 模板：背景 / 变更点 / 验证步骤（含 `bun run build`）/ 影响 / 截图(样式或 UI)。
- 关联 Issue: `Closes #id`；保持单一主题。

## Configuration & Theme
- 主题改动优先通过 `source/_data/next.yml` 与 custom_file_path；仅在无法覆盖时再改主题文件。
- 性能：保持 `cache.enable=true`；配置大改后执行 clean。
- 部署前检查 `deploy` 配置与站点 URL。

## Security & Agent Rules
- 不提交 Secrets / Token；使用 `.env`（不入库）。
- 升级依赖分离 PR，说明可能 breaking。
- 禁止直接编辑 `public/`、`.deploy_git/`；最小 diff；新增结构需同步更新本文件。
- 未获指令不执行 commit/push。

## Issue Tracking with bd (beads)

**IMPORTANT**: This project uses **bd (beads)** for ALL issue tracking. Do NOT use markdown TODOs, task lists, or other tracking methods.

### Why bd?

- Dependency-aware: Track blockers and relationships between issues
- Git-friendly: Auto-syncs to JSONL for version control
- Agent-optimized: JSON output, ready work detection, discovered-from links
- Prevents duplicate tracking systems and confusion

### Quick Start

**Check for ready work:**
```bash
bd ready --json
```

**Create new issues:**
```bash
bd create "Issue title" -t bug|feature|task -p 0-4 --json
bd create "Issue title" -p 1 --deps discovered-from:bd-123 --json
bd create "Subtask" --parent <epic-id> --json  # Hierarchical subtask (gets ID like epic-id.1)
```

**Claim and update:**
```bash
bd update bd-42 --status in_progress --json
bd update bd-42 --priority 1 --json
```

**Complete work:**
```bash
bd close bd-42 --reason "Completed" --json
```

### Issue Types

- `bug` - Something broken
- `feature` - New functionality
- `task` - Work item (tests, docs, refactoring)
- `epic` - Large feature with subtasks
- `chore` - Maintenance (dependencies, tooling)

### Priorities

- `0` - Critical (security, data loss, broken builds)
- `1` - High (major features, important bugs)
- `2` - Medium (default, nice-to-have)
- `3` - Low (polish, optimization)
- `4` - Backlog (future ideas)

### Workflow for AI Agents

1. **Check ready work**: `bd ready` shows unblocked issues
2. **Claim your task**: `bd update <id> --status in_progress`
3. **Work on it**: Implement, test, document
4. **Discover new work?** Create linked issue:
   - `bd create "Found bug" -p 1 --deps discovered-from:<parent-id>`
5. **Complete**: `bd close <id> --reason "Done"`
6. **Commit together**: Always commit the `.beads/issues.jsonl` file together with the code changes so issue state stays in sync with code state

### Writing Self-Contained Issues

Issues must be fully self-contained - readable without any external context (plans, chat history, etc.). A future session should understand the issue completely from its description alone.

**Required elements:**
- **Summary**: What and why in 1-2 sentences
- **Files to modify**: Exact paths (with line numbers if relevant)
- **Implementation steps**: Numbered, specific actions
- **Example**: Show before → after transformation when applicable

**Optional but helpful:**
- Edge cases or gotchas to watch for
- Test references (point to test files or test_data examples)
- Dependencies on other issues

**Bad example:**
```
Implement the refactoring from the plan
```

**Good example:**
```
Add timeout parameter to fetchUser() in src/api/users.ts

1. Add optional timeout param (default 5000ms)
2. Pass to underlying fetch() call
3. Update tests in src/api/users.test.ts

Example: fetchUser(id) → fetchUser(id, { timeout: 3000 })
Depends on: bd-abc123 (fetch wrapper refactor)
```

### Dependencies: Think "Needs", Not "Before"

`bd dep add X Y` = "X needs Y" = Y blocks X

**TRAP**: Temporal words ("Phase 1", "before", "first") invert your thinking!
```
WRONG: "Phase 1 before Phase 2" → bd dep add phase1 phase2
RIGHT: "Phase 2 needs Phase 1" → bd dep add phase2 phase1
```
**Verify**: `bd blocked` - tasks blocked by prerequisites, not dependents.

### Auto-Sync

bd automatically syncs with git:
- Exports to `.beads/issues.jsonl` after changes (5s debounce)
- Imports from JSONL when newer (e.g., after `git pull`)
- No manual export/import needed!

### MCP Server (Recommended)

If using Claude or MCP-compatible clients, install the beads MCP server:

```bash
pip install beads-mcp
```

Add to MCP config (e.g., `~/.config/claude/config.json`):
```json
{
  "beads": {
    "command": "beads-mcp",
    "args": []
  }
}
```

Then use `mcp__beads__*` functions instead of CLI commands.

### Managing AI-Generated Planning Documents

AI assistants often create planning and design documents during development:
- PLAN.md, IMPLEMENTATION.md, ARCHITECTURE.md
- DESIGN.md, CODEBASE_SUMMARY.md, INTEGRATION_PLAN.md
- TESTING_GUIDE.md, TECHNICAL_DESIGN.md, and similar files

**Best Practice: Use a dedicated directory for these ephemeral files**

**Recommended approach:**
- Create a `history/` directory in the project root
- Store ALL AI-generated planning/design docs in `history/`
- Keep the repository root clean and focused on permanent project files
- Only access `history/` when explicitly asked to review past planning

**Example .gitignore entry (optional):**
```
# AI planning documents (ephemeral)
history/
```

**Benefits:**
- ✅ Clean repository root
- ✅ Clear separation between ephemeral and permanent documentation
- ✅ Easy to exclude from version control if desired
- ✅ Preserves planning history for archeological research
- ✅ Reduces noise when browsing the project

### CLI Help

Run `bd <command> --help` to see all available flags for any command.
For example: `bd create --help` shows `--parent`, `--deps`, `--assignee`, etc.

### Important Rules

- ✅ Use bd for ALL task tracking
- ✅ Always use `--json` flag for programmatic use
- ✅ Link discovered work with `discovered-from` dependencies
- ✅ Check `bd ready` before asking "what should I work on?"
- ✅ Store AI planning docs in `history/` directory
- ✅ Run `bd <cmd> --help` to discover available flags
- ❌ Do NOT create markdown TODO lists
- ❌ Do NOT use external issue trackers
- ❌ Do NOT duplicate tracking systems
- ❌ Do NOT clutter repo root with planning documents

For more details, see README.md and QUICKSTART.md.

## Optional Testing
暂未内建测试；如添加脚本逻辑可引入 `vitest` 置于 `tests/`，文件 `*.spec.(js|ts)`；覆盖核心分支即可。

## Quick Start
1. `bun install`
2. （若无脚本）在 package.json 添加 `"new:post": "hexo new post"`
3. `bun run new:post "Title"`
4. 编辑 + 图片 + `<!-- more -->`
5. `bun run server` 预览校验
6. `bun run deploy`

欢迎通过 `docs: update guidelines` 改进此文件。

## Landing the Plane (Session Completion)

**When ending a work session**, you MUST complete ALL steps below. Work is NOT complete until `git push` succeeds.

**MANDATORY WORKFLOW:**

1. **File issues for remaining work** - Create issues for anything that needs follow-up
2. **Run quality gates** (if code changed) - Tests, linters, builds
3. **Update issue status** - Close finished work, update in-progress items
4. **PUSH TO REMOTE** - This is MANDATORY:
   ```bash
   git pull --rebase
   bd sync
   git push
   git status  # MUST show "up to date with origin"
   ```
5. **Clean up** - Clear stashes, prune remote branches
6. **Verify** - All changes committed AND pushed
7. **Hand off** - Provide context for next session

**CRITICAL RULES:**
- Work is NOT complete until `git push` succeeds
- NEVER stop before pushing - that leaves work stranded locally
- NEVER say "ready to push when you are" - YOU must push
- If push fails, resolve and retry until it succeeds
