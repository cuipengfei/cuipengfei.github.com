---
title: Head First C# 中文版 图文皆译 第六章 继承 page234
date: 2009-03-08 22:29:00
tags: 我翻译的Head First C#（习作）
---
现在你可以去完成凯瑟琳的任务了！ ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei
1/EntryImages/20090308/2009-03-08_22-20-58.jpg)

你上次放手凯瑟琳的工作的时候，刚刚给她的程序添加了生日聚会的功能。她需要你给她的程序添加一个向12人以上的聚会加收$100的功能。当时你好像必须把一样的代码
写两次，每个类一次。现在你会用继承了，你可以让两个聚会类继承同一个父类，父类中含有两个聚会类的公共代码，这样同样的代码就不用写两次了。

  

如果你做的好的话，我们可以做到修改两个类而无须修改窗体！

  

①  我们来创建新的类模型

  

我们还是会保留DinnerParty和BirthdayParty类，但是现在它们要继承自同一个Party类。我们需要让它们保持和原来一样的方法、字段、属性，
这样就无需修改窗体了。但是某些方法、字段、属性需要移动到Party类中去，我们还需要覆写它们中的一部分。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090308/2009-03-08_22-27-06.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

