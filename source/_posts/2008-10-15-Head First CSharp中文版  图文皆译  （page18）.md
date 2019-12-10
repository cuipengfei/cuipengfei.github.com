---
title: Head First C#中文版  图文皆译  （page18）
date: 2008-10-15 10:54:00
tags: 我翻译的Head First C#（习作）
---
我们需要一个数据库来存储我们的信息  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

在向窗体添加剩下的字段之前，我们需要创建一

个数据库来和窗体连接。IDE会创建很多代码来

把数据和我们的窗体连接起来，但是我们还是要

先创建数据库本身。  ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/Entr
yImages/20081015/%E6%88%AA%E5%9B%BE00.jpg)

1  向你的项目添加一个SQL数据库

在解决方案浏览器里，右键点击Contacts项目，选择添加，然后选择新建项。选择SQL数据库图标，并把它命名为ContactDB.mdf。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081015/%E6%88%AA%E5%9B%BE01.jpg)

2  取消数据源配置向导。

现在，我们想要跳过配置数据源的步骤，所以，点击取消按钮。我们一设置完数据库结构就回到这一步来。

3  在解决方案浏览器里查看你的数据库。

在解决方案浏览器里你会看到ContactDB已经被添加到文件列表里。双击ContactDB.mdf，看看你屏幕的左侧。工具箱已经变成了数据库浏览器。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081015/%E6%88%AA%E5%9B%BE02.jpg)



