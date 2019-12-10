---
title: Head First C# 中文版 第九章 读写文件 page405
date: 2009-05-06 12:52:00
tags: 我翻译的Head First C#（习作）
---
IDisposable  确保你的对象被适当的销毁掉

  

.NET  中的很多类都实现一个很有用的接口，它叫做  IDisposable  。它只有一个成员：一个叫做  Dispose  （）的方法。一个类实现
IDisposable  接口也就是告诉你它需要做一些紧要的事情来把自己结束掉，通常是因为其中有一些已分配的资源必须要显式的释放。  Dispose
（）方法就是用来让对象释放资源的。

  

IDisposable  接口的定义如下：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90506/2009-05-06_12-38-00.jpg)

在using语句块中声明的对象的Dispose（）方法将

会被自动的调用。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

