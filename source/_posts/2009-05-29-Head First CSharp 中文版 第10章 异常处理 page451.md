---
title: Head First C# 中文版 第10章 异常处理 page451
date: 2009-05-29 16:37:00
tags: 我翻译的Head First C#（习作）
---
③  使用监视窗口

  

监视窗口是调试器提供的一个很有用的特性，它让你可以检查对象中的字段和变量的值。把鼠标悬停在  fileName.Length
上，然后右击选择“添加监视”。它将会被添加到监视窗口中去。然后把  random.Next(fileName.Length)
添加进去。下图是监视窗口看起来的样子，这取决于你指向的文件夹中有多少文件  \--  这里，我们有五个文件，所以  fileName  有五个元素：

  

![](http://student.csdn.net/attachment/200905/29/39098_12435862662f7R.jpg)

④  把  fileName  赋值为一个空字符串数组

  

在监视窗口的两个被监视变量下面得空白处双击，你会看到一个光标。键入：  fileName = new String [0]  。观察窗口的最上端  \--
一旦你按下了回车，  fileName  将会改变为  {string [0]}  。一个重新估算按钮将会显示在  random.Next  那一行
\--  点击它，其值将会变成  0  。这是怎么回事  ?

  

监视窗口有一个很有用的特性  \--  让你可以修改其中显示的变量和字段的值。它还允许你执行方法和创建对象  \--
你这么做的时候，就会显示出重新估算按钮，你看可以点击该按钮来让这一行再次执行，因为有的时候同一个方法运行两次会得到不同的结果（比如说  Random  ）。

  

![](http://student.csdn.net/attachment/200905/29/39098_1243586267Dq35.jpg)

⑤  重现最开始导致  Brian  遇到的异常的那个问题

  

下面是调试中有趣的部分。给调试器添加一行  \--  实际导致异常的语句：  fileName[random.Next  （
fileName.Length  ）  ]  。你把它键入之后，监视窗口就会计算其值  ...
这也就导致了异常。它用显示一个叹号的方式来表示遇到了异常，并把异常的文本值显示在“值”这一列中。

  

![](http://student.csdn.net/attachment/200905/29/39098_1243586267ooPO.jpg)

你可以在调试器中重现遇到过的异常。这是富有描述性的异常信息可以帮你修改代码的另一种方式。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





