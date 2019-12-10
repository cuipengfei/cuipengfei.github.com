---
title: Head First C# 中文版 图文皆译 第五章 封装 page191
date: 2009-02-09 22:59:00
tags: 我翻译的Head First C#（习作）
---
给封装的类的一些好主意

  

*  考虑一下字段可能会被滥用的方式 

如果字段没有被合适的设置的话会出什么错？

  

*  你的类的里面的东西都是公有的吗？ 

如果你的类的字段和方法都是公有的话，你可能需要花点时间来考虑一下封装了。

  

*  什么字段在设置的时候需要处理或者计算？ 

这些是封装的首要考虑因素。如果有人稍后会写方法来改变这些字段的值，那就会给你的程序已经做过的工作造成问题。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090209/%E6%88%AA%E5%9B%BE01633698172342656250.jpg)

*  只有你真的需要的时候才把方法设置为public 

没有确切的理由声明公有的时候，就别声明公有。把类内的东西都声明为公有会使得事情很糟糕--
但是也别把所有东西都声明为私有。首先把哪些是公有，哪些是私有想清楚，之后就可以节省很多时间。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

