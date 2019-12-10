---
title: Head First C# 中文版 第13章 控件和图形 page570
date: 2009-07-20 13:26:00
tags: 我翻译的Head First C#（习作）
---
控件对于可视化显示元素来说是很适合的

  

一只幼蜂被添加到蜂巢的时候，我们需要让模拟器添加一个新的  BeeControl  到  Hive
窗体中去，并且在蜜蜂飞来飞去的过程中移动控件的位置。蜜蜂从蜂巢中飞出去到田园中去的时候，需要把控件从  Hive  窗体中移除并添加到  Filed
窗体中去。蜜蜂从外面再次飞回来的时候，又要做相反的处理。同时我们希望蜜蜂会扇动翅膀  ...  控件可以使这一切变得简单。

  

①  World  添加一个幼蜂，渲染器就创建一个  BeeControl  并把它添加到  Hive  窗体的  Controls  中去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090720/2009-07-20_13-20-51.jpg)

②蜜蜂从蜂巢中飞出来进入田园的时候，渲染器会把  BeeControl  从  Hive  窗体的  Controls  中移除并添加到  Filed
窗体的  Controls  中去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090720/2009-07-20_13-22-25.jpg)

③一只蜜蜂在太老了并处于空闲状态的时候就会退休。渲染器检查  World  中的蜜蜂列表，如果找出一只蜜蜂已经不在其中了，就会把其对应的控件从  Hive
窗体中移除。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090720/2009-07-20_13-25-08.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

