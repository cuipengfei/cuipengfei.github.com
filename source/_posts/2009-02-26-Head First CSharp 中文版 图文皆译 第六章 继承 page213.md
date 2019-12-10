---
title: Head First C# 中文版 图文皆译 第六章 继承 page213
date: 2009-02-26 12:29:00
tags: 我翻译的Head First C#（习作）
---
另一件事儿...你可以给超过12人的聚会额外收费$100吗？

用了你的程序，凯瑟琳接到了很多的用户，她可以跟某些大客户多收一点费用了。那，要怎么做才能让你的程序有多收费一点的功能呢？

*  修改DinnerParty.CalculateCost（）方法来让它检查NumberOfPeople，如果人数多于12，就给返回值加上$100。 

*  用同样的方式修改BirthDayParty.CalculateCost（）方法。 

想一下怎么能够一下子就让DinnerParty和BirthDayParty同时具有多收费的功能呢。要写设么代码？写在哪儿呢？

这个问题很简单...但是如果有三个类似的类呢？四个呢？十多个呢？如果你还要维护这些类，有可能要做更多的更改呢？如果要对五六个相似的类做同样的更改需要怎么做呢
？

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090226/2009-02-26_12-19-34.jpg)

对！在多个类中写同样的代码是低效率的，而且容易出错。

很幸运，C#给我们构建相互关联的、有共同行为的类提供了更好的方法：继承。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

