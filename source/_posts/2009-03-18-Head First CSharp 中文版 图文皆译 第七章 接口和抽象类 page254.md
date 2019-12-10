---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page254
date: 2009-03-18 13:33:00
tags: 我翻译的Head First C#（习作）
---
接口要求类必须实现某些方法、属性

  

一个类只可以继承自一个类。所以如果需要一种蜜蜂即会做StingPatrol的工作又会做NectarCollector的工作的时候，给这两个类分别创建一个子类
是不行的。

  

蜂后的DefendTheHive（）（保护蜂巢）方法只可以让StingPatrol对象去保护蜂巢。蜂后希望训练别的蜜蜂让它们也会用毒刺蜇人，但是蜂后无法命令
别的蜜蜂去保护蜂窝：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90318/2009-03-18_13-07-54.jpg)

NectarCollector  对象懂得如何采集花粉，StingPatrol对象懂得如何防护蜂巢。即使蜂后可以通过给NectarCollector添加Sh
arpenStinger（）和LookForEnemies（）方法来让花粉采集者学会防护蜂巢，但是蜂后还是无法把NectarCollector作为参数传入D
efendTheHive（）方法。或许蜂后可以用两个各版本的方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90318/2009-03-18_13-12-51.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90318/2009-03-18_13-19-44.jpg)

但是这个解决方法并不好。这两个方法几乎是一样的，它们都要都要被传入的参数的相同的方法。这两个方法唯一区别就是参数类型不同。你早就知道维护两个相同的方法是多么
麻烦了。幸运的是，C#提供了接口来处理这种情况。接口定义其实现类必须实现的方法。接口要求实现它的类必须包含它定义的方法，否则，编译器将会报错。实现类可以直接
编码这些方法，或者从父类继承来也可以，接口并不关心这一点。只要编译的时候可以找到这些方法就可以了。

接口要求其实现类实现接口中定义的所有方法和属性，否则，编译器将会报错。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

