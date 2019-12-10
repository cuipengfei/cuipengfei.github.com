---
title: Head First C# 中文版 第13章 控件和图形 page589
date: 2009-07-31 17:17:00
tags: 我翻译的Head First C#（习作）
---
看起来不错，但是有的东西不太对

  

仔细观察一下飞舞在蜂巢和花朵之间的蜜蜂，你将会发现蜜蜂们被渲染的方式有些问题。还记得你把  BeeControl  的  BackColor  属性设置为
Color.Transparent  了吗？不幸的是，这并不足以使得程序避免问题，这些问题在图形程序中是很常见的。

  

①有一些很严重的性能问题

  

注意到所有的蜜蜂都在蜂巢之内的时候模拟器会变慢吗？如果没有的话，改变一下  World  类中的常数。注意帧率  \--
添加更多的蜜蜂，帧率将会显著地下降。

  

②花朵的背景并非真的透明

  

花朵的图片的背景是透明的，以此确保花朵的背景和窗体的背景是匹配的，但是当花朵互相重叠的时候看起来不怎么好。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090731/2009-07-31_17-03-07.jpg)

③蜜蜂的背景也并不是透明的

  

实际上，  Color.Transparent  是有些局限性的。蜜蜂飞过花朵的时候，也会出现“切割”问题。蜂巢窗体的背景确实可以通过透明的区域显示出来，但
是蜜蜂互相重叠的时候，就会出问题。但是你观察蜜蜂在蜂巢中移动的时候，你将会发现蜜蜂的图像被扭曲了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090731/2009-07-31_17-14-12.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

