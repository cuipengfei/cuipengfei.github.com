---
title: Head First C# 中文版 第12章 回顾与前瞻 page529
date: 2009-06-14 15:31:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90614/2009-06-14_15-02-27.jpg)

编写  Hive  的代码是你的任务。

  

①编写  Hive  类的框架代码

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90614/2009-06-14_15-07-41.jpg)

就像编写  Flower  类时一样，你应该开始先给  Hive  类写一个骨架代码。类图如右图所示。把  Honey  设置为只读的自动属性，
locations  要是私有的，  beeCount  只在内部使用，所以也可以是私有的字段。

  

②定义  Hive  的常量

  

初始蜜蜂数（  6  ）需要一个常量，开始时候的蜂蜜数量（  3.2  ），蜂巢最多可以承载的蜂蜜数（  15  ），花粉向蜂蜜转化率（  0.25
），最大蜜蜂数（  8  ），可以生育幼蜂的最小蜂蜜数（  4  ）。

  

你需要给这些常量想出好的名字和适合的类型。关于类型，不要只是关注其初始值，还要注意这些常量会和哪些值一起使用。  Double  最好和  Double
一起使用，  int  最好和  int  一起使用。

  

③编写代码来操作位置

  

首先，编写  GetLocation  （）方法。它要接受一个  string  ，在  locations
字典中查找并返回相关的地点。如果查找不到，抛出一个  ArgumentException  。

  

然后，编写  InitializeLocation  （）方法。这个方法会设置蜂巢中的下列地点：

  

◆入口（  600  ，  100  ）

◆保育场所（  95  ，  174  ）

◆蜂蜜工厂（  157  ，  98  ）

◆出口（  194  ，  213  ）

  

这些中的每一个都会映射到蜂巢占用的  2D  空间中的一个点。稍后我们要确保模拟器覆盖所有这些点。这个模拟器中，我们假设只有一个蜂巢，点都是固定的。如果你要
做多个蜂巢，你可能会想要把点设置为相对于蜂巢坐标而不是整个世界坐标的。

  

④创建  Hive  的构造方法

  

当一个蜂巢初始化的时候，会把蜂蜜量设置为初始值。需要设置蜂巢中的所有地点，还需要创建一个  Random  的新实例。然后，要调用  AddBee
（）方法  \--  每次创建一个幼蜂都要传入刚刚创建的  Random  实例。

  

AddBee  （）需要一个  Random  对象，是因为它要在保育场所中添加一个随机点  \--  这样幼蜂才不会重叠在一起。

  

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

