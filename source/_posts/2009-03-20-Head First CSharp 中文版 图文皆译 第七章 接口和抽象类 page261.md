---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page261
date: 2009-03-20 22:51:00
tags: 我翻译的Head First C#（习作）
---
接口引用和对象引用基本一样 ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/E
ntryImages/20090320/2009-03-20_22-45-24.jpg)

  

你已经知道对象在托管堆上的存在形式了。而接口引用只是指向对象的另一种方式而已。看--很简单！

  

①  创建几个蜜蜂对象

  

这种语句我们已经很熟悉了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090320/2009-03-20_22-35-45.jpg)

②  添加IStingPatrol和INectarCollector接口引用

使用接口引用就和使用其他引用类型一样。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090320/2009-03-20_22-37-29.jpg)

③  接口引用可以使得对象不被垃圾回收

  

当没有任何引用指向一个对象的时候，该对象就会被垃圾回收。但是并没有规定说这些引用必须是同一个类型的！一个接口引用在指向对象的时候和对象引用是一样的。

  

biff = null  ；

  

④  把一个实例赋值给一个接口引用

  

可以不用对象引用而直接创建一个对象并把它直接赋值给一个接口引用变量。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090320/2009-03-20_22-44-15.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

