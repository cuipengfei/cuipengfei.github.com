---
title: Head First C# 中文版 图文皆译 第三章 page89
date: 2008-12-02 09:04:00
tags: 我翻译的Head First C#（习作）
---
用你所学的构建一个简单的应用  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

我们在一个类里面创建一个窗体，让它的按钮调用一个该类中的方法。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081202/%E6%88%AA%E5%9B%BE00.jpg)

1  在IDE中创建一个新的窗体应用。然后添加一个叫做Talker.cs的类文件。你把类文件命名为Talker.cs，IDE就会自动把文件里面的类命名为Ta
lker。然后，IDE就会在一个新标签页里面弹出这个类。

2  在类文件开头添加一句using System.Windows.Forms；然后向类里面添加代码：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081202/%E6%88%AA%E5%9B%BE02.jpg)

这个新类的BlahBlahBlah（）方法接受两个参数。第一个参数告诉它说什么，第二个指示说几遍。它被调用的时候将会弹出一个消息框，带有重复的字符串信息。它
的返回值是字符串的长度。它的thingToSay参数接受String，numberOfTimes参数接受一个数字。它将会在窗体的TextBox控件和Nume
ricUpDown控件里得到这两个参数。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081202/%E6%88%AA%E5%9B%BE03.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

