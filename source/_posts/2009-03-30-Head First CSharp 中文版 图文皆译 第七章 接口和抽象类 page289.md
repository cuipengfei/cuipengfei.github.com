---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page289
date: 2009-03-30 08:43:00
tags: 我翻译的Head First C#（习作）
---
多态意味着一个对象可以呈现多种形态

你用仿声鸟代替动物的时候，就是在使用多态。每次向上转型或者向下转型的时候也就是用到了多态。也就是在一个期待某种类型的语句或者方法中使用一个其他类型的对象。

注意接下来练习中的多态！

你要做一个大型的练习了--你见过的最大的--
在其中你会经常用到多态。注意观察下面是使用多态的四种典型情况。我们给出了每一个的范例。你在练习中用到了哪一个就把那一个勾掉。

□  把一个类的实例赋值给另一个类的引用变量。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090330/2009-03-30_08-28-28.jpg)

□  通过在期待父类的语句或者方法中使用子类来向上转型

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090330/2009-03-30_08-30-10.jpg)

□  创建一个接口的引用变量并把它指向实现了该接口的类的实例。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090330/2009-03-30_08-33-52.jpg)

□  用as关键字向下转型

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090330/2009-03-30_08-35-08.jpg)

把一个实例用在期待其父类或它实现过的接口的语句或者方法中，这就是在使用多态了。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

