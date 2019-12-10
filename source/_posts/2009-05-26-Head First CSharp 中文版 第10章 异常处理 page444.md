---
title: Head First C# 中文版 第10章 异常处理 page444
date: 2009-05-26 18:03:00
tags: 我翻译的Head First C#（习作）
---
你的程序抛出一个异常的时候，  .NET  就会生成一个  Exception  对象

  

你已经看到  .NET  是以何种方式告诉你程序中出错了的：一个异常。在  C#  中，异常发生的时候，就会生成一个代表所发生问题的对象。毫无悬念，它叫做
Exception  。

  

比如，你有一个含有  4  个元素的数组。然后你试着去访问第十六个元素（位序是  15  ，由于我们的数组是从  0  开始的）

  

![](http://student.csdn.net/attachment/200905/26/39098_1243333016Hk78.jpg)

.NET  不辞劳苦的创建一个对象是因为它想要给你提供关于导致异常的全部信息。你可能会根据这些信息来修改错误，或者改变你处理程序中某个状况的方式。

  

在我们的例子中，  IndexOutOfRangeException  提醒你你有一个  bug
：你试图去访问一个越界的数组元素。你也得到了问题出在代码中什么位置的信息，这样跟踪问题就简单多了（即使你有数千行代码）。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

