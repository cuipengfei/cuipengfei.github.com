---
title: Head First C# 中文版 第13章 控件和图形 page592
date: 2009-08-01 11:45:00
tags: 我翻译的Head First C#（习作）
---
使用  Graphics  对象缩放  Bitmap

  

我们来仔细研究一下添加到渲染器中的  ResizeImage  （）方法。首先，它创建一个  Bitmap
对象，其尺寸正是图片需要被缩放到的尺寸。然后使用  Graphics.FromImage  （）方法来创建一个新的  Graphics  对象。使用
Graphics  对象的  DrawImage  （）方法来把图片绘制到  Bitmap  上去。向该方法中传递进去代表图片尺寸的长宽数值。最后返回
Bitmap  对象，它可以被用作窗体的背景或者是动画单元。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-08-01_10-49-47633847239951073595.jpg)

看看实际中的图像缩放

  

下面做的只是临时的，做完之后把按钮和代码删掉。

  

拖拽一个按钮到田园窗体中去并添加代码。它创建一个  PictureBox  ，尺寸为  100x100
，设置其边线为黑色，这样你就可以看到它有多大了。然后使用  ResizeImage  （）方法来把蜜蜂的图片压缩到  80x40  并把它赋值给
Image  属性。把  PictureBox  添加到窗体之后，蜜蜂就出现了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-08-01_11-01-59633847239951698595.jpg)

ResizeImage（）方法创建一个Graphics对象来把图像绘制到Bitmap上去。然后把Bitmap返回，它可以用以显示在窗体上或者是Picture
Box上。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

