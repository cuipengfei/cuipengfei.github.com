---
title: Head First C# 中文版 第九章 读写文件 page409
date: 2009-05-08 20:40:00
tags: 我翻译的Head First C#（习作）
---
③让  Save  按钮把当前的借口保存到文件

  

点击  Save  按钮会显示出一个另存为对话框。

  

★每个借口都被保存到一个文本文件。文件的第一行是借口，第二行是结果，第三行是最后一次使用的日期。  Excuse  类应该有一个  Save
（）方法来把借口保存到某个特定的文件。

★另存为对话框打开的时候应该从用户用  Folder  按钮选择的文件夹开始，文件名应该被设置为借口名加上一个“  .txt  ”。

★对话框应该有两个过滤器：  Text Files  （  *.txt  ）和  All Files  （  *.*  ）。

★如果用户要保存当前借口但是借口或者结果文本框为空的话，窗体应该弹出一个警告对话

框。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090508/2009-05-08_20-18-37.jpg)

④让  Open  按钮打开一个保存过的借口

  

点击  Open  按钮应该弹出一个打开文件对话框。

  

★打开文件对话框显示出来的时候打开的文件夹应该是用户通过  Folder  按钮选择的文件夹。

★给  Excuse  类添加一个  Open  （）方法来打开一个给定的文件。

★使用  Convert.ToDateTime  （）来把保存日期载入到  DateTimePicker  控件。

★如果你在当前借口没有保存的情况下试着去打开另一个借口，将会弹出下面这个对话框：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090508/2009-05-08_20-29-17.jpg)

⑤最后，让  Random Excuse  按钮载入一个随机的借口

  

用户点击了  Random Excuse  按钮的话，它会去遍历文件夹，从中随机选择一个借口，并打开它。

  

★窗体需要保存一个  Random  类型的字段并把它传递给  Excuse  类的某个重载过的构造方法。

★如果当前的借口没有保存，这个按钮应该弹出和  Open  按钮一样的对话框。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

