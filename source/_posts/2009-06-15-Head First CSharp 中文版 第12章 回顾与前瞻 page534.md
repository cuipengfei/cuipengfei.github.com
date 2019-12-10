---
title: Head First C# 中文版 第12章 回顾与前瞻 page534
date: 2009-06-15 17:23:00
tags: 我翻译的Head First C#（习作）
---
我们准备好了来写  World  类了

  

有了  Hive  ，  Bee  和  Flower  类，我们可以开始写  World  类了。  World
负责协调模拟器中的每一个方面：记录所有的蜜蜂，告诉蜂巢是否有足够的控件来容纳更多的蜜蜂，定位花朵，等等：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090615/2009-06-15_12-55-22.jpg)

World  对象维持所有事物的运行

  

World  对象的最重要的任务之一就是在模拟器中的每一轮去调用每一朵花，每一只蜜蜂还有蜂巢的  Go  （）方法。换句话说，  World
维持了模拟器世界中的生命的运转。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090615/2009-06-15_17-19-50.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

