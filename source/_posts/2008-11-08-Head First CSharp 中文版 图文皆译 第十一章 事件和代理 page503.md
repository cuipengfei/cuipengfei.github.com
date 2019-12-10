---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page503
date: 2008-11-08 22:07:00
tags: 我翻译的Head First C#（习作）
---
<?xml:namespace prefix = o ns = "urn:schemas-microsoft-com:office:office" />

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081108/%E6%88%AA%E5%9B%BE00633617788299445000.jpg) ![](https://p-blog.csdn.net/
images/p_blog_csdn_net/cuipengfei1/EntryImages/20081108/%E6%88%AA%E5%9B%BE0163
3617788300382500.jpg)

你有一个强大的工具--IDE的调试器--它会帮你理解委托是如何运作的。做下面的步骤：

*  先运行程序。点击“Get the ingredient”按钮--它会向控制台输出“I don't have a secret ingredient”。 

*  点击“Use Suzanne's delegate”按钮--它会把窗体的ingredientMethod字段（也就是GetSecretIngredient委托的实例）赋值为Suzanne的GetSecretIngredient属性的返回值。这个属性返回GetSecretIngredient类型的指向SuzannesSecretIngredient（）方法的实例。 

*  再次点击“Get the ingredient”按钮。现在窗体的ingredientMethod字段指向了SuzannesSecretIngredient（），于是就调用它，传递NumericUpDown控件的值作为参数并把输出写到控制台。 

*  点击“Use Amy's delegate”按钮。它会用Amy.GetSecretIngredient属性来给窗体的ingredientMethod字段赋值，使之指向AmysSecretIngredient（）方法。 

*  再点击“Get the ingredient”，现在它调用Amy的方法。 

*  现在用调试器来看看究竟发生了什么。在窗体的三个方法的第一行都加一个断点。然后再次开启程序（这会重新给ingredientMethod赋值，使之等于空），在做上面这五个步骤。用调试器的单步（F11）特性来一步一步的跑这个程序的每一行。看看你点击“Get the ingredient”。它会进入Suzanne或Amy类，这取决于ingredientMethod字段指向哪个方法。 



