---
title: Head First C# 中文版 图文皆译 第六章 继承 page233
date: 2009-03-08 13:19:00
tags: 我翻译的Head First C#（习作）
---
父类有构造方法，子类也要有

一个类由构造方法，那么任何继承自它的子类都会调用它的构造方法。子类和父类的构造方法可以有不同的参数列表。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_12-54-35.jpg)

父类的构造方法首先得以调用，然后才执行子类的构造方法

但是别盲目相信我们说的--自己试试看！

①  创建一个父类，让它的构造方法弹出一个消息框

然后向窗体添加一个按钮，它实例化父类并会弹出消息框：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_13-02-59.jpg)

②  写一个子类，但是别调用父类的构造方法

然后向窗体添加一个按钮，让它实例化一个子类的对象并显示一个消息框：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_13-07-06.jpg)

③  通过让子类的构造方法调用父类的构造方法来解决问题

然后实例化子类，看看消息框以什么顺序弹出来！

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_13-12-44.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





