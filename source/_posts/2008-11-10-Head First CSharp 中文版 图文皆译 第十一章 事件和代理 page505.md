---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page505
date: 2008-11-10 13:20:00
tags: 我翻译的Head First C#（习作）
---
任何对象都可以注册一个公有事件  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

假设我们添加了一个新类进模拟器去，一个球棒类，它有一个HitTheBall事件。它是这样工作的：如果模拟器发现球手击球，就调用球棒对象的OnHitTheBa
ll（）方法，这将会触发一个HitTheBall事件。

所以现在我们可以添加一个bat_HitTheBall方法到球类，它注册球棒的HitTheBall事件。然后击球时，球的事件处理器调用它的OnBallInPl
ay（）方法来触发BallInPlay事件，连锁反应就开始了。守场员守场，球迷欢呼，裁判大吼...我们就有了一场球赛。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/sec.jpg)

...  但是并那不是总是很好！

球赛里并不是只有一个球。但是如果球棒用一个事件来代表它击打的球，那么所有的球都可以注册它了。这意味着我们让自己陷入了一个险恶的小bug--
如果程序员恰巧添加了另外三个球会怎么样呢？那么击球手就会晃动，击球，会有四个球飞入球场！

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/sec2.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

