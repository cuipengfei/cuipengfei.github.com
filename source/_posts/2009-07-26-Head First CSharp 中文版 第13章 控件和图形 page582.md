---
title: Head First C# 中文版 第13章 控件和图形 page582
date: 2009-07-26 18:05:00
tags: 我翻译的Head First C#（习作）
---
向项目中添加田园和蜂巢窗体

  

把  BeeControl  添加到项目中去。再添加两个窗体。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090726/2009-07-26_17-49-21.jpg)

搞明白你的位置在哪儿

  

你需要搞明白蜂巢处于  Filed  窗体的什么位置。使用属性窗口给  MouseClick  事件创建一个处理方法，添加如下代码：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090726/2009-07-26_17-55-49.jpg)

现在运行程序，点击蜂巢所在位置将会给出你蜂巢所在位置的坐标：

  

给蜂巢窗体添加同样的处理方法，并运行。让它获取出口，育婴室和蜂蜜工厂。你可以用这些位置来更新上一章中  Hive  类内的  ResetLocation
（）方法。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090726/2009-07-26_18-01-06.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

