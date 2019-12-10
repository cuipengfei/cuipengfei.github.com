---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page268
date: 2009-03-22 22:05:00
tags: 我翻译的Head First C#（习作）
---
向下转型让你把电器再次转换回咖啡壶

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090322/2009-03-22_21-56-50.jpg)

向上转型很好很强大，因为它让你可以在需要电器的地方使用咖啡壶或者电热炉。但是这样也有缺点--如果用Appliance的引用指向了一个CoffeeMaker对
象，那就只可以调用Appliance定义的方法、属性。这时就需要向下转型了：向下转型把原先向上转型的引用再次转换回去。你可以用is关键字来检查你的Appli
ance引用是否实际指向了一个CoffeeMaker对象。如果是的话，你可以用as关键字再次把它转换回去。

  

①  从已经向上转型过的咖啡壶开始吧

  

下面是我们原来写的代码：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090322/2009-03-22_21-42-53.jpg)

②  如果我们想要把Appliance转换回CoffeeMaker怎么办？

  

向下转型的第一步就是用is关键字检查向下转型是否可行。

If ( powerConsumer is CoffeMaker )

  

③  现在我们知道它就是CoffeeMaker对象了，就把它当做CoffeeMaker对象来用吧

  

使用is关键字是第一步。一旦你知道了有一个Appliance引用指向了一个CoffeeMaker对象，你就可以用as关键字来向下转型了。这样你就可以使用Co
ffeeMaker的方法、属性了。而由于CoffeeMaker又是继承自Appliance，你仍然可以使用Appliance定义的方法、属性。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090322/2009-03-22_21-49-01.jpg)

如果向下转型失败，as将会返回null

  

如果试着把Oven对象转型为CoffeeMaker会怎样呢？将会返回null--而如果你使用转型后的对象，.NET将会中止你的程序。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090322/2009-03-22_21-52-07.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

