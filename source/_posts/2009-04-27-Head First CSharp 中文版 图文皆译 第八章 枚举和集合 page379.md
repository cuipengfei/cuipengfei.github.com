---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page379
date: 2009-04-27 09:04:00
tags: 我翻译的Head First C#（习作）
---
不同的武器攻击方式也不同

  

每个Weapon类的子类都有自己的名称和攻击方式。你的任务就是去实现这些子类。下面是一个Weapon子类的基本框架：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090427/2009-04-27_08-34-49.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090427/2009-04-27_09-01-49.jpg)

剑是玩家捡起来第一件武器。它的攻击范围很大：向上攻击的时候，试着攻击上方的敌人--
如果向上的方向没有敌人，就攻击任何处于顺时针方向上的敌人，如果还是没有敌人，就试着去攻击逆时针方向上的敌人。它的攻击长度是10，可以造成3点伤害。

  

弓箭的攻击范围很窄，但是攻击距离很长--
攻击半径为30，但是攻击值为1。与剑不同，剑可以攻击三个方向（因为玩家可以在很宽的范围内挥舞），而弓箭只可以在一个方向上射。

  

钉头锤是地牢中的攻击力最大的武器。玩家用它向哪个方向攻击都无所谓--因为玩家用它向着所有方向挥舞，它的攻击半径为20，攻击值最大为6。

  

不同的武器以不同的方式调用DamageEnemy（）方法。钉头锤可以攻击四个方向，所以如果玩家向右攻击，它将会调用DamageEnemy（Direction
.Right，20，6，random）。如果没有打到敌人，就再次向上攻击。如果还是没有敌人，将会试着攻击左侧，然后再是下方--
这样钉头锤就可以向着四周攻击了。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





