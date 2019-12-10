---
title: Head First C# 中文版 第10章 异常处理 page465
date: 2009-06-04 08:59:00
tags: 我翻译的Head First C#（习作）
---
用  Exception  的对象来获取有关问题的信息

  

我们一直在说抛出异常的时候  .NET  会生成一个  Exception  的对象。你在写  catch  块的时候可以访问这个对象的。如下：

  

①一个对象正在哼哼着小曲做自己的事儿，就在这时它遇到了某些未预期的事情并抛出了一个异常。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090604/2009-06-04_08-49-29.jpg)

②幸运的是，  try/catch  块捕获了这个异常。在  catch  块中，异常对象命名为  ex  。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090604/2009-06-04_08-52-12.jpg)

③异常对象在  catch  块结束之前都是有效的。  catch  块结束之后  ex  引用失效，对象也就被垃圾收集了。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090604/2009-06-04_08-56-57.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

