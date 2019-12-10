---
title: Head First C# 中文版 第13章 控件和图形 page597
date: 2009-08-03 09:33:00
tags: 我翻译的Head First C#（习作）
---
⑤添加一个指向花朵的箭头

  

Graphics  有一些方法接受  Point  数组，并用直线或者曲线把它们连接起来。我们将使用  DrawLines  （）方法来绘制箭头，用
DrawCurve  （）方法绘制箭杆。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090803/2009-08-03_09-16-48.jpg)

⑥添加一个字体来绘制文本

  

绘制文本的时候，首先需要创建  Font  对象。  Font  实现了  IDisposable  接口，所以需要使用  using  语句。  Font
有多个重载过的构造方法  \--  最简单的一个接受字体名，字体大小和一个  FontStyle  枚举。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090803/2009-08-03_09-23-50.jpg)

⑦添加写着“  Nectar here  ”的文本

  

你已经有一个字体了，现在就可以搞明白绘制的文本的尺寸有多大了。  MeasureString  （）方法返回一个  SizeF
，它定义了尺寸。由于我们知道箭头的结尾点，我们可以把文本的中心绘制到箭头正上方。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090803/2009-08-03_09-30-11.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090803/2009-08-03_09-31-16.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

