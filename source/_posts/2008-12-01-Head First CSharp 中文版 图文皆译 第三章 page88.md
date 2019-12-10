---
title: Head First C# 中文版 图文皆译 第三章 page88
date: 2008-12-01 09:03:00
tags: 我翻译的Head First C#（习作）
---
Mike  的导航类有用来设置和修改路径的方法  <?xml:namespace prefix = o ns = "urn:schemas-
microsoft-com:office:office" />

Mike  的导航器类有一些方法，功能就是在里面实现的。但是和你知道的button_Click（）方法不一样，他们都围绕着一个问题：在城市中导航一条道路。这
也就是Mike要把它们归为一类，并称该类为Navigator的原因了。

Mike  设计导航器类来方便的生成和修改路径。要得到一条路径，先要调用SetDestination（）方法来设置目的地，然后用GetRoute（）方法把路
径信息包含到一个String中去。如果需要改变路径，他的程序调用ModifyRouteToAvoid（）方法来避开一条特定的街道，然后再调用GetRoute
（）方法来得到新方向。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081201/%E6%88%AA%E5%9B%BE00.jpg)

有的方法有一个返回值

每个方法都是用语句组成的。有的方法只是执行完了语句就退出了。但是有的方法就有一个返回值，或者说一个在方法内部生成的或者计算得来的值，并把它送回到调用它的语句
去。返回的值的类型（比如String、int）叫做返回类型。

return  语句告诉方法马上退出。如果你的方法没有返回值--也就是说返回类型为void--
那么return语句就用分号结束，或者干脆不写return语句都可以。但是如果方法有返回类型，那么就必须有return语句。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081201/%E6%88%AA%E5%9B%BE01.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081201/%E6%88%AA%E5%9B%BE02.jpg)

这个语句调用方法来计算两个数字相乘。它返回一个int：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081201/%E6%88%AA%E5%9B%BE03.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





