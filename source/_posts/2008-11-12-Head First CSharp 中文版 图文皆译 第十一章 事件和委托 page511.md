---
title: Head First C# 中文版 图文皆译 第十一章 事件和委托 page511
date: 2008-11-12 22:43:00
tags: 我翻译的Head First C#（习作）
---
<?xml:namespace prefix = o ns = "urn:schemas-microsoft-com:office:office" />

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081112/%E6%88%AA%E5%9B%BE00633621265944445000.jpg)

金镶玉蟹的案例

其他寻宝者怎么会比亨利更早的找到螃蟹呢？

这个传奇的关键在于寻宝者是怎么搜寻宝物的。但是我们要先看一下亨利在偷来的类关系图里面看到了什么。

在偷来的类关系图里，亨利发现金镶玉蟹在每次有人靠近的时候都会触发RunForCover事件。更好的是事件包含一个NewLocationArgs，它说明螃蟹会
跑到哪儿去。但是其他的寻宝者不知道这回事，所以亨利觉得自己可以先找到螃蟹。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081112/%E6%88%AA%E5%9B%BE01633621265944757500.jpg)

亨利是怎么利用他的内幕消息的呢？

亨利在自己的构造器里注册自己的ListenForClues（）作为螃蟹的引用的RunForCover的事件处理器。然后派一个手下过去惊动螃蟹，它会逃跑，躲起
来，并触发RunForCover事件--这就给了亨利的ListenForClues（）方法需要的所有信息。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081112/%E6%88%AA%E5%9B%BE02.jpg)

亨利就这么失败了。他自己注册事件时就不经意的帮别的寻宝者都注册了！这意味着每个人的事件处理器都关联到同一个事件。所以螃蟹逃跑时，每个人都收到通知。但是亨利不
知道自己什么时候被调用，要是别人先注册，别人就先得到通知。



