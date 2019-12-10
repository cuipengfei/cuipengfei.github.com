---
title: Head First C# 中文版 第13章 控件和图形 page580
date: 2009-07-25 09:26:00
tags: 我翻译的Head First C#（习作）
---
渲染器使用  BeeControl  来在你的窗体上绘制会动的蜜蜂

  

有了一个  BeeControl  和两个窗体，你现在只需要一种方式来放置蜜蜂，把它们从一个窗体移动到另一个窗体，并记录其存在。你也需要在
FiledForm  上放置花朵，不过花朵不会动，这就很简单了。这些代码都可以放入一个新类中，  Renderer  。下面是该类的职责：

  

①统计数据窗体将会是蜂巢窗体和田园窗体的父窗体

  

向项目中添加两个窗体，  HiveForm  和  FiledForm  。然后在主窗体的构造方法中添加代码来显示其子窗体并告知  Windows
统计数据窗体是它们的所有者。

  

![](http://student.csdn.net/attachment/200907/25/39098_1248485216MEXM.jpg)

子窗体会随着父窗体一起最小化。

  

②渲染器中含有对  World  和每个子窗体的引用

  

渲染器需要知道每只蜜蜂和每朵花的位置，所以它需要  World  的引用。他需要在子窗体中移除和添加控件，所以也需要子窗体的引用：

  

![](http://student.csdn.net/attachment/200907/25/39098_1248485216bak2.jpg)

③渲染器使用字典来记录控件

  

World  用  List  来记录花朵和蜜蜂。而渲染器既需要记录花朵和蜜蜂也需要记录它们对应于哪一个  BeeControl  和
PictureBox--  或者，若没有找到对应的控件就需要创建一个。所以这就是使用字典的绝佳机会了。所以  Renderer  类中还需要两个字段：

  

![](http://student.csdn.net/attachment/200907/25/39098_1248485216YtIi.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

