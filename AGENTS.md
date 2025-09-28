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
