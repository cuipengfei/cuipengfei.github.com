---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page367
date: 2009-04-18 16:37:00
tags: 我翻译的Head First C#（习作）
---
地牢里的一切都是PictureBox

  

玩家，武器，敌人都要用图标表示。添加九个PictureBox控件，把它们的Visible属性设置为False。然后，游戏可以移动这些控件，根据需要切换它们的
Visible属性值。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090418/2009-04-18_16-08-35.jpg)

物品栏中也包含PictureBox控件

  

你可以用五个50X50的PictureBox控件来表示物品栏。把它们的BackColor属性设置为Transparent。由于图片文件的背景是透明的，所以你
可以看见它们背后的卷轴和地牢：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090418/2009-04-18_16-25-00.jpg)

创建状态窗口

  

生命值和攻击按钮、移动按钮共同处于一个TableLayoutPanel中。关于生命值，你要创建两个列，把列分割线拖动到左边一点。添加四行，每行高度占四分之一
，然后添加八个标签控件到八个小格里面：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090418/2009-04-18_16-33-28.jpg)



