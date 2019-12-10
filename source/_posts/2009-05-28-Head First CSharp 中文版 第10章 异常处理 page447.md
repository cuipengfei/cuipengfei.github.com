---
title: Head First C# 中文版 第10章 异常处理 page447
date: 2009-05-28 10:37:00
tags: 我翻译的Head First C#（习作）
---
④  最后发现  Directory.GetFiles  （）方法在你把它指向一个空文件夹的时候会返回一个空数组。嘿，我们可以测试它！我们可以添加一个检查，
在打开文件之前确保文件夹不为空，然后未处理的异常窗口会被替换为含有丰富信息的  MessageBox  。

  

![](http://student.csdn.net/attachment/200905/28/39098_1243478450828E.jpg)

![](http://student.csdn.net/attachment/200905/28/39098_1243478450Pwst.jpg)

对。异常是一种很有用的工具，你可以用它找到代码中以未预期的方式行为的地方。

  

有时候程序员看见异常就会感觉很沮丧。不过异常是很有用处的，你可以利用它。看见异常的时候，它给出你很多的线索，帮你找出代码中有未预期行为的地方。这对你有好处：
它让你知道你的程序需要处理什么新的状况，并给你一个机会去处理它。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

