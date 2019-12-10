---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page277
date: 2009-03-25 21:51:00
tags: 我翻译的Head First C#（习作）
---
有些类永远不应该被实例化

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090325/2009-03-25_21-47-59.jpg)

还记得我们的动物园模拟器程序么？在那个程序里你肯定会实例化一些河马、狗、狮子。但是犬科类和猫科类怎么办呢？实际上有些类根本不需要被实例化...而且即使实例化
也没有任何意义。下面是一个例子。

我们来给一个在书店购书的学生写一个基类。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090325/2009-03-25_21-43-20.jpg)

下面是ArtStudent类--它继承自Shopper类：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090325/2009-03-25_21-46-05.jpg)

下面这个类也继承Shopper：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090325/2009-03-25_21-46-52.jpg)

实例化Shopper类会怎么样呢？这样做有意义吗？



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





