---
title: Head First C# 中文版 第10章 异常处理 page453
date: 2009-05-30 10:24:00
tags: 我翻译的Head First C#（习作）
---
啊  ...  代码还是有问题  ...

  

Brian  用借口管理器用的很开心，突然想起来最初创建这个程序的时候曾经创建了一个装满借口文件的文件夹  \--
但是他忘记了那个文件夹是创建在他给程序添加序列化能力之前的。我们来看看发生什么事了  ...

  

①你可以通过用记事本创建基于文本的借口文件来重现  Brian  的问题。文件中第一行写描述，第二行写结果，第三行写最后使用日期。

  

②打开借口管理器并打开借口文件。抛出异常了！但是这次，点击  Details  按钮，这样我们就可以仔细观察过一下它说什么了。注意调用栈  \--
它是指一个方法是被另一个方法调用的，而这个方法又是被另外一个方法调用的，等等等等。

  

![](http://student.csdn.net/attachment/200905/30/39098_1243650287znzv.jpg)

③未处理的异常窗口上的  Details  按钮给你提供了导致问题的信息。你可以想出要如何处理它吗？

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

