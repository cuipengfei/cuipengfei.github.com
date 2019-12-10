---
title: Head First C# 中文版 图文皆译 第五章 封装 page198
date: 2009-02-18 09:11:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090218/2009-02-17_22-27-00.jpg) 我们来近距离的看看Farmer的构造方法，这有助于我们更好的了解它的运作。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090218/2009-02-17_22-30-22.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090218/2009-02-17_22-45-22.jpg)

问：构造方法可以没有参数吗？

答：可以。其实一个类的构造方法没有参数是很常见的。实际上，你已经见过一个例子了--你的窗体的构造方法。找一个窗体的构造方法的定义来看一看：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090218/2009-02-17_22-48-28.jpg)

这是你的窗体对象的构造方法。它虽然不接受参数，可是也做很多事儿。花一分钟打开Form1.Designer.cs来看看。找到InitializeCompone
nt（）方法。这个方法初始化窗体上的所有控件并给这些控件的所有属性赋值。如果你在IDE中拖拽一个控件到窗体上去并在属性窗口中修改一些属性，这些就会反映在In
itializeComponent（）方法中。InitializeComponent（）方法在窗体的构造方法中被调用，所以所有控件都在窗体创建时被初始化。

一个方法的参数名和某个字段同名时，它会遮盖住字段。

注意到构造方法的numberOfCows参数和NumberOfCows属性的后备字段同名了吗？所以如果想在构造方法中使用这个后备字段的话，需要写this.n
umberOfCows。



