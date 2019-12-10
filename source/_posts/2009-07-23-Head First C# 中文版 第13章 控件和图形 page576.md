---
title: Head First C# 中文版 第13章 控件和图形 page576
date: 2009-07-23 21:37:00
tags: 我翻译的Head First C#（习作）
---
创建一个按钮来向窗体添加  BeeControl

  

向一个窗体添加一个控件很简单  \--  把它加入  Controls  集合即可。移除也是一样简单。不过控件都实现了  IDisposable
接口，所以要确定移除控件之后要去销毁对象。

  

如下做：

  

①从你的窗体上移除  BeeControl  ，并添加一个按钮。我们将使用这个按钮来添加和移除  BeeControl  。

  

②添加一个按钮来添加和移除  BeeControl

  

下面是它的事件处理方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90723/2009-07-23_21-25-02.jpg)

现在运行程序，点击按钮一次将会添加一个新的  BeeControl  到窗体上去。再次点击将会删除它。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90723/2009-07-23_21-32-57.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90723/2009-07-23_21-32-05.jpg)

工具栏中的每一个可视化控件都继承自  System.Windows.Forms.Control  。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

