---
title: Head First C# 中文版 第12章 回顾与前瞻 page539
date: 2009-06-19 21:50:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90619/2009-06-19_21-34-46.jpg)

问：为什么在  GetLocation  （）方法中抛出了一个异常呢？

  

答：为了防止传入的参数不合法。这样在传入的参数不合法的时候就会爆出异常，可以帮助你调试错误。

  

问：既然我们不把蜜蜂绘制出来那为什么还要把位置存储在  Point  对象里面呢？

  

答：无论绘制与否，蜜蜂都需要记录自己的位置。这样就可以知道自己是否到达了目的地。

  

问：为什么要把位置存储在  Point  里面呢？  Point  不就是用来绘制的吗？

  

答：对的，所有的可视化控件都是使用  Point  来存储位置。然而，  .NET
如此使用它并不意味着我们不可以用它来存储位置。当然，我们可以自己创建一个  BeeLocation  ，内涵  X  和  Y  坐标。但是  .NET
已经给我们提供了  Point  了，我们没必要再次去重复发明轮子。

  

扩展或者改用一个已经存在的可以实现你

  

的多数目的的类总是好于重头开始自己写

  

一个新类。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

