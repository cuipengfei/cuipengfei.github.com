---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page492
date: 2008-11-03 22:26:00
tags: 我翻译的Head First C#（习作）
---
IDE  我为你自动创建事件处理者  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

大多数的程序员都遵循惯例给他们的事件处理者命名。如果有一个含有BallInPlay事件的Ball对象叫做ball，那么事件处理者将会很典型的叫做ball_B
allInPlay（）。这不是不可违背的规矩，但是这么写，别的程序员读起来会容易点。

很幸运，IDE使得给事件处理者合适的命名很容易。IDE有一个特性来给你操作的有事件触发的类自动添加事件处理方法。IDE会做这个，太令人惊讶了--
其实，你双击窗体上的按钮的时候，IDE就是这么做的。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081103/%E6%88%AA%E5%9B%BE00633613479867955000.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081103/%E6%88%AA%E5%9B%BE02633613479876392500.jpg)

向项目中添加一个投手类。然后给它一个构造器，以一个叫做ball的Ball的引用作为参数。向ball.BallInPlay添加事件处理只会用一行代码。开始写代
码，但是先别用+=。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081103/%E6%88%AA%E5%9B%BE03.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

