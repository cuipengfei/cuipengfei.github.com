---
title: Head First C# 中文版 第12章 回顾与前瞻 page547
date: 2009-06-26 16:57:00
tags: 我翻译的Head First C#（习作）
---
Timer  在幕后使用一个委托

  

C#  和  .NET  如何告诉  Timer  每过一个时间段要做什么？  Timer  每次是如何调用  timer1_Tick
（）方法的？我们要像上一章一样去讨论事件和委托了。使用  IDE  的“转到定义”功能来复习一下事件处理委托是如何工作的：

  

④右击  timer1  变量并选择“转到定义”

  

“转到定义功能”可以让  IDE  自动跳到  timer1  变量被定义的代码处。  IDE  将会跳到创建并添加  timer1  为  Form1
的属性的地方。在该文件中向下滚动，直到你看见下面这一行：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090626/2009-06-26_16-19-38.jpg)

⑤现在右击  EventHandler  并选择“转到定义”

  

IDE  将会跳转到定义  EventHandler  的代码。看看显示代码的新标签页的名字：“  EventHandler[  从原数据  ]
”。这意味着定义  EventHandler  的代码不存在于你的代码中。它是  .NET  框架内建的，而且  IDE
生成了一行“伪”代码来给你看它是如何被声明的：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090626/2009-06-26_16-51-34.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

