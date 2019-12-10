---
title: Head First C# 中文版 图文皆译 第五章 封装 page196
date: 2009-02-11 21:10:00
tags: 我翻译的Head First C#（习作）
---
如果想要修改饲料的乘数怎么办呢？

我们写的程序使用一个const来做乘数。如果我们想要在其他程序中使用Farmer类，而其他程序需要不同的饲料乘数怎么办呢？你已经见识过给别的类过多的访问权的
时候，糟糕的封装性会造成什么问题。所以，只有真正需要的时候才把字段和方法设置为公有的。由于饲料计算器自己从来不需要更新FeedMultiplier（饲料乘数
），所以也没必要让别的类可以修改它。我们把它修改为只读的自动属性吧。

① ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/2
0090211/%E6%88%AA%E5%9B%BE04.jpg)

②  然后运行。啊~~出错了！饲料数总是返回0！等会儿，这也说得通。FeedMultiplier从没有得以初始化。它开始就是默认值0并且从来没有改变过。当它
乘以牛的数目的时候，还是0。所以添加一个对象初始化器：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090211/%E6%88%AA%E5%9B%BE05.jpg)

啊~~无法编译！你会得到这个错误：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090211/%E6%88%AA%E5%9B%BE06.jpg)

在对象初始化器内只可以初始化公有的字段和属性。那么，在某些需要初始化的字段是私有的情况下，怎么确保对象可以得以恰当的初始化呢？



