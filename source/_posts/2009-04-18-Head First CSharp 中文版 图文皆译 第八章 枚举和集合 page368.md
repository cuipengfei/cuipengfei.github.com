---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page368
date: 2009-04-18 22:02:00
tags: 我翻译的Head First C#（习作）
---
架构：使用对象

  

在游戏中你会需要很多类型的对象：一个Player对象，一些Enemy的子类的对象，一些Weapon的子类的对象。还需要一个管理全局的对象：Game对象。

  

下面只是一个概貌。关于玩家和敌人的移动、敌人如何判断自己是否离玩家够近的细节，我们稍后会告诉你。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090418/2009-04-18_21-40-33.jpg)

Game  对象管理回合

  

窗体的移动按钮被点击的话，窗体将会调用Game对象的Move（）方法。这个方法将会让玩家移动一回合，然后让敌人移动。所以Game对象负责管游戏的理回合制部分
。

  

比如说，下面是移动按钮的工作过程：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090418/2009-04-18_21-52-36.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





