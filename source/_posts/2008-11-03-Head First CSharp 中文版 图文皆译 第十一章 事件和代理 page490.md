---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page490
date: 2008-11-03 08:26:00
tags: 我翻译的Head First C#（习作）
---
5  一个球对象触发事件来告诉注册者们它进入比赛了  <?xml:namespace prefix = o ns = "urn:schemas-
microsoft-com:office:office" />

现在事件都设置好了，球可以触发它的事件来回应模拟器里面发生的事情。触发事件很简单--只要调用BallInPlay事件就好了。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
81103/%E6%88%AA%E5%9B%BE00.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
81103/%E6%88%AA%E5%9B%BE01.jpg)

如果你触发一个事件而没有处理者，它将会抛出异常。

如果其他对象没有联系起事件处理者和事件，它将会是null。所以总是在触发事件之前检查你的事件处理者是否为空。如果没有的话，它将会抛出NullReferenc
eException。

标准命名触发事件的方法

进入任何一个窗体的代码，在可以定义方法的地方键入override关键字。你一按空格，一个智能感应窗口就弹出来：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
81103/%E6%88%AA%E5%9B%BE02.jpg)

一个窗体可以触发很多事件，它们每一个都有自己的触发方法。窗体的OnDouble（）触发OnDouble事件，这也是它存在的意义。所以球的事件也要遵循这个惯例
：我们要确保它有一个叫做OnBallInPlay的方法，以BallEventArgs对象作为参数。棒球模拟器会在需要球触发BallInPlay时间的时候调用
这个方法--所以当模拟器检测到球棒击打棒球，它创建一个带有球的距离和轨道的BallEventArgs新实例并把它传递给OnBallInPlay（）。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

