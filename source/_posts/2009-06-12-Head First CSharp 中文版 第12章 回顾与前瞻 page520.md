---
title: Head First C# 中文版 第12章 回顾与前瞻 page520
date: 2009-06-12 09:23:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090612/2009-06-12_08-57-34.jpg)

我们来直接跳到代码。首先，我们需要一个  Flower  类。  Flower  类需要一个由  Point
定义的位置，有年龄，还有寿命。随着时间的流逝，鲜花会变老。然后，当年龄到达了寿命值的时候，花朵会死去。你的任务就是把这些实现出来。

  

①写出  Flower  类的框架代码

  

下面是  Flower  类的类图。写出基本的类框架，  Location  ，  Age  ，  Alive  ，  Nectar  和
NectarHarvested  都是自动属性。  NectarHarvested  是可写的，其他四个都是只读的。现在把方法留空；我们稍后会回来处理它们。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090612/2009-06-12_09-08-25.jpg)

②给类添加一些常量

  

我们需要给花朵很多常量。给你的  Flower  类添加六个：

  

◆  LifeSpanMin  ，花朵的最短寿命

◆  LifeSpanMax  ，花朵的最长寿命

◆  MaxNectar  ，一朵花朵可以含有多少花粉

◆  NectarAddedPerTurn  ，花朵每次变老的时候会增加多少花粉

◆  NectarGatheredPerTurn  ，一个生命周期中有多少花粉会被采集

  

（顺便说一下，常量通常不画在类图中）

  

你应该可以根据每个常量的值来推测出它们的类型。花朵可以活过  15000  到  30000  个生命周期，刚刚开放的时候含有  1.5
单位的花粉。最多可以含有  5  单位的花粉。每个生命周期中，花朵会增加  0.01  单位的花粉，而每个生命周期中，可以被采集的花粉是  0.3  单位。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

