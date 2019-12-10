---
title: Head First C# 中文版 第13章 控件和图形 page609
date: 2009-08-06 11:03:00
tags: 我翻译的Head First C#（习作）
---
双缓冲是内建于窗体和控件中的

  

你可以使用  Bitmap  来做双缓冲，但是  C#  和  .NET  通过内置的支持使得这一点更简单。只需要把  DoubleBuffered
属性设置为  true  。回到  Zoomer  用户控件  \--  把它的  DoubleBuffered  设置为  true
，它就会停止闪烁了。现在回到  BeeControl  也这样处理。这样不可以解决所有的图形问题  \--  但是会有所改善。

  

彻底检修蜂巢模拟器

  

如下做：

  

①从移除  BeeControl  用户控件开始

  

在蜂巢和田园中不再会有任何的控件。没有  BeeControl  ，没有  PictureBox  ，什么控件都没有。蜜蜂，蜂巢，和花朵都要用  GDI+
来绘制。所以，删掉  BeeControl.cs  和  OldBeeControl  吧。

  

②需要一个  Timer  来控制蜜蜂扇动翅膀

  

蜜蜂扇动翅膀的频率比模拟器的帧频低多了，所以你需要另一个慢得多的  Timer  。这没什么可奇怪的，  BeeControl  就有自己的  Timer
。

  

③重要步骤：彻底检修渲染器

  

你可以把现有的渲染器废弃掉了，因为它做的工作都是基于控件的。你不再需要那个叫做  lookup  的字典了，因为已经没有  PictureBox  和
BeeControl  了，不需要查询了。新的渲染器将会有两个重要的方法：  DrawHive  （  g  ）和  DrawField  （  g
），它俩分别绘制蜂巢和田园。

  

④最后，把渲染器和模拟器的其他部分联系起来

  

蜂巢和田园两个窗体的  Paint  事件处理方法将会需要调用渲染器的  DrawHive  （）和  DrawField  （）方法。两个
Timer--  一个控制模拟器的帧频的，一个控制蜜蜂扇动翅膀的  \--  它俩将要调用两个窗体的  Invalidate
（）方法，以此来使得两个窗体重绘自身。这时，窗体的  Paint  事件处理方法就回去渲染新的一帧。

  

使用Paint事件的方式绘制图形的时候，你可以通过设置DoubleBuffered属性来很方便的开启双缓冲。



