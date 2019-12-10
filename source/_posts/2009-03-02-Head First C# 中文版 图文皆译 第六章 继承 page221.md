---
title: Head First C# 中文版 图文皆译 第六章 继承 page221
date: 2009-03-02 13:32:00
tags: 我翻译的Head First C#（习作）
---
每个子类都扩展它的基类

你并不会被局限于一个子类继承来的方法...你已经知道这一点了！毕竟你已经自己构建类很久了。向一个类添加继承就是把已经创建好的类拿过来，并通过添加属性，字段，
方法来扩展它。所以如果你要给狗添加一个Fetch（）方法，这是很普通的。这不会继承或者覆盖任何东西--
只有狗才有这个方法，河马，狼，犬科动物，动物等等类都不会得到这个方法。

![https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090
302/2009-03-02_13-18-21.jpg](https://p-blog.csdn.net/images/p_blog_csdn_net/cu
ipengfei1/EntryImages/20090302/2009-03-02_13-18-21.jpg)  

C#  总是会调用最具体的方法

如果你让狗对象转悠（Roam（）方法），只有一个方法可以做到--就是Animal中的版本。那要是要让狗出声音呢？调用的是哪个MakeNoise（）方法呢？要
搞明白这一点并不难。Dog类中的方法告诉你狗是怎么做这件事儿的。如果这个方法是在Canine中的话，那就是告诉你所有的犬科动物都是这样做这件事儿的。如果它在
Animal中的话，那就是描述了一个普遍性的、对于所有动物都适用的行为。所以如果你让狗出声音的话，C#会先去Dog类中找适用于狗的这种行为。如果Dog类没有
这种行为，就去Canine中找，然后再去Animal中找。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

