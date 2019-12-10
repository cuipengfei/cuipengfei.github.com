---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page292
date: 2009-03-31 09:23:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090331/2009-03-31_08-44-41.jpg) 你已经有了类模型了，你可以给房子的各个部分创建对象，并用一个窗体来浏览它们。

⑥  房子中的对象是如何工作的

下面是frontYard和diningRoom的构造图。它们都有门，所以都要实现IHasExteriorDoor接口。DoorLocation属性持有一个指
向门的另一侧的地点的引用。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090331/2009-03-31_08-54-35.jpg)

⑦  完成类，并实例化之

所有类的雏形都有了，现在把它们写完并且实例化吧

★  你需要确定Outside的构造方法设置只读属性Hot的值并覆写Description属性来在Hot为真的情况下把“这儿好热啊！”添加进去。后院很热，但
是前院和花园不热。

★Room  的构造方法需要给Decoration赋值，还需要覆写Description属性来添加“你看到了（装饰的内容）”。起居室有一个古董地毯，餐厅有一
个水晶吊灯，厨房有一套不锈钢厨具还有一个通向后院的纱门。

★  窗体需要创建每一个类的对象并且保有它们的引用。给窗体添加一个CreateObjects（）方法，并在构造方法中调用它。

★  初始化房子中六个地点的对象。下面是一部分代码：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090331/2009-03-31_09-14-42.jpg)

★CreatObjects  （）方法需要填充每一个对象的Exits[]数组：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090331/2009-03-31_09-16-14.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

