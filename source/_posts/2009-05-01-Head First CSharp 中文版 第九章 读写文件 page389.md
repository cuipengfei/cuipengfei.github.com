---
title: Head First C# 中文版 第九章 读写文件 page389
date: 2009-05-01 22:06:00
tags: 我翻译的Head First C#（习作）
---
如何通过三个简单的步骤向文件写入文本

  

C#  可以用一个很简单的叫做StreamWriter类来把上面说到的事情在一步之内完成。你只需要创建一个StreamWriter对象并给它一个文件名。它可
以自动的创建一个FileStream对象并把它连接到文件。然后你可以使用StreamWriter的Write（）和WriteLine（）方法来写文件。

  

①  使用StreamWriter的构造方法来打开或者创建一个文件

  

你可以给StreamWriter的构造方法传递一个文件名。这样就可以自动的打开文件。StreamWriter还有一个重载的构造方法，它还接受一个bool参数
：如果要向已经存在的文件尾部添加文本就传递true，如果要把已经存在的文件删除并创建一个同名的新文件就传递false进去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090501/2009-05-01_21-52-48.jpg)

②  使用Write（）或者WriteLine（）方法来写文件

  

这两个方法和Console中的一样：Write（）只写出文本，WriteLine（）方法写出文本并且在尾部添加一个换行。如果在字符串中包含{0}，{1}，{
2}，{0}将会被方法中字符串之后的第一个参数替代，{1}将会被第二个替代，等等。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090501/2009-05-01_22-01-07.jpg)

③  使用Close（）方法来释放文件

  

如果你让流开着并连接到文件的话，它将会锁定文件而使得其他程序无法访问该文件。所以确定你总是关闭流！

  

writer.Close  （）；



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

