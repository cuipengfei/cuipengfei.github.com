---
title: Head First C# 中文版 第九章 读写文件 page428
date: 2009-05-20 19:51:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090520/2009-05-20_19-18-09.jpg)

使用  BinaryReader  来把数据读取回来

  

BinaryReader  和  BinaryWriter  的工作方式很相似。创建一个流，把流和  BinaryReader  对象联系起来，然后调用
BinaryReader  的方法。但是二进制读取器并不知道文件中存储的是什么数据！而且也无从得知。你的值为  491.695F  的  float
值编码为  d8 f5 43 45  。但是把它解释为值为  1140185334  的  int  值也是完全合法的。所以你需要告诉
BinaryReader  要从文件中读取什么类型的数据。再向窗体添加一个按钮，让它读取刚刚写入文件的数据。

①从创建  FileStream  和  BinaryReader  开始：

  

using (FileStream input = File.OpenRead("binarydata.dat")){

BinaryReader reader = new BinaryReader(input);

  

②通过调用  BinaryReader  的不同方法来告诉它去读取什么类型的数据。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090520/2009-05-20_19-44-05.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090520/2009-05-20_19-48-01.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

