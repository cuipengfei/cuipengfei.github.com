---
title: Head First C# 中文版 第九章 读写文件 page431
date: 2009-05-21 19:02:00
tags: 我翻译的Head First C#（习作）
---
操作二进制文件有点麻烦

  

如果你有一个文件但是并不知道里面是什么内容你会怎么办？你不知道什么应用创建了它，你需要了解它  \--
但是你用记事本打开它的时候，内容看起来像是一堆垃圾。如果你已经试过了用所有方法来看看文件内部都告失败又怎么样呢？看看下面的图，很明显，记事本不是正确的选择。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090521/2009-05-21_18-37-43.jpg)

还有一个选择  \--  一种叫做十六进制转储的格式，这是查看二进制数据的标准方式。这比用记事本阅读文件好多了。每个字节在十六进制中占用两个字符，这样你就可
以在很小的空间内查阅很多信息了。还有，在长度为  8  ，  16  或者  32  的行中显示二进制数据是很有用的，因为二进制数据趋向于分解到长度为  4
，  8  ，  16  或  32  的块中  ...  就像  C#  中的所有类型一样。比如，  int  占用  4
个字节，被序列化的时候也会占用四个字节。这是上面的文件用十六进制查看时的样子，随便用一种十六进制查看工具来查看。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090521/2009-05-21_18-55-45.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





