---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page260
date: 2009-03-20 15:06:00
tags: 我翻译的Head First C#（习作）
---
不可以实例化一个接口，但是可以创建它的引用

  

假设有一个接口定义一个FindFlower（）方法，它要求对象有该方法。任何实现了INectarCollector接口的对象都可以。可以是Worker对象，
Robot对象，甚至Dog对象也可以。

  

接口引用就是做这个用的。你可以用接口的引用指向实现了该接口的类的实例，你可以确保指向的对象一定包含会有你想要的方法--即使你并不怎么了解这个对象。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090320/2009-03-20_14-41-28.jpg)

很显然，你不可以用new关键字来实例化接口，因为其中并没有方法、属性的实现体。如果可以实例化一个接口的话，那创建出来的对象怎么可能知道自己应该如何行为呢？

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090320/2009-03-20_14-47-22.jpg)

第一行是一个普通的new语句，创建一个叫做Fred的引用并把它指向一个NectarStinger对象。

第二行很有趣。它创建了一个IStingPatrol接口类型的引用变量。初看起来有点奇怪。但是看看下面：

  

NectarStinger ginger = fred  ；

  

上面这行代码你肯定懂--它创建一个叫做ginger的NectarStinger的引用并把它指向fred指向的对象。声明george的那一行也是一样。

  

到底是怎么样呢？

  

只有一个new语句，所以只有一个新对象被创建出来。第二个语句创建一个叫做george的引用变量，它可以指向任何实现了IStingPatrol的类的实例。 !
[](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/2009
0320/2009-03-20_15-02-09.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

