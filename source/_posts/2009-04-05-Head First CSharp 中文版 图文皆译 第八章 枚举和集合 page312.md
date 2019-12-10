---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page312
date: 2009-04-05 21:10:00
tags: 我翻译的Head First C#（习作）
---
枚举让你可以给数字取一个名字来代表它

如果数字有名字的话会更容易操作。你可以把枚举中的值赋为数字并用名字来引用数字值。这样你的代码中就不会出现一大堆意义不明的数字了。下面是一个用来给狗狗比赛计分
的枚举。它处于DogCompetition类内部，所以如果你要在类外部使用它的话，就要称它作DogComptition.TrickScore。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090405/2009-04-05_20-48-40.jpg)

你可以把枚举转型为数字并用它来做计算，或者你也可以用ToString（）方法来把它当做字符串来对待。如果你没有给某个名称分配任何值，枚举中的项将会获得默认值
。第一项会被赋值为0，第二个是1，等等。

但是如果你想要给某个枚举项赋值为一个很大的数字会怎么样呢？枚举中数字的默认类型是int，所以你需要用冒号来这样指明类型：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090405/2009-04-05_21-03-32.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

