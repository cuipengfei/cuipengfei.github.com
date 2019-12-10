---
title: Head First C# 中文版 第13章 控件和图形 page603
date: 2009-08-04 17:31:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90804/2009-08-04_17-10-38.jpg) 使用  Bitmap  对象和  DrawImage
（）方法，以及窗体和用户控件的知识来创建一个使用  TrackBar  来缩放图片的用户控件。

  

①向一个新用户控件上添加两个  TrackBar  控件

  

创建一个新的窗体应用。添加一个用户控件  \--  命名为  Zoomer--  设置其  Size  为（  300  ，  300  ）。拖拽两个
TrackBar  到上面去。  trackBar1  拖到底部。  trackBar2  拖到右侧，并设置其  Orientation  为
Horizontal  。两个  TrackBar  的  Minimum  都设置为  1  ，  Maximum  设置为  175  ，  Value
设置为  175  ，  TickStyle  设置为  None  ，背景色设置为白色。最后，双击两个  TrackBar  来添加  Scroll
事件处理方法。让两个事件处理方法都调用控件的  Invalidate  （）方法。

  

用户控件的  Paint  事件和窗体的基本一样。

  

②把一张图片载入  Bitmap  对象并把它绘制到控件上

  

给  Zoomer  用户控件添加一个叫做  photo  的私有  Bitmap  类型的字段。创建  Bitmap
实例的时候，载入一张图片。然后添加用户控件的  Paint  事件处理方法，它用白色的填充矩形填充整个用户控件，然后把  photo
绘制到上面，其左上角为（  0  ，  0  ），宽度为  trackBar1.Value  ，高度为  trackBar2.Value
。然后把用户控件拖拽到窗体上去，记得调整窗体的大小，以使得  TrackBar  处于边缘。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90804/2009-08-04_17-28-10.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

