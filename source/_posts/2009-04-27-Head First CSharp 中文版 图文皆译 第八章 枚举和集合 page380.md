---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page380
date: 2009-04-27 13:20:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090427/2009-04-27_13-02-54.jpg)

药剂要实现IPotion接口

  

有两种药剂，蓝色药剂和红色药剂，用来增加玩家的生命值。它们和武器类似--
玩家在地牢中捡起它来，通过点击物品栏来装备，通过点击攻击按钮来使用。所以它们理应继承自Weapon类。

  

但是药剂也有一点不同，所以你需要添加一个IPotion接口来让药剂含有额外的行为：增加玩家的生命值。IPotion接口很简单，只需要一个叫做Used的只读属
性，如果玩家还没有使用该药剂它返回false，使用过了就返回true。窗体通过这个属性来决定是否把药剂显示在物品栏上。

  

public interface IPotion

{

bool Used{get;}

}

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090427/2009-04-27_13-14-28.jpg)

IPotion  接口使得药剂只可以使用一次。通过该接口也可以判断某个武器是不是药剂：“if（weapon is IPotion）”。

  

BluePotion  类的Name属性要返回“Blue Potion”。它的Attack（）方法会在玩家使用蓝色药剂时被调用--
它要通过调用IncreasePlayerHealth（）方法来增加玩家的生命值，最多5点。玩家使用过药剂之后，Used属性将会返回true。

  

RedPotion  类和BluePotion很类似，只是它的Name属性返回“Red Potion”，它的Attack（）方法最多给玩家增加10点生命值。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

