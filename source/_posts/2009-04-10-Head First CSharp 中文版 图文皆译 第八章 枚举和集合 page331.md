---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page331
date: 2009-04-10 12:04:00
tags: 我翻译的Head First C#（习作）
---
给你的比较器创建一个实例

  

想要用IComparer来排序，就要创建一个实现了该接口的类的实例。这个实例的存在只是为了--
让List的Sort（）方法知道如何排序。但是像任何其他（非静态）类一样，在使用它之前先要实例化它。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090410/2009-04-10_11-43-30.jpg)

多种IComparer实现，多种排序方式

  

你可以根据不同的需要来创建多种IComparer的实现类。然后你需要某种特定排序的时候，你就可以调用它。下面是另一种鸭子比较器的实现：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090410/2009-04-10_11-51-44.jpg)



