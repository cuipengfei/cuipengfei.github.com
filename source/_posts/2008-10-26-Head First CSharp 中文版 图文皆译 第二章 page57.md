---
title: Head First C# 中文版 图文皆译 第二章 page57
date: 2008-10-26 11:28:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081026/%E6%88%AA%E5%9B%BE00.jpg)

怎么样了？  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

现在你的程序不会显示联系人窗体了，它只弹出消息框窗口。你写了新的Main（）方法，你就给了程序一个新的入口点。现在程序做的第一件事就是运行Main里的指令
--也就是运行MessageBox.Show（）这条指令。Main里除了这条就没有别的指令了，所以你点击OK按钮之后，程序没指令可执行了，它将会停止。

5  想想怎么修复你的程序，让它还可以弹出联系人窗口。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081026/%E6%88%AA%E5%9B%BE01.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081026/%E6%88%AA%E5%9B%BE02.jpg)

问：大括号都是干嘛用的？

答：C#用大括号来把指令组织到代码块里。大括号总是成对的。一个左括号后总是可以看见一个右括号。IDE帮你匹配大括号--
单击一个括号，它的配对括号和它将会被阴影显示。

问：我不太懂入口点是什么。可以再给我解释一下嘛？

答：程序有很多指令。但是它们不是一起执行的。程序从第一个指令开始，然后下一个，下一个，再下一个，等等。这些指令通常组织进类里，那程序怎么知道从哪儿开始呢？

入口点就是这么来的。除非有一个叫做Main（）方法的入口点，否则编译器无法编译你的代码。程序从Main（）方法里的第一个指令开始执行。

问：怎么在我运行的时候在错误列表里出现错误提示了呢？我认为只有“生成解决方案”是才会那样。

答：因为你运行程序时，发生的第一件事就是保存解决方案里的所有文件并尝试去编译。而编译的时候--不管是运行是还是生成时--
如果有错误，IDE都会把错误显示在错误列表中而不会去运行程序。



