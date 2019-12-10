---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page258
date: 2009-03-19 12:57:00
tags: 我翻译的Head First C#（习作）
---
做一点接口的练习

  

接口很容易使用，但是理解它的最好方式就是亲自用一下。创建一个新窗体应用，拖拽一个按钮进去，开始吧！

  

①  下面是TallGuy类，按钮的事件响应方法初始化TallGuy对象，并调用其TalkAboutYourself  （）方法。这儿没什么不懂的--
我们一会会用到它：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090319/2009-03-19_12-45-30.jpg)

②  创建一个IClown接口。你已经知道了接口中的成员都是公有的。但是不要轻易相信我们  告诉你的。如下声明一个接口：

  

public interface IClown

  

然后试着在其中声明一个私有方法：

  

private void Honk  （）；

  

编译它，你将会看到如下的IDE报出的错误：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090319/2009-03-19_12-49-34.jpg)

现在删除private访问修饰符--错误将会消失，程序可以编译了。

  

③  翻页之前，试着看看你可不可以完成IClown接口，并让TallGuy类实现它。IClown接口
要有一个无参的返回值为void的方法，叫做Honk，还有一个只读的strig属性，叫做FunnyThingIHave，它只有get访问器没有set访问器。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

