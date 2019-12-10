---
title: Head First C# 中文版 第12章 回顾与前瞻 page559
date: 2009-07-13 12:05:00
tags: 我翻译的Head First C#（习作）
---
最后的挑战：打开和保存

  

我们就快要可以着手于图形的工作，给模拟器添加一些养眼的东西了。不过，首先还是给这一版再做一些别的事：允许载入，保存，和打印蜜蜂的统计数据。

  

①添加打开，保存和打印的图标

  

ToolStrip  控件有一个很实用的功能  \--  它可以添加含有标准图标的按钮：新建，打开，打印，剪切，复制，粘贴和帮助。右击处于窗体设计器底部的
ToolStrip  并选择“添加标准项”。点击第一个图标  \--  它是“新建”  \--  删除它。留着余下的三个。把其余的不需要的按钮也删掉。

  

②添加按钮的事件处理方法

  

这几个按钮被命名为  openToolStripButton  ，  saveToolStripButton  和
printToolStripButton  。右击每一个来给它们添加事件处理方法。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090713/2009-07-13_12-01-01.jpg)

添加代码来让保存和打开按钮可以工作。

  

1  让保存按钮把  World  对象序列化并保存到文件。

  

2  让打开按钮把  World  对象从文件中反序列化出来。

  

3  别忘了异常处理！

  

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

