---
title: Head First C# 中文版 第九章 读写文件 page418
date: 2009-05-12 18:15:00
tags: 我翻译的Head First C#（习作）
---
到底什么是对象的状态呢？什么需要被存储起来？

  

我们已经知道了对象将其状态存储在自己的字段中。所以对象序列化的时候，每个字段都需要被保存到文件。

  

如果你有一些复杂的对象序列化才会有趣。  37  和  70  都是  byte  类型的  \--  它们是值类型，所以可以依照原样被存储到文件。但是如果
一个对象拥有一个对象引用的变量怎么办？如果一个对象内含有五个对象引用的实例变量怎么办呢？如果这五个对象又各自含有引用类型的变量呢？

  

想一下。一个对象的哪部分有可能会是唯一的呢？想像一下要让被存储的对象可以恢复原样需要存储哪些部分。托管堆上所有东西的都需要被写到文件。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090512/2009-05-12_18-05-00.jpg)

要怎样才可以让这个  Car  对象被存储后还可以依原样被恢复呢？我们假设这辆车有三个乘客，一台三公升引擎还有一个全天候辐射层轮胎  ...
这些东西不都是状态的一部分吗？应该如何处理它们呢？

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090512/2009-05-12_18-09-56.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





