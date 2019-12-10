---
title: Head First C# 中文版 第12章 回顾与前瞻 page535
date: 2009-06-16 10:11:00
tags: 我翻译的Head First C#（习作）
---
我们在构建一个回合制的系统

  

每个对象的  Go  （）方法都应该在我们的模拟器中的每一轮，或者叫做每一个回合中被调用。每一轮可以是任意的一段时间  ...  比如说，一轮可以是  10
秒，或者  60  秒，或者是  10  分钟。

  

每一轮在动画中表现为一帧，所以世界每一轮只改变一小点。

  

关键是每一轮都会影响世界中的每一个对象。蜂巢每一轮检查是否需要增加更多的蜜蜂。然后每只蜜蜂运行一轮，向着目的地移动一点点距离或者是做一个小动作，并变老。然后
每朵花运行一轮，产生一点花粉并变老。这正是  World  做的：它确定每一次  World  的  Go  （）方法被调用，每个对象都会获得一轮的行动。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090616/2009-06-16_10-00-14.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090616/2009-06-16_10-06-59.jpg)

我们在模拟器中用到的面向对象的一个重要原则就是封装。看看你可不可以根据观察我们已经写过的类来找出每个类中两个封装的例子。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090616/2009-06-16_10-10-07.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

