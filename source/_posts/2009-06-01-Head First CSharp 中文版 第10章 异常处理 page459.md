---
title: Head First C# 中文版 第10章 异常处理 page459
date: 2009-06-01 21:58:00
tags: 我翻译的Head First C#（习作）
---
③  调试器一执行到  Deserialize  （）这一语句就抛出了异常，程序直接跳到了  catch  块的第一句语句去执行。它直接跳过了对
UpdateForm  （）的调用而跳到了  catch  块中去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090601/2009-06-01_21-43-25.jpg)

④  按  F5  来让程序开始运行。这将会重新启动程序，从黄色高亮显示的部分开始，在这儿，也就是  catch  块了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090601/2009-06-01_21-48-38.jpg)

注意！小心处理构造方法中的异常！

  

你应该已经注意到了，构造方法没有返回值，甚至连  void  都没有。这是因为构造方法实际上并不返回任何东西。它的唯一目的就是初始化一个对象  \--
这就是构造方法内异常处理的问题。构造方法中抛出异常的时候，创建该类对象的语句将会无法得到该类的实例。所以你需要把  try/catch
块移动到按钮的事件处理方法中去。这样做，如果构造方法中出现异常的话，代码就不会预期  CurrentExcuse  包含一个有效的  Excuse
对象了。

  

职业提示：很多  C#  编程工作的面试中会问到你如何去处理构造方法中的异常。



