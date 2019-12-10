---
title: Head First C# 中文版 第九章 读写文件 page427
date: 2009-05-17 18:44:00
tags: 我翻译的Head First C#（习作）
---
使用  BinaryWriter  来写出二进制数据

  

你可以在写文件之前把字符串，字符，整数，和浮点数编码为字节数组，但是这很麻烦。所以  .NET  给你提供了一个很有用的类叫做  BinaryWriter
，它自动把你的数据编码并把它们写到文件。你只需要创建一个  FileStream  并把它传递进  BinaryWriter  的构造方法内。然后就调用
BinaryWriter  的方法来写出数据。所以再向窗体添加一个按钮吧，我们将会演示给你看如何使用  BinaryWriter  。

  

①开始先准备好一些要写出到文件的数据。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90517/2009-05-17_18-27-28.jpg)

②要想使用  BinaryWriter  ，首先要用  File.Create  （）来创建一个新的流：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90517/2009-05-17_18-29-11.jpg)

③现在调用它的  Write  （）方法。每次你调用它，它都会把新的字节添加到文件的末尾，该  文件包含着你传递给它的作为参数的数据的已编码版本。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90517/2009-05-17_18-32-07.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90517/2009-05-17_18-35-55.jpg)

④现在使用你曾经用过的代码来读取你刚刚写的文件。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90517/2009-05-17_18-37-20.jpg)

在下面的空白处写出输出。你可以搞明白每个  Write  （）语句对应着什么字节吗？把每一组字节标记上其对应的变量名。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90517/2009-05-17_18-41-07.jpg)

小提示：字符串的长度不定，所以字符串必须要以一个标记其长度的数字开始。你也可以通过字符映射表来查看字符串和字符对应的  Unicode  值。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

