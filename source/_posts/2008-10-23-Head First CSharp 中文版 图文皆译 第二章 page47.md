---
title: Head First C# 中文版 图文皆译 第二章 page47
date: 2008-10-23 13:13:00
tags: 我翻译的Head First C#（习作）
---
生成程序来创建可执行文件  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

当你选择生成菜单里的“生成解决方案”时，IDE就会编译你的程序。IDE通过运行编译器来把源文件生成为可执行文件。可执行文件就是双击可执行的.exe结尾的文件
。当你生成程序时，它就会在bin文件夹下生成可执行文件。但你发布解决方案的时候，它会把你的可执行文件及其他必要文件一起复制到你指定的发布文件夹。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081023/%E6%88%AA%E5%9B%BE00.jpg)

你的程序在CLR中运行

当你双击可执行文件时，Windows运行它，但是在你的程序和Windows之间还有一个特殊的“层”，叫做Common Language
Runtime，或叫做CLR。不久之前（但是在C#出现之前），写程序会更难一些，因为你要自己接触硬件和底层的东西。你从不知道人们怎么配置电脑。CLR--
常被称作虚拟机--通过在你的电脑和你的程序之间做“翻译”工作来为你处理那些配置问题。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081023/%E6%88%AA%E5%9B%BE01.jpg)

你将会了解关于CLR为你做的的很多事儿。比如说，它为你管理内存，在程序结束时销毁无用的数据。程序员原来要自己处理这些，现在你不用麻烦了。现在你可能不懂，但是
CLR将会使你学习C#变得容易多了。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

