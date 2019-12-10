---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page280
date: 2009-03-27 17:07:00
tags: 我翻译的Head First C#（习作）
---
有些类永远不应该被实例化

实例化PlanetMission是问题的罪魁祸首。它的FuelNeeded（）方法期望字段是被子类赋了值的。但是如果没有赋值，字段们将会被赋值为默认值0.而
当C#用一个数字除以0的时候...

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090327/2009-03-27_16-55-21.jpg)

解决方案：使用抽象类

C#  中不允许创建一个声明为抽象的类的实例。抽象类很像接口--它就好像是继承它的类的模板。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090327/2009-03-27_17-01-21.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

