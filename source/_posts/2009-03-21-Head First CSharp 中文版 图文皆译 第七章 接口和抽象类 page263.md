---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page263
date: 2009-03-21 09:45:00
tags: 我翻译的Head First C#（习作）
---
接口可以继承接口

  

一个类继承自另一个类会得到父类的方法、属性。而接口继承则更加简单，由于接口中没有实现的方法体，你都不用去调用父接口的构造方法或者其他方法。子接口只是简单的获
得父接口的方法、属性。

  

在类图中，接口

的继承用虚线表示

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090321/2009-03-21_09-30-11.jpg)

实现IWoker的子接口的类必须要实现IWorker接口的方法、属性

  

一个类实现一个接口，就必须要在其中包含该接口中定义的方法、属性。如果该接口还继承自其他接口，那么这个类也要实现其父接口中的方法、属性。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090321/2009-03-21_09-36-46.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

