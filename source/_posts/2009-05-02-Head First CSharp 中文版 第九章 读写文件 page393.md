---
title: Head First C# 中文版 第九章 读写文件 page393
date: 2009-05-02 15:22:00
tags: 我翻译的Head First C#（习作）
---
读和写要用到两个对象

  

我们用另一个流  \--StreamReader  来读取骗子的秘密计划。  StreamReader  和  StreamWriter
的工作方式很相似，只是你在  StreamReader  的构造方法中给出需要读取的文件名，而不是要去写入。  ReadLine
（）方法返回一个包含文件中一行的字符串。你可以用一个循环来从中持续读取行，知道其  EndOfStream  字段为  true--
这意味着其中已经没有行可读取了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090502/2009-05-02_15-10-23.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

