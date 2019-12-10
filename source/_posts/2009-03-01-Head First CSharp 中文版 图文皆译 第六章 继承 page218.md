---
title: Head First C# 中文版 图文皆译 第六章 继承 page218
date: 2009-03-01 12:34:00
tags: 我翻译的Head First C#（习作）
---
不同的动物出声也不同

狮吼、犬吠，据我们所知河马不出声。每一个Animal的子类都有一个MakeNoise（）方法，但是它们的工作方式不同，代码也不同。当一个子类要改变继承来的某
个行为的时候，这就叫做覆盖方法。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090301/2009-03-01_12-09-17.jpg)

③  搞明白每个Animal子类是怎样不同的实现基类的行为--或者根本不实现

每种动物都做什么别的动物不做的事儿呢？狗吃狗粮，所以狗的Eat（）方法需要覆盖Aniaml.Eat（）方法。河马会游泳，所以河马会有一个基类中根本不存在的S
wim（）方法。

想想要覆盖什么

子类改变继承来的行为就叫做覆盖。每个都需要进食。狗吃少量的肉，河马吃大量的草。那么它们的行为的代码是什么样的呢？狗和河马都要覆盖Eat（）方法。河马的方法每
次被调用将会消耗，大概，二十磅干草。狗的Eat（）方法将会消耗动物园的食品供给中的十二盎司狗粮。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090301/2009-03-01_12-27-15.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

