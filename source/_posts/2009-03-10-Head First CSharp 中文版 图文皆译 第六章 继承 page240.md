---
title: Head First C# 中文版 图文皆译 第六章 继承 page240
date: 2009-03-10 19:31:00
tags: 我翻译的Head First C#（习作）
---
首先你要构建基本系统 ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/Entr
yImages/20090310/2009-03-10_18-38-26.jpg)

这个项目分为两部分。第一部分你要创建基本系统来管理蜂窝。这一部分有两个类，Queen（蜂后）和Worker（工蜂）。你要为程序创建窗体，并把它与这两个类结合
起来。这两个类需要封装良好，这样在你去创建第二部分的时候它们才能易于修改。

程序含有一个Queen对象来管理工作。

*Queen  用一个Worker数组来管理工蜂，看它们是否已经有分配了的工作。这个数组是一个叫做workers的 ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090310/2009-03-10_19-21-14.jpg) Woker[]私有字段。 

*  窗体调用AssignWork（）方法，它接受一个string参数代表需要做的工作还有一个int参数代表班次。如果找到可以做这项工作的工蜂，返回true，否则返回false。 

*  窗体上的“Work the next shift”按钮调用WorkTheNextShift（）方法，此方法告诉工蜂去工作并返回一个用来显示的班次报告。它通知工蜂去工作一个班次，并检查工蜂的状态以便添加到班次报告中去。 

Queen  用一个Worker数组来管理工蜂，了解它们正在做什么工作。

*CurrentJob  是一个只读属性，它告诉蜂后工蜂在做什么（“巡逻”，“维护蜂窝”，等等）如果工蜂没做事儿，将会返回一个空字符串。 

*  蜂后用自己的DoThisJob()方法来给工蜂分配工作。如果工蜂没有正在做的工作，并且知道怎么做这个工作，它将会接受这个工作并返回true，否则返回false。 

*  调用WorkOneShift（）方法的时候，工蜂去工作一个班次。工蜂记录手头的工作还需要做几个班次。如果手头工作做完了，它将会把手头工作设置为空字符串，这样它将可以接受下一个工作。 

String.IsNullOrEmpty（）

由于每一个工蜂用一个字符串存贮正在做的工作，工蜂通过检查CurrentJob属性来知道自己是否手头有工作--如果它在等待下一个工作的话该字符为空。C#给你提
供了一个简单的方式来完成这个功能：String.IsNullOrEmpty（CurrentJob）在字符串为空或者为null的时候返回true，否则返回fa
lse。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

