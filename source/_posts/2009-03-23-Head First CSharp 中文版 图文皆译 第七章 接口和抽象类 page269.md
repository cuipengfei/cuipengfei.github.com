---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page269
date: 2009-03-23 08:34:00
tags: 我翻译的Head First C#（习作）
---
向上转型和向下转型也适用于接口

  

你已经知道is和as关键字可以用于接口。其实所有向上转型和向下转型的把戏都可以。我们来给可以加热食物的类添加一个ICookFood接口。再添加一个Micro
wave类--
它和Oven类都实现ICookFood接口。现在你可以用三种方式访问Oven对象，IDE的智能感应可以帮你找出在每种方式下哪些方法、属性是可用的。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090323/2009-03-23_08-12-49.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090323/2009-03-23_08-19-40.jpg) 三个指

向同一个对象的三个引用可以访问不同的方法、属性，而可以访问哪些方法、属性则取决于引用的类型。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

