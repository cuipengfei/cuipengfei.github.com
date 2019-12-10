---
title: Head First C# 中文版 图文皆译 第十一章 事件和委托 page508
date: 2008-11-11 08:46:00
tags: 我翻译的Head First C#（习作）
---
回调使用委托，不用事件  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

回调是使用委托的另一种方式。它不是一个新的关键字或运算符。它只是描述了一种模式--这是一种使用类中委托的方式，一个对象告诉另一个“发生这个事儿的时候告诉我
--别告诉别人！”。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE00.jpg)

由于球棒会有一个私有的委托字段指向球对象的OnBallInPlay（）方法，所以我们要有一个匹配它的签名的委托：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE01.jpg)

球棒类很简单。它有一个HitTheBall（）方法，模拟器在每次有球被击打时都会调用它。HitTheBall（）方法使用hitBallCallback（）的
委托去调用球的OnBallInPlay（）方法（或者任何被传入到它的构造器的方法）。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE02.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE03.jpg)

怎么在球棒的构造器里获得一个球的OnBallInPlay（）方法的引用呢？简单--
只需要调用球的GetNewBat（）方法，这就要求你添加下面的代码到球类里:

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE04.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

