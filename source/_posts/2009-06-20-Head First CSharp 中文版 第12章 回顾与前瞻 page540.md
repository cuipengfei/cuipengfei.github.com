---
title: Head First C# 中文版 第12章 回顾与前瞻 page540
date: 2009-06-20 12:23:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090620/2009-06-20_12-11-51.jpg)
我们已经有了四个核心类，我们需要做一些工作来把它们联系起来。遵循下面的步骤来做。但是要知道：在完成之前你必须修改几乎每一个类的某些地方。

  

①修改  Bee  来接受  Hive  和  World  的引用。

  

现在有了  Hive  和  World  类，  Bee  对象需要知道它们。更新你的代码来让  Bee  在其构造方法中接受  Hive  和
World  的引用并且保存这些引用来在稍后使用。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090620/2009-06-20_12-14-32.jpg)

②更新  Hive  来让它接受一个  World  的引用

  

就像  Bee  需要知道  Hive  一样，  Hive  也需要知道  World  。更新  Hive  来让它在构造方法中接受一个  World
的引用。你还需要更新  Hive  中创建幼蜂的代码来把  Hive  自己和  World  传递给  Bee  。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090620/2009-06-20_12-16-53.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

