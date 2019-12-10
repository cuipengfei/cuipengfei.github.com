---
title: Head First C# 中文版 第13章 控件和图形 page611
date: 2009-08-06 21:01:00
tags: 我翻译的Head First C#（习作）
---
③为双缓冲的动画设置好蜂巢和田园窗体

  

移除窗体构造方法中设置背景图像的代码。移除所有窗体上的控件并把窗体的  DoubleBuffered  属性设置为  true  。给两个窗体添加
Paint  事件处理方法。下面是蜂巢窗体的  Paint  事件的处理方法  \--  田园窗体的差不多，只是它调用  PaintField
（）方法而已：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_17-25-26.jpg)

④两个窗体都需要一个公有的  renderer  属性：

  

移除所有的对于渲染器的  Reset  （）方法的调用，把所有的  new Renderer  （）语句替换为对下面的  CreateRenderer
（）方法的调用：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_17-31-54.jpg)

⑤大修渲染器

  

如下做：

  

*  移除两个字典。移除  Render  （）方法，还有  DrawBees  （），  DrawFlowers  （）方法。 

  

*  添加一些叫做  HiveInside  ，  HiveOutside  和  Flower  的  Bitmap  类型的字段，它们用于存储图像。然后创建两个  Bitmap[]  数组，分别叫做  BeeAnimationLarge  和  BeeAnimationSmall  。它们两个分别存储四张蜜蜂的图片  \--  大的是  40x40  ，小的是  20x20  。创建一个叫做  InitializeImages  （）的方法来缩放资源并把它们存储在这些字段中，并从  Renderer  的构造方法中调用它。 

  

*  添加  PaintHive  （）方法，它接受一个  Graphics  作为参数，它用来绘制蜂巢。首先绘制蓝色的矩形，然后使用 

DrawImageUnscaled  （）来绘制蜂巢内部的图片，然后使用  DrawImageUnscaled  （）来绘制每一只处于蜂巢内部的蜜蜂。

  

*  最后，添加  PaintField  （）方法。它在窗体上半部分绘制天蓝色的矩形，下半部分绘制绿色的矩形。窗体的两个属性  ClientSize  和  ClientRectangle  可以告诉你绘制区域的大小。你可以用这两个属性找到窗体的一半的位置。用  FillEllipse  （）来绘制一个黄色的太阳，用  DrawLine  （）绘制用于悬挂蜂巢的树枝。用  DrawImageUnscaled  （）来绘制蜂巢的外部形象。然后绘制花朵。最后绘制蜜蜂，这样蜜蜂就可以处于最前端。 

  

*  绘制蜜蜂的时候，要记得  AnimateBees  （）方法设置了  Cell  字段。 



