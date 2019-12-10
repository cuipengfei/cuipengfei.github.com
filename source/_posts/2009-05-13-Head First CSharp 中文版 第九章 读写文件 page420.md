---
title: Head First C# 中文版 第九章 读写文件 page420
date: 2009-05-13 13:22:00
tags: 我翻译的Head First C#（习作）
---
序列化让你可以读写对象

  

可以写入文件的并不仅仅是一行一行的文本。你可以用序列化让程序把对象写入文件并且还可以读取回来  ...  只需要写几行代码而已！要做序列化需要做一点前期工作
\--  给想要序列化的类前面添加一行  [Serializable]--  做完这一步就万事俱备了。

  

你需要一个  BinaryFormatter  对象

  

如果你想序列化一个对象  \--  你要首先创建一个  BinaryFormatter  的实例。这很简单  \--  只需要一行代码（还有类文件头上的一个
using  语句）。

  

using System.Runtime.Serialization.Formatters.Binary;

...

BinaryFormatter formatter = new BinaryFormatter( );

  

现在创建一个流来读写你的对象

  

用  BinaryFormatter  对象的  Serialize  （）方法来把对象写出到流。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090513/2009-05-13_11-55-18.jpg)

把对象序列化到文件之后，可以用  BinaryFormatter  对象的  Deserialize  （）方法来把它读取回来。此方法返回一个
object  引用，所以你需要把它转型为你需要的类型。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090513/2009-05-13_12-10-04.jpg)



