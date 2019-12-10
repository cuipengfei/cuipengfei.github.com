---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page313
date: 2009-04-06 10:28:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90406/2009-04-06_10-26-22.jpg) 用你学过的关于枚举的知识来创建一个扑克牌的程序。

①  创建一个新项目并添加一个Card类

你会需要两个公有字段：Suit（代表花色，可以是Spades，黑桃；Clubs，梅花；Diamonds，方片；Hearts，红桃）。还有Value（代表牌的
大小，可以是A，2，3......10，J，Q，K）。你还需要一个只读属性Name（它的值可以是黑桃A，方片5）。

②  用两个枚举来代表花色和牌的大小

确保（int）Card.Suits.Spades的值是0，然后是Clubs等于1，Diamonds等于2，Hearts等于3。让Value的值等于牌面的大小
：（int）Card.Values.Ace等于1，Two等于2，Three等于3，等等。Jack等于11，Queen等于12，King等于13。

③  给牌的名字添加一个属性

Name  应该是一个只读属性。它的get访问器会返回一个描述牌的字符串。下面的代码将会在窗体中调用Card类的Name属性，并把它显示出来：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90406/2009-04-06_10-07-17.jpg)

④  在窗体上添加一个按钮，让它弹出一个消息框来显示随机一张牌

你可以通过把一个0到3的随机值转型为Cards.Suits，还把一个1到13之间的随机值转型为Cards.Values来创建一张随机的牌。你可以通过内建的R
andom类来做这件事，你有三种方式来调用它的Next（）方法。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90406/2009-04-06_10-13-20.jpg)  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90406/2009-04-06_10-17-04.jpg)

问：  等一下。当我键入上面的代码的时候，我注意到一个智能感应窗口在我用Random.Next（）方法的时候弹出来说什么“3 of 3”。这是什么意思？

答：  这就是重载了。一个方法有多种被调用的方式就叫做重载。使用重载方法的时候，IDE会给出你所有可能的选择。在这儿，Random的Next（）方法有三种调
用方式。你一旦键入“random.Next（”，IDE就会弹出智能感应窗口来提示你。现在不用太关心重载的问题，本章后面我们会讲的。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

