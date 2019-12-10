---
title: Head First C# 中文版 第10章 异常处理 page477
date: 2009-06-08 22:09:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090608/2009-06-08_21-39-33.jpg)

使用你学到的  try/catch/finally  的知识来改进  Brian  的借口管理器中的异常处理。

  

①  给  Open  按钮的事件处理方法添加异常处理。只写一个弹出消息框的  try/catch
就可以了。下面是你试图打开一个不是借口文件的文件时弹出的消息框的样子：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090608/2009-06-08_21-44-09.jpg)

②  还没完事儿。运行程序，选择一个文件夹，在  Description  和  Last Results  栏中写入数据，但是不要输入  Last
Used date  。现在选择一个文件夹并试着去保存借口。得下面这个  ArgumentOutOfRange  异常了吧？

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090608/2009-06-08_21-54-27.jpg)

使用调试器追踪这个异常。这个异常完全可以避免  \--  你可以修复这个问题并确保这个异常永不再发生吗？

（提示：这和添加  try/catch  块无关。你需要搞明白为什么“  Last Used date  ”会导致问题。仔细观察异常信息来寻找线索。）

  

③  最后。程序在抛出  ArgumentOutOfRange  异常之前保存了一个文件。加载这个文件  \--  你又会得到这个异常。如果试图去打开一个无
效的借口文件的话你会得到另一个异常。在前面添加的异常中添加一个嵌套的异常处理来确保在试图加载无效文件（这时常发生）的时候不会崩溃：

  

1\.  在  try/catch  块之前添加一个叫做  clearForm  的布尔型变量。如果出现异常就把它设置为  true
，稍后再检查它来判断是否需要清空窗体。

  

2\.  在  Open  按钮中刚刚添加的异常处理中再添加一个  try/catch  块。

  

3\.  给外层  try/catch  块添加一个  finally  来把窗体重设为它的原始的空白状态。如果  clearForm  为  true
的话，把  LastUsed.Value  重设为  DateTime.Now  （这会返回当前时间）。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





