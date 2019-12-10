---
title: Head First C# 中文版 图文皆译 第十一章 事件和委托 page509
date: 2008-11-11 22:27:00
tags: 我翻译的Head First C#（习作）
---
<?xml:namespace prefix = o ns = "urn:schemas-microsoft-com:office:office" />

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE00633620392498820000.jpg)

通常以On...什么什么开头的方法才触发一个公有的事件。你可以自己检查一下--进入窗体的代码试着调用playBall按钮的OnClick（）事件。你会调用失
败的，因为它是protected的（子类可以覆盖）。我们来把我们的球类的OnBallInPlay（）方法设置为protected来使用这种模式吧：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE01633620392499132500.jpg)

窗体不能调用球对象的OnBallInPlay（）方法了--这正是我们希望的。这也正是我们创建Ball.GetNewBat（）方法的原因。现在窗体要向棒球要一
根球棒来击球。这么做的时候，球对象将会把它的OnBallInPlay（）方法联系到球棒的回调上。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE02633620392499445000.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081111/%E6%88%AA%E5%9B%BE03633620392500070000.jpg)

*  向项目里添加一个委托就是在添加一个新类型，它含有方法的引用。 

*  事件用委托来告知对象有事儿发生。 

*  如果对象需要对某个对象中发生的事儿做出反应，它就注册到那个事件。 

*EventHandler  是你在使用事件的时候很常见的一个委托。 

*  你把多个事件处理器关联到一个事件上。所以需要用+=运算符。 

*  总是在使用委托或者事件之前检查它是否为空，以防NullReferenceException异常。 

*  工具箱里的所有控件都使用事件来驱动你的程序。 

*  一个对象传递一个引用给方法再给另一个对象，以使得另一个对象--唯一的--可以回应，这叫做回调。 

*  事件允许方法匿名的关联。而回调则会掌控到底要接受哪个委托。 

*  事件和回调都使用委托来引用、调用其他的对象中的方法。 

*  调试器是帮你更好的认识事件、委托、回调是怎么运作的好工具。好好利用它！ 



