---
title: Head First C# 中文版 第12章 回顾与前瞻 page533
date: 2009-06-15 11:27:00
tags: 我翻译的Head First C#（习作）
---
蜂巢的  Go  （）方法

  

我们给  Flower  类写了一个  Go  （）方法，给  Bee  类也写了一个  Go  （）方法（虽然还有一些代码要补充）。下面是  Hive
类的  Go  （）方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090615/2009-06-15_10-55-05.jpg)

不幸的是，这不太现实。很多时候，蜂后没有时间来生育幼蜂。我们没有一个  QueenBee
类，但是我们来假设蜂蜜足够的时候会有十分之一的几率去创建一只幼蜂。我们可以如下来写：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090615/2009-06-15_11-04-31.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090615/2009-06-15_11-06-50.jpg)

问：蜂巢可以创建无数的幼蜂？

  

答：现在是可以的  \--  当然，这不太现实。稍后我们会回来添加约束来只允许一定数量的蜜蜂同时存在于蜂巢中。

  

问：我们不可以把  Random  的实例赋值给一个类的属性吗？而不是把它作为参数传递给  AddBee  （）方法？

  

答：可以。这样  AddBee  方法就可以使用属性值而不是参数了。这个问题没有确定答案。随你喜欢。

  

问：我还是不知道这些  Go  （）方法要怎么得以调用。

  

答：这没关系，我们就快要讲解这一点了。不过，首先我们还需要一个类  \--World  ，它将会管理蜂巢中的一切，还有蜜蜂，甚至还有花朵。

  

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

