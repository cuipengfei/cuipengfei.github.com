---
title: Head First C# 中文版 图文皆译 第二章 page62
date: 2008-10-26 16:21:00
tags: 我翻译的Head First C#（习作）
---
你的程序用变量来操作数据  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

彻底了解后，你会知道每个程序都是处理数据的。数据有时会是文件中的表单，有时是游戏里的图像，有时是一条即时消息。这些都是数据。变量此时就有用了。程序用变量存储
数据。

声明你的数据

声明一个变量，你要告诉程序它的类型和名字。一旦C#知道了变量的类型，它将会在你犯了错误或作一些没有道理的事儿时组织编译，比如用“Fido”去减48353.

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081026/%E6%88%AA%E5%9B%BE04.jpg)

变量可变

程序运行过程中变量会有不同的值。换句话，变量的值可变。（所以叫“变量”是个好名字。）这个很重要，因为这是你写的和将要写的程序的核心思想。所以如果你的程序把m
yHeight变量设置为63；

Int myHeight=63  ；

每次myHeight出现在代码里，C#将会用它的值63来代替。然后，如果你把它改作12：

myHeight=12  ；

C#  将会用12代替myHeight--但是这个变量仍叫做myHeight。

无论何时你的程序需要操作数

字，文本，True/False值，或

者其他任何数据，你将会用变

量来记录它们。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





