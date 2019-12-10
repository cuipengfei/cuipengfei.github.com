---
title: Head First C# 中文版 第13章 控件和图形 page578
date: 2009-07-24 12:50:00
tags: 我翻译的Head First C#（习作）
---
使用  UseControl  是一种创建控件的好方式

  

有一种创建控件的简单方法，使用  IDE  向项目中添加一个  UserControl  。  UserControl
的使用就和窗体很类似，你可以把工具箱中的控件拖拽上去，也可以使用  IDE  的窗体设计器。也可以像使用窗体一样的使用其中的事件。我们来用
UserControl  重建  BeeControl  吧。

  

如下做：

  

①在  IDE  中右击  BeeControl.cs  并把它重命名为  OldBeeControl.cs  。

  

②右击项目添加一个  UserControl  ，  IDE  将会在一个窗体设计器中打开该控件。

  

③拖拽一个  Timer  控件到  UserControl  上去，把  Interval  设置为  150  。双击  Timer
来创建其事件处理方法，在其中使用原来编写的事件处理代码。

  

④更新  BeeControl  的构造方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090724/2009-07-24_12-43-18.jpg)

⑤回到窗体的按钮事件处理方法。当你把  BeeControl  改名为  OldBeeControl  的时候，窗体的代码也改变了。所以把这两行重新改为
BeeControl  ，这样窗体将会使用新的  UserControl  而不是  PictureBox  ：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090724/2009-07-24_12-48-15.jpg)

运行程序  \--  它将会运行的与原来无异。按钮现在添加和删除基于  UserControl  的  BeeControl  。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

