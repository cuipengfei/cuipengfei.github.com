---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page316
date: 2009-04-06 19:40:00
tags: 我翻译的Head First C#（习作）
---
数组不易操作

数组适合存储一系列固定的值和引用。但是一旦你需要移动元素或者添加的元素大于数组的长度，情况就不妙了。

①  每个数组都有长度，你需要知道长度才可以操作数组。你可以用空引用来把某些数组元素保持为空：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090406/2009-04-06_19-13-37.jpg)

②  你需要知道保存了多少张牌。所以你需要一个int类型的字段，把它叫做topCard，它保存着数组中最后一张牌的位序。所以我们的只有三张牌的数组长度为7，
但是我们要把topCard赋值为3。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090406/2009-04-06_19-26-18.jpg)

③  现在情况复杂了。添加一个返回最上面的牌的引用的Peek（）（偷窥）方法很简单--这样你就可以偷看牌堆中最上面的牌了。但是如果想要添加一张牌怎么办呢？如
果topCard小于数组的长度，你可以把一张牌放进数组去并给topCard加1。但是如果数组满了，你就需要创建一个新的，更大的数组并把原数组中的牌复制进去。
移除牌是很简单的--但是给topCard减1之后，你需要把被移除的牌原来所在的位序设置为空。而如果需要在数组中间某个位置移除一张牌又怎么办呢？如果你想要移除
第四张牌，那你就要把第五张牌向前移来填补空白，然后前移第六张牌，然后第七张...噢，这太麻烦了！



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





