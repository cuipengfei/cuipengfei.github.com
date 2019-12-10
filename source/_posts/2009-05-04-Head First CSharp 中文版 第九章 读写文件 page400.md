---
title: Head First C# 中文版 第九章 读写文件 page400
date: 2009-05-04 16:56:00
tags: 我翻译的Head First C#（习作）
---
使用内建的  File  和  Directory  类来操作文件和文件夹

  

和  StreamWriter  类似，  File  类也会给你创建出用于操作文件的流。你无须创建  FileStream
对象就可以用它的方法来完成多数的普通操作。  Directory  对象让你可以操作文件夹，你可以用它很容易的改变文件结构。

  

可以用  File  做的事：

  

①判断文件是否存在

  

你可以用  Exists  （）方法来判断一个文件是否存在。如果存在，该方法将会返回  true  ，否则返回  false  。

  

②读写文件

  

你可以用  OpenRead  （）方法来从文件中获取数据，还可以用  Create  （）或者  OpenWrite  （）方法来写文件。

③向文件添加文本内容

  

AppendAllText  （）方法让你可以向一个已经创建好的文件中添加文本。如果该方法运行的时候文件不存在，它甚至可以创建文件。

  

④获取文件的信息

  

GetLastAccessTime  （）和  GetLastWriteTime  （）方法返回文件最后一次被访问和修改的日期和时间。

  

可以用  Directory  做的事：

  

①创建一个新文件夹

  

使用  CreateDirectory  （）方法创建一个新文件夹。你只需要提供路径，其余的有该方法去完成。

  

②获取一个文件夹中的文件的列表

  

你可以用  GetFiles  （）方法来创建一个文件夹中的文件的数组，只需要告诉该方法你想要了解哪个文件夹，其余的由该方法去完成。

  

③删除一个文件夹

  

删除一个文件夹很简单。使用  Delete  （）方法就可以了。

  

FileInfo和File的工作方式很类似。如果你要做很多操作文件的工作，你可能会想要创建一个FileInfo类的实例而不是总是使用File类的静态方法。F
ileInfo类几乎可以做File类可以做的一切事情，只是你必须要实例化它。你可以创建一个FileInfo类的实例并且访问它的Exits（）方法或者Open
Read（）方法，访问方法和使用File类的方法一样。唯一的区别是File类对于小规模的应用来说更快，而FileInfo更适合于大规模的工作。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

