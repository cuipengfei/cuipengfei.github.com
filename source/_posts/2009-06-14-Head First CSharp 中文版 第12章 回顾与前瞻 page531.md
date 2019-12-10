---
title: Head First C# 中文版 第12章 回顾与前瞻 page531
date: 2009-06-14 22:21:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90614/2009-06-14_21-55-53.jpg) 真正的代码是一点一点写出来的

如果可以一次性写完一个类的代码，编译，测试，然后把它放到一边，再来写另一个类的话自然是很好的。不幸的是，这是不可能的。

通常，你会像我们这一章一样写代码：一点一点的。我们可以创建差不多整个的  Flower  类，但是到了  Bee
类的时候，我们就还有一些工作要做（多数就是告诉蜜蜂在每一种状态之下做什么）。

现在，对于  Hive  来说，我们需要填充很多空方法。还有，我们还没有把  Bee  和  Hive  联系起来。还有一个麻烦问题就是如何成千次的调用
Go  （）方法。

首先设计，然后创建 ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/Entry
Images/20090614/2009-06-14_22-09-38.jpg)

我们开始这个项目的时候知道我们想要的是什么：一个蜂巢模拟器。而且知道很多关于蜜蜂，花朵，蜂巢还有世界是如何一起工作的事情。所以我们从架构开始，这告诉我们类之
间如何协同工作。然后再去关心每一个类，单独设计每一个类。

如果你在创建项目之前知道要创建什么，项目往往会进行的更加顺利。这看起来很简明易懂而且是常识性的。但是它对最终产品至关重要。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

