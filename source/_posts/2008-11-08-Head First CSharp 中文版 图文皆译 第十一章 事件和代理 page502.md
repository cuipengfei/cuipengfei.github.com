---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page502
date: 2008-11-08 09:57:00
tags: 我翻译的Head First C#（习作）
---
委托开始行动  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

委托没什么神秘的，使用它用不了多少代码。我们来用它来帮助一个酒店老板给他的大厨的秘方分类。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081108/%E6%88%AA%E5%9B%BE00.jpg)

委托一般出现在类之外。向你的项目添加一个叫做AddSecretIngredient.cs的类文件。它将会只有一行代码：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081108/%E6%88%AA%E5%9B%BE01.jpg)

这个委托能用来创建可以指向任何接受一个int参数，返回一个string值的方法的变量。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081108/%E6%88%AA%E5%9B%BE02.jpg)

Suzanne.cs  将会内有一个含有第一个大厨的秘方的类。它有一个叫做SuzannesSecretIngredient（）的私有方法，且它匹配GetSe
cretIngredient的方法签名。但是它也有一个只读属性--并检查属性的类型。它返回一个GetSecretIngredient。这样别的对象就可以通过
这个属性来获得SuzannesSecretIngredient（）方法的引用。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081108/%E6%88%AA%E5%9B%BE03.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

