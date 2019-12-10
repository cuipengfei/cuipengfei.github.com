---
title: Head First C# 中文版 第九章 读写文件 page424
date: 2009-05-16 17:00:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090516/2009-05-16_16-17-35.jpg)

把一个对象序列化到文件的时候，是以二进制格式写的。

  

这并不意味着它不可读  \--  只是紧凑而已。你打开序列化对象的文件的时候你可以识别里面的字符串的原因就是因为  C#
把字符串写入到文件的最紧凑的方式就是作为字符串写入。但如果把数字作为字符串写入文件的话就太浪费了。任何  int  都可以用四个字节存储起来。所以如果
C#  把数字  49  ，  369  ，  144  以你可以阅读的方式作为八个字符（算上逗号就是十个字符）的字符串写入文件的话，那就太浪费空间了！

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090516/2009-05-16_16-29-40.jpg)

.NET  使用  Unicode  来把字符或者字符串编码到字节。很幸运，  Windows  有一个很有用的工具可以帮你来搞明白  Unicode
如何工作。打开字符映射表（它在开始菜单内的附件之内，或者在  cmd  中输入“  charmap.exe  ”）。

  

当你看全世界中这些语言中所有字母和符号的时候，你就可以意识到要向文件中写入文本需要多少不同的东西。所以  .NET  把其字符串编码为  Unicode
。编码意味着把逻辑数据（比如字母  H  ）转换为字节（比如  72
）。之所以这么设计是因为字母，数字，枚举和其他数据在内存和硬盘上都以字节的形式存在。这正是字符映射表的有用之处  \--
它向你展示了字符是如何转换为数字的。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090516/2009-05-16_16-45-58.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

