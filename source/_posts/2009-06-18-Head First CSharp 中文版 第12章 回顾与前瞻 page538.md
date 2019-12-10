---
title: Head First C# 中文版 第12章 回顾与前瞻 page538
date: 2009-06-18 18:08:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090618/2009-06-18_17-19-47.jpg)
我们已经有了四个核心类，我们需要做一些工作来把它们联系起来。遵循下面的步骤来做。但是要知道：在完成之前你必须修改几乎每一个类的某些地方。

  

①修改  Bee  来接受  Hive  和  World  的引用。

  

现在有了  Hive  和  World  类，  Bee  对象需要知道它们。更新你的代码来让  Bee  在其构造方法中接受  Hive  和
World  的引用并且保存这些引用来在稍后使用。

  

②更新  Hive  来让它接受一个  World  的引用

  

就像  Bee  需要知道  Hive  一样，  Hive  也需要知道  World  。更新  Hive  来让它在构造方法中接受一个  World
的引用。你还需要更新  Hive  中创建幼蜂的代码来把  Hive  自己和  World  传递给  Bee  。

  

③更新  World  的代码来把自己传递给  Hive

  

更新  World  的代码来让它在创建新  Hive  的实例的时候把自己的引用传递进去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090618/2009-06-18_17-54-25.jpg)

④给  Hive  可以创建的蜜蜂数一个上限

  

Hive  类有一个  MaximumBees  常量，它决定了  Hive  可以支持多少蜜蜂（蜂巢内和蜂巢外都算）。现在  Hive  可以访问
World  了，你应该可以运用这个约束了。

  

⑤  Hive  创建蜜蜂的时候，让  World  知道

  

World  类保持所有的存在的蜜蜂。当  Hive  创建幼蜂的时候，确保这个蜜蜂被添加到  World  保持的全局列表里面去。



