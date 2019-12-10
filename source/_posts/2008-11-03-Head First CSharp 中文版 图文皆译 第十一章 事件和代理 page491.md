---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page491
date: 2008-11-03 13:32:00
tags: 我翻译的Head First C#（习作）
---
<?xml:namespace prefix = o ns = "urn:schemas-microsoft-com:office:office" />

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081103/%E6%88%AA%E5%9B%BE00633613159027955000.jpg)

问：声明事件的时候为什么要用EventHandler关键字？我认我EventHandler是别的对象用来注册事件的。

答：那是对的--当你需要注册一个事件的时候，你写一个方法来调用事件处理者，但是你注意到我们在事件声明时怎么用EventHandler了吗（第四步骤）？还有注
册事件处理者的时候了吗（第四步骤）？EventHandler做的就是定义事件的签名--它告诉注册事件的对象怎么定义它们的事件处理方法。特别的，它告诉你如果想
要注册这个事件，需要两个参数（一个object和一个EventArgs的引用）并有一个void返回值。

问：如果我试着用不匹配EventHandler的定义的方法会怎么样？

答：没法编译。编译器确保你不会意外的用不匹配的方法注册事件。这就是标准事件处理者，EventHandler，这么有用的原因--
你看见它，你就知道你的事件处理者应该改长什么样子了。

问：等会，“标准”事件处理者？还有别的种类的事件处理者？

答：对！你的事件不一定要传送一个object和一个EventArgs。实际上，他们可以传送任何东西--或者什么都不传送。看看首页底部智能感应窗口里面的最后一
行。注意到OnDragDrop方法是怎么用DragEventArgs引用来代替一个EventArgs的引用了吗?DragEventArgs继承自EventA
rgs，就像BallEventArgs那样。窗体的DragDrop事件不用EventHandler。他用别的东西。DragEventHandler，如果你想
用它，你的事件处理方法需要用一个object和一个DragEventArgs作参数。

问：那我的事件处理者也可以不返回void，对吗？

答：对，你可以，但是那是个坏主意。如果你不返回void，那么事件处理者就不能串联起来。那就意味着你不能用多个方法注册同一个事件。串联起来是很方便的，所以你最
好还是返回void。

问：串联起来？啥意思？

答：多个方法注册同一个事件--把事件处理者串联起来，一个接一个。一会我们会讨论它。

问：那就是我用+=添加事件处理者的原因？就好像我在把新的处理者加到已经存在的处理者上？

答：完全正确！添加一个事件处理者就要用+=。这样，你的事件处理者就不会覆盖掉已经存在的处理者。它只会成为一长串注册同一事件的事件处理者中的一个。

问：球触发BallInPlay（）事件的时候为什么要用“this”？

答：因为那是标准事件处理者的第一个参数。你注意到每一个事件处理者方法都有一个“object sender”参数了吗?那个参数是本实例的引用。所以处理按钮点击
事件，sender就指向被点击的按钮。处理BallInPlay事件，sender就指向被击打的球--

并且那个球触发事件的时候设置这些参数。

一个事件是被  一个  对象触发的，但是可以被  多个  对象响应。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

