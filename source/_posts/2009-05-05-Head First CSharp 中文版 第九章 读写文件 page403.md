---
title: Head First C# 中文版 第九章 读写文件 page403
date: 2009-05-05 12:38:00
tags: 我翻译的Head First C#（习作）
---
使用文件对话框来打开和保存文件（只需要几行代码）

  

你可以创建一个打开文本文件的程序。你可以用它改变文件，并且保存更改。只需要很少的代码，用的都是  .NET  控件。下面是步骤：

  

①创建一个简单的窗体

  

你只需要一个文本框和两个按钮。拖拽  OpenFileDialog  和  SaveFileDialog
控件到窗体上去。双击按钮来创建它们的事件处理方法并添加一个私有的  string  类型的字段，叫做  name  。不要忘记在顶部添加  using
System.IO  ；这一句。

  

②把  Open  按钮和打开文件的对话框联系起来

  

Open  按钮显示出来一个  OpenFileDialog  并且使用  File.ReadAllText  （）来读取文件把内容置入文本框：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090505/2009-05-05_12-27-03.jpg)

③现在，把  Save  按钮联系起来

  

Save  按钮使用  File.WriteAllText  （）方法来保存文件：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090505/2009-05-05_12-30-45.jpg)

④试玩一下对话框的其它属性

  

★使用  Title  属性来改变  saveFileDialog  的标题栏。

  

★使用  OpenFileDialog  的  initialFolder  属性来让对话框从某个特定的文件夹开始。

  

★使用  OpenFileDialog  的  Filter  属性来进行过滤，这样它只会显示文本文件。

  



