---
title: Head First C# 中文版 图文皆译 第五章 封装 page185
date: 2009-02-07 14:36:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090207/%E6%88%AA%E5%9B%BE00.jpg)

封装意味着把类中的某些数据保持为私有

有一个简单的方式可以避免这类问题：确保只有一种方式来使用你的类。幸运的是，C#通过允许你把字段设置为private来解决这类问题。迄今为止，你只见过公有字段
。如果你的对象有一个公有字段，那么其他对象都可以读取或者改变这个字段。如果这个字段是私有的，那么这个字段就只可以在对象内部访问（或者另一个同类的对象）。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090207/%E6%88%AA%E5%9B%BE01.jpg)

把保持聚会人数的字段设置为private，就使得窗体只有一个方式来告诉DinnerParty聚会有多少人数--
并且我们可以确保装饰费用被合适的重新计算。当你把某些数据设置为private的时候，这就叫做封装。

封装的，形容词。

用衣料或者羊皮做保护性

的包装。潜水员被自己的

潜水服很好的封装起来，并

且只可以通过气门进出。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

