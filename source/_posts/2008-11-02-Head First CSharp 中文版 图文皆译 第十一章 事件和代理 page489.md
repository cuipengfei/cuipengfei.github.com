---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page489
date: 2008-11-02 19:21:00
tags: 我翻译的Head First C#（习作）
---
3  订阅者类需要事件处理者方法  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

每一个订阅球的BallInPlay事件的对象都需要一个事件处理者。你已经知道事件处理者怎么工作了--每次你添加一个方法来处理一个按钮点击事件或者一个数值改变
事件，IDE就给你的类添加一个事件处理方法。球的BallInPlay事件也没有什么不一样的，并且它的事件处理者应该看起来很熟悉：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE00633612505289986250.jpg)

4  每个对象注册这个事件

一旦我们把事件处理者设置好，那些投手，裁判，三垒手和球迷都需要它们自己的事件处理者。它们每一个都会有自己的ball_BallInPlay方法，以不同的方式响
应事件。所以如果有一个叫做ball的球对象的引用变量或者字段，然后+=运算符将会联系起事件处理者：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE01633612505308267500.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

