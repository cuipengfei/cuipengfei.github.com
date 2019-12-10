---
title: Head First C# 中文版 第10章 异常处理 page446
date: 2009-05-27 22:28:00
tags: 我翻译的Head First C#（习作）
---
Brian  的代码做了一些我们没有预期到的事儿

  

Brian  写这个程序的时候，没有想到用户会试图去从一个空文件夹中取出随机的借口。

  

①  Brian  把程序指向笔记本上的一个空文件夹并点击  Random Button
的时候就发生了这个问题。我们来看看可不可以找出出了什么问题。下面是我们在  IDE  之外运行程序的时候弹出的未预期的异常窗口：

  

![](http://student.csdn.net/attachment/200905/27/39098_1243434849ppfo.jpg)

  

②  好的，很好的开头。这告诉我们位序超出了数组的界限，对吧？我们来在  Random Excuse  按钮的事件处理方法中找找有没有数组：

  

![](http://student.csdn.net/attachment/200905/27/39098_1243434849544x.jpg)

③  哦，里面没有数组。不过其中用  Excuse  类的重载的构造方法创建了一个新实例。或许这个构造方法中有数组吧：

  

![](http://student.csdn.net/attachment/200905/27/39098_124343485066xX.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

