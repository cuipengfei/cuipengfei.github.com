---
title: Head First C# 中文版 第13章 控件和图形 page591
date: 2009-08-01 09:45:00
tags: 我翻译的Head First C#（习作）
---
需要改进图形性能的方法就是给渲染器添加一个方法来缩放图片。然后我们就可以在每张图片载入之后缩放它，而且只在  BeeControl
中和蜂巢窗体中使用缩放过之后的版本。

  

如下做：

  

①给渲染器添加一个  ResizeImage  方法

  

所有保存在项目中的图片都是以  Bitmap  对象的形式存在的。下面是一个缩放图片的静态方法  \--  把它添加到  Renderer  类中：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-07-31_22-06-56.jpg)

②给  BeeControl  添加这个  ResizeCells  方法

  

你的  BeeControl  可以存储自己的  Bitmap  对象  \--  在一个数组中。下面可以产生这个数组，把每一个图片缩放为合适的尺寸：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-07-31_22-12-33.jpg)

③修改  switch  语句来让它使用  cells  数组，而不是使用资源

  

BeeControl  的  Tick  事件的处理方法中有一个  switch  语句，这个语句用于设置其  BackgroundImage  ：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-08-01_09-25-55.jpg)

把  Properties.Resources.Bee_animation_1  替换为  cells[0]  。依次把其他的  case  代码行也替换为
cells  的元素，这样就只有缩放过的图片才会被显示出来。

  

④给  BeeControl  添加对  ResizeCells  （）方法的调用

  

需要添加两次对  ResizeCells  （）方法的调用。首先，把它添加到构造方法中去  \--  然后改变  BackgroundImageLayout
属性为  None  。然后添加  BeeControl  的  Resize  事件的处理方法，在其中调用  ResizeCells  （）方法  \--
这样，每次窗体缩放的时候，都会缩放其动画。

  

⑤手动设置窗体的背景

  

到属性窗口中去把蜂巢窗体的背景图片设置为  none  。然后在构造方法中把图片设置为合适的尺寸。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090801/2009-08-01_09-38-56.jpg)

现在运行模拟器，快多了！



