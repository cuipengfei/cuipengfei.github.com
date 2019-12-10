---
title: Head First C# 中文版 第九章 读写文件 page421
date: 2009-05-14 13:29:00
tags: 我翻译的Head First C#（习作）
---
如果你想让你的类可以被序列化，对其应用  [Serializable]  特性

  

特性是添加在  C#  类之前的特殊标签。  C#  就是这样存储关于你的代码的元数据或者代码应该如何被使用或对待的方式的。在你的类声明之前添加
[Serialization]  就是告诉  C#  你的这个类对于序列化是安全的。一个类的字段必须是值类型或者可序列化的类型才可以应用
[Serialization]
特性。如果你把这个特性应用到了某个含有不可序列化字段的类上或者是对于要序列化的类没有应用这个特性的话，运行的时候将会出错。自己试一下  ...

  

①创建一个类并将它序列化

  

还记得第三章的  Guy  类吗？我们来把  Joe  序列化，这样我们将可以在关闭程序之后把  Joe  有多少钱这个信息存储到文件中了。

  

[Serialization]

public class Guy

  

下面的代码把  Joe  对象存储到一个叫做“  Guy_file.dat  ”的文件中  \--  在窗体上添加一个“  Save Joe
”按钮和一个“  Load Joe  ”按钮。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090514/2009-05-14_13-19-30.jpg)

②运行程序并试玩一下

  

如果在运行程序的过程中  Joe  在和  Bob  交易中攒了  200  美元，而仅仅因为你要关闭程序而把这些钱丢失了那实在是太不爽了。现在你可以把
Joe  存储到文件并且随时可以读取回来。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

