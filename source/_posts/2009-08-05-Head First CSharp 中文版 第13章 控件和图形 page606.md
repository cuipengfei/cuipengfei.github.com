---
title: Head First C# 中文版 第13章 控件和图形 page606
date: 2009-08-05 17:50:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090805/2009-08-05_17-26-05.jpg)

问：在  PhotoShop  中缩放图片不是更好吗？

答：如果你掌控着这些图片资源的话就可以。而实际上，这些图片有可能是只读的或者线上资源。

问：但是在  .NET  之外把它缩小总是更好的吧  ?

答：如果你确信你不会再次需要把它放大就可以，实际上把它放大比缩小麻烦多了。

问：我知道  CreateGraphics  （）方法用来获取在窗体上绘制的  Graphics  对象，那么  FromImage  （）方法是做什么的？

答：它用来获取在一个  Bitmap  上绘制用的  Graphics  对象。

问：那么说来，  Graphics  并不是只用来在窗体上绘制图形了？

答：  Graphics  可以用来在任何向你提供  Graphics  对象的控件上绘制图形。比如  Button  ，  Label
，等等。它们都含有  CreateGrphics  （）方法。

问：我以为只有使用流的时候才会用到  using  语句，绘制图形为什么要用它呢？

答：  using  语句用来处理任何实现了  IDisposable  接口的类的对象。使用流的时候，  using
语句可以确保所有文件都被关闭。绘制图形的时候，也可以确保  Graphics  ，  Pen  ，  Brush  等资源可以被释放。

问：如果我要创建一个新控件，应该继承自某个已存在控件呢？还是应该使用  UserControl  呢？

答：这取决于你想要这控件做什么。如果它的功能和某个已存在的控件类似的话，就继承一个已存在的控件，否则，多数情况下会使用用户控件。

用户控件可以承载其他控件，IDE的窗体设计器允许你把别的控件拖拽到用户控件上面去。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

