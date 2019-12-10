---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page336
date: 2009-04-11 12:26:00
tags: 我翻译的Head First C#（习作）
---
字典的功能概要

  

字典和列表很是相似。它们两个在处理多种类型上都很灵活，而且都有很多内建功能。下面是Dictionary的基本方法：

  

★  添加一项

  

你可以通过给字典的Add（）方法传递一个键和一个值来向字典添加一项。

Dictionary<string , string> myDictionary = new Dictionary<string , string> (
);

myDictionary.Add("some key" , "some value");

  

★  用键来查找它对应的值

  

你可以用字典做的最重要的一件事就是查找值--这很有道理，因为你把值存在字典里就是为了用其对应的唯一键来查找它。

String lookupValue = myDictionary["some key"];

  

★  移除一项

  

像列表一样，在字典中你也可以通过Remove（）方法来移除一项。只需要把键传递给Remove（）方法就可以把键和值同时移除。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090411/2009-04-11_12-07-04.jpg)

★  获取键的集合

  

你可以用KeyCollection从字典中获取所有键的集合并用foreach遍历它。通常这样用KeyCollection：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090411/2009-04-11_12-15-49.jpg)

★  获取值的集合

  

你可以用ValueCollection从字典中获取所有值的集合。大多数情况下，你也会把foreach和ValueCollection拿来一起用：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090411/2009-04-11_12-22-36.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

