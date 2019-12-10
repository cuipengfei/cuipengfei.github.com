---
title: Head First C# 中文版 第13章 控件和图形 page601
date: 2009-08-04 09:33:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090804/2009-08-04_09-13-20.jpg) Graphics  可以解决我们的透明度问题

你可能已经想出来了，  DrawImage  （）方法是解决重叠问题的关键。我们来解决问题吧！

①  添加一个  DrawBee  （）方法，它用来使用任何  Graphics  对象来绘制一只蜜蜂。它使用  DrawImage
（）方法的重载版本，它接受一个  Rectangle  来定义在何处绘制及其尺寸。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090804/2009-08-04_09-13-06.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090804/2009-08-04_09-19-34.jpg) ②  下面是窗体的新的  Click  事件处理器。它把蜂巢的左上角绘制在窗体之外，位置在（
-Width  ，  -Height  ）。并用  DrawBee  （）方法绘制四只蜜蜂。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090804/2009-08-04_09-25-14.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090804/2009-08-04_09-30-50.jpg) ③  ...  但是有一个问题

当你把窗体拖拽出屏幕再拖拽回来的时候，图像消失了！回到你原来写的那个“  Nectar here  ”程序，也有一样的问题！

你认为有什么问题？



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

