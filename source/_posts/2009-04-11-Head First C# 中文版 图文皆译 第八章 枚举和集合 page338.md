---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page338
date: 2009-04-11 22:24:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90411/2009-04-11_21-44-38.jpg)

①  创建一个窗体来在两副扑克之间移动牌

  

你已经创建了一个Card类了。现在该是时候创建一个类来包含任意数量的牌了，我们把它叫做Deck（一副牌）。现实中一副牌有52张，但是Deck类可以包含任意数
量的牌--或者其中没有牌也可以。

  

然后你要创建一个窗体来显示两个Deck对象的内容。启动程序的时候，第一个Deck对象最多有10张随机的牌，第二个Deck对象是完整的一副52张牌，它们都是先
依据花色后依据牌面值来排序的--而且你可以用两个Reset按钮把两副牌重设为初始状态。窗体上还有标记为“<<”和“>>”的按钮来在两副牌之间移动牌。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90411/2009-04-11_21-57-34.jpg)

除了六个按钮的事件处理方法之外，你还需要给窗体添加两个方法。首先添加一个ResetDeck（）方法，它把一副牌重设到初始状态。它接受一个int参数：如果传入
1，它把第一副牌重设为含有十张随机牌的初始状态；如果传入2，它把第二副牌重设为含有52张牌的一整副牌。再然后添加下面这个方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90411/2009-04-11_22-16-09.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

