---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page255
date: 2009-03-18 22:14:00
tags: 我翻译的Head First C#（习作）
---
用interface关键字定义接口

写一个接口和写一个类类似，只是不用写具体方法实现。只需要定义方法的返回值和参数，然后不用写用大括号括起来的语句，只写一个分号就可以了。

接口不能存储数据，所以不可以在里面定义字段。但是可以定义属性，因为get和set访问器也是方法，而接口就是要强迫类去实现有特定名字、特定返回值、接受特定参数
的方法。所以如果你想要让接口要求其实现类含有某个字段，定义一个属性吧--这可以完成一样的效果。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90318/2009-03-18_21-52-49.jpg)

这个接口对于蜂后有什么帮助呢？现在蜂后可以写一个方法接受所有知道怎么防卫蜂巢的对象作为参数了：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90318/2009-03-18_22-04-22.jpg)

由于这个方法接受IStingPatrol接口类型的参数，所以你可以传递任何实现了IStingPatrol的类的对象进去。

蜂后的这个方法可以接受StingPatrol，NectarCollector，还有任何实现了IStingPatrol的类的对象。DefendTheHive（
）方法由于其接受的参数类型可以保证接收来的参数肯定有保护蜂巢所需的方法和属性。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90318/2009-03-18_22-09-29.jpg)

公有接口中的任何东西都默认为公有的。因为你要用它们来定义其实现类需要的公有方法和属性。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

