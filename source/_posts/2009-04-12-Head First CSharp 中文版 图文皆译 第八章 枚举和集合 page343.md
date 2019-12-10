---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page343
date: 2009-04-12 16:52:00
tags: 我翻译的Head First C#（习作）
---
你自己也可以创建重载方法

  

你已经用过重载方法了，甚至还用过.NET
Framework的内建类的重载构造方法，所以你应该知道重载是多么实用了。要是你可以在自己的类中写重载方法岂不是很酷？好吧，你可以做到--
而且很简单！你需要做的只是写两个或更多个同名但是接收参数不同的方法。

  

①  创建一个新项目并把Card类添加进去

  

这很容易，在项目上右击，再在“添加”中选择“现有项”。IDE将会复制Card类并把它添加进本项目。类文件中的命名空间还是其出处的名字，所以你要到Card.c
s类文件的顶部去把它修改为与当前项目一致。

  

②  给Card类添加一些重载方法。

  

创建两个static DoesCardMatch（）方法。第一个检查牌的花色。第二个检查牌的面值。只有当牌匹配的时候它们才会返回true。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090412/2009-04-12_16-42-46.jpg)

③  向窗体添加一个按钮来使用新方法

  

给按钮添加下面的代码：

  

Card cardToCheck = new Card(Card.Suits.Clubs , Card.Values.Three);

  

bool doesItMatch = Card.DoesCardMatch(cardToCeck , Card.Suits.Hearts);

  

你键入“DoesCardMatch（”之后，IDE的反应就说明了你的确是写出了重载的方法：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090412/2009-04-12_16-49-21.jpg)

使用一下这两个方法，熟悉一下重载。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

