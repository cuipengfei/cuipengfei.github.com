---
title: Head First C# 中文版 图文皆译 第六章 继承 page232
date: 2009-03-08 10:14:00
tags: 我翻译的Head First C#（习作）
---
子类可以通过使用base关键字访问基类

即使你已经覆写了父类的方法、属性，但是有时你还是会需要访问父类。很幸运，我们可以用base关键字来访问父类的任何方法。

①  所有动物都要吃东西，所以Animal类有一个Eat（）方法，它接受一个Food对象作为参数。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_10-03-34.jpg)

②  变色龙用舌头捕食。所以Chameleon（变色龙）类会继承Animal类并覆写Eat（）。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_10-06-05.jpg)

③  无需写重复的代码，我们只需使用base关键字来调用已经被覆盖的方法。我们现在对旧的和新版本的Eat（）方法都有访问权。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_10-10-30.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

