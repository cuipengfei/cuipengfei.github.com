---
title: Head First C# 中文版 图文皆译 第五章 封装 page186
date: 2009-02-07 18:29:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090207/%E6%88%AA%E5%9B%BE04.jpg) 用封装来控制你的类的字段和方法的访问

当你把你的一个类的字段和方法都设置为public的时候，其他的类都可以访问它们，这个类知道的和会做的事儿对其他类都是一目了然...而且你也看见了这会怎么样的
使得你的程序以你未预期的方式运作。封装让你可以控制要共享什么要把什么设置为私有的。我们来看看这是怎么工作的：

1  超级间谍 Herb Jones，作为一个卧底探员正在苏联保卫生命，自由和对幸福的追逐。他的ciaAgent对象

是一个SecretAgent类的实例。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090207/%E6%88%AA%E5%9B%BE02.jpg)

2  Jones  探员有一个计划要避开敌方的克格勃探员。他有一个方法AgentGreeting（），它接受一个密码作为参数。如果没有接收到正确的密码，他将
只会向外透露别名，Dash Martin。

3  看起来用这个方法保护特工的身份是很简单的，对吧？只要调用他的特工没有正确的密码，我们的探员就是安全的。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090207/%E6%88%AA%E5%9B%BE03.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

