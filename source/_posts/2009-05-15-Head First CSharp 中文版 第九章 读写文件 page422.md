---
title: Head First C# 中文版 第九章 读写文件 page422
date: 2009-05-15 17:32:00
tags: 我翻译的Head First C#（习作）
---
我们来序列化并反序列化一副扑克

  

把一副牌写入文件。  C#  把序列化对象做得很简单。你只需要创建一个流并且写出你的对象。

  

①创建一个新项目并且添加  Deck  和  Card  类进去

  

你还需要添加两个比较器类，因为  Deck  需要它们。  IDE  将会把文件复制进项目中去  \--  一定要把类文件开头的  namespace
那一行修改掉，以确定它和当前的项目的命名空间相匹配。

  

②让所有的类都可以序列化

  

给你添加进项目的所有类都添加  [Serializable]  特性。不添加这一行的话，  C#  不会允许你把该类序列化到文件中去。

  

③给窗体添加两个有用的方法

  

RandomDeck  方法随机创建一副牌，  PrintCards  方法处理所有的牌并把它们打印到控制台。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090515/2009-05-15_17-27-10.jpg)



