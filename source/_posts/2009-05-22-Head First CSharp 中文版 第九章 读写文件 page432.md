---
title: Head First C# 中文版 第九章 读写文件 page432
date: 2009-05-22 17:36:00
tags: 我翻译的Head First C#（习作）
---
用文件流来创建一个十六进制转储

  

十六进制转储是以十六进制的方式查看文件，程序员通常以这种方式来深入了解文件内部结构。绝大多数操作系统自带有内建的十六进制组件。很不巧，  Windows
没有。那么我们自己来创建一个吧！

  

如何创建一个十六进制转储

  

一下面这段熟悉的文本开始：

  

We the people of United States, in order to form a more perfect union...

  

下面是上面文本的十六进制转储：

  

![](http://student.csdn.net/attachment/200905/22/39098_1242985090WH9u.jpg)

57  ，  65  ，  6F
，这些数字中的每一个都是文件中的一个字节的值。某些数字中含有字符是因为这是十六进制表示的。这只是另一种表示数字的方式。它使用  0  到  9  外加  A
到  F  的十六个符号来表示数字，而不是仅使用  0  到  9  的十个数字。

  

十六进制转储中的每一行对应输入中的十六个字符。在我们的转储中，前四个数字代表偏移量  \--  第一行在文件中的第  0  个字符处开始，下一行从第  16
（十六进制的  10  ）个字符处开始，下一个从  32  （十六进制的  20  ）处开始，等等。

  

操作十六进制数

  

你可以在程序中直接使用十六进制数  \---  只要在数字之前添加  0x  就可以了：

  

int j = 0x20;

MessageBox.Show("The value is" + j);

  

使用  \+  运算符把一个数字连接到一个字符串的时候，数字会被转换为十进制。你可以使用  String.Format
（）静态方法来把数字转化为十六进制格式：

  

String h = String.Format("{0:x2}",j);



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

