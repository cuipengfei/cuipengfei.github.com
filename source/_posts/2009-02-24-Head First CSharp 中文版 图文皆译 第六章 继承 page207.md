---
title: Head First C# 中文版 图文皆译 第六章 继承 page207
date: 2009-02-24 08:52:00
tags: 我翻译的Head First C#（习作）
---
** 我们需要一个  ** ** BirthDayParty  ** ** 类  ** ** **

修改你的程序来计算生日聚会的花费就意味着要添加一个类并改变窗体来同时掌管两种类型的聚会。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090224/2009-02-23_22-29-56.jpg)

** 一、  ** ** 创建一个新的  BirthDayParty  ** ** 类  **

这个类要计算花费，处理装饰，并检查蛋糕上的字体的大小。

** 二、  ** ** 添加一个  TabControl  ** ** 控件到窗体上。  **

窗体上的每一个  tab  就像第三章上显示  Joe  和  Bob  有多少现金的  GroupBox  控件。点击你想要显示的  tab
，并向其中拖拽控件。

** 三、  ** ** 把晚宴聚会需要的控件拖拽到第一个  tab  ** ** 上去  **

要把每一个处理晚宴聚会的空间都拖拽到上面去。它们将会像原来一样的工作，但是它们只有在晚宴聚会的  tab  被选中时才显示出来。

** 四、  ** ** 向第二个  tab  ** ** 上添加新的生日聚会的控件  **

你需要像设计晚宴聚会的界面一样的来设计生日聚会的界面。

** 五、  ** ** 根据控件来写生日聚会的类  **

现在只需要在窗体的字段里面添加一个  BirthDayParty  的引用，并向新的控件添加代码以使得它们可以使用类的方法和属性。

** ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090224/2009-02-24_08-40-39.jpg) **

** 问：为什么不可以像  Mike  ** ** 想要在导航器中比较三条道路时一样，直接创建一个  DinnerParty  ** ** 的新实例呢？  **

答：因为如果你创建了  DinnerParty  的另一个新实例，它只能用来计划另一个晚宴聚会。两个同类的实例只有在你需要处理同类的两份数据的时候才会显得有
用。但是如果你需要存储不同的数据，那你就需要不同的类来完成。

** 问：我怎么知道该往新类里面写些什么呢？  **

答：开始构建爱一个类之前，需要先知道它是用来解决什么问题的。因此你必须和凯瑟琳谈谈  —
是她要使用这个程序。还好你记了不少笔记！你可以通过考虑类的行为（类需要做什么）和状态（类需要知悉什么）来想出类的方法，字段，和属性。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

