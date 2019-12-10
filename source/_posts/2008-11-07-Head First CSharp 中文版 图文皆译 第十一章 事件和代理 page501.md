---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page501
date: 2008-11-07 19:53:00
tags: 我翻译的Head First C#（习作）
---
一个委托代表一个真实的方法  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

事件最有用的一个方面就是当事件被触发的时候，他不知道他要调用谁的事件处理方法。任何注册了事件的都会使得它的事件处理方法被调用。那么事件怎么管理这些？

它使用一个叫做delegate的C#类型。你可以创建委托的引用变量，但是它不是指向一个类的实例而是指向一个类中的方法。

实际上你在这一章已经使用委托很多次了！创建BallInPlay事件的时候，你使用了EventHandler。EventHandler就是一个委托。

如果你在IDE里面右键单击一个EventHandler，并选择“转到定

义”，你就会看见下面的代码：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081107/%E6%88%AA%E5%9B%BE00633616843806057924.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081107/%E6%88%AA%E5%9B%BE01.jpg)

一个委托向你的项目添加一个新的类型

向你的项目添加一个委托，就是在添加一个委托类型。用它来创建一个字段或者变量的时候，你就是在在创建一个委托类型的实例。创建一个新项目。然后添加一个新的叫做Re
turnAString.cs的类文件到项目中去。不要写一个类，就添加一行进去：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081107/%E6%88%AA%E5%9B%BE02.jpg)

去到窗体代码那儿并添加这一行进去：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081107/%E6%88%AA%E5%9B%BE03.jpg)

现在生成你的程序--可以编译！（你没有使用这个变量--会有一个警告，没事儿。）你添加一个新的委托进去，它就创建了一个叫做ReturnAString的类型。如
果你用这个类型创建一个变量，你可以让它等于一个返回String值没有参数的方法。试着把这个方法添加进去：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081107/%E6%88%AA%E5%9B%BE04.jpg)

添加一个有以下三行代码的按钮。点击它，看看怎么样：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081107/%E6%88%AA%E5%9B%BE05.jpg)

委托，名词。

一个人被送去或者被授权

去代表其他人。总统派了

一个委托去峰会。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

