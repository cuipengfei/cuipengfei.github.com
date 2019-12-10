---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page487
date: 2008-11-02 12:28:00
tags: 我翻译的Head First C#（习作）
---
然后，其他对象  处理  事件  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

一旦一个事件被触发，所有注册了这个事件的对象都得到通知，并且做点什么。

4  注册者得到通知

因为投手，裁判和球迷对象都注册了球对象的BallInPlay事件，他们就都得到通知--它们的事件处理方法一个接一个的得到调用。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE03.jpg)

5  每个对象处理事件

现在，投手，裁判，和球迷对象都可以以自己的方式处理事件了。但是它们并不是一起执行--
它们的事件处理者一个接一个的得以执行，都带着一个BallEventArgs对象作参数。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE04.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

