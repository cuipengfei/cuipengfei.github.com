---
title: Head First C# 中文版 第10章 异常处理 page448
date: 2009-05-28 16:56:00
tags: 我翻译的Head First C#（习作）
---
所有的异常都继承自  Exception

  

.NET  有很多种异常。由于很多异常有许多相似的特性，那这儿就用的上继承了。  .NET  定义了一个基类，叫做  Exception
，所有的异常类都要继承自它。

  

Exception  类有两个很有用处的成员。  Message  属性存储了关于出了什么问题的简单易读的信息。  StackTrace
属性告诉你异常发生的时候内存中的情况和导致问题的原因。（  Exception  还有一些其他属性，我们暂时只用到这两个）

  

![](http://student.csdn.net/attachment/200905/28/39098_1243501005nr8R.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





