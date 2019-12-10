---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page375
date: 2009-04-25 10:42:00
tags: 我翻译的Head First C#（习作）
---
编写Player类的Move（）方法

  

当窗体上的某个移动按钮被点击的时候，Game调用Player的Move（）方法来让玩家移动向某个方向。Move（）接受移动的方向作为参数（使用你已经创建的D
irection枚举）。下面是Move（）方法的开头：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090425/2009-04-24_18-08-17.jpg)

你需要补全这个方法。检查是否有武器在玩家附近（距离在1个单位之内）。如果有，捡起武器并把它加入到玩家的物品栏。

  

如果捡起的武器是玩家唯一持有的武器，立即装备上。这样，玩家就可以在下一轮使用它了。

玩家捡起武器之后，武器要在地牢中消失并在物品栏出现。这有窗体来控制，不是Player类的职责。

  

添加一个Attack（）方法

  

接下来是Attack（）方法。窗体的一个按钮被点击之后Attack（）方法会被调用，并且带有一个方向（还是Direction枚举）。下面是方法签名：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090425/2009-04-25_10-27-31.jpg)

所有的武器都有一个Attack（）方法，它接受一个Direction枚举和一个Random对象作为参数。玩家的Attack（）方法调用当前装备的武器并调用其
Attack（）方法。

  

如果该武器是一瓶药剂，那么Attack（）方法会在玩家饮用药剂之后把药剂从物品栏移除。

如果玩家没有装备任何武器，这个方法什么也不做。如果玩家装备的有武器，这个方法会调用武器的Attack（）方法。

  

不过药剂是一个特例。如果它被使用了，把它从玩家的物品栏移除，因为它不再可用了。

药剂会实现一个IPotion接口（一会再讨论它），所以你可以用“is”关键字来看某个武器是不是实现了IPotion。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

