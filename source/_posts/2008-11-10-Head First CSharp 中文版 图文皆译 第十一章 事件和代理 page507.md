---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page507
date: 2008-11-10 22:03:00
tags: 我翻译的Head First C#（习作）
---
以回调代替事件来把一个对象联系到一个委托  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

我们只有拥有一个球一个球棒才能让系统运行。如果我们有多个球对象，它们都注册了公有事件HitTheBall，事件被触发时它们就会都飞起来。但是这根本不通啊..
.只有一个球被击打了啊。我们需要让被击打的球联系到球棒，又不能让其他的球联系到球棒。这就用到回调了--
这是一种只有调用委托的对象才可以调用委托需要调用的方法，其他的方法不能联系到这个委托的使用委托的方式。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/%E6%88%AA%E5%9B%BE00.jpg)

让球棒把它的委托设置为私有是防止错误的球把它们自己联系到球棒的委托的最简单方式。这样，就可以控制到底是哪个球的方法得以调用了。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/%E6%88%AA%E5%9B%BE01633619514033820000.jpg)

当球进入比赛，它创建一个球棒的实例，并传给球棒一个它的OnBallInPlay（）方法的指针。这叫做回调方法，因为球棒将用它来回调创建它的对象。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/%E6%88%AA%E5%9B%BE02633619514034132500.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/%E6%88%AA%E5%9B%BE03.jpg)

由于球棒把它的委托保持为私有，所以可以百分百保证不会有别的球被击打。为题解决了！

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/%E6%88%AA%E5%9B%BE04.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

