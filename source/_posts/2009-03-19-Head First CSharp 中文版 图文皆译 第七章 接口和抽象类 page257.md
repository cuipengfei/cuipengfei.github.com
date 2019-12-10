---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page257
date: 2009-03-19 10:43:00
tags: 我翻译的Head First C#（习作）
---
实现接口的类必须要包含接口定义的所有方法

  

实现接口也就意味着类要实现接口中定义的每一个方法、属性，否则无法编译。如果一个类实现不止一个接口，那就要在该类中写所有接口定义的每一个方法、属性。但是，不要
轻易相信我们给你的结论，如下做：

  

①  创建一个新应用并添加一个叫做IStingPatrol.cs的类文件

  

不要向该文件写入一个类，把上一页的IStingPatrol接口键入。

  

②  向项目添加一个Bee类

  

暂时不要写方法、属性进去。只是让它实现IStingPatrol接口：

  

public class Bee : IStingPatrol {

  

③  编译

  

试着编译一下。啊！编译器报错了：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090319/2009-03-19_10-34-14.jpg)

④  向Bee类添加属性、方法

  

添加一个LookForEnemies方法和一个SharpenStinger方法--这两个方法不需要做什么。然后添加一个叫做AlertLevel的int属性的
get访问器还有一个叫做StingerLength的int属性的set访问器。现在程序可以编译了！



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

