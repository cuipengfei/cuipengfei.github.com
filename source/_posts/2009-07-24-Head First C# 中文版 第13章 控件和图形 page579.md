---
title: Head First C# 中文版 第13章 控件和图形 page579
date: 2009-07-24 20:22:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90724/2009-07-24_19-57-47.jpg)

因为窗体替你完成了工作

  

IDE  替你覆写了  Dispose  （）方法，当窗体要释放的时候，它将会释放  Controls
集合中的所有控件。而现在你要自己创建控件或者把控件从集合中移除了，你就要自己负责释放它们。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90724/2009-07-24_20-04-23.jpg)

问：为什么窗体中对应于基于  PictureBox  的  BeeControl  的代码同样适用于基于  UserControl  的
BeeControl  ？

  

答：因为代码并不管  BeeControl  是如何实现的。只要可以把它加入到窗体的  Controls  集合中去即可。

  

问：双击  OldBeeControl  类的时候，会出现一个消息提示要不要添加组件。这是怎么回事？

  

答：在项目中通过添加一个继承自控件的类来创建新控件的时候，  IDE  会做一些智能的事。其中之一就是让你可以操作组件，组件就是不可见的控件。创建一个继承自
PictureBox  的空类，重新生成项目，双击该类，机会见到那条消息。

  

向新类中拖拽一个  OpenFileDialog  并设置它的一些属性。区代码中查看该类的构造方法  \--IDE  为你做了初始化工作并设置了那些属性。

  

问：改变  OpenFileDialog  中的属性之后，  IDE  会提示说需要重新生成项目，为什么？

  

答：因为设计器需要运行你的控件，而只有在你重新生成之后它才可以运行最新版本的代码。

  

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

