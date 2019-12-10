---
title: Head First C# 中文版 第13章 控件和图形 page577
date: 2009-07-24 08:46:00
tags: 我翻译的Head First C#（习作）
---
BeeControl  也需要释放本身包含的控件

  

Control  类实现了  IDisposable  接口，所以你需要确保每个使用过的控件都得以释放掉。  BeeControl  内部含有一个
Timer  控件，而它没有得以释放掉，这是个问题。不过很幸运，这个问题很容易修复  \--  覆写  Dispose  （）方法即可。

  

③覆写  Dispose  （）方法来释放  Timer

  

BeeControl  继承自  Control  ，所以它必然含有  Dispose  （）方法。所以我们可以覆写该方法来释放  Timer
。进入代码键入  override  ：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090724/2009-07-24_08-35-34.jpg)

在智能感应窗口中点击了  Dispose  （）之后，  IDE  将会用  base.Dispose  （）这一语句填充该方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090724/2009-07-24_08-39-22.jpg)

④添加代码来处理  Timer

  

给  IDE  帮你填充的方法添加一行即可：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090724/2009-07-24_08-41-20.jpg)

现在你的  BeeControl  可以处理掉  Timer  了。把  IDE  给你生成的那行代码留在那儿，这样  PictureBox
本身也可以得以处理了。

  

现在清理掉  Timer  已经成为  BeeControl  的  Dispose  （）方法的一部分了。

  

自定义的控件需要负责清理其中创建的其他控件。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

