---
title: Head First C# 中文版 图文皆译 第五章 封装 page201
date: 2009-02-18 19:49:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90218/2009-02-18_19-30-10.jpg)

问：我注意到你给某些字段命名时用大写字母而有些是小写的。这个要紧吗？

答：是的，对你来说要紧。不过对于编译器无关紧要。C#不关注你怎么给你的变量命名，但是如果你的命名很怪异的话，你的代码就会很难读懂。有时同名的变量会让人迷惑，
而一个大写一个小写的情况就可以除外。下面有几条建议帮你了解变量名。这不是严格的规矩--编译器不管变量名是大写还是小写--但是这些建议可以帮你把代码弄得易懂。

1\.  私有字段应该用camelCase（  骆驼拼写法  ）。（小写字母开头，后面的单词大写字母开头，看起来像一个驼峰，因而得名骆驼。）

2\.  公有属性和方法用PascalCase（大写字母开头）。

3\.  方法的参数用camelCase。

4\.
有些方法，尤其是构造方法，经常有与字段同名的参数。这时，参数会遮盖掉字段，也就是说在方法中使用这个名字是代表参数而不是字段。用this关键字来解决这个问题
--在变量名前写this，告诉编译器你指的是字段而不是参数。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90218/2009-02-18_19-45-08.jpg) 这段代码有问题。写下你认为代码中哪儿有错，你会怎么修改。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90218/2009-02-18_19-47-14.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

