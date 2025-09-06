---
title: "Reveal.js 高级特效演示"
date: 2025-01-20
tags: [slides, revealjs, effects, demo]
slidehtml: true
---

<!-- .slide: data-background-video="https://www.w3schools.com/html/mov_bbb.mp4" data-background-video-loop data-background-video-muted data-background-opacity="0.3" -->
<div style="text-align: center; padding: 3rem; background: rgba(0,0,0,0.7); border-radius: 20px;">
    <h1 style="font-size: 4rem; color: #fff; margin-bottom: 1rem; text-shadow: 2px 2px 4px rgba(0,0,0,0.5);">
        🚀 Reveal.js 高级特效
    </h1>
    <h2 style="font-size: 2rem; color: #fff; margin-bottom: 2rem; opacity: 0.9;">
        极致的视觉体验
    </h2>
    <div style="font-size: 1.5rem; color: #fff; opacity: 0.8;">
        <p>背景视频 · 动态效果 · 交互体验</p>
    </div>
</div>

---

<!-- .slide: data-background-gradient="linear-gradient(to bottom, #667eea 0%, #764ba2 100%)" -->

## 🎨 背景特效展示

### 渐变背景效果

<div style="background: rgba(255,255,255,0.2); padding: 2rem; border-radius: 15px; backdrop-filter: blur(10px);">
    <p style="font-size: 1.5rem; margin-bottom: 1rem;">支持多种背景效果</p>
    <ul style="font-size: 1.2rem;">
        <li>线性渐变</li>
        <li>径向渐变</li>
        <li>图片背景</li>
        <li>视频背景</li>
        <li>纯色背景</li>
    </ul>
</div>

--

<!-- .slide: data-background-image="https://picsum.photos/seed/tech1/1920/1080.jpg" data-background-opacity="0.4" -->

### 图片背景 + 透明度

<div style="background: rgba(0,0,0,0.8); padding: 2rem; border-radius: 15px;">
    <h3 style="color: #fff; margin-top: 0;">图片背景效果</h3>
    <p style="color: #fff; font-size: 1.2rem;">支持透明度调节的图片背景</p>
    <div style="margin-top: 1rem;">
        <span style="background: #3498db; color: white; padding: 0.5rem 1rem; border-radius: 20px; margin: 0.5rem;">图片透明度</span>
        <span style="background: #e74c3c; color: white; padding: 0.5rem 1rem; border-radius: 20px; margin: 0.5rem;">背景模糊</span>
        <span style="background: #2ecc71; color: white; padding: 0.5rem 1rem; border-radius: 20px; margin: 0.5rem;">视觉效果</span>
    </div>
</div>

--

<!-- .slide: data-background-color="#ff6b6b" -->

### 纯色背景

<div style="background: rgba(255,255,255,0.9); padding: 2rem; border-radius: 15px;">
    <h3 style="color: #333; margin-top: 0;">纯色背景效果</h3>
    <p style="color: #666; font-size: 1.2rem;">简洁的纯色背景设计</p>
    <div style="display: grid; grid-template-columns: repeat(4, 1fr); gap: 1rem; margin-top: 1rem;">
        <div style="background: #3498db; height: 50px; border-radius: 8px;"></div>
        <div style="background: #e74c3c; height: 50px; border-radius: 8px;"></div>
        <div style="background: #2ecc71; height: 50px; border-radius: 8px;"></div>
        <div style="background: #f39c12; height: 50px; border-radius: 8px;"></div>
    </div>
</div>

---

<!-- .slide: data-background="#2c3e50" -->

## 💻 代码高亮演示

### 多语言代码展示

<pre style="background: #34495e; padding: 1.5rem; border-radius: 10px; overflow-x: auto;"><code data-line-numbers>function fibonacci(n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

// 优化版本
function fibonacciOptimized(n, memo = {}) {
    if (n in memo) return memo[n];
    if (n <= 1) return n;
    
    memo[n] = fibonacciOptimized(n - 1, memo) + 
               fibonacciOptimized(n - 2, memo);
    return memo[n];
}</code></pre>

--

### Python 代码示例

<pre style="background: #1e3a8a; color: #e2e8f0; padding: 1.5rem; border-radius: 10px;"><code>class AdvancedCalculator:
    def __init__(self):
        self.history = []
    
    def calculate(self, operation, a, b):
        result = {
            '+': a + b,
            '-': a - b,
            '*': a * b,
            '/': a / b
        }.get(operation, "Invalid operation")
        
        self.history.append(f"{a} {operation} {b} = {result}")
        return result
    
    def get_history(self):
        return self.history</code></pre>

---

<!-- .slide: data-background="#ffffff" -->

## 📊 图表与数据可视化

### Mermaid 图表支持

```mermaid
graph TD
    A[开始] --> B{数据输入}
    B -->|有效数据| C[数据处理]
    B -->|无效数据| D[错误处理]
    C --> E[结果输出]
    D --> F[日志记录]
    E --> G[完成]
    F --> G
```

--

### 复杂流程图

```mermaid
flowchart TB
    subgraph 前端层
        A[React App] --> B[Redux Store]
        B --> C[Component Tree]
    end

    subgraph 后端层
        D[Express API] --> E[Database]
        E --> F[Cache Layer]
    end

    C -.->|HTTP Request| D
    F -.->|Data Response| C
```

--

### 序列图演示

```mermaid
sequenceDiagram
    participant 用户
    participant 浏览器
    participant 服务器
    participant 数据库

    用户->>浏览器: 输入数据
    浏览器->>服务器: POST /api/data
    服务器->>数据库: INSERT INTO table
    数据库-->>服务器: 返回 ID
    服务器-->>浏览器: JSON 响应
    浏览器-->>用户: 显示结果
```

---

<!-- .slide: data-background="#f8f9fa" -->

## 🎬 动画效果演示

### 列表动画效果

<ol>
    <li class="fragment fade-up" data-fragment-index="1">第一步 - 淡入上升效果</li>
    <li class="fragment fade-down" data-fragment-index="2">第二步 - 淡入下降效果</li>
    <li class="fragment fade-left" data-fragment-index="3">第三步 - 淡入左移效果</li>
    <li class="fragment fade-right" data-fragment-index="4">第四步 - 淡入右移效果</li>
    <li class="fragment fade-in" data-fragment-index="5">第五步 - 淡入效果</li>
</ol>

--

### 高级动画效果

<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 2rem;">
    <div>
        <h4>高亮效果</h4>
        <ul>
            <li class="fragment highlight-red">红色高亮</li>
            <li class="fragment highlight-blue">蓝色高亮</li>
            <li class="fragment highlight-green">绿色高亮</li>
        </ul>
    </div>
    <div>
        <h4>特殊效果</h4>
        <ul>
            <li class="fragment grow">放大效果</li>
            <li class="fragment shrink">缩小效果</li>
            <li class="fragment strike-out">删除线效果</li>
        </ul>
    </div>
</div>

--

### 堆叠动画

<div class="r-stack">
    <div class="fragment fade-in-then-out">
        <div style="background: #3498db; color: white; padding: 2rem; border-radius: 10px;">
            <h3>第一层内容</h3>
            <p>淡入然后淡出</p>
        </div>
    </div>
    <div class="fragment fade-in-then-out">
        <div style="background: #e74c3c; color: white; padding: 2rem; border-radius: 10px;">
            <h3>第二层内容</h3>
            <p>同样淡入淡出</p>
        </div>
    </div>
    <div class="fragment fade-in">
        <div style="background: #2ecc71; color: white; padding: 2rem; border-radius: 10px;">
            <h3>第三层内容</h3>
            <p>最后显示</p>
        </div>
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎯 交互式元素

### 点击触发动画

<div style="text-align: center;">
    <div class="fragment" data-fragment-index="1">
        <div style="background: #3498db; color: white; padding: 2rem; border-radius: 10px; margin: 1rem;">
            <h3>第一步</h3>
            <p>点击显示第一步</p>
        </div>
    </div>
    <div class="fragment" data-fragment-index="2">
        <div style="background: #e74c3c; color: white; padding: 2rem; border-radius: 10px; margin: 1rem;">
            <h3>第二步</h3>
            <p>点击显示第二步</p>
        </div>
    </div>
    <div class="fragment" data-fragment-index="3">
        <div style="background: #2ecc71; color: white; padding: 2rem; border-radius: 10px; margin: 1rem;">
            <h3>第三步</h3>
            <p>点击显示第三步</p>
        </div>
    </div>
</div>

--

### 自动动画演示

<!-- .slide: data-auto-animate data-auto-animate-easing="cubic-bezier(0.250, 0.460, 0.450, 0.940)" -->
<div style="text-align: center;">
    <h2>自动动画效果</h2>
    <p>元素会自动过渡到下一个状态</p>
</div>

--

<!-- .slide: data-auto-animate data-auto-animate-easing="cubic-bezier(0.250, 0.460, 0.450, 0.940)" -->
<div style="text-align: center;">
    <h2>自动动画效果</h2>
    <p>元素会自动过渡到下一个状态</p>
    <div style="background: #3498db; color: white; padding: 1rem; border-radius: 8px; display: inline-block;">
        新增的内容块
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎵 多媒体支持

### 视频嵌入

<video controls width="100%" height="auto" style="border-radius: 10px;">
    <source src="https://www.w3schools.com/html/mov_bbb.mp4" type="video/mp4">
    您的浏览器不支持视频标签。
</video>

--

### 音频支持

<audio controls style="width: 100%; margin: 1rem 0;">
    <source src="https://www.w3schools.com/html/horse.mp3" type="audio/mpeg">
    您的浏览器不支持音频标签。
</audio>

--

### 外部内容嵌入

<div style="background: #f8f9fa; padding: 2rem; border-radius: 10px; border: 2px dashed #dee2e6;">
    <h3>外部内容支持</h3>
    <p>支持嵌入 YouTube 视频、iframe 内容、外部网页等</p>
    <div style="background: #e9ecef; padding: 1rem; border-radius: 5px; margin-top: 1rem;">
        <code>&lt;iframe src="..."&gt;&lt;/iframe&gt;</code>
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎨 主题与样式

### 内置主题展示

<div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem;">
    <div style="background: #000; color: white; padding: 1rem; border-radius: 8px;">
        <h4>Black</h4>
        <p>经典黑色主题</p>
    </div>
    <div style="background: #fff; color: #000; padding: 1rem; border-radius: 8px; border: 1px solid #ddd;">
        <h4>White</h4>
        <p>纯净白色主题</p>
    </div>
    <div style="background: #2c3e50; color: white; padding: 1rem; border-radius: 8px;">
        <h4>League</h4>
        <p>深灰色主题</p>
    </div>
</div>

--

### 自定义样式

<div style="background: linear-gradient(45deg, #ff6b6b, #4ecdc4); color: white; padding: 2rem; border-radius: 15px;">
    <h3>自定义渐变背景</h3>
    <p>支持 CSS 自定义样式</p>
    <div style="background: rgba(255,255,255,0.2); padding: 1rem; border-radius: 8px; margin-top: 1rem;">
        <p>半透明效果</p>
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 📊 数据可视化

### 表格样式

<table style="width: 100%; border-collapse: collapse; margin: 1rem 0;">
    <thead>
        <tr style="background: #3498db; color: white;">
            <th style="padding: 1rem; text-align: left;">功能</th>
            <th style="padding: 1rem; text-align: left;">支持程度</th>
            <th style="padding: 1rem; text-align: left;">说明</th>
        </tr>
    </thead>
    <tbody>
        <tr style="border-bottom: 1px solid #ddd;">
            <td style="padding: 1rem;">Markdown</td>
            <td style="padding: 1rem;">✅ 完全支持</td>
            <td style="padding: 1rem;">原生 Markdown 语法</td>
        </tr>
        <tr style="border-bottom: 1px solid #ddd;">
            <td style="padding: 1rem;">HTML</td>
            <td style="padding: 1rem;">✅ 完全支持</td>
            <td style="padding: 1rem;">HTML 标签支持</td>
        </tr>
        <tr style="border-bottom: 1px solid #ddd;">
            <td style="padding: 1rem;">LaTeX</td>
            <td style="padding: 1rem;">✅ 支持</td>
            <td style="padding: 1rem;">数学公式渲染</td>
        </tr>
    </tbody>
</table>

--

### 数学公式

$$
E = mc^2
$$

$$
\int_{-\infty}^{\infty} e^{-x^2} dx = \sqrt{\pi}
$$

$$
\frac{d}{dx}\left( \int_{a}^{x} f(t) dt \right) = f(x)
$$

---

<!-- .slide: data-background="#ffffff" -->

## 🎮 演示控制

### 快捷键指南

<div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 2rem;">
    <div style="background: #f8f9fa; padding: 1.5rem; border-radius: 10px;">
        <h4>基本控制</h4>
        <ul>
            <li><kbd>空格</kbd> / <kbd>→</kbd> - 下一张</li>
            <li><kbd>Shift</kbd> + <kbd>空格</kbd> / <kbd>←</kbd> - 上一张</li>
            <li><kbd>F</kbd> - 全屏模式</li>
            <li><kbd>ESC</kbd> - 退出概览</li>
        </ul>
    </div>
    <div style="background: #f8f9fa; padding: 1.5rem; border-radius: 10px;">
        <h4>高级功能</h4>
        <ul>
            <li><kbd>S</kbd> - 演讲者视图</li>
            <li><kbd>O</kbd> - 幻灯片概览</li>
            <li><kbd>Alt</kbd> + <kbd>点击</kbd> - 缩放</li>
            <li><kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> - 搜索</li>
        </ul>
    </div>
</div>

--

### 演讲者视图

<!-- .slide: data-notes="这是演讲者备注，只有在演讲者视图中可见。可以在这里添加备注、提示、时间安排等信息。" -->
<div style="background: #e3f2fd; padding: 2rem; border-radius: 10px;">
    <h3>演讲者视图功能</h3>
    <p>按 <kbd>S</kbd> 键打开演讲者视图，可以看到：</p>
    <ul>
        <li>当前幻灯片</li>
        <li>下一张幻灯片预览</li>
        <li>演讲者备注</li>
        <li>时间显示</li>
        <li>幻灯片计时</li>
    </ul>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🚀 高级特效

### 3D 转场效果

<!-- data-transition="convex" -->
<div style="text-align: center;">
    <h3>3D 凸面转场</h3>
    <p>使用 3D 效果的转场动画</p>
</div>

--

<!-- data-transition="concave" -->
<div style="text-align: center;">
    <h3>3D 凹面转场</h3>
    <p>另一种 3D 转场效果</p>
</div>

--

### 背景动画

<!-- .slide: data-background="https://picsum.photos/seed/animated/1920/1080.jpg" data-background-size="cover" data-background-transition="zoom" -->
<div style="background: rgba(0,0,0,0.7); padding: 2rem; border-radius: 15px;">
    <h3 style="color: white;">背景转场效果</h3>
    <p style="color: white;">背景图片的缩放转场效果</p>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎯 实用功能

### 搜索功能

<div style="background: #fff3cd; padding: 2rem; border-radius: 10px; border-left: 4px solid #ffc107;">
    <h3>搜索功能</h3>
    <p>按 <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>F</kbd> 打开搜索框</p>
    <p>支持全文搜索，快速定位内容</p>
</div>

--

### 缩放功能

<div style="background: #d1ecf1; padding: 2rem; border-radius: 10px; border-left: 4px solid #17a2b8;">
    <h3>缩放功能</h3>
    <p>按住 <kbd>Alt</kbd> 键并点击任意位置</p>
    <p>可以放大查看细节</p>
    <p>再次点击恢复原大小</p>
</div>

--

### 概览模式

<div style="background: #d4edda; padding: 2rem; border-radius: 10px; border-left: 4px solid #28a745;">
    <h3>概览模式</h3>
    <p>按 <kbd>ESC</kbd> 键进入概览模式</p>
    <p>可以看到所有幻灯片的缩略图</p>
    <p>点击任意幻灯片快速跳转</p>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎨 创意布局

### 网格布局

<div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem;">
    <div style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 1.5rem; border-radius: 10px;">
        <h4>卡片 1</h4>
        <p>渐变背景</p>
    </div>
    <div style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); color: white; padding: 1.5rem; border-radius: 10px;">
        <h4>卡片 2</h4>
        <p>另一种渐变</p>
    </div>
    <div style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); color: white; padding: 1.5rem; border-radius: 10px;">
        <h4>卡片 3</h4>
        <p>蓝色渐变</p>
    </div>
</div>

--

### 弹性布局

<div style="display: flex; justify-content: space-between; align-items: center; gap: 1rem;">
    <div style="flex: 1; background: #3498db; color: white; padding: 2rem; border-radius: 10px;">
        <h4>左侧内容</h4>
        <p>弹性布局</p>
    </div>
    <div style="flex: 2; background: #2ecc71; color: white; padding: 2rem; border-radius: 10px;">
        <h4>右侧内容</h4>
        <p>更宽的区域</p>
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎪 特殊效果

### 打字机效果

<div class="typing-demo" style="font-family: monospace; font-size: 1.2rem; background: #2c3e50; color: #2ecc71; padding: 1rem; border-radius: 5px;">
    <span class="typing-text">这是模拟的打字机效果...</span>
</div>

--

### 闪烁效果

<div style="text-align: center;">
    <div style="background: #e74c3c; color: white; padding: 2rem; border-radius: 10px; display: inline-block; animation: blink 2s infinite;">
        <h3>闪烁效果</h3>
        <p>使用 CSS 动画</p>
    </div>
</div>

--

### 旋转效果

<div style="text-align: center;">
    <div style="background: #3498db; color: white; padding: 2rem; border-radius: 10px; display: inline-block; animation: rotate 4s linear infinite;">
        <h3>旋转效果</h3>
        <p>持续旋转</p>
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎯 交互演示

### 点击计数器

<div style="text-align: center;">
    <button onclick="this.nextElementSibling.textContent++" style="background: #3498db; color: white; border: none; padding: 1rem 2rem; border-radius: 5px; font-size: 1.2rem; cursor: pointer;">
        点击我
    </button>
    <div style="font-size: 3rem; color: #2c3e50; margin: 1rem 0;">0</div>
    <p>点击次数</p>
</div>

--

### 颜色选择器

<div style="text-align: center;">
    <h3>选择背景颜色</h3>
    <div style="margin: 1rem 0;">
        <button onclick="document.body.style.background='#ff6b6b'" style="background: #ff6b6b; border: none; width: 50px; height: 50px; border-radius: 50%; margin: 0.5rem; cursor: pointer;"></button>
        <button onclick="document.body.style.background='#4ecdc4'" style="background: #4ecdc4; border: none; width: 50px; height: 50px; border-radius: 50%; margin: 0.5rem; cursor: pointer;"></button>
        <button onclick="document.body.style.background='#45b7d1'" style="background: #45b7d1; border: none; width: 50px; height: 50px; border-radius: 50%; margin: 0.5rem; cursor: pointer;"></button>
        <button onclick="document.body.style.background='#f9ca24'" style="background: #f9ca24; border: none; width: 50px; height: 50px; border-radius: 50%; margin: 0.5rem; cursor: pointer;"></button>
    </div>
    <button onclick="document.body.style.background='#ffffff'" style="background: #333; color: white; border: none; padding: 0.5rem 1rem; border-radius: 5px; cursor: pointer;">
        重置
    </button>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎨 CSS 动画展示

### 自定义动画

<style>
@keyframes slideInFromLeft {
    0% { transform: translateX(-100%); opacity: 0; }
    100% { transform: translateX(0); opacity: 1; }
}

@keyframes bounce {
    0%, 20%, 50%, 80%, 100% { transform: translateY(0); }
    40% { transform: translateY(-20px); }
    60% { transform: translateY(-10px); }
}

@keyframes pulse {
    0% { transform: scale(1); }
    50% { transform: scale(1.05); }
    100% { transform: scale(1); }
}
</style>

<div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 1rem;">
    <div style="background: #3498db; color: white; padding: 1.5rem; border-radius: 10px; animation: slideInFromLeft 1s ease-out;">
        <h4>滑入效果</h4>
        <p>从左侧滑入</p>
    </div>
    <div style="background: #e74c3c; color: white; padding: 1.5rem; border-radius: 10px; animation: bounce 2s infinite;">
        <h4>弹跳效果</h4>
        <p>持续弹跳</p>
    </div>
    <div style="background: #2ecc71; color: white; padding: 1.5rem; border-radius: 10px; animation: pulse 2s infinite;">
        <h4>脉冲效果</h4>
        <p>缩放脉冲</p>
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎯 响应式设计

### 适配各种设备

<div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
    <div style="background: #3498db; color: white; padding: 1.5rem; border-radius: 10px; text-align: center;">
        <div style="font-size: 2rem;">💻</div>
        <h4>桌面端</h4>
        <p>完整功能</p>
    </div>
    <div style="background: #e74c3c; color: white; padding: 1.5rem; border-radius: 10px; text-align: center;">
        <div style="font-size: 2rem;">📱</div>
        <h4>移动端</h4>
        <p>触控优化</p>
    </div>
    <div style="background: #2ecc71; color: white; padding: 1.5rem; border-radius: 10px; text-align: center;">
        <div style="font-size: 2rem;">📟</div>
        <h4>投影仪</h4>
        <p>高清显示</p>
    </div>
    <div style="background: #f39c12; color: white; padding: 1.5rem; border-radius: 10px; text-align: center;">
        <div style="font-size: 2rem;">🖨️</div>
        <h4>打印</h4>
        <p>PDF 导出</p>
    </div>
</div>

---

<!-- .slide: data-background="#ffffff" -->

## 🎉 特效总结

### 展示的功能

<div style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 2rem; border-radius: 15px;">
    <h3 style="margin-top: 0;">高级特效清单</h3>
    <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 1rem;">
        <div>
            <h4>视觉效果</h4>
            <ul>
                <li>✅ 背景视频/图片</li>
                <li>✅ 渐变背景</li>
                <li>✅ 动画效果</li>
                <li>✅ 3D 转场</li>
            </ul>
        </div>
        <div>
            <h4>交互功能</h4>
            <ul>
                <li>✅ 点击动画</li>
                <li>✅ 搜索缩放</li>
                <li>✅ 概览模式</li>
                <li>✅ 演讲者视图</li>
            </ul>
        </div>
        <div>
            <h4>内容支持</h4>
            <ul>
                <li>✅ 代码高亮</li>
                <li>✅ 数学公式</li>
                <li>✅ 图表支持</li>
                <li>✅ 多媒体</li>
            </ul>
        </div>
        <div>
            <h4>高级特性</h4>
            <ul>
                <li>✅ 自定义动画</li>
                <li>✅ 响应式设计</li>
                <li>✅ 插件扩展</li>
                <li>✅ 主题定制</li>
            </ul>
        </div>
    </div>
</div>

---

<!-- .slide: data-background="linear-gradient(45deg, #ff6b6b, #4ecdc4, #45b7d1, #f9ca24)" -->
<div style="text-align: center; padding: 3rem; background: rgba(0,0,0,0.8); border-radius: 20px;">
    <h1 style="font-size: 3rem; color: white; margin-bottom: 1rem;">🎊 感谢观看！</h1>
    <h2 style="font-size: 2rem; color: white; margin-bottom: 2rem;">Reveal.js 高级特效演示</h2>
    <div style="font-size: 1.5rem; color: white; opacity: 0.9;">
        <p>体验网页演示的无限可能</p>
        <p style="margin-top: 1rem;">使用 Markdown 创建专业演示文稿</p>
    </div>
    <div style="margin-top: 2rem;">
        <span style="background: rgba(255,255,255,0.2); color: white; padding: 1rem 2rem; border-radius: 30px; margin: 0.5rem;">
            🚀 现在开始使用
        </span>
    </div>
</div>
