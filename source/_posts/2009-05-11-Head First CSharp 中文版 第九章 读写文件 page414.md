---
title: Head First C# 中文版 第九章 读写文件 page414
date: 2009-05-11 18:16:00
tags: 我翻译的Head First C#（习作）
---
用  switch  语句来从文件读取一副牌或者把一副牌写入文件

  

向一个文件写入扑克牌很直接  \--  写一个循环来把每张牌的名称写入文件就可以了。你可以把下面的方法写入  Deck  类来完成上面提到的功能：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090511/2009-05-11_13-03-44.jpg)

那么从文件读取扑克呢？那就不那么简单了。这时  switch  语句就会显得很方便了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090511/2009-05-11_13-05-12.jpg)

switch语句让你可以把一个值拿来和很多种情况做比较，并根据匹配的不同来执行不同的语句。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

