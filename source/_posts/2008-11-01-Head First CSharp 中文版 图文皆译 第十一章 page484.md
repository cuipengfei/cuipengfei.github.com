---
title: Head First C# 中文版 图文皆译 第十一章 page484
date: 2008-11-01 16:50:00
tags: 我翻译的Head First C#（习作）
---
你希望你的东西会为它们自己考虑吗？  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

假设你在写一个棒球模拟器。你要去建模一个游戏，卖给洋基队（你很财大气粗，对吧？），赚他个一百万美元。你创建了球，投手，裁判，和球迷对象，还有很多。你甚至编码
让投手可以抓球。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081101/%E6%88%AA%E5%9B%BE02.jpg)

现在你需要把所有东西联系起来。你给球添加了一个OnBallInPlay（）方法，现在你想让你的投手东西回应它的事件处理方法。一旦方法写好了，你只需要把方法连
到一起：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081101/%E6%88%AA%E5%9B%BE03.jpg)

一个对象怎么知道要去回应呢？

有个问题。你想你的球只关心被击打，投手只关心接球。换句话说，你不希望球去告诉投手，“我冲你来喽”。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081101/%E6%88%AA%E5%9B%BE04.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





