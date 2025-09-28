# Repository Guidelines

本仓库已迁移为 Hexo + NexT 主题（不再使用 Ruby/Jekyll/Rake）。本指南覆盖根目录及所有子目录；贡献或由 AI 进行自动修改前需先阅读。目标：最小变更、可重复构建、内容清晰版本化。

## Project Structure
- 根配置: `_config.yml` (站点)；`themes/next-old/_config.yml` (主题)；可用 `source/_data/next.yml` 做增量覆盖。
- 内容: `source/_posts/`(文章), `source/_drafts/`(草稿，可建), `source/images/`, `source/javascripts/`, `source/assets/`。
- 主题与定制: `themes/next-old/`；自定义资源放入 `source/_data/` 避免直接改主题核心。
- 插件: `node_modules/` 中 NPM 包；自建脚本可放 `scripts/` (若新增需在指南补充)。
- 构建 / 部署输出: `public/` (临时) 与 `.deploy_git/` (git 部署缓存) 均只读，不直接编辑。

## Build, Dev & Deployment
```bash
pnpm|npm|bun install     # 安装依赖
npx hexo clean           # 清理缓存与 public/
npx hexo generate        # 生成静态文件到 public/
npx hexo server          # 启动本地预览 (默认 4000)
npx hexo deploy          # 清理 + 生成 + 部署 (hexo-deployer-git)
```
也可用 package.json scripts: `npm run build|clean|server|deploy`。

## Coding Style & Naming
- Markdown 文件: `YYYY-MM-DD-slug.md` (slug 小写短横线)。
- Front matter 最少: `title`, `date`, `categories`；可选：`tags`, `updated`, `description`。
- 代码块：fenced code ```lang；避免行尾多余空格。
- 自定义 JS/CSS：放入 `source/`，使用 NexT 提供 `custom_file_path` 或 `_data/styles.styl`。

## Content Guidelines
- 草稿存 `source/_drafts/`，发布前移动到 `_posts/`。
- 图片：`source/images/<post-slug>/` 相对引用；大图可加缩略版本。
- 摘要：在正文插入 `<!-- more -->` 分隔。
- 外链尽量加协议前缀与有效性检查。

## Testing (可选)
默认无测试。若引入生成脚本或数据处理：
1. 选用 `vitest` 或 `jest`，目录 `tests/` 或 `__tests__/`。
2. 命名：`*.spec.ts|js`。
3. 覆盖关键分支 (逻辑覆盖≈80%)。

## Commit & Pull Request
- 格式: `<type>: <summary>`；type: feat|fix|docs|refactor|style|perf|chore|build|ci。
- PR 内容：背景、变更列表、影响范围、验证步骤（含 `hexo clean && hexo generate`）、截图（UI 变化）。
- 关联 Issue：`Closes #id`。
- 单一主题：避免同时提交内容与构建工具大改。

## Configuration Tips
- 主题 override：使用 `source/_data/next.yml` 而非直接改 `themes/next-old/_config.yml`（便于后续升级）。
- 性能：启用主题 `cache.enable=true`；变更后执行 `hexo clean`。
- 部署：确认 `_config.yml` 中 `deploy` 正确指向仓库。

## Security & Secrets
- 不提交 Token / 密钥；放 `.env` 并在 `_config.yml` 引用前先判断存在性（通过 scripts 读取）。
- 依赖升级：分开 PR，说明潜在 BREAKING CHANGE。

## Agent-Specific Instructions
1. 不写入或直接修改 `public/`、`.deploy_git/`。
2. 新增或移动结构需同步更新本文件相关段落。
3. 优先使用 `npx hexo` 命令，不擅自添加全局依赖。
4. 仅在显式指令下进行 Git commit/push。

## Quick Start
1. 安装依赖 (`npm install` 或 `bun install`)
2. 创建文章：`npx hexo new post "Title"`
3. 本地预览：`npx hexo server`
4. 调整与校验链接/图片/摘要
5. 部署：`npx hexo deploy`

欢迎使用 `docs: update guidelines` 提交改进。
