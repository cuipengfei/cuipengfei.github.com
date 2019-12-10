---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page372
date: 2009-04-20 14:42:00
tags: 我翻译的Head First C#（习作）
---
找到共同行为：移动

  

你已经知道冗余代码的坏处了，而冗余代码通常在多个对象含有同样的行为的时候出现。我们的游戏正是这样...玩家和敌人都要移动。

  

我们来创建一个Mover类，把共同的行为都抽象进去。Player和Enemy都要继承它，虽然武器不会移动，也要继承Mover，因为武器需要其中的一些方法、属
性。Mover有一个Move（）方法用来四处移动，一个只读的Location属性，窗体用它来摆放Mover的子类。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090420/2009-04-20_14-26-44.jpg)

添加一个Direction枚举

  

Mover  类和其他一些类都需要一个Direction枚举，其中要包含四个枚举值：Up,Down,Left和Right。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

