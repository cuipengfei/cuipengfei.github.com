---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page279
date: 2009-03-26 21:54:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090326/2009-03-26_21-31-03.jpg)

因为你想要向子类提供一些实现了的代码，而又想要要求子类去实现另外一些代码。

  

有时候如果你创建了不应该被创建的对象，会有不良的影响。类图顶部的类通常会有一些需要子类来设置的字段。一个Animal类可能会基于一个布尔类型的HasTail
（有尾巴）或者Vertebrate（有脊椎骨）来做一些计算，但是Animal本身不应该去给这两个字段赋值。

  

如下示例...

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090326/2009-03-26_21-41-13.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

