---
title: Head First C# 中文版 图文皆译 第六章 继承 page227
date: 2009-03-05 08:57:00
tags: 我翻译的Head First C#（习作）
---
可以使用父类的地方，都可以用子类来替代父类  ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuip
engfei1/EntryImages/20090305/2009-03-04_22-50-43.jpg)

继承最有用的方面就是可用子类来替代父类。所以，如果Recipe（）方法接受一个Cheese类型的参数，而有一个叫做AgedVermontCheddar的类继
承自Cheese，你可以把AgedVermontCheddar的实例传进该方法作为参
数。Recipe（）方法只可以访问Cheese中定义的属性，方法，字段，它不可以访问AgedVermontCheddar中的独有的成员。

①  假设我们有一个分析三明治对象的方法

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90305/2009-03-04_22-52-02.jpg)  

②  你可以给上面的方法传递一个三明治对象--不过你也可以传递一个BLT对象，因为我们让BLT继承了Sandwich类，BLT也是一种三明治了。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90305/2009-03-04_22-55-00.jpg)  

③  类图可以向下看--也就是说父类的引用变量可以被赋值为子类的实例。而反之则不然。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90305/2009-03-04_22-58-19.jpg)  

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

