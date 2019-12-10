---
title: Head First C# 中文版 图文皆译 第六章 继承 page219
date: 2009-03-01 22:23:00
tags: 我翻译的Head First C#（习作）
---
想一想怎么给动物们分组

Aged Vermount  奶酪是一种奶酪，奶酪是一种乳制品，乳制品是一种食物，一个好的类模型要可以表现出这点来。很幸运，C#给我们一种简单的方式来实现这
一点。你可以创建一系列互相继承的类，自顶向下的继承。你可以有一个Food类，它有一个子类叫做DairyProduct，而DairyProduct又是Chee
se的基类，Cheese又有一个叫做Cheddar的子类，AgedVermountCheddar就是继承自Cheddar。

④  寻找有很多共同点的类

狼和狗是不是看起来很相似？它们都是犬科动物，去看看它们的行为，肯定有很多的共同点。它们有可能吃一样的食物，睡觉的方式也相似。家猫，狮子和老虎又怎么样呢？事实
是它们都以相同的方式绕着自己的栖息地转悠。在Animal和这三个猫科动物的类之间有一个Feline（猫科动物）类肯定会对于避免冗余代码有帮助。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090301/2009-03-01_22-11-24.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

