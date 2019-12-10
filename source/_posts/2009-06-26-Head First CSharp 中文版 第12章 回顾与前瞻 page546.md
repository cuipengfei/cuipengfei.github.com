---
title: Head First C# 中文版 第12章 回顾与前瞻 page546
date: 2009-06-26 13:40:00
tags: 我翻译的Head First C#（习作）
---
Timer  会一次又一次的触发事件

  

还记得你是怎么用一个循环来驱动猎犬的吗？其实我们有更好的办法。  Timer  是一个特别有用的控件，它可以一次又一次的触发事件，每秒钟可以上千次。

  

如下做：

  

①创建一个有三个按钮和一个  Timer  的新项目

  

向窗体上拖拽三个按钮和一个  Timer  。点击窗体设计器下方的  Timer  图标把它的  Interval  属性设置为  1000
。这个数字是以毫秒为单位来衡量的  \--  它告诉  Timer  每秒钟触发自己的  tick  事件一次。

  

②打开  IDE  的属性窗口并点击事件按钮

  

Timer  控件只有一个事件，  Tick  。双击它，  IDE  将会给你创建一个新的事件处理方法并把它和  Timer  关联起来。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90626/2009-06-26_13-24-25.jpg)

③给  Tick  事件和你的按钮添加代码

  

下面的代码可以帮你了解  Timer  是如何工作的：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90626/2009-06-26_13-30-34.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

