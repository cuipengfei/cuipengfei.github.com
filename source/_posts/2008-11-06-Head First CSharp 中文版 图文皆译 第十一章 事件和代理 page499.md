---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page499
date: 2008-11-06 18:47:00
tags: 我翻译的Head First C#（习作）
---
单事件，多处理  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

这个是你可以用事件来做的一件很有用的事：把它们连成串，这样一个事件或者代理就可以调用多个方法，一个接一个。我们来向你的窗体添加几个按钮来看看他怎么工作。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE02633615940568111250.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE03633615940568580000.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE04633615940569048750.jpg)

怎么样了？

每次你点击一个按钮，都会联动另一个方法--或者是Something（）或者是SomethingElse（）--到窗体的点击事件上去。你可以一直点击按钮，这将
会一直联动相同的方法到事件上去。事件不在乎有多少方法被联系到它身上，或者是一个方法多次被联系到它。它只会每次被触发时调用它们，一个接一个的，以他们被添加的顺
序。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE05.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

