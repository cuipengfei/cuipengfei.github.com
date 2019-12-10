---
title: Head First C# 中文版 第10章 异常处理 page471
date: 2009-06-06 21:21:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090606/2009-06-06_20-56-30.jpg)

■如果在运行时某些事情出错的话，任何语句都会抛出异常。

  

■用  try/catch  块来处理异常，未处理的异常会是你的程序停止并弹错误窗口。

  

■  try  块中的任何异常都会导致程序的执行跳转到  catch  块中的第一个语句。

  

■  Exception  对象给你提供关于捕获的异常的信息。如果你在  catch  块中声明了  Exception  变量的话，该变量将会包含
try  块中抛出的任何异常的信息。

  

■你可以捕获很多种的异常。每种异常都是继承自  Exception  类的。不要捕获  Exception  ，去捕获特定种类的异常。

  

■每个  try  块可以搭配多于一个  catch  块。

  

■你可以用  throw  在代码中抛出一个异常。

  

■你可以通过继承自  Exception  类来创建一个自定义异常。

  

■多数情况下，你只需要抛出  .NET  内建的异常，比如  ArgumentException  。你使用不同的异常的目的就是为了给用户提供更多的信息。弹
出一个写着“未知错误”窗口可不如一个写着“借口文件夹为空。如果想要读取借口请选择另一个的文件夹”的出口来的有用。

  

避免问题的捷径：  using  关键字给你免费提供  try  和  catch

  

记住，在  using  语句中声明一个引用的话，其  Dispose  （）方法将在代码块的最后自动被调用。你已经知道了  using
是一个确保文件流被关闭的简单方式。但是你不知道的就是它是  try  和  catch  在  C#  中的使用捷径！

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090606/2009-06-06_21-12-47.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

