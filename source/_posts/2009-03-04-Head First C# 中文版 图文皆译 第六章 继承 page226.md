---
title: Head First C# 中文版 图文皆译 第六章 继承 page226
date: 2009-03-04 15:43:00
tags: 我翻译的Head First C#（习作）
---
子类可以通过覆盖来改变或者替换继承来的方法

有时候你会想要让子类继承父类的大部分行为，而不是全部。你可以用继承改变一个类继承来的方法。

①  给父类中的方法添加virtual关键字

子类可以覆盖在父类中用virtual关键字标记的方法，virtual关键字标记的方法表示可以被子类覆盖。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90304/2009-03-04_13-03-08.jpg)

②  给子类添加一个同名的方法

需要用相同的方法签名--也就是说相同的返回值和参数--而且还需要在声明中用override关键字。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90304/2009-03-04_13-06-57.jpg)

在子类中用以override关键字修饰的方法来替代从父类中继承来的方法。在此之前，父类中的方法需要用virtual关键字修饰。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

