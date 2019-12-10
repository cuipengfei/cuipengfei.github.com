---
title: Head First C# 中文版 图文皆译 第五章 封装 page183
date: 2009-02-06 21:07:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE00633695512283076708.jpg)

看看响应NumericUpDown控件的值的改变的方法。它先把值赋给NumberofPeople变量然后调用DisplayDinnerPartyCost()
方法。然后就靠着这个方法来重新计算每一项的花费。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE01633695512283389200.jpg)

所以，当你修改了NumberofPeople字段的值的时候，这个方法不会得到调用：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE02633695512284014184.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE03.jpg)

人们并不是总会按照你预期的方式使用你的程序的。

很幸运，C#给了你一个很有用的工具来使得你的程序总是正确工作--即使用户做了你从来没有预期的事儿。这叫做封装，这对于操作对象是很有用的技术。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





