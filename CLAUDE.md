# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a personal Chinese tech blog (cuipengfei.me) that has evolved from Octopress/Jekyll to Hexo. The site covers software development topics including Scala, functional programming, testing methodologies, and architecture patterns.

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

### Hexo (Current System)
```bash
# Install dependencies
npm install

# Clean generated files
npm run clean

# Generate static site
npm run build

# Start development server
npm run server

# Deploy to GitHub Pages
npm run deploy
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
- `_config.yml`: Main Hexo configuration
- `_config.next.yml`: NexT theme configuration
- Site URL: `https://cuipengfei.me`
- Deploy target: GitHub Pages (`master` branch)

### Content Structure
- Blog posts use YAML front matter with `title`, `date`, `tags`
- Chinese language site (`language: zh-CN`)
- Permalink pattern: `blog/:year/:month/:day/:title/`

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

## Key Features

### Plugins & Extensions
- `hexo-generator-*`: Various content generators
- `hexo-theme-next`: Primary theme
- `hexo-markmap`: Mind mapping support
- `hexo-graphviz`: Diagram generation
- Disqus comments integration
- Google Analytics tracking

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