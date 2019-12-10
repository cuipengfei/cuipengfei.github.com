---
title: 自己动手重新实现LINQ to Objects 1 - 引言
date: 2011-08-19 02:03:08
tags: linq
---
本文翻译自 [ Jon Skeet ](http://stackoverflow.com/users/22656/jon-skeet)
的系列博文“Edulinq”。

本篇原文地址： [ http://msmvps.com/blogs/jon_skeet/archive/2010/09/03/reimplementing-
linq-to-objects-part-1-introduction.aspx
](http://msmvps.com/blogs/jon_skeet/archive/2010/09/03/reimplementing-linq-to-
objects-part-1-introduction.aspx)





大约一年半之前，我在 [ DDD ](http://developerdeveloperdeveloper.com/)
的活动日上做了一次演讲。我当时试图去重新实现LINQ to
Objects，在一小时内能实现多少算多少。根据会后的反馈信息来看，我当时做得太快了...而且我还是远远没有实现完整。不过无论如何我还是觉得重新实现LINQ
to Objects是一个很有趣的练习，所以我觉得我应该用且行且博、不徐不疾方式来再做一遍。

这一系列的博文都会标上 [ "Edulinq"的标签
](http://msmvps.com/blogs/jon_skeet/archive/tags/Edulinq/default.aspx)
，你可以用这种方式过滤出这一系列博文。




** 总体思路 **

** **

我的计划是要完整的重新实现LINQ to
Objects，用每篇博客来解释一个方法（或者是一组方法）。我将会尽力把代码写的达到生产质量，但是我不会写任何XML文档注释 -
既然我已经在写博客来解释了，那我就不想在代码中再重复一次了。我将在适当的情况下做一些优化，但愿会 [ 比LINQ to Objects本身的实现做得更好
](http://msmvps.com/blogs/jon_skeet/archive/2010/02/10/optimisations-in-linq-
to-objects.aspx) 。

我将采取一种相当简单的方式：我将为每一个LINQ方法写一些单元测试（这些单元测试中的大部分不会出现在接下来的博文中），确保.NET的LINQ实现可以通过这些
单元测试。然后我会注释掉对System.Linq的引用并引入JonSkeet.Linq。这时单元测试会无法通过，我将会实现测试所针对的方法，最终让单元测试可
以绿灯通过。这和通常的TDD做法不太一样，但还是蛮好用的。

我将为每个LINQ操作符写一篇博文，其中将包含几乎所有的成品代码，不过测试代码的话我只会把有趣的部分贴出来。遇到重要的模式的时候我会把它重点标示出来，这也正
是本次练习的一半意义之所在。

在每篇博文的最后，我会附上一个下载最新代码的链接。为以后的读者考虑，我会给这些下载文件分别编号，而不是不断更新同一个文件，不过我敢说以后肯定会有对这些下载文
件做修改的。

我们的目标不是要做出一个类似于 [ LINQBridge ](http://linqbridge.googlecode.com/)
的项目来：我将使用.NET 3.5（主要是因为这样我可以直接使用扩展方法，而无需创建自定义Attribute），而且我肯定也不会去担心安装包之类的事儿。这系
列博文纯粹是教育性质的：如果你逐一读完这一系列，只要运气不是太差，你会对LINQ有更深的总体的了解，尤其是对LINQ to
Objects有更深刻的、细致的认识。比如说，像延迟执行一类的事，人们总是有很多误解：但是如果你看过代码的话，就能够很好的理清头绪了。

** **

** 测试 **

**   
**

我将使用 [ NUnit ](http://nunit.org/)
来写单元测试（仅仅是因为我本人对它更熟悉）。很明显，我们将会很频繁的需要测试两个序列是否等价。我们将通过使用 [
MoreLINQ中的TestExtensions类型 ](http://code.google.com/p/morelinq/source/browse/t
runk/MoreLinq.Test/TestExtensions.cs)
来做到这一点（我把这个类型复制到了项目中了）。我将用来写作这一系列博文的上网本上只安装了C# Express
2010，所以我将使用NUnit的外部GUI程序。我把项目的启动项设置为了NUnit的GUI程序...在C#
Express中无法直接做此设置，但是修改项目文件也是很简单的：

<StartAction>Program</StartAction>

<StartProgram>C:\Program
Files\NUnit-2.5.7.10213\bin\net-2.0\nunit-x86.exe</StartProgram>

这种方法虽然有点不太正当，但是完全可用。然后把"additional command line
parameters"设置为JonSkeet.Linq.Tests.dll - 当前目录默认就是bin/debug，这样就算做好准备了。很明显，如果你安装了
ReSharper或之类的工具，并且自己运行这些单元测试的话，你将发现测试结果会被集成到Visual Studio中。

尽管我想要写出合理的工业级质量的代码来，但是我觉得自己在真的生产环境中都未必会写那么多的单元测试。虽是如此，我还是完全有理由相信测试代码量将会超过成品代码量
。因为边边角角的需要测试的情况实在太多了...而且有时重载也不少。不过请记住，我们此举的目的在于研究LINQ中有趣的特性。

** **

** 代码分布 **

**   
**

就像原版的LINQ to Object一样，我将会创建一个叫做Enumerable的静态类型...但是我会用到分部类，每个方法（包括其多个重载）占用一个代码
文件。举例来说：Where方法将会在Where.cs中来实现，而它的测试代码将会写在WhereTest.cs中。

** **

** 第一次代码下载 **

**   
**

代码在这儿下载： [ Linq-To-Objects-1.zip
](http://pobox.com/~skeet/blogfiles/csharp/l2o/Linq-To-Objects-1.zip)
。其中暂时还没有任何的成品代码，只有四个Where方法的单元测试，主要目的在于检查NUnit可以正常工作。下一步...实现Where方法。

  * [ 点赞  1  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)




