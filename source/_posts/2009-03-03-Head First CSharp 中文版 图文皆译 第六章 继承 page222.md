---
title: Head First C# 中文版 图文皆译 第六章 继承 page222
date: 2009-03-03 13:21:00
tags: 我翻译的Head First C#（习作）
---
继承基类要用冒号

写一个类的时候，继承另一个类要用冒号（：）。冒号前面的就是子类，它会得到父类所有的字段，属性，方法。

子类继承基类的时候，基类所有的字段，属性，方法会自动的添加进子类中去。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090303/2009-03-03_12-58-14.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090303/2009-03-03_13-07-14.jpg)

问：为什么子类和父类之间的箭头指向父类？如果箭头向下指的话，这个类图不是会好看一点吗？

答：是会好看一点，但是就不如之前准确了。一个类继承另一个类的时候，这种关系是体现在子类中的，父类是保持不变的。从父类的角度考虑，这样是有道理的，给一个类添加
一个子类，父类的行为是不会改变的。父类甚至到不会知晓有一个新的子类存在。父类的方法，字段，和属性都是原封不动的。而子类的行为却改变了。每一个子类的实例都会得
到父类所有的字段，方法和属性，而这一切，只写一个冒号就做到了。在类图上这样画箭头就代表父类会成为子类的一部分，而箭头指向子类继承的父类。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





