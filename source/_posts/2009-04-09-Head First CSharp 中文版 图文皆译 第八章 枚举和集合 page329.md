---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page329
date: 2009-04-09 21:44:00
tags: 我翻译的Head First C#（习作）
---
给鸭子排序的两种方式

  

List.Sort  （）方法知道如何给实现了IComparable接口的类排序。这个接口只有一个成员--一个叫做CompareTo（）的方法。Sort（）
方法用一个对象的CompareTo（）方法来把该对象和另一个对象进行比较，然后用返回值（一个int值）来决定哪一个对象排在前面。

  

但是有时你需要给没有实现IComparaBle接口的对象排序，.NET有另一个接口来解决这种状况。你可以给Sort（）方法传递一个实现了IComparer接
口的类的实例。这个接口也有一个方法。List的Sort（）方法使用要比较的对象的Compare（）方法来比较成对的对象，以此来搞清楚哪个对象应该在先。

  

一个对象的CompareTo（）方法把它自身与另一个对象比较

  

可以通过修改Duck类为实现IComparable接口来让List对象可以给鸭子排序。要这样做，就要添加一个接收Duck引用作为参数的CompareTo（）
方法。如果要比较的鸭子应该排在当前鸭子的后面，CompareTo（）方法会返回一个负值。

  

下面是一个根据大小来排序的Duck类：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090409/2009-04-09_21-29-06.jpg)

你可以通过让一个类实现IComparable接口并给它添加CompareTo（）方法来让List的Sort（）方法可以给它排序。



