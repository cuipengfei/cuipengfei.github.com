---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page498
date: 2008-11-06 08:13:00
tags: 我翻译的Head First C#（习作）
---
你创建过的窗体都使用事件  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

每次你在窗体设计器里面双击按钮，给形如button_Click（）的方法写代码，你都是在用事件。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE00.jpg)

1  创建一个处于应用，打开窗体，在属性窗口上方有很多图标--点击那个闪电形状的图标。这将会在属性窗口打开事件窗口。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE01.jpg)

2  双击事件页里的“Click”。IDE将会给你自动添加一个叫做Form1_Click的事件处理方法。添加下面这一行代码进去：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE02.jpg)

3 Visual Studio
不仅仅写一个方法声明。它还会把事件和事件处理联系起来。打开form1.designer.cs并用快速查找找到Form1_Click。你将会看到下面这行：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE03.jpg)

现在运行，以确保你的代码可以工作！

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081106/%E6%88%AA%E5%9B%BE00633615752960298750.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





