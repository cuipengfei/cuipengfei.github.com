# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a personal Chinese tech blog (cuipengfei.me) that has evolved from Octopress/Jekyll to Hexo. The site covers software development topics including Scala, functional programming, testing methodologies, and architecture patterns.

**Important**: This project uses Bun as the package manager, not npm. Always use `bun` commands instead of `npm`.

## Architecture

### Dual Static Site Generators
- **Legacy System**: Octopress/Jekyll (Ruby-based) with files in `source/` and Ruby dependencies
- **Current System**: Hexo (Node.js-based) generating from `source/` to `public/`
- **Theme**: Uses NexT theme for Hexo (`themes/next-old/`)

### Key Directories
- `source/_posts/`: Markdown blog posts (shared by both systems)
- `source/_layouts/`: Jekyll layouts (legacy)
- `public/`: Generated static site output (Hexo target)
- `themes/`: Hexo themes including NexT theme
- `plugins/`: Ruby plugins for Octopress
- `sass/`: Stylesheets for legacy system

## Development Commands

### Hexo (Current System) - 使用 Bun
```bash
# Install dependencies
bun install

# Clean generated files
bun run clean

# Generate static site
bun run build

# Start development server (default: http://localhost:4000)
bun run server

# Deploy to GitHub Pages (master branch)
bun run deploy
```

### Legacy Octopress Commands (Ruby/Rake)
```bash
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

## Configuration

### Hexo Configuration
- `_config.yml`: Main Hexo configuration (v7.3.0)
- `_config.next.yml`: NexT theme configuration (override: false)
- Site URL: `https://cuipengfei.me`
- Deploy target: GitHub Pages (`master` branch)
- Custom file paths: `source/_data/head.swig`, `source/_data/footer.swig`
- Package manager: Bun (not npm)

### Content Structure
- Blog posts use YAML front matter with `title`, `date`, `tags`
- Chinese language site (`language: zh-CN`)
- Permalink pattern: `blog/:year/:month/:day/:title/`
- Post naming: `:year-:month-:day-:title.md`
- Pagination: 25 posts per page

## Development Workflow

### Content Creation
1. Create new posts in `source/_posts/` with format: `YYYY-MM-DD-title.md`
2. Use appropriate front matter with tags
3. Generate and test locally
4. Deploy to GitHub Pages

### Image Management
- Blog images stored in `source/images/`
- Legacy CSDN image migration script: `csdn-imgs.js`
- Public images served from `public/images/`

### Slides & Presentations
- **Reveal.js Integration**: Create presentation slides using `hexo-generator-slidehtml`
- **Slide Creation**: Add `slidehtml: true` to post front matter
- **Slide Syntax**: Use `---` for horizontal slides, `--` for vertical slides
- **Access URL**: Slides available at `/blog/:year/:month/:day/:title/slide.html`
- **Themes**: Support for multiple Reveal.js themes (black, white, league, etc.)

## Key Features

### Core Plugins & Extensions
- `hexo-theme-next`: Primary theme (v8.25.0)
- `hexo-generator-*`: Content generators (archive, category, tag, feed, sitemap)
- `hexo-markmap`: Mind mapping support for markdown
- `hexo-graphviz`: Graphviz diagram generation
- `hexo-excerpt`: Automatic post excerpt generation
- `hexo-generator-searchdb`: Local search functionality
- `hexo-related-popular-posts`: Related posts recommendations
- `hexo-symbols-count-time`: Post reading time and word count
- `hexo-generator-slidehtml`: Reveal.js slides generation (v0.0.65)

### SEO & Social
- Sitemap generation
- RSS feed
- Baidu search optimization
- GitHub integration

## Technical Notes

### Legacy Compatibility
- Some Ruby dependencies remain for legacy content processing
- Rakefile contains legacy deployment scripts
- Jekyll layouts preserved for reference

### Build Process
- Hexo processes Markdown files from `source/_posts/`
- Generates static HTML to `public/`
- Auto-deployment to GitHub Pages via `hexo-deployer-git`
- **Package Manager**: Uses Bun exclusively (not npm) for dependency management

### Testing & Quality
- No automated test suite
- Content validation through local preview
- Manual quality checks before deployment

## Content Guidelines

The blog focuses on:
- Software development methodologies (TDD, BDD)
- Functional programming (Scala, FP patterns)
- System architecture and design patterns
- Technical reflection and best practices
- Chinese technical writing with English code terms

## Writing Style Requirements

**Critical**: All content creation must follow the writing style profile defined in `.memory-bank/writing-style-profile.md`. Key principles:

### Language and Tone
- **Primary Language**: Chinese for all main content, with English preserved for technical terms and code
- **Tone**: Humble, exploratory, and conversational - avoid authoritative or prescriptive language
- **Uncertainty Markers**: Use "我认为", "似乎", "可能", "据我理解", "目前来看" to maintain humble tone
- **Interactive Elements**: Include thoughtful questions and "不过再想一下" progressive inquiry patterns

### Technical Content Standards
- **Technical Term Density**: 15-25 technical terms per 100 characters
- **Complex Sentence Ratio**: 60-75% compound sentences to reflect analytical thinking
- **Question Frequency**: 8-15% of sentences should be questions to enhance reader engagement
- **Code-to-Text Ratio**: Maintain 1:2 to 1:4 ratio for balanced theory and practice

### Content Structure
- **Spiral Progression**: Revisit themes at deeper levels throughout the article
- **Multi-perspective Analysis**: Present different viewpoints using "从...角度" and "另一方面"
- **Problem-Solution Framework**: Structure content as problem identification → analysis → solution exploration
- **Metaphor Usage**: 3-5 analogies per 1000 characters to explain complex concepts

### Reader Interaction
- **Peer-Level Discussion**: Write as a colleague exploring ideas together, not as a teacher
- **Cognitive Collaboration**: Invite readers to think along: "我们可以考虑", "值得探讨的是"
- **Knowledge Boundary Acknowledgment**: Explicitly state limitations: "在我的经验中", "可能存在遗漏"
- **Open Endings**: 30-45% of paragraphs should end with open questions or further thinking directions

### Content Quality Indicators
- Avoid absolute statements ("必须", "应该", "显然", "众所周知")
- Include personal journey elements when relevant to technical understanding
- Use story-telling techniques to make complex concepts accessible
- Maintain technical depth while ensuring approachability