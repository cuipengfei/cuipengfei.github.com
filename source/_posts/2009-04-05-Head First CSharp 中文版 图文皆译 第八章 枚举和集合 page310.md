---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page310
date: 2009-04-05 09:34:00
tags: 我翻译的Head First C#（习作）
---
不要总是用字符串来存储分类的数据

  

假设Worker类表示工蜂。你会如何给它写一个构造方法来接受代表工作的参数呢？如果你用字符串来代表工作，你的代码有可能会写成下面这样：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090405/2009-04-05_09-08-51.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090405/2009-04-05_09-29-27.jpg)

或许你可以在Woker类的构造方法中添加代码来检查代表工作类型的字符串。但是如果你要让蜜蜂掌握一些
新工作，那你就需要修改检查代码并重新编译Woker类。这是相当短视的解决方案。如果有别的类也需要检查蜜蜂工作的类型怎么办呢？那就会造成冗余代码，此路不通。

我们需要一种方式来表达出这样的意思：“嘿，只有某些特定值在这儿才是合法的。”我们需要枚举出可以使用的值。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

