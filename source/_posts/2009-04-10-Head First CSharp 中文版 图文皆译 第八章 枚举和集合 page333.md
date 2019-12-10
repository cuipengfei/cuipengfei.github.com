---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page333
date: 2009-04-10 22:03:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090410/2009-04-10_21-30-12.jpg)

随机创建五张扑克然后给它们排序。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090410/2009-04-10_21-58-26.jpg)

①  写代码来生成一堆乱七八糟的扑克

  

向窗体添加一个按钮来创建五个Card对象。创建每个扑克对象之后，用内建的Console.WriteLine（）方法来把扑克的名字输出。你可以在程序运行的时候
选择视图中的输出来查看输出。

  

②  创建一个实现IComparer接口的类来给扑克排序

  

下面是一个好机会可以试试IDE实现接口的捷径：

  

Public class CardComparer_byValue : IComparer<Card>

  

然后点击IComparer并用鼠标在字母I上面悬停。下面会出现一个小框，点击小框，IDE将会弹出一个窗口：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090410/2009-04-10_21-45-56.jpg)

如果你点击“实现IComparer<Card>接口”，IDE将会自动写出你需要实现的方法、属性。在这个特例下，IDE将会创建一个空的Comparer（）方法
来比较两张扑克，x和y。自己写方法让方法在x比y大的时候返回1，反之返回-1，如果一样大就返回0。在这个例子中，确保K在J之后，而J在4之后，4在A之后。

  

③  确保输出正确

  

下面是你的窗体在按钮被点击后看起来的样子：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090410/2009-04-10_21-52-59.jpg)



