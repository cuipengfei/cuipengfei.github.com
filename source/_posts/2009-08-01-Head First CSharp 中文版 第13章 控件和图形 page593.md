---
title: Head First C# 中文版 第13章 控件和图形 page593
date: 2009-08-01 20:45:00
tags: 我翻译的Head First C#（习作）
---
图像资源是储存在  Bitmap  对象中的

  

图形文件被包含在项目中的时候，它们会怎么被处理？你已经知道可以通过  Properties.Resources  来访问它们。但是它们到底会怎么被处理？

  

.NET  把图片以  Bitmap  对象的形式返回给你：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-08-01_20-23-09.jpg)

然后每一个  Bitmap  被绘制到屏幕上

  

图像被存储在  Bitmap  之后，窗体可以通过如下的调用来把它们绘制到屏幕：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-08-01_20-29-13.jpg)

图片越大，就  ...

  

注意到  DrawImage  （）方法的后两个参数了吗？如果图片是  175x175  呢？那么图形库就必须把图片缩放为  150x150
。如果图片大小为  1500x2025  呢？那么缩放过程就更为棘手了  ...

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-08-01_20-38-23.jpg)

缩放图片耗费很多处理器时间！只做一次的话，没关系。但是如果每一帧都处理的话，程序就会变慢。蜜蜂的尺寸很大，渲染器把它四处移动的时候（尤其是在蜂巢中的时候），
就必须一次又一次的缩放。这就导致了性能问题！



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





