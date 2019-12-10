---
title: Head First C# 中文版 第九章 读写文件 page433
date: 2009-05-23 09:20:00
tags: 我翻译的Head First C#（习作）
---
StreamReader  和  StreamWriter  可以胜任

  

我们的十六进制转储将会把转储输出到文件，由于仅仅是写出文本，所以  StreamWriter  就可以胜任了。不过我们也可以使用  StreamReader
的  ReadBlock  （）方法。它把一块字符读取进一个  char  数组  \--  由你指出你想要读取的字符数，本方法有可能会读取你指定的数目的字
符，也或者余下的字符比你指定的数目小，那么就会只读取剩下的所有字符。由于我们要在一行中显示十六个字符，我们将会每次读取十六个字符。

  

向你的程序添加一个按钮  \--  把这个十六进制转储添加进去。改变头两行，这样你就可以指向你硬盘上真实存在的文件了。从一个存储  Card
对象的序列化文件开始。然后看看你能不能把它修改为使用打开和保存对话框。

  

![](http://student.csdn.net/attachment/200905/23/39098_1243041623T8Nx.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





