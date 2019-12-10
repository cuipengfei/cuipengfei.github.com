---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page264
date: 2009-03-21 18:28:00
tags: 我翻译的Head First C#（习作）
---
机器蜂4000可以完成工蜂的工作而无须消耗蜂蜜

  

我们来创建一个新的蜜蜂，叫做RoboBee4000，它以汽油作能源。我们可以让它实现IWorker接口，这样它就可以完成工蜂的工作了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090321/2009-03-21_18-05-04.jpg)

记住，对于应用中的其它类来说，RoboBee和普通的工蜂并没有什么功能上的区别。它们都实现了IWorker接口，所以对于相关的程序来说它们工作起来是一样的。

  

但是你可以用is关键字来区分类型：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090321/2009-03-21_18-16-50.jpg)

一个类实现一个接口的前提就是要实现接口中定义的所有方法、属性。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

