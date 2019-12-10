---
title: Head First C# 中文版 第13章 控件和图形 page605
date: 2009-08-05 09:37:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090805/2009-08-05_09-03-40.jpg)

仔细研究窗体和控件是如何重绘自己的

  

还记得早些时候我们说使用  Graphics  对象 就是自己控制图形绘制吧。这就好像告诉  .NET
“嘿，我知道自己做什么呢。我可以承担额外的责任。”在绘制和重绘的时候，有可能你不想在窗体最小化和最大化的时候重绘  ...
或者你想要更频繁的重绘。一旦你了解了窗体和控件的幕后情景，你就可以自己掌握重绘了：

  

①每个窗体都有  Paint  事件

  

窗体需要重绘自己的时候，就会触发  Paint  事件。那么是谁负责触发事件呢？是一个叫做  OnPaint  的方法，该方法是窗体或者用户控件从
Control  类继承来的。去任意一个窗体中去，覆写  OnPaint  ：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090805/2009-08-05_09-14-43.jpg)

拖动窗体  \--  拖出屏幕，最小化，把它隐藏到其它窗口后面。仔细观察输出。任何时候窗体的一部分被弄花了或者失效了的时候，它就需要被重绘。
ClipRectangle  定义了窗体需要被重绘的部分。只重绘需要重绘的一部分，可以提高性能。

  

②需要重绘的时候，调用  Invalidate  （），并定义重绘的部分

  

在被遮挡的部分重新出现的时候，  .NET  触发  Paint  事件。它调用  Invalidate  （）方法，并传递进去一个  Rectangle
。这个  Rectangle  定义了需要重绘哪部分。然后  .NET  调用  OnPaint  方法来告诉你的窗体触发一个  Paint
事件并重绘部分窗体。

③  Update  （）方法给你的  Invalidate  请求做高的优先级

你或许没有觉察到，窗体每时每刻都在接收消息。几乎所有以  On  开头的方法都是窗体需应答的消息。  Update  （）方法把

Invalidate  消息移动到消息队列中的最上方。

④窗体的  Refresh  （）方法相当于  Invalidate  （）加  Update  （）

有一个捷径，就是  Refresh  （）方法，它首先调用  Invalidate  （）方法来让整个区域失效，然后调用  Update
（）来使得消息移动到队列的顶端。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





