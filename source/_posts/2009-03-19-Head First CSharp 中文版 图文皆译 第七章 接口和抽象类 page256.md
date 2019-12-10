---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page256
date: 2009-03-19 09:07:00
tags: 我翻译的Head First C#（习作）
---
现在你可以创建一个会保卫蜂巢的NectarCollector对象了

  

用冒号声明实现一个接口，就像继承一个类一样。具体如下：冒号后面首先写该类要继承的类，然后写所有需要实现的接口--
如果不需要继承自任何类的话，就只写所有需要实现的接口（顺序无限制）。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90319/2009-03-19_08-28-10.jpg)

一个实现了接口的类和其他类并无二致。你可以用new来实例化它也可以调用它的方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90319/2009-03-19_08-41-47.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90319/2009-03-19_08-42-10.jpg)

** 问：我还是看不出接口对于我们的代码有什么促进。还是需要写NectarCollector类，还是会有冗余代码...对吧？  **

  

答：接口不是用来防止冗余代码的。它的作用是让一个类可以在多种情况下使用。我们的目的是让一个蜜蜂可以做两种工作。你确实还是需要创建NectarCollecto
r类--但是这不是重点。重点是现在你可以让一个类做多种工作了。我们假设PatrolTheHive（）方法接受一个StingPatrol类型的参数，还有一个C
ollectNectar（）方法接受NectarCollector参数。但是你不想要让StingPatrol继承NectarCollector，也不想让Ne
ctarCollector继承StingPatrol--
因为这两个类都有对方不应该具有的方法、属性。放下书仔细想想应该怎么创建一个类，才能让它的实例可以被传递进两个方法中呢？知道吗？

  

接口可以解决这个问题。你可以创建一个IstingPatrol的引用--
它可以指向任何实现了该接口的类的实例。这样就可以调用其指向的实例实现的接口中定义的的方法、属性。而不需要管具体是哪个类的实例。

接口只是解决方案的一部分。你还是需要创建实现这个接口的类，因为接口中并没任何实现代码。接口并不能用来防止创建额外类或者防止冗余代码。它是用来使得一个类可以做
不止一种工作而无须继承，因为继承会带来很多额外的负担。

  

你可以想到使用接口的同时还可以防止冗余代码的方法吗？你可以创建一个叫做Stinger或者Proboscis的类，它包含采集花粉或者巡逻的特定代码。Necta
rCollector和StingPatrol可以创建一个它的实例，在需要做某种工作的时候就调用它的方法、属性。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

