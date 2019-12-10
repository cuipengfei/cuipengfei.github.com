---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page330
date: 2009-04-10 10:35:00
tags: 我翻译的Head First C#（习作）
---
使用IComparable来告诉List如何排序

  

.NET Framework  中有一个内建的特殊接口来让你构建自己的排序逻辑。通过实现IComparer接口，编写其中定义的Compare（）方法，你可以
告诉List如何去给你的对象排序。Compare（）方法接收x和y两个参数，并返回一个int值。如果x小于y，返回负值。如果x和y相等，返回0。如果x大于y
，返回正值。

  

下面是一个比较鸭子的大小的例子：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090410/2009-04-10_10-21-04.jpg)

你实现IComparer的方式决定List如何给你的对象排序。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

