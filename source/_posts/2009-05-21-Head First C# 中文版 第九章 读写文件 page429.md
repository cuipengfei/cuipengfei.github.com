---
title: Head First C# 中文版 第九章 读写文件 page429
date: 2009-05-21 08:50:00
tags: 我翻译的Head First C#（习作）
---
你也可以手动的读写序列化的文件

  

你用记事本打开序列化的文件的时候，它看起来不怎么漂亮。你可以在你的项目下的“  bin/Debug  ”目录下找到所有写出的文件  \--
花点时间来了解一下序列化文件的内部。

  

①把两个  Card  对象序列化到不同的文件

  

用你原来写过的代码来把方片三写到  card1.dat  并把红桃六写到  card2.dat
。确保两个文件都被写出了，而且处于同一个文件夹下且文件大小一样。然后用记事本打开其中之一。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90521/2009-05-20_22-30-01.jpg)

②写一个循环来比较两个二进制文件

  

我们用  ReadByte  （）方法来读取流中的下一个字节  \--  它返回一个包含着  byte  值的  int  。我们会用流的  Length
属性来确保我们读到了整个文件。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90521/2009-05-21_08-37-33.jpg)

注意！写文件的时候并不总是从头开始的！

  

如果你用  File.OpenWrite  （）的话就要小心一点了。它并不删除原文件  \--  只是从文件的开头处覆盖原数据而已。所以我们用了
File.Create  （）  \--  它创建一个新文件。

  

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

