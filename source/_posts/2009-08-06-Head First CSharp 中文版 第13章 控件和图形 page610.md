---
title: Head First C# 中文版 第13章 控件和图形 page610
date: 2009-08-06 14:50:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_13-37-19.jpg)

该是时候解决掉蜂巢模拟器中的小故障了。使用双缓冲来把模拟器弄得漂亮一点。

  

①改变主窗体的  RunFrame  （）方法

  

你需要移除对于  Renderer.Recder  （）的调用并添加两个  Invalidate  （）调用。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_13-45-13.jpg)

②给主窗体再添加一个  Timer  来使得蜜蜂的翅膀扇动

  

拖拽一个  Timer  到主窗体上去，设置其  Interval  为  150  ，设置其  Enabled  为  true
。然后双击它并添加如下的事件处理方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_13-53-09.jpg)

然后把这个  AnimateBees  （）方法添加到渲染器中去来使得蜜蜂的翅膀扇动：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_13-54-13.jpg)



