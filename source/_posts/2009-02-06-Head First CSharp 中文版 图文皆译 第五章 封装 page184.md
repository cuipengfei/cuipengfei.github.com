---
title: Head First C# 中文版 图文皆译 第五章 封装 page184
date: 2009-02-06 22:53:00
tags: 我翻译的Head First C#（习作）
---
你一不小心就会意外的错用了对象

凯瑟琳的问题是因为她忽略了方便的CalculateCostOfDecorations（）方法而是直接去用DinnerParty的字段。所以，虽然你的Dinn
erParty类工作的不错，但是窗体以未预期的方式调用了它...这就导致了问题。

1 DinnerParty  类被预期以什么方式调用

DinnerParty  类给了窗体一个很好的方法来计算装饰的总花费。它所需要做的就是调用CalculateCostOfDecorations（），然后Ca
lculateCost（）方法就会返回正确的花费。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE04.jpg)

2 DinnerParty  类实际是怎么被调用的

窗体设置人数，但是没有重新计算装饰费就只调用了CalculateCost（）方法。这样凯瑟琳就给了Rob错误的价格。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE05.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

