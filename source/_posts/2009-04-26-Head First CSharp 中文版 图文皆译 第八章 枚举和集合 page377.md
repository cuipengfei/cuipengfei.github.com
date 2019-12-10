---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page377
date: 2009-04-26 10:54:00
tags: 我翻译的Head First C#（习作）
---
编写不同的Enemy的子类

  

三个Enemy的子类非常直观易懂。每个敌人初始生命值不同，移动的方式不同，攻击时造成的伤害值也不一样。你需要给每一个敌人的基类构造方法传递一个不同的star
tingHitPoints参数，还需要给每一个子类实现不同的Move（）方法。

  

下面是其中一个子类看起来应该有的样子：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090426/2009-04-26_10-30-27.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090426/2009-04-26_10-50-37.jpg)

蝙蝠初始生命值为6。只要他还有一点生命值就继续向玩家移动并攻击。有50%的可能性它会向着玩家移动，50%的可能它向一个随机的方向移动。它移动之后，检查是否与
玩家近距离--如果近距离，然后攻击玩家，最高伤害值为2。

  

幽灵比蝙蝠难打，但是和蝙蝠一样，它将只在生命值大于零的时候攻击玩家。它生命值为8。移动的时候，三分之一的可能会向着玩家移动，三分之二的可能站着不动。如果距离
玩家比较近，就攻击玩家，最多伤害值为3。

  

食尸鬼是最厉害的敌人。它生命值是10。只在生命值大于零的时候移动并攻击玩家。移动的时候，三分之二的可能会向着玩家移动，三分之一的可能站着不动。如果距离玩家比
较近，就攻击玩家，最多伤害值为4。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

