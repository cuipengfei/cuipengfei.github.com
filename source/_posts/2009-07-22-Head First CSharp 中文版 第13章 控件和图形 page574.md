---
title: Head First C# 中文版 第13章 控件和图形 page574
date: 2009-07-22 22:27:00
tags: 我翻译的Head First C#（习作）
---
BeeControl  和  PictureBox  很类似  ...  所以我们就从继承  PictureBox  开始吧

  

工具箱中的控件都是对象，所以要创建一个新的控件也很简单。你只需要创建一个新类让它继承一个已有的控件并添加需要的行为即可。

  

我们需要一个叫做  BeeControl  的控件，它显示一只扇动翅膀的蜜蜂。不过我们首先从显示一个不会动的图片开始。

  

如下做：

  

①创建一个项目并把四个动画单元添加到项目的资源中去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090722/2009-07-22_22-19-42.jpg)

②我们已经绘制好了四幅图，你可以在  www.headfirstlabs.com/books/hfcsharp  下载到。把它们添加到资源中去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090722/2009-07-22_22-24-56.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

