---
title: Head First C# 中文版 第12章 回顾与前瞻 page537
date: 2009-06-18 09:15:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090618/2009-06-18_08-47-01.jpg)

我们在模拟器中用到的面向对象的一个重要原则就是封装。看看你可不可以根据观察我们已经写过的类来找出每个类中两个封装的例子。

  

下面是我们想出来的，你有没有想出别的来？

  

Hive  ：蜂巢的  Locations  字典是私有的；它给蜜蜂提供了一个添加蜂蜜的方法。

  

Bee  ：蜜蜂的位置和年龄都是只读的，这样其他类将无法写这些数据。

  

Flower  ：花朵提供了采集花粉的方法；花朵的布尔型  alive  字段是私有的。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090618/2009-06-18_08-52-29.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090618/2009-06-18_09-01-50.jpg)

问：为什么不用  foreach  循环来移除死掉的花朵和退休的蜜蜂呢？

  

答：  foreach  是用来迭代集合的，在其中无法移除元素。如果你那么做的话，  .NET  将会抛出异常。

  

问：好的。那为什么那些  for  循环都是从列表的尾部开始向位序  0  进行呢？

  

答：因为每个循环都需要维护列表的编号。假设你在遍历一个五朵花的列表，而循环的时候发现中间的花朵已经死亡了。如果移除这朵花，列表中就只有四朵花了，而原来的位序
处将会被后面的花朵占据  \--  而这朵花将会被跳过。而循环从尾部开始的话，移动到空出来的位序去的花朵将会是已经被查看过的，这样就不会跳过任何一朵花了。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

