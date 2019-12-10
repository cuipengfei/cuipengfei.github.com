---
title: Head First C# 中文版 图文皆译 第五章 封装 page192
date: 2009-02-10 12:26:00
tags: 我翻译的Head First C#（习作）
---
封装让你的数据原封不动

有时你的程序会改变字段的值。如果你不显式的告诉程序去重设值，你可以用旧的值来做计算。这种情况下，你会想让程序在每次某个字段改变的时候执行一些语句--就好像让
凯瑟琳的程序每次修改人数的时候都重新计算聚会花费。我们可以通过用私有字段封装数据来避免这个问题。我们将会提供一个方法来获取字段值，另一个方法设置字段值并做必
须的计算。

一个封装的快速范例

一个Farmer类用一个字段存储牛的数目，并用一个数字乘它来计算需要多少饲料来喂牛：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090210/%E6%88%AA%E5%9B%BE00.jpg)

当你需要创建一个窗体来让用户输入牛的数目的时候，你得可以改变存储牛的数目的字段值。你可以创建一个向窗体返回字段值的方法。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090210/%E6%88%AA%E5%9B%BE01.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

