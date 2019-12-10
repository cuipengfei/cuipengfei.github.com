---
title: Head First C# 中文版 第10章 异常处理 page473
date: 2009-06-07 22:12:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90607/2009-06-07_21-49-44.jpg)

问：  using  中只可以使用实现了  IDisposable  接口的对象？

  

答：对。  IDisposable  就是设计来与  using  一起使用的。

  

问：可以在  using  块中写任何代码吗？

  

答：可以。  Using  的目的就是帮你把创建的对象最终处置掉。而你用这些对象做什么完全是你的事儿。

  

问：可以在  using  之外调用  Dispose  （）吗？

  

答：可以。你可以不用  using  语句，自己手动去清除资源。而用了  using  则可以使你的代码移动且防止你忘记了处置你的对象。

  

问：你提到了  try/finally  块。这意味着可以只有  try  和  finally  而没有  catch  ？

  

答：对！可以只有  try  和  finally  而没有  catch  。如果  try  块中的代码抛出异常，  finally  块将会立即执行。

  

问：只有文件和流才有  Dispose  （）方法吗  ?

  

答：不是的，很多类都实现了  IDisposable  ，你在使用它们的时候总是应该用  using
语句。如果你写一个需要用某种方式去处置的类，你也可以实现  IDisposable  。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90607/2009-06-07_22-03-22.jpg)

  

你想要知道抛出的是哪一种异常，这样你才可以处理该异常。

  

异常处理不仅是打印一个错误信息。比如说，在借口管理器中，如果我们知道捕获了一个  FileNotFoundException
，我们可以打印一个信息来提示在哪儿可以找到正确的文件。如果我们捕获了关于数据库的异常，我们可以给数据库管理员发一封邮件。这都是基于捕获特定种类的异常的。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

