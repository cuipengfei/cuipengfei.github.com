---
title: Head First C# 中文版 第九章 读写文件 page408
date: 2009-05-07 13:37:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90507/2009-05-07_12-59-52.jpg)

创建借口管理器，这样  Brian  就可以管理自己工作中的借口了。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90507/2009-05-07_13-35-25.jpg)

①创建窗体

  

这个窗体有一些特性：

  

★窗体最初载入的时候只有  Folder  按钮是可用的  \--  在选择文件夹之前其他三个按钮都是不可用的。

  

★打开或者保存一个借口的时候，文件日期会显示在一个  AutoSize  设置为  false  、  BorderStyle  属性设置为
Fixed3D  的标签上。

  

★一个借口保存之后，会弹出一个写有“  Excute Written  ”的消息框。

  

★  Folder  按钮会显示出一个文件夹浏览器对话框。如果用户选择了文件夹，  Save  、  Open  和  Random Excuse
按钮会被设置为可用。

  

★窗体知道没有保存的变动。如果没有未保存的变动，窗体的标题栏显示为“  Excuse Manager
”。但是如果用户改变了三个字段中的任意一个，窗体会在标题栏上添加一个星号（  *  ）。星号会在数据保存之后或者打开一个新的借口之后消失。

  

★窗体将会记录当前文件夹和当前的借口是否被记录了。你可以通过三个输入控件的  Changed  事件处理方法来判断借口是否被保存了。

  

②创建一个  Excuse  类并在窗体中保存一个  Excuse  的实例

  

向窗体添加一个  CurrentExcuse
字段来保存当前借口。你将会需要三个重载的构造方法：一个用于窗体最初加载的时候，一个用于打开文件时，一个用于随机借口。添加  OpenFile
（）方法来打开借口（在构造方法中使用），还有  Save  （）方法用来保存借口。然后添加一个  UpdateForm  （）方法来更新窗体上的控件：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90507/2009-05-07_13-22-04.jpg)

③让  Folder  按钮打开一个文件夹浏览器

  

点击  Folder
按钮的时候，窗体将会弹出一个文件夹浏览器对话框。窗体将会把文件夹存储在一个字段内，这样其他的对话框就可以使用这个文件夹了。窗体最初载入的时候  Save
、  Open  和  Random Excuse  按钮是不可用的，但是如果用户选择了一个文件夹，那么  Folder
文件夹的事件响应方法将会使得其他三个按钮可用。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

