---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page278
date: 2009-03-26 08:52:00
tags: 我翻译的Head First C#（习作）
---
抽象类就好像是类和接口的混合物

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90326/2009-03-26_08-37-19.jpg)

假设你需要一个像接口一样的东西，它要求类实现某些特定的方法、属性。但是你还需要在其中写一些代码实现，这样某些特定方法就不用在子类中去实现。这时，你需要的是抽
象类。你可以获得借口的特性，而同时可以像在普通类中一样的写实现代码。

★  抽象类和普通类类似

  

定义抽象类的方式和普通类一样。其中可以有字段和方法，也可以从其他类继承，和普通类一样。基本没有什么新东西需要学，因为你已经知道抽象类可以做什么了！

  

★  抽象类和接口类似

  

创建一个实现某个接口的类，就需要实现接口中定义的所有方法、属性。抽象类也可以做到这一点--
抽象类可以包含一些方法、属性的声明，就像接口一样，这些方法、属性必须被派生类实现。

  

★  抽象类不可以被实例化

  

抽象类和实现类最大的不同就是不可以用new关键字来实例化抽象类。如果你这样做了，C#将会在编译的时候报错。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90326/2009-03-26_08-36-26.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

