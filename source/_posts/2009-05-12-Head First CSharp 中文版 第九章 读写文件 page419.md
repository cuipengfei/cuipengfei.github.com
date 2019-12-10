---
title: Head First C# 中文版 第九章 读写文件 page419
date: 2009-05-12 22:12:00
tags: 我翻译的Head First C#（习作）
---
一个对象被序列化的时候，它所引用的所有对象也会被序列化  ...

  

...  还有被引用的对象引用的对象，还有这些对象再次引用的对象，还有等等等等。但是别担心  \--  听起来很复杂，但是这是自动完成的。  C#
从你想要序列化的对象开始并遍历其字段指向的对象。然后再对所有字段同样处理。每一个对象及  C#  反序列化它的时候需要的附加信息都会被写到文件。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090512/2009-05-12_21-54-22.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

