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

# CLAUDE.md

> Note: This project uses bd (beads) for issue tracking. Use `bd` commands instead of markdown TODOs. See `AGENTS.md` for workflow details.

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a personal Chinese tech blog (cuipengfei.me) that has evolved from Octopress/Jekyll to Hexo. The site covers software development topics including Scala, functional programming, testing methodologies, and architecture patterns.

**Important**: This project uses Bun as the package manager exclusively. Never use npm, yarn, pnpm, or direct hexo binary calls. Legacy Ruby/Jekyll components are preserved for reference only and are no longer actively maintained.

## Architecture

### Current System Architecture
- **Primary System**: Hexo (Node.js-based, v8.0.0) generating from `source/` to `public/`
- **Legacy System**: Octopress/Jekyll (Ruby-based) - preserved for reference, no longer maintained
- **Theme**: Uses NexT theme for Hexo (`themes/next-old/`)
- **Package Manager**: Bun (mandatory, exclusive) - strictly forbidden: npm, yarn, pnpm, direct hexo calls
- **Deployment**: GitHub Pages via `hexo-deployer-git`

### Core Configuration Files
- `_config.yml`: Main Hexo site configuration
- `_config.next.yml`: NexT theme configuration (primary customization file)
- `themes/next-old/_config.yml`: Theme core config (avoid direct modification)
- `source/_data/next.yml`: Theme override file (optional, for incremental changes)
- `source/_data/*.swig`: Custom template extensions
- `source/_data/*.styl`: Custom stylesheets

### Content Structure
- `source/_posts/`: Published blog posts (YYYY-MM-DD-slug.md format)
- `source/_drafts/`: Draft posts (optional directory)
- `source/images/`: Static images (organize by post-slug subdirectories)
- `source/_layouts/`: Jekyll layouts (legacy, reference only)
- `public/`: Generated static site output (**read-only**, auto-generated)
- `.deploy_git/`: Git deployment cache (**read-only**)

### Legacy & Reference Files
- `themes/`: Hexo themes including NexT theme
- `plugins/`: Ruby plugins for Octopress (reference only)
- `sass/`: Stylesheets for legacy system (reference only)
- `Rakefile`: Ruby/Jekyll build tasks (legacy, reference only)

## Development Commands

### Primary Commands (Bun Only)
```bash
# Install dependencies
bun install

# Clean generated files and cache
bun run clean

# Generate static site
bun run build

# Start development server (default: http://localhost:4000)
bun run server

# Deploy to GitHub Pages (clean + generate + deploy)
bun run deploy
```

**Important**: After theme/configuration changes, always run `bun run clean` before building to ensure cache is cleared.

### Create New Posts
```bash
# Add to package.json scripts if not present:
# "new:post": "hexo new post"

# Create new post
bun run new:post "Post Title"
```

**Critical**: Use only bun commands. Never use npm, yarn, pnpm, or direct hexo binary calls to maintain environment consistency.

### Legacy Octopress Commands (Reference Only - DO NOT USE)
```bash
# These commands are preserved for reference only
# The Ruby/Jekyll system is no longer actively maintained

# Generate site (legacy Jekyll)
rake generate

# Watch for changes and auto-regenerate
rake watch

# Preview site locally
rake preview

# Deploy via Git push (legacy)
rake deploy

# Create new post
rake new_post["Post Title"]

# Create new page
rake new_page["Page Name"]
```

**Warning**: Do not use these Ruby/Rake commands unless you have specific knowledge of the legacy system requirements.

## Configuration

### Hexo Configuration
- `_config.yml`: Main Hexo configuration (v8.0.0)
- `_config.next.yml`: NexT theme configuration (primary customization method)
- Site URL: `https://cuipengfei.me`
- Deploy target: GitHub Pages (`master` branch)
- Custom file paths: `source/_data/head.swig`, `source/_data/footer.swig`
- Package manager: Bun (exclusive, mandatory)

### Theme Customization Strategy
**Recommended**: Use `source/_data/next.yml` or custom_file_path for theme modifications instead of directly editing `themes/next-old/_config.yml`. This approach:
- Preserves upgrade compatibility
- Keeps customizations separate from theme core
- Enables version control of customizations only

### Content Structure Requirements
- Blog posts use YAML front matter with required fields: `title`, `date`, `categories`
- Optional front matter: `tags`, `updated`, `description`, `slidehtml`
- Post naming convention: `YYYY-MM-DD-slug.md` (slug in lowercase with hyphens)
- Chinese language site (`language: zh-CN`)
- Permalink pattern: `blog/:year/:month/:day/:title/`
- Pagination: 25 posts per page
- Excerpt separation: Use `<!-- more -->` in post content

## Development Workflow

### Content Creation
1. Create new posts in `source/_posts/` with format: `YYYY-MM-DD-slug.md`
2. Use appropriate front matter with required and optional fields
3. Add excerpt separator `<!-- more -->` after introduction
4. Organize images in `source/images/<post-slug>/` with relative paths
5. Validate content against writing style profile requirements
6. Generate and test locally: `bun run server`
7. Deploy to GitHub Pages: `bun run deploy`

### Quality Assurance Steps
- **Configuration changes**: Always run `bun run clean` before building
- **Image optimization**: Ensure images are properly sized and use relative paths
- **Link validation**: Check internal links and ensure proper permalink structure
- **Style consistency**: Validate technical terminology against site consistency
- **Language mixing**: Ensure Chinese/English language mixing follows established patterns
- **Responsive testing**: Test mobile compatibility and responsive design

### Agent-Specific Rules
- **Read-only directories**: Never directly edit `public/` or `.deploy_git/` directories
- **Minimal changes**: Make only necessary modifications to achieve the goal
- **Environment consistency**: Use only `bun` commands - strictly forbidden: npm, yarn, pnpm, direct hexo binary calls
- **Post-configuration cleanup**: After theme/config changes, always run `bun run clean` before building
- **Structure updates**: If adding new directories or major changes, update this CLAUDE.md file
- **Git operations**: Only perform git commit/push operations when explicitly instructed
- **Dependency management**: Use `bun` exclusively, never install global dependencies without permission

### Image Management & Assets
- **Structure**: Blog images stored in `source/images/<post-slug>/` subdirectories
- **Referencing**: Use relative paths in markdown: `![alt text](../images/post-slug/image.png)`
- **Optimization**: Ensure images are web-optimized (appropriate size and format)
- **Legacy migration**: CSDN image migration script available: `csdn-imgs.js` (reference only)
- **Public assets**: Generated images served from `public/images/` (auto-generated)

### Content Migration & Legacy References
- **Legacy platform**: Historical content migrated from CSDN platform
- **Jekyll layouts**: Preserved in `source/_layouts/` for reference only
- **Ruby rake commands**: Available but not recommended for normal operations
- **Migration scripts**: Various migration utilities in root directory (reference)

### Slides & Presentations
- **Reveal.js Integration**: Create presentation slides using `hexo-generator-slidehtml`
- **Slide Creation**: Add `slidehtml: true` to post front matter
- **Slide Syntax**: Use `---` for horizontal slides, `--` for vertical slides
- **Access URL**: Slides available at `/blog/:year/:month/:day/:title/slide.html`
- **Themes**: Support for multiple Reveal.js themes (black, white, league, etc.)

### Dependencies & Version Information
- **Hexo**: v8.0.0 (main static site generator)
- **Axios**: v1.12.2 (HTTP client)
- **NexT Theme**: v8.25.0 using Gemini scheme with dark mode
- **Node.js**: Required for Hexo plugins and build process
- **Bun**: Package manager (mandatory, exclusive) - never use npm/yarn/pnpm

### Key Plugins & Extensions
- `hexo-deployer-git`: GitHub Pages deployment
- `hexo-generator-*`: Content generators (archive, category, tag, feed, sitemap)
- `hexo-markmap`: Mind mapping support for markdown
- `hexo-graphviz`: Graphviz diagram generation
- `hexo-excerpt`: Automatic post excerpt generation with configurable depth
- `hexo-generator-searchdb`: Local search functionality
- `hexo-related-popular-posts`: Related posts recommendations
- `hexo-symbols-count-time`: Post reading time and word count
- `hexo-generator-slidehtml`: Reveal.js slides generation (v0.0.65)

### Testing & Security
- **No automated test suite**: Content validation through local preview only
- **Manual quality checks**: Required before deployment
- **Writing style validation**: Against `.memory-bank/writing-style-profile.md` standards
- **Security**: Never commit secrets/tokens - use `.env` files (excluded from git)
- **Dependency updates**: Handle in separate PRs with breaking change notes

## SEO & Social Integration

### Built-in SEO Features
- **Sitemap generation**: `sitemap.xml` auto-generated
- **RSS feed**: `atom.xml` for syndication
- **Baidu optimization**: Search engine optimization with push functionality
- **Meta tags**: Automatic generation for posts and pages
- **Structured URLs**: SEO-friendly permalink structure

### Social & Analytics
- **GitHub integration**: Author profile and repository links
- **Google Analytics**: Tracking ID UA-46270419-1 (configured)
- **Disqus commenting**: Community interaction system
- **Social sharing**: Built-in sharing capabilities
- **Related posts**: Algorithm-based content recommendations

## Content Guidelines & Writing Style

### Content Focus Areas
- Software development methodologies (TDD, BDD, testing strategies)
- Functional programming (Scala, functional patterns)
- System architecture and design patterns
- Technical reflection and best practices
- AI-assisted development and tooling
- Chinese technical writing with English code terms preservation

### Writing Style Requirements (Critical)
**All content creation must follow the writing style profile defined in `.memory-bank/writing-style-profile.md`**

### Language and Tone Guidelines
- **Primary Language**: Chinese for all main content, with English preserved for technical terms and code
- **Tone**: Humble, exploratory, and conversational - avoid authoritative or prescriptive language
- **Uncertainty Markers**: Use "我认为", "似乎", "可能", "据我理解", "目前来看" to maintain humble tone
- **Interactive Elements**: Include thoughtful questions and "不过再想一下" progressive inquiry patterns

### Technical Writing Standards
- **Technical Term Density**: 15-25 technical terms per 100 characters
- **Complex Sentence Ratio**: 60-75% compound sentences to reflect analytical thinking
- **Question Frequency**: 8-15% of sentences should be questions to enhance reader engagement
- **Code-to-Text Ratio**: Maintain 1:2 to 1:4 ratio for balanced theory and practice

### Content Structure Principles
- **Spiral Progression**: Revisit themes at deeper levels throughout articles
- **Multi-perspective Analysis**: Present different viewpoints using "从...角度" and "另一方面"
- **Problem-Solution Framework**: Structure as problem identification → analysis → solution exploration
- **Metaphor Usage**: 3-5 analogies per 1000 characters to explain complex concepts

### Reader Interaction Guidelines
- **Peer-Level Discussion**: Write as colleague exploring ideas together, not as teacher
- **Cognitive Collaboration**: Invite readers to think along: "我们可以考虑", "值得探讨的是"
- **Knowledge Boundary Acknowledgment**: Explicitly state limitations: "在我的经验中", "可能存在遗漏"
- **Open Endings**: 30-45% of paragraphs should end with open questions or thinking directions

### Content Quality Indicators
- Follow **文体学** (stylistic) principles from `.memory-bank/writing-style-profile.md`
- Avoid absolute statements ("必须", "应该", "显然", "众所周知")
- Include personal journey elements when relevant to technical understanding
- Use story-telling techniques to make complex concepts accessible
- Maintain technical depth while ensuring approachability

## Quick Start Guide

Based on AGENTS.md recommendations:

1. **Install dependencies**: `bun install`
2. **Add post creation script** (if not present in package.json):
   ```json
   "scripts": {
     "new:post": "hexo new post"
   }
   ```
3. **Create new post**: `bun run new:post "Title"`
4. **Edit content**: Add front matter, content, images to `source/images/<post-slug>/`, and `<!-- more -->` separator
5. **Preview**: `bun run server` (http://localhost:4000)
6. **Deploy**: `bun run deploy`

**Environment Rules**: Use only `bun` commands. Never use npm, yarn, pnpm, or direct hexo binary calls to maintain consistency.

## Project File Structure

### Key Directories
- `source/_posts/`: Blog posts (500+ posts dating back to 2008)
- `source/images/`: Static images and assets
- `public/`: Generated site output (auto-generated)
- `themes/next-old/`: Custom NexT theme configuration
- `.memory-bank/`: AI writing guidelines and style profiles
- `plugins/`: Ruby plugins for legacy Octopress support
- `scaffolds/`: Post templates for new content creation

### Important Files
- `_config.yml`: Main Hexo configuration
- `_config.next.yml`: NexT theme customization
- `package.json`: Node.js dependencies (uses Bun)
- `Rakefile`: Legacy Ruby/Jekyll build tasks
- `CLAUDE.md`: This guidance file
