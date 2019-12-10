---
title: Head First C# 中文版 第九章 读写文件 page386
date: 2009-04-29 22:06:00
tags: 我翻译的Head First C#（习作）
---
C#  使用流来读写数据

  

.NET Framework  使用  流  的方式来向程序读入或者从程序写出数据。C#程序读写文件的时候，通过网络和另一台电脑连接的时候，或者是概括来讲，
只要是把字节从一处发送至另一处的时候，你都要使用流。

无论何时你想要从一个文件中读取数据或者想要向一个文件中写入数据，你都要使用Stream对象。

假设你有一个简单的程序--一个含有事件处理方法的窗体，它需要从一个文件读取数据。你需要用Stream对象来完成这件事。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090429/2009-04-29_21-52-49.jpg)

你可以在程序中使用另一个Stream对象来把数据写到文件中。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090429/2009-04-29_21-59-02.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





