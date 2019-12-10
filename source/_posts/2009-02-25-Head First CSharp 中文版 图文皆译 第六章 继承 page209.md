---
title: Head First C# 中文版 图文皆译 第六章 继承 page209
date: 2009-02-25 09:33:00
tags: 我翻译的Head First C#（习作）
---
四．  构建生日聚会的界面

生日聚会的GUI含有一个NumbericUpDown控件来代表人数，含有一个CheckBox控件来代表高级装饰，一个有着3D边界的标签来显示总花费。然后还有
一个TextBox控件来显示蛋糕上要写的字。

![https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090
225/2009-02-24_22-11-33.jpg](https://p-blog.csdn.net/images/p_blog_csdn_net/cu
ipengfei1/EntryImages/20090225/2009-02-24_22-11-33.jpg)

五．  你将需要这个属性

这是BirthDayParty.CakeWriting属性的代码--它会派上用场的：

![https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090
225/2009-02-25_09-11-22.jpg](https://p-blog.csdn.net/images/p_blog_csdn_net/cu
ipengfei1/EntryImages/20090225/2009-02-25_09-11-22.jpg)

六．  把它们组合起来

所有的部分都写好了，现在只需要写一些代码来让控件工作。

*  添加一个BirthDayParty对象到窗体。确保你实例化了它。 

*  编写NumbericUpDown控件的事件处理方法，该方法用来设置NumberOfPeople属性。 

*  让高级装饰的CheckBox可以工作 

*  添加一个DisplayBirthDayPartyCost（）方法，并在所有的事件处理方法中调用它，这样显示花费的标签将会在任何变化发生的时候得以更新。 

七．  运行

确保程序以它被预期的方式运行。检查一下文字过长的时候是不是会弹出错误提示框。确保价钱总是正确的。上面这些做好了，你的工作就搞定了！

![https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090
225/2009-02-25_09-31-07.jpg](https://p-blog.csdn.net/images/p_blog_csdn_net/cu
ipengfei1/EntryImages/20090225/2009-02-25_09-31-07.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

