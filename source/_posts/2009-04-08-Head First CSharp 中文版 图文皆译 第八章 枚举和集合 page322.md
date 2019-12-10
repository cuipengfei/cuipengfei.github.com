---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page322
date: 2009-04-08 13:02:00
tags: 我翻译的Head First C#（习作）
---
List  对象可以存储任何类型

你已经看见List可以存储字符串或者是Shoe对象。你也可以创建整数列表，或者任何你可以创建的类型的对象都可以包含到列表中。这就叫做泛型集合。创建List对
象的时候，你可以把它绑定到一个特定的类型：你可以创建int列表，string列表，或者是Shoe对象的列表。这使得操作列表变得简单--
一旦创建了列表，你就知道其中存储的数据的类型了。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090408/2009-04-08_12-44-05.jpg)

.NET Framework
有很多泛型接口，它们使得集合类可以操作任何类型。List实现了这些接口，所以你可以创建一个int列表，并以和操作Shoe列表类似的方式来操作它。

自己检查一下：在IDE中键入List，右键点击它并选择“转到定义”。这会转到List类的定义。它实现了一些接口。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090408/2009-04-08_12-56-21.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

