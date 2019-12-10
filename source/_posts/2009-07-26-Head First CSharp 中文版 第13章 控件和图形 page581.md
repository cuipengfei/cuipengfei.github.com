---
title: Head First C# 中文版 第13章 控件和图形 page581
date: 2009-07-26 11:09:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090726/2009-07-26_10-01-52.jpg)

④蜜蜂和花朵已经知道自己的位置了

  

我们用  Point  存储蜜蜂和花朵的位置是有原因的，这样我们可以根据  Bee  对象的位置来设置它对应的  BeeControl  的位置：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090726/2009-07-26_10-55-49.jpg)

⑤如果某只蜜蜂没有对应的控件，渲染器会去把它添加到蜂巢窗体中去

  

渲染器类可以很简单那的通过字典的  ContainsKey  （）方法来查询出一个  Bee  对象是否有一个对应的  Beecontrol
。如果没有渲染器会创建一个  BeeControl  ，把它添加入字典并添加到窗体中去。（它还会调用控件的  BringToFront
（）方法，以免蜜蜂会被花朵的  PictureBox  遮挡住）：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090726/2009-07-26_11-02-23.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

