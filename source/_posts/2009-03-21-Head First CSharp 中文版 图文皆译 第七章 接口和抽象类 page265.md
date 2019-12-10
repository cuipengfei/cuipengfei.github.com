---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page265
date: 2009-03-21 21:48:00
tags: 我翻译的Head First C#（习作）
---
is  关键字告诉你一个对象实现了什么，as关键字告诉编译器如何对待该对象

  

有时候你需要调用对象从接口实现来的方法。但是如果你不知道该对象的类型是否正确怎么办呢？你可以用is来判断。然后，你可以对它用as操作--
你已经知道这个对象类型正确了--它肯定有你需要调用的方法。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090321/2009-03-21_21-32-01.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090321/2009-03-21_21-43-05.jpg)
观察左侧的数组，写出i的值是多少的时候可以使得右侧的is语句返回true。还有，左侧有两个语句无法编译--把它们圈出来。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090321/2009-03-21_21-46-52.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

