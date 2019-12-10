---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page267
date: 2009-03-22 13:18:00
tags: 我翻译的Head First C#（习作）
---
向上转型对于对象和接口都适用

  

用子类代替基类就叫做向上转型。这对于你创建类层次结构来说是一个很有用的工具。向上转型的唯一缺点就是你只可以调用父类定义的方法、属性。换句话说，当你把一个咖啡
壶仅仅当作一个器具来对待的时候，你不可以让它煮咖啡或者装水。但是你可以判断它是否插上电源了，因为每一个电器都会可以到这一点（所以PluggedIn属性定义在
Appliance类中）。

  

①  我们来创建一些对象

  

我们可以像平常一样创建CoffeeMaker和Oven类：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090322/2009-03-22_12-57-28.jpg)

②  如果想要创建一个电器的数组怎么办？

  

不可以把咖啡壶放进炉子数组，也不可以把炉子放进咖啡壶数组。但是他们都可以放进Appliance[]数组：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090322/2009-03-22_13-02-02.jpg)

③  但是你不可以把电器当作电热炉

  

如果你有一个Appliance的引用，你只可以通过它调用与电器有关的方法、属性。你不可以通过Appliance引用调用咖啡壶的方法、属性，即使你知道它实际上
就是CoffeeMaker的对象也不管用。所以下面的代码完全可以工作的很好，因为这段代码把咖啡壶当做点起来对待：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090322/2009-03-22_13-10-40.jpg)

一旦从子类向父类转型，你就只可以调用和引用类型相匹配的方法、属性。



