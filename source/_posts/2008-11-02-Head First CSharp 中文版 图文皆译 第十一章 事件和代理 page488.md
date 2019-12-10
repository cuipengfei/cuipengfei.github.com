---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page488
date: 2008-11-02 14:56:00
tags: 我翻译的Head First C#（习作）
---
把这些点联系起来  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

现在你可以处理发生的事儿了，让我们近距离看看这些东西都是怎么联系起来的。很幸运，只有一些变动的部分。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE05.jpg)

1  我们需要一个事件参数对象

记住，我们的BallInPlay事件带着几个参数。所以我们需要一个很简单的对象。.NET有一个叫做EventArgs的标准类，但是这个类没有成员。它的唯一目
的就是让你可以把你的事件参数对象传递给事件处理者。下面是类的定义：

Public class BallEventArgs:EventArgs

2  然后我们需要定义在类里面被触发的事件

球类里有一行带有event关键字--它就是这样通知其他对象可以注册它的。这一行可以在类里面的任何地方--
它通常靠近属性定义的地方。它在球类里，其他对象就可以注册一个球的事件。看起来是这样的：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE06.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

