---
title: Head First C# 中文版 第10章 异常处理 page467
date: 2009-06-05 09:07:00
tags: 我翻译的Head First C#（习作）
---
一个类抛出异常，另一个类捕获

  

你在创建类的时候并不知道它会怎么样的被使用。有时候其他人会以一种导致问题的方式来使用你的类  \--  有时候你自己也会这么做！这时就用到异常了。

  

抛出异常的目的就是要去发现有什么问题，以便做一些计划。一般你不会看到一个方法自己抛出异常并捕获它。通常是一个方法抛出异常然后另一个方法捕获  \--
这个方法又通常是另一个对象中的。

  

不要如下这样做  ...

  

没有好的异常处理，一个异常会把整个程序都拖垮。下面是一个没有异常处理的例子：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090605/2009-06-05_08-51-52.jpg)

...  要如下这样做

  

BeeProfile  对象可以截获异常并写一条日志记录。然后转身把它抛给  Hive  对象，这样就可以捕获异常并优雅的回去继续运行了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090605/2009-06-05_09-00-00.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

