---
title: Head First C# 中文版 第13章 控件和图形 page573
date: 2009-07-21 20:41:00
tags: 我翻译的Head First C#（习作）
---
创建你自己的第一个动画控件

  

你要创建一个动画的控件，它显示一只会动的蜜蜂。这并没有听上去那么难：一张接一张的绘制图片就可以产生动画的效果了。我们很幸运，  C#  和  .NET
处理资源的方式使得这件事儿很简单。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090721/2009-07-21_20-30-47.jpg)

我们想要一个可以放入工具箱的控件

  

如果你正确的创建了  BeeControl  ，它将会出现在工具箱里，和你可以拖拽的那些控件一样。它看起来就会类似一个显示着蜜蜂的  PictureBox
，只是翅膀在挥动。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090721/2009-07-21_20-37-07.jpg)



