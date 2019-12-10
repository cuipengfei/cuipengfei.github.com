---
title: Head First C# 中文版 第10章 异常处理 page456
date: 2009-05-31 22:01:00
tags: 我翻译的Head First C#（习作）
---
当你要调用的方法有风险的时候会怎么样？

  

用户的行为是不可预期的。他们会给你的程序输入各种奇怪的数据，还会以你不可预期的方式点击一些东西。不过这也不要紧，因为你可以用良好的异常处理来处理未预期的输入
。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090531/2009-05-31_21-43-03.jpg)

①我们来假设用户正在使用你的代码，并给出了一些程序未预期的输入。

  

②这个方法会做一些有风险的事情，这些事情在运行时可能会无法运行。（“运行时”指“当你的程序正在运行的时候”。有些人把异常叫做“运行时错误”）。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090531/2009-05-31_21-43-17.jpg)

③你需要知道你调用的方法是有风险的。

  

④然后你写代码来处理有可能发生的风险。你需要做好准备，以防万一。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

