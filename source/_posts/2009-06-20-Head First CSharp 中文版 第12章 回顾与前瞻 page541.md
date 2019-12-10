---
title: Head First C# 中文版 第12章 回顾与前瞻 page541
date: 2009-06-20 18:29:00
tags: 我翻译的Head First C#（习作）
---
③更新  World  的代码来把自己传递给  Hive

  

更新  World  的代码来让它在创建新  Hive  的实例的时候把自己的引用传递进去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090620/2009-06-20_18-19-15.jpg)

④给  Hive  可以创建的蜜蜂数一个上限

  

Hive  类有一个  MaximumBees  常量，它决定了  Hive  可以支持多少蜜蜂（蜂巢内和蜂巢外都算）。现在  Hive  可以访问
World  了，你应该可以运用这个约束了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090620/2009-06-20_18-21-04.jpg)

⑤  Hive  创建蜜蜂的时候，让  World  知道

  

World  类保持所有的存在的蜜蜂。当  Hive  创建幼蜂的时候，确保这个蜜蜂被添加到  World  保持的全局列表里面去。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090620/2009-06-20_18-26-11.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





