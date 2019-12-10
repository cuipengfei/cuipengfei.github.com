---
title: Head First C# 中文版 第九章 读写文件 page394
date: 2009-05-02 22:46:00
tags: 我翻译的Head First C#（习作）
---
数据可以通过不止一个流

  

操作  .NET  中的流的一个优点就是你可以让数据通过不止一个流来到达最终目的地。  .NET  中有一个流叫做  CryptoStream
。它可以让你在做别的操作之前给数据加密：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090502/2009-05-02_22-37-33.jpg)

你可以把流连接起来。一个流写到另一个流，然后再写到另一个流...最终通常以网络流或者文件流结束。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

