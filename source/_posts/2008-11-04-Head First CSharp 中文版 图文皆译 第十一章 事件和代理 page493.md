---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page493
date: 2008-11-04 08:27:00
tags: 我翻译的Head First C#（习作）
---
3  键入+=，IDE将会为你完成这一条语句  <?xml:namespace prefix = o ns = "urn:schemas-
microsoft-com:office:office" />

键入+=之后，IDE马上就为你显示一个很有用的窗口：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081104/%E6%88%AA%E5%9B%BE00.jpg)

按下tab键之后，IDE就为你完成这一条语句。看起来像下面这样：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081104/%E6%88%AA%E5%9B%BE01.jpg)

4 IDE  也会添加你的事件处理者

你还没完成--你还需要一个方法来挂钩到事件上。很幸运，IDE也可以为你处理它。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081104/%E6%88%AA%E5%9B%BE02.jpg)

再按tab键来让IDE给你的投手类添加一个事件处理者方法。IDE总是会遵循对象名_事件处理者名()的规则：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081104/%E6%88%AA%E5%9B%BE03.jpg)

5  完成投手的事件处理者

现在你有了事件处理者的骨架，填充如下的代码。投手将会去接任何的低飞球，否则他就去一垒的位置。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081104/%E6%88%AA%E5%9B%BE04.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





