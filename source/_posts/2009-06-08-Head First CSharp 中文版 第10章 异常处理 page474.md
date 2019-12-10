---
title: Head First C# 中文版 第10章 异常处理 page474
date: 2009-06-08 08:54:00
tags: 我翻译的Head First C#（习作）
---
史上最差的  catch  块：注释

  

如果你想的话一个  catch  块可以保持程序一直运行。异常抛出了，你捕获它，没有关闭程序或者给出出错信息，只是继续运行。但是有时候，没有这种好事。

  

看看下面的除法运算器，看起来很有趣，怎么回事儿呢？

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090608/2009-06-08_08-43-14.jpg)

你应该处理异常，而不是把它遮掩起来

  

你可以让程序继续运行不代表你处理了异常。上面的代码中  Divide
（）方法不会使得程序崩溃。但是如果别的方法调用这个方法并试图打印结果呢？如果除数为零，那么这个方法很有可能返回一个不正确的、未预期的值。

  

你应该处理异常而不是添加注释并掩盖异常。如果你无法处理异常，不要留下一个空的或者写着注释的  catch
块！这会使别人寻错更难。让程序依然抛出异常更好一些，因为这样容易找出问题所在。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

