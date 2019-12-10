---
title: Head First C# 中文版 第九章 读写文件 page387
date: 2009-05-01 13:31:00
tags: 我翻译的Head First C#（习作）
---
不同的流读写不同的数据

  

每一种流都是抽象类Stream的子类，很多内建类可以做不同的事情。这一章我们将会专注于读写普通文件，但是你在这一章学到的东西也可以很简单的应用于压缩文件或者
加密文件，或者不使用文件的网络流也可以。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090501/2009-05-01_13-08-03.jpg)

你可以用流做的事情：

  

①  向流中写入

你可以通过流的Write（）方法来向其中写入文本或者二进制数据。

  

②  从流中读取

你可以通过Read（）方法从文件中、网络上、内存中或者任何使用流的地方读取数据。

  

③  改变在流中的位置

大多数的流都支持Seek（）方法，你可以通过使用它来找到流中某个位置，这样你可以在某特定位置插入数据。

  

流让你可以读写数据。要使用和你操作的数据类型对应的流。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

