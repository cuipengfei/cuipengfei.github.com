---
title: Head First C# 中文版 第13章 控件和图形 page596
date: 2009-08-02 20:19:00
tags: 我翻译的Head First C#（习作）
---
在窗体上绘制一张图片

  

我们来创建一个窗体应用，当你点击窗体的时候，窗体上会绘制出一张图片。

  

如下做：

  

①从给窗体添加  Click  事件处理方法开始

  

事件处理方法从一个  using  语句开始。你使用  GDI+  的时候，会用到很多实现了  IDisposable
接口的对象。如果你不处理这些对象，它们会耗费你的计算机资源，直到你关闭程序为止。所以你需要很多  using  语句：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_20-02-22.jpg)

②注意在窗体上绘制的顺序

  

我们需要天蓝色的背景，所以首先绘制一个蓝色矩形，后面的图形都要绘制在其上。你需要用到窗体的  ClientRectangle  属性，它指明了窗体的边界。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_20-12-11.jpg)

③绘制蜜蜂和花朵

  

你已经知道  DrawImage  （）方法怎么用了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_20-13-58.jpg)

④添加一个用来绘制的  Pen

  

画线的时候，你需要用  Pen  对象来定义颜色和线粗。内置的  Pens  类给你提供很多的  Pen  。你也可以用  Pen
的构造方法来自己创建其实例，它的构造方法需要接受一个  Brush  对象和一个代表线粗的浮点数。  Brush  是用来绘制填充图形的，有一个
Brushes  类，它给你提供了多种颜色的刷子。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_20-18-07.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

