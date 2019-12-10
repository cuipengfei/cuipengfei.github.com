---
title: Head First C# 中文版 图文皆译 第二章 page50
date: 2008-10-24 00:13:00
tags: 我翻译的Head First C#（习作）
---
在IDE里面改变东西就同步的改变了代码  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

IDE  长于为你写可视化代码。但是别全靠它。打开VS，创建一个新窗体应用项目，亲自看看吧。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081024/%E6%88%AA%E5%9B%BE06.jpg)

1  打开设计器代码

在IDE里打开Form1.Designer.cs文件。但是这次，不是在窗体设计器里面打开，而是通过在解决方案浏览器里右击它并选择“查看代码”来打开它的代码。
找到Form1类的声明：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081024/%E6%88%AA%E5%9B%BE07.jpg)

2  打开窗体设计器并向窗体添加一个PictureBox

要习惯于操作多个标签页。打开Form1.cs的窗体设计器。拖拽一个新PictureBox到窗体上去。

3  找到并扩展PictureBox控件的设计器生成代码

回到Form1.Designer.cs标签页。向下滚动找到这一行代码：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081024/%E6%88%AA%E5%9B%BE08.jpg)

点击左侧的+来展开代码。向下滚动找到这些代码：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081024/%E6%88%AA%E5%9B%BE09.jpg)



