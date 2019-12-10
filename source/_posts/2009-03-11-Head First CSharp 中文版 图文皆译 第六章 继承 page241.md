---
title: Head First C# 中文版 图文皆译 第六章 继承 page241
date: 2009-03-11 09:57:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090311/2009-03-11_09-18-41.jpg) 蜂后需要你的帮助！用你学过的关于类和对象的知识来构建一个蜂窝管理系统来帮助蜂后管理工蜂。

①  创建窗体

窗体很简单--功能都在Queen和Worker类中完成。窗体有一个私有的Queen字段，还有两个按钮分别调用AssignWork（）和WorkTheNext
Shift（）方法。添加一个ComboBox控件来显示蜜蜂的工作列表（返回前几页查看蜜蜂的工作有哪几项），添加一个NumbericUpDown控件，两个按钮
，还有一个多行的textbox控件来显示班次报告。你也需要一个窗体的构造方法--就在截图下面。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090311/2009-03-11_09-27-38.jpg)

②  创建Worker和Queen类

你对于这两个类需要的东西了解的已经够多了。现在只需要一点细节了。Queen的AssignWork（）方法遍历工蜂数组并用工蜂的DoThisJob（）方法来分
配工作。工蜂用自己的jobsICando字符串数组来检查自己是否会做分配的工作。如果会做该工作它将自己的shiftToWork私有字段设置为工作时间，设置C
urrentJob为分配的工作，设置shiftNumber为零。工蜂工作一个班次之后，给shiftNumber减一
。只读的ShiftsNumber属性返回shiftsToWork-shiftsWorked。蜂后用它检查一项工作还剩多少班次。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

