---
title: Head First C# 中文版 图文皆译 第五章 封装 page197
date: 2009-02-17 14:45:00
tags: 我翻译的Head First C#（习作）
---
用构造方法来初始化私有字段

如果你需要初始化对象，而这个对象需要被初始化的字段中有一些是私有的，这时对象初始化器就不适用了。很幸运，有一种你可以添加进任何类中的方法叫做构造方法。一个类
，如果有构造方法的话，它被new关键字创建的时候，构造方法将会是第一个得以运行的。你可以给构造方法传参，参数就是需要初始化的值。但是构造方法没有返回值，因为
你实际上并没有直接调用它。参数传递进new语句中。而且你也知道new语句返回对象--所以构造方法没有可能返回别的东西。

要添加构造方法，只要

添加一个与类同名，没

有返回值的方法就行了。

①  给你的Farmer类添加一个构造方法

构造方法只有两行，但是要做的不少。我们一步一步来。我们知道我们需要牛的数目和饲料乘数，那就把它们当成参数传进构造方法吧。把feedMultiplier从co
nst改为int。它需要一个值，一定要传进构造方法去。我们还要用构造方法设置牛的数目...

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090217/2009-02-17_14-14-07.jpg)

②  现在修改窗体m让它使用构造方法

现在你需要做的就是修改窗体的代码，来让创建Farmer对象的new语句使用构造方法而不是使用对象初始化器。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090217/2009-02-17_14-33-23.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





