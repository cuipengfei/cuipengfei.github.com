---
title: Head First C# 中文版 第13章 控件和图形 page594
date: 2009-08-02 08:47:00
tags: 我翻译的Head First C#（习作）
---
使用  System.Drawing  来自己处理图形

  

Graphics  属于  System.Drawing  命名空间。  .NET  框架中有很多比工具箱中的  PictureBox
更强大的工具。你可以绘制图形，使用字体，进行复杂的图形计算  ...  这些都从  Graphics
对象开始。你想要添加或者修改任何东西的图形或者图像的时候，你都需要创建一个与该对象相关的  Graphics  对象，然后使用  Graphics
对象的方法来在目标上绘制。

  

①从一个你想要绘制的对象开始

  

以一个窗体为例。调用窗体的  CreateGraphics  （）方法将会返回一个用以在该窗体上绘制的  Graphics  对象。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-01_23-12-54.jpg)

②使用  Graphics  对象地方法来在对象上绘制

  

每个  Graphics  对象的方法都可以让你在创建它的对象上绘制。当你调用  Graphics
的方法来绘制线，圈，矩形，文本或者图像的时候，它们会出现在窗体上。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_08-37-22.jpg)

System.Drawing命名空间下的方法有时候被称作GDI+，是Graphics Device Interface的缩写。用GDI+绘制图形的时候，要用
一个Graphics对象开始，用该对象的方法来在与之联系着的Bitmap，窗体，控件或者其他对象上绘制图形。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

