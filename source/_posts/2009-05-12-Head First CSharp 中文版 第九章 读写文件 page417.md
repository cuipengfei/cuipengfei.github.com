---
title: Head First C# 中文版 第九章 读写文件 page417
date: 2009-05-12 12:43:00
tags: 我翻译的Head First C#（习作）
---
一个对象被序列化的时候会怎么样？

  

把一个对象从托管堆上复制到文件里面去看起来很是神秘，但是实际上这个过程是很简洁明了的。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090512/2009-05-12_12-21-14.jpg)

每个实例都有状态。一个对象“知道”的东西将它和同类的其他对象区分开来。

C#  序列化对象的时候，会把对象的完整状态保存起来，这样就可以从文件重新创建一个相同的对象到托管堆上去了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090512/2009-05-12_12-32-38.jpg)

③然后  ....

  

然后  \--  或许几天之后，在另外一个程序中  \--  你可从文件中把它反序列化。这会把该对象从文件中取出，所有字段值原封不动。



