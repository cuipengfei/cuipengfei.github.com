---
title: Head First C# 中文版 第13章 控件和图形 page569
date: 2009-07-20 09:51:00
tags: 我翻译的Head First C#（习作）
---
渲染器把  World  中的所有事物绘制到两个窗体上去

  

World  对象记录着模拟器中的一切，蜂巢、蜜蜂、花朵。但是它并不把任何东西绘制出来。这是渲染器的任务。它读取  World
中的信息并据此绘制到窗体上去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090720/2009-07-20_09-33-22.jpg)

模拟器在每一帧之后渲染画面

  

主窗体调用  World  的  Go  （）方法之后要去调用渲染器的  Render  （）方法来重绘显示窗口。比如，每朵花都是用一个
PictureBox  控件来显示的。不过我们在蜜蜂身上深入一些吧，创建一个可显示动画的控件。你要去创建这个新控件，叫做  BeeControl
，并自己定义其行为。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090720/2009-07-20_09-47-20.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

