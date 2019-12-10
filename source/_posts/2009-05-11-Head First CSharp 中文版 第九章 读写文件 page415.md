---
title: Head First C# 中文版 第九章 读写文件 page415
date: 2009-05-11 21:32:00
tags: 我翻译的Head First C#（习作）
---
添加一个重载的  Deck  （）构造方法，它从文件读取一副牌

  

你可以用  switch  语句给上一章写的  Deck  类写一个新的构造方法。它读文件来检查每一行是否是有效地扑克信息，如果是则加入这副牌中。

  

String  有一个很方便的方法叫做  Split  （）。该方法让你可以通过传递进去一个  char[]  数组作为分隔符来把字符串切割为一组子字符串。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090511/2009-05-11_21-22-44.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

