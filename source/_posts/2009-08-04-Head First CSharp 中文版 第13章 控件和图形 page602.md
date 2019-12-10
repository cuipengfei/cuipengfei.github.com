---
title: Head First C# 中文版 第13章 控件和图形 page602
date: 2009-08-04 10:41:00
tags: 我翻译的Head First C#（习作）
---
使用  Paint  事件来让你的图形可以持续存在

如果绘制到窗体上的图形会在窗体被遮挡的时候消失的话，那可不好。很幸运，有一个简单的方式来让图形持久存在：编写一个  Paint
事件处理器。窗体在每次需要重绘的时候都会触发  Paint  事件  \--  比如被拖动出屏幕范围的时候。它的  PaintEventArgs
参数有一个  Graphics  属性，用它绘制的东西可以“持久”。

①添加一个  Paint  事件处理方法

Paint  事件在每一次你的图像被弄花的时候都会被触发。所以在  Paint  事件处理方法内部编写绘制的代码可以使得绘制的图形持久存在。

②使用  Paint  事件的  EventArgs  的  Graphics  对象

你的事件处理方法可以如下开头，而不用  using  语句：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090804/2009-08-04_10-33-20.jpg)

你无须使用  using  语句，因为不是你创建的它，所以你也就不需要释放它。

③复制绘制重叠的蜜蜂和蜂巢的代码

把原来写的制重叠的蜜蜂和蜂巢的  Click  事件的代码复制粘贴到  Paint  事件处理方法中来  \--  开头的  using
语句除外。现在运行程序，图形可以持久存在了！



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

