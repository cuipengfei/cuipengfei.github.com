---
title: Head First C# 中文版 第13章 控件和图形 page590
date: 2009-07-31 20:23:00
tags: 我翻译的Head First C#（习作）
---
仔细研究一下那些性能问题

  

你下载的那些蜜蜂的图片都很大。这就意味着  PictureBox
在显示它们的时候需要缩放，而缩放图片需要耗费很多时间。很多蜜蜂在蜂巢中飞舞的时候程序会变慢的原因是因为蜂巢的图片很  巨大  。当你把
BeeControl  的背景设置为透明的时候，有两件事儿要做：首先需要缩小图片，然后需缩小窗体背景的一部分，这样就可以把它绘制到蜜蜂背后的透明区域。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090731/2009-07-31_20-13-45.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090731/2009-07-31_20-16-34.jpg)

所以我们需要做的就是在显示之前首先把图片缩小。



