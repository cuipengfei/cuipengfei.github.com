---
title: Head First C# 中文版 图文皆译 第五章 封装 page203
date: 2009-02-19 13:22:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090219/2009-02-19_12-43-26.jpg) 用你已经学过的属性和构造方法知识来修改凯瑟琳的聚会计划程序。

①  怎么修改聚会计划计算器

要想修改DinnerParty类，我们需要有办法让CalculateCostOfDecorations（）方法在每次NumberOfPeople改变时都得以
执行。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090219/2009-02-19_12-49-44.jpg)

②  添加属性和构造方法

想要修复凯瑟琳的问题，确保DinnerParty类被良好的封装就可以了。从把NumberOfPeople修改为每次被调用时都调用CalculateCostO
fDecorations（）的属性开始吧。然后添加一个构造方法来确保实例被适当的初始化。最后，修改窗体让它使用新的构造方法。如果你做的对的话，这就是窗体唯一
需要改变的地方。

*  你要创建一个新的属性，叫做NumberOfPeople，它有一个set访问器，调用CalculateCostOfDecorations（）。它的后备字段叫做numberOfPeople。 

*  NumberOfPeople  的set访问器需要有一个值来作为参数传递给CalculateCostOfDecorations（）方法。所以添加一个叫做fancyDecorations的bool字段，它在每次CalculateCostOfDecorations（）被调用的时候都被设置。 

*  添加一个构造方法。它接受三个参数，分别是给人数、健康选择、高级装饰的。现在，窗体在初始化DinnerParty对象的时候会调用两个方法--把它们移动到构造方法里面去。 

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090219/2009-02-19_13-18-57.jpg)

*  这是窗体的构造方法--窗体中其他的东西都保持不变： 

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090219/2009-02-19_13-20-21.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

