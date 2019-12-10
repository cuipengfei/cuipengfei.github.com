---
title: Head First C# 中文版 第10章 异常处理 page460
date: 2009-06-02 08:34:00
tags: 我翻译的Head First C#（习作）
---
如果你有总是要执行的代码，用  finally  块吧

  

程序抛出异常的时候，很多事情会发生。如果异常未被处理，程序将会停止并崩溃掉。如果异常被处理了，代码将会跳到  catch  块去执行。那  try
块中剩下的代码怎么办呢？如果你在关闭流或者清理重要资源的话怎么办呢？这些代码即使在异常发生情况下也应该执行，否则你就会把程序的状态搞得一团糟。这时
finally  块就显得很方便了。它出现在  try  和  catch  块之后。  finally
块总是得以执行，即使发生了异常也会执行。你要如下这样结束  Random Excuse  按钮的事件处理方法的异常处理：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090602/2009-06-02_08-20-41.jpg)

注意到  catch  后面跟着（  Exception  ）了吗？写  catch
语句的时候，你可以在其后面写一种特定类型的异常来告诉它要捕获什么。如果你指明是（  Exception
）或者留空的话，它将会捕获所有异常。如果你只想要捕获  SerializationException  ，你可以在括号中指明它。或者你可以用
IOException  ，这将会捕获任何的文件输入输出异常。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

