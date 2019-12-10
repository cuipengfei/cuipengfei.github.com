---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page383
date: 2009-04-28 22:04:00
tags: 我翻译的Head First C#（习作）
---
③  更新武器的PictureBox

声明一个weaponControl变量并用一个很长的switch语句来把它赋值为当前武器对应的PictureBox。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90428/2009-04-28_21-39-06.jpg)

余下的case语
句要把weaponControl赋值为当前窗体上的控件。Switch语句之后，把weaponControl.Visible设置为true来把它显示出来。

④  设置物品栏中每个图标对应的PictureBox的Visible属性

用Game对象的CheckPlayerInventory（）方法来搞明白是否要把物品栏中的各个图标显示出来。

⑤  本方法余下的部分

首先更新玩家的PictureBox的位置并更新显示玩家的生命值的标签。然后还需要一些变量来表示是否要显示各个敌人。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90428/2009-04-28_21-52-24.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

