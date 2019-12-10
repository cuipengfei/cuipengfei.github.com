---
title: Head First C# 中文版 第10章 异常处理 page458
date: 2009-06-01 15:56:00
tags: 我翻译的Head First C#（习作）
---
使用调试器来跟踪  try/catch

  

异常处理中很重要的一点就是当  try  块中抛出一个异常的时候，余下的代码就不再执行了。程序的执行将会立即跳到  catch
块中的第一个语句去。不过，耳听为虚  ...

  

做下面的步骤：

  

①  确保你把这一章的代码都归结到  Random Excuse  按钮的事件处理方法中去了。在事件处理方法的第一行设置一个断点。在  IDE
中运行程序。点击  Folder  按钮来选择一个仅含有一个文件的文件夹  \--  并且要确保这个文件不是有效的借口文件。点击  Random
Excuse  按钮。调试器会在你设置的断点处停下来。按  F10  六次来执行到调用  Excuse  构造方法的语句。你的屏幕看起来应该是这样的：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090601/2009-06-01_15-40-30.jpg)

②  用  F11  来进入  new  语句。调试器将会跳入  Excuse  类的构造方法，黄色的“下一条语句”栏将会指在构造方法的声明上。一直点击
F11  直到进入  OpenFile  （）方法。看看你碰到  Deserialize  （）这一行的时候会怎样。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090601/2009-06-01_15-51-31.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





