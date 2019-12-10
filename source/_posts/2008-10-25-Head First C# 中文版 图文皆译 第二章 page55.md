---
title: Head First C# 中文版 图文皆译 第二章 page55
date: 2008-10-25 21:34:00
tags: 我翻译的Head First C#（习作）
---
1 C#  和.NET有很多内建特性  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

几乎每个C#类文件顶部都可以找到上页类似的代码行。System.Windows.Forms是一个命名空间。using
System.Windows.Forms这一行使得这个命名空间内的一切在你的程序中都是可用的。在我们的情况下，这个命名空间有很多可视化元素，比如按钮和窗体。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
81025/%E6%88%AA%E5%9B%BE00.jpg)

2 IDE  给你的代码选择一个命名空间

IDE  给你创建命名空间--它基于项目名字选择Contacts作为命名空间的名字。你的程序的代码都处于这个命名空间里面。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
81025/%E6%88%AA%E5%9B%BE01.jpg)

3  你的代码存在类中

这个类叫做Program。IDE创建它，并添加了开始程序和显示窗体的代码进去。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
81025/%E6%88%AA%E5%9B%BE02.jpg)

4  这段代码有一个方法，内有三条指令。

命名空间里有类，类里有方法。每个方法里又有一些指令。在这个程序内，这些指令处理开始Contacts窗体的事儿。动作都在方法内发生--每个方法都做些事儿。

5  每个程序都有一个叫做入口点的特殊方法。

每个C#程序肯定有一个叫做Main的方法。即使你的程序有许多方法，只有一个可以被首先执行，那就是Main方法。C#在你的代码里查找一个标有static
void Main（）的方法。然后，程序执行时,Main方法的第一条指令被执行，所有其他东西都跟随而来。

每个C#程序肯定有一个叫做Main的方法。

这个方法是你代码的入口点。执行时，Main（）方法里的代码首先被执行。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

