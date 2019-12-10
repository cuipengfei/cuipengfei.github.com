---
title: Head First C# 中文版 图文皆译 第五章 封装 page195
date: 2009-02-11 16:56:00
tags: 我翻译的Head First C#（习作）
---
使用自动属性来结束这个类

看起来牛饲料计算器工作的不错。试一下--运行一下并按按钮。然后把牛数改为30并再点击按钮。然后再把牛数改为5和20。下面是输出窗口应该看起来的样子： ![]
(https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200902
11/%E6%88%AA%E5%9B%BE03.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090211/%E6%88%AA%E5%9B%BE01.jpg)

但是这个类有个问题。给窗体添加一个按钮让它执行下面的语句：

farmer.BagsOfFeed = 5  ；

再次运行你的程序。不点击新添加的按钮的话，看起来还好。但是点击了这个新添加的按钮，再点击Calculate按钮的话，输出结果将会告诉你你需要五袋饲料--
不管牛数设置为多少！

把Farmer类完全封装

问题就在于类不是完全封装的。你使用属性封装了NumerOfCows，但是BagsOfFeed还是public的。这是一个很普遍的问题。实际上，由于这个问题太
过于普遍，C#提供了一个方式来自动解决它。把BagsOfFeed字段改为自动属性就可以了。IDE使得添加自动属性很是简单。就这样做：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090211/%E6%88%AA%E5%9B%BE02.jpg)

①  把Farmer类中的BagsOfFeed字段删掉。把光标定位到字段原来所在地，并键入prop然后按tab键两次。IDE将会向你的代码添加这行代码：

public int MyProperty  { get  ； set； }

②  按tab键--光标将会定位到MyProperty。把它的名字改为BagsOfFeed：

public int BagsOfFeed  { get  ； set； }

现在你得到了一个代替字段的属性。C#对待这个，就会像对待后台字段一样（就像NumberOfCows属性后面的numberOfCows私有字段一样）。

③  这还不足以解决问题。有一个简单的解决方法--只读属性：

public int BagsOfFeed  { get  ；private set； }

重新生成代码--你将会在新添加的按钮设置BagsOfFeed的那一行代码处发现一个错误，它告诉你set访问器是私有的。现在Farmer类封装的好多了！



