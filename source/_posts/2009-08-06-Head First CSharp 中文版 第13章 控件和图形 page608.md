---
title: Head First C# 中文版 第13章 控件和图形 page608
date: 2009-08-06 09:04:00
tags: 我翻译的Head First C#（习作）
---
双缓冲可以使得动画更加平滑

  

回到图片缩放器，随便玩玩上面的  TrackBar  。注意到拖动滑块的时候，有很多的闪烁了么？这是因为每次滑块移动一点，  Paint
事件的处理器就要绘制白色矩形和图片。当你的眼睛每秒钟看到很多次交替的白色矩形和图片的时候，就会感觉到闪烁。这很可恶  ...
而这是可以通过使用一种叫做双缓冲的技术来避免的。这就是说，把每一帧绘制到一个不可见的  Bitmap
上去（一个“缓冲”），只在一帧被绘制完成之后才把它显示出来。下面是该方法使用  Bitmap  的方式：

  

①  这是一个典型的使用  Graphics  来把图形绘制到窗体的程序：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_08-44-05.jpg)

②  要做双缓冲的话，可以添加一个  Bitmap
到程序中去充当缓冲器的角色。这样，每当窗体或者控件需要重绘的时候，图形就可以绘制到缓冲器上而不是窗体上去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_08-49-57.jpg)

③  现在一帧已经绘制到一个不可见的  Bitmap  上了，我们可以使用  DrawImageUnscaled  （）方法来把  Bitmap
复制回窗体的  Graphics  去。复制是一次性完成的，这就避免了闪烁。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090806/2009-08-06_09-02-33.jpg)



