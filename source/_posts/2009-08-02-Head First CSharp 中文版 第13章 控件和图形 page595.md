---
title: Head First C# 中文版 第13章 控件和图形 page595
date: 2009-08-02 10:11:00
tags: 我翻译的Head First C#（习作）
---
半分钟简介  GDI+

  

创建了  Graphics  对象之后，你就可以绘制各种的图形和图像。你只需要调用它的方法，就可以绘制到创建  Graphics  的对象上。

  

①  第一步骤总是要获取一个  Graphics  对象。可以通过窗体的  CreateGraphics  （）方法来获得。记住，  Graphics
实现了  IDisposable  接口，所以如果你创建  Graphics  对象，请使用  using  语句。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_09-48-34.jpg)

②  如果你想要画线，调用  DrawLine  （）方法，参数为用  X  和  Y  坐标代表的起始点和结束点：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_09-51-15.jpg)

或者用两个  Point  作为参数也可以：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_09-52-50.jpg)

③  如下的代码绘制一个填充的石灰色的矩形，并给它绘制一个天蓝色的边框。它使用一个  Rectangle  来定义绘制区域  \--  在这儿，左上角是（
150  ，  15  ），宽为  140  ，高为  90\.

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_09-57-40.jpg)

④  可以使用  DrawCircle  （）或者  FillCircle  （）方法来绘制椭圆或者圆，这两个方法也使用  Rectangle
来指明图形的大小。下面的代码绘制两个椭圆，它们稍有交错，呈现阴影的效果：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_10-01-01.jpg)

⑤  使用  DrawString  （）方法可以绘制任何字体和颜色的文本。使用这个方法，需要创建一个  Font  对象。它实现了
IDisposable  ，要使用  using  语句：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090802/2009-08-02_10-05-18.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

