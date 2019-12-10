---
title: Head First C# 中文版 图文皆译 第六章 继承 page217
date: 2009-03-01 10:27:00
tags: 我翻译的Head First C#（习作）
---
使用继承来防止子类中的冗余代码

你已经知道了冗余代码很糟糕。它难以维护，很是让人头疼。我们来选择要写在Animal类中的只需要写一次的字段和方法，这样每一个动物的子类都可以从其中继承。我们
从公有字段开始吧：

*Picture  ：可以放进PictureBox控件的图像。 

*Food  ：该种动物要吃的食物种类。现在，只有两种食物类型值：肉或者草。 

*Hunger  ：一个代表饥饿程度的int。它的改变取决于动物什么时候进食了和吃了多少。 

*Boundaries  ：存储笼子高度、宽度、位置的类的一个应用。动物会在这个笼子中转悠。 

*Location  ：动物所在地的X、Y坐标。 

Animal  类还有四个方法可以供子类继承：

*MakeNoise  （）：让动物发声的方法。 

*Eat  （）；动物吃它喜爱的食物的行为。 

*Sleep  （）；让动物躺下并小睡的方法。 

*Roam  （）：动物喜欢在笼子里面转悠。 

②  创建一个基类来给动物们提供共同的特性

基类中的字段、属性、和方法将会给所有继承自它的子类们共同的状态和行为。子类们都是动物，所以基类叫做Animal是说的通的。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090301/2009-03-01_10-16-35.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

