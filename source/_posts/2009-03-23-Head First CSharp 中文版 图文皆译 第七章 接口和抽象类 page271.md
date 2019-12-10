---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page271
date: 2009-03-23 21:49:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090323/2009-03-23_21-28-17.jpg) 扩展IClown接口并使用实现了它的类。 ![](https://p-blog.csdn.ne
t/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090323/2009-03-23_21-45-39.
jpg)

  

①  从258页的IClown接口开始

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090323/2009-03-23_21-29-43.jpg)

②  创建一个叫做IScaryClown的新接口，让它继承IClown。它比IClown多一个string类型的只有get没有set访问器的属性，还有一个叫
做ScareLittleChildren（）的方法。

  

③  创建下面这些类：

  

*  一个叫做FunnyFunny的搞笑小丑类它用一个string字段来存储小丑会做的好玩的事情，它的构造方法接受一个叫做funnyThingIHave的参数，并用这个参数给字段赋值。Honk（）方法说“Honk honk！I have a”后面跟着小丑会做的好玩的事情。FunnyThingIHave属性的get访问器也返回小丑会做的好玩的事情。 

  

*  一个叫做ScaryScary的可怕小丑类，它用一个私有整数字段来存储从构造方法接收来的叫做numberOfScaryThings的值。ScaryThingIHave的get访问器会返回一个以构造方法接收来的数字开头，后跟“spider”的字符串。它的Honk（）方法弹出一个消息框说“Boo！Gotcha！” 

  

④  下面是一个按钮的代码--但是它有点问题不能运行。你可以修复它吗？

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090323/2009-03-23_21-42-13.jpg)



