---
title: Head First C# 中文版 图文皆译 第二章 page46
date: 2008-10-22 20:01:00
tags: 我翻译的Head First C#（习作）
---
程序从哪儿来  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

一个C#程序可能从一堆文件中的声明开始，最终成了你电脑上运行的程序。下面是它怎么来的。

每个程序都从源代码文件来

你已经看见怎么编辑程序，和IDE怎么把你的程序保存到一个文件夹里。这些文件就是你的程序--
你可以把它们复制到一个新文件夹并打开，所有东西都还在那儿：窗体，资源，代码，和其他任何你添加到你的项目的东西。

你可以认为IDE就是一个很帅的编辑器。它为你自动缩进，改变关键字的颜色，为你匹配括号，甚至建议下一个可能用的词。但是最后，IDE做的所有事儿就是编辑包含你的
程序的文件。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081022/%E6%88%AA%E5%9B%BE00633603024940937500.jpg)

IDE  创建一个solution（.sln）文件和一个包含所有程序用到的文件的文件夹，以此来把程序的所有文件都绑定到这个解决方案。解决方案文件包含一个项目
文件（以.csproj结尾）列表，而项目文件包含了所有与项目关联的文件的列表。

这本书里，你只创建单项目解决方案，但是用解决方案浏览器可以很容易的添加其他项目到解决方案里。

.NET Framework  给你合适的工具

C#  只是一门语言--单靠它自己，它不能做所有事。所以在这儿就需要.NET
Framework了。还记得你从窗体上去掉的最大化按钮吗？当你点击窗口上的最大化按钮的时候，有告诉窗口怎么最大化它自己并占据全屏的代码。这些代码是.NET
Framework的一部分。按钮，复选框，列表...这些都是.NET
Framework的组成部分。把你的窗体和数据库连接起来的也是一些内部代码块。它有绘制图形，读写文件，管理集合的工具，做程序员的各种日常工作的工具。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081022/%E6%88%AA%E5%9B%BE01633603024941406250.jpg)

.NET Framework
中的工具分割到命名空间里。你已经见过那些命名空间了，就在你的代码最上面的“using”那些行。有一个命名空间叫做System.Windows.Forms--
你的按钮，复选框，窗体都是在那儿来的。无论何时你创建一个窗体应用项目，IDE都会添加必要的文件以使得你的项目包含一个窗体，并且文件里最上面都有一行“usin
g System.Windows.Forms”。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

