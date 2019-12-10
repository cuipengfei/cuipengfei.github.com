---
title: Head First C# 中文版 第10章 异常处理 page468
date: 2009-06-05 16:14:00
tags: 我翻译的Head First C#（习作）
---
蜜蜂需要一个新的  OutOfHoney  异常

  

你的类可以抛出自定义的异常。比如说，如果你在方法接受到了一个  null  参数，而需要的是一个值，在此处与  .NET
中的方法抛出一样的异常是很常见的：

  

throw new ArgumentNullException( );

  

但是有时候你会想要让程序根据遇到的某种特殊情况来抛出异常。举例来说，我们创建的蜂窝中的蜜蜂，它们根据自己的体重以不同的速率消耗蜂蜜。如果没有蜂蜜可消耗了，让
蜂窝抛出异常是很说得通的。你可以创建一个自定义异常来处理这种特殊情况的错误，只需要让你自己的类继承自  Exception
并且在遇到这种情况的时候抛出它就可以了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090605/2009-06-05_15-12-40.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

