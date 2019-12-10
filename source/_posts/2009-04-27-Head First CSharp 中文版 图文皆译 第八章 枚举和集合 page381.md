---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page381
date: 2009-04-27 22:08:00
tags: 我翻译的Head First C#（习作）
---
窗体把这些都组合起来

  

窗体中包含有一个Game对象作为私有字段。该对象在窗体的Load事件中被创建，窗体中各种事件处理方法通过使用Game对象的字段和方法来保持游戏运行。

  

窗体的Load事件开启游戏，它给Game传递一个定义了地牢的场地的Rectangle。下面是窗体中的一点代码，来帮你开个头：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090427/2009-04-27_21-42-08.jpg)

每个PictureBox的Click事件对应一个事件处理方法。玩家点击剑的时候，首先用Game对象的CheckPlayerInventory（）方法来检查剑
是否在玩家的物品栏中。如果玩家持有剑，窗体将会调用Game的Equip（）方法来装备它。然后设置每个PictureBox的BorderStyle属性来在剑的
周围画一个边框，并确保别的图标周围没有边框。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090427/2009-04-27_21-59-06.jpg)

每个移动按钮对应一个事件处理方法。这些方法很简单。首先用一个适当的Direction值去调用Game的Move（）方法，然后调用窗体的UpdateChara
cters（）方法。

  

这四个攻击按钮的事件处理方法也很简单。每个都调用Game的Attack（）方法，然后调用窗体的UpdateCharacters（）方法。如果玩家装备着某个药
剂，攻击按钮还是调用Game的Attack（）方法--但是使用药剂的时候不需要方向。所以要使Left、Right、Down的按钮不可见（玩家装备别的武器之后
要把它们修改回可见状态），并把Up按钮的文字设置为“Drink”。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

