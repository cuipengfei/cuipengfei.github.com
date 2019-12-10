---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page486
date: 2008-11-02 10:54:00
tags: 我翻译的Head First C#（习作）
---
一个对象  触发  事件，其他的对象监听它...  <?xml:namespace prefix = o ns = "urn:schemas-
microsoft-com:office:office" />

让我们来看看事件，事件处理者，和注册者在C#里面是怎么工作的：

1  首先，其他对象注册事件

在球触发它的BallInPlay事件之前，其它对象注册它。那就是它们说无论何时BallInPlay事件发生我们想要知道的方式。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE00.jpg)

2  某对象触发一个事件

球被击打。球对象就是时候该触发一个新事件了。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE01.jpg)

3  球触发一个事件

一个新事件被创建（我们马上就讨论那是怎么工作的）。那个事件也有一些参数，比如球的速度，轨道。这些参数作为EventArgs对象的实例被联系到事件上，然后事件
被送出，其他对象可以监听他了。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081102/%E6%88%AA%E5%9B%BE02.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





