---
title: Head First C# 中文版 图文皆译 第二章 page63
date: 2008-10-26 17:30:00
tags: 我翻译的Head First C#（习作）
---
使用变量之前必须赋值  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

把下面的指令写入一个C#程序：

Int z  ；

MessageBox.Show  （“The answer is”+z）；

向前走，试一试。你会收到一个错误，IDE将会拒绝编译代码。那是因为IDE将会检查每一个变量来确定它已经在被使用之前被赋值过了。防止忘记赋值的最简单方法就是把
声明变量和给它赋值的指令合为一句。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081026/%E6%88%AA%E5%9B%BE00633606390336206250.jpg)

一些有用的类型

每个变量都有一个类型，这告诉C#它可以承载什么数据。第四章我们会研究更多C#中的类型的细节。现在，我们将会专注于这三个最流行的类型。Int承载整形（或者叫做
整数），string承载文本，还有bool承载布尔值true/false。

如果你写的代码用了没有赋值的变

量，那代码没法编译。防止忘记赋

值的最简单方法就是把声明变量和

给它赋值的指令合为一句。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081026/%E6%88%AA%E5%9B%BE01633606390336675000.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

