---
title: Head First C# 中文版 第10章 异常处理 page472
date: 2009-06-07 12:51:00
tags: 我翻译的Head First C#（习作）
---
预防异常：实现  IDisposable  来自己做清理

  

流很好用，因为它们已经有代码来在它们被处置的时候关闭自己。但是如果你自己写了类需要在被处置的时候做某些处理呢？如果你自己写的类中的某些代码可以再
using  中得以运行岂不是很酷？

  

使用  IDisposable  接口就可以在  C#  中做到这一点。只有实现了  IDisposable  接口的类才可以  using
中使用，否则无法编译。实现  IDisposable  接口，把你自己的清理代码写在  Dispose  （）方法中，就象下面这样：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90607/2009-06-07_12-37-12.jpg)

我们可以使用多重的  using  语句。首先，使用一个内建的实现了  IDisposable  的类型，  Stream  。然后，操作我们更新了的
Nectar  对象，它也实现了  IDisposable  ：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90607/2009-06-07_12-44-41.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

