---
title: Head First C# 中文版 第13章 控件和图形 page568
date: 2009-07-19 17:26:00
tags: 我翻译的Head First C#（习作）
---
给你的架构中添加一个渲染器

  

我们需要另一个类，让它来读取  World  中的信息并据此绘制出蜂巢，蜜蜂和花朵。我们将会添加一个叫做  Renderer
的类来做上述的事情。而由于你的其他类都是封装良好的，所以这并不会导致已有代码的太多修改。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090719/2009-07-19_17-11-00.jpg)

由于Bee，Hive，Flower和World这些类是封装良好的，所以可以添加一个渲染这些对象的类并不会对已有代码产生什么影响。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

