---
title: Head First C# 中文版 图文皆译 第六章 继承 page247
date: 2009-03-14 15:13:00
tags: 我翻译的Head First C#（习作）
---
③  让Worker类继承Bee类

  

需要让Worker的构造方法对于父类的构造方法。Worker的构造方法要接受一个代表体重的参数并把它传进父类的构造方法里面去。然后给Worker的Shift
Left添加override关键字。这样工蜂就可以计算蜂蜜消耗量了...并没有大幅度的修改Worker类！

  

④  让Queen类继承Bee类

  

由于蜂后需要计算蜂蜜消耗量并向报告中写入消耗量，所以她需要做比较多的改动。

*  蜂后覆写Bee.GetHoneyConsumption（）方法，向其中添加额外的计算。蜂后需要知道是否有2个或者更少的工蜂在工作，这样才可以知道是否需要20或者30个单位的额外蜂蜜。 

*  修改蜂后的WorkTheNextShift（）方法来把消耗量写入报告。最后需要向报告中写入“总共蜂蜜消耗量：XXX单位”。 

*  像工蜂类一样的修改蜂后的构造方法。 

  

⑤  修改窗体来初始化蜜蜂

  

由于修改了蜜蜂的构造方法，所以需要修改创建它们的方式，多加一个代表体重的参数。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090314/2009-03-14_15-11-23.jpg)

对于窗体只需要这么一点修改！



