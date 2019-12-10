---
title: Head First C# 中文版 第九章 读写文件 page397
date: 2009-05-03 12:16:00
tags: 我翻译的Head First C#（习作）
---
使用内建对象来弹出标准对话框

  

你编写读写文件的程序的时候很有可能会需要弹出对话框来向用户询问文件名。所以  .NET  中内建了可以弹出标准  Windows  文件对话框的对象。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090503/2009-05-03_12-00-47.jpg)

ShowDialog  （）方法弹出一个对话框

  

要弹出一个对话框很简单。下面几步就可以：

  

①  创建一个对话框的实例。你可以在代码中用  new  语句创建，或者从工具箱中拖拽到窗体上去。

②  设置对话框的属性。对话框的有用的属性有  Title  （它可以设置标题栏的文本），  InitialDirectory
（用于设置从哪个文件夹开始），还有  FileName  （用于打开或者保存对话框）。

③  调用  ShowDialog  （）方法。这个方法弹出对话框，在用户点击  OK  或者  Cancel  按钮，或者关闭窗口之后这个方法才返回。

④  ShowDialog  （）方法返回一个  DialogResult  枚举值。它的枚举值有  OK  （代表用户点击了  OK  按钮），
Cancel  ，  Yes  和  No  （用于  Yes/No  对话框）。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

