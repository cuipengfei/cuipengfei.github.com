---
title: Head First C# 中文版 第九章 读写文件 page406
date: 2009-05-06 22:54:00
tags: 我翻译的Head First C#（习作）
---
用  using  语句来避免文件系统错误

  

我们在这一章里一直在给你说要关闭流。这是因为程序员在处理文件的时候犯下的错误大多数是因为没有恰当的关闭流。很幸运的是，  C#
给你提供了一个强大的工具来防止上述情况：  IDisposable  接口和  Dispose  （）方法。把有关流的代码写在  using  语句（这儿的
using  语句和代码顶端用于添加引用的代码不同）中可以使你的流得以自动关闭。你只需要在  using
语句中声明流的引用，然后在一个代码块（被大括号包围）中使用引用就可以了。你这样做的话，  using  语句将会在代码块中的代码执行完毕的时候自动调用流的
Dispose  （）方法。下面是示例：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90506/2009-05-06_22-40-07.jpg)

每个流都有一个用于关闭流的  Dispose  （）方法。所以如果你在  using  语句中声明流，它将总可以关闭自己！

用多个  using  语句来使用多个对象

  

你可以嵌套使用  using  语句  \--  无需使用额外的括号或者缩进。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90506/2009-05-06_22-51-44.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

