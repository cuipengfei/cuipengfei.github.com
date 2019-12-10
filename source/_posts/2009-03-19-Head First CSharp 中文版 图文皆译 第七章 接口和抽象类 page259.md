---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page259
date: 2009-03-19 22:07:00
tags: 我翻译的Head First C#（习作）
---
④  接口写出来是下面这样的--做对了吗？

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090319/2009-03-19_21-37-02.jpg)

现在让TallGuy类实现IClown接口。记住冒号后面首先写需要继承的类，然后才是接口，以逗号隔开。因为TallGuy不继承其它类，只实现一个接口，所以声
明语句如下：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090319/2009-03-19_21-45-52.jpg)

暂时不要对TallGuy类做别的修改，编译一下，你会看到下面的报错：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090319/2009-03-19_21-49-16.jpg)

⑤  把接口中定义的方法、属性写进类里面去，错误就会消失了。来实现接口吧。添加一个只读的string类型的属性，叫做FunnyThingIHave，它的ge
t访问器总是返回“big shoes”。然后添加一个Honk（）方法，它弹出一个写着“Honk honk”的消息框。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090319/2009-03-19_21-56-21.jpg)

⑥  现在你的代码可以编译了！修改你的按钮的事件响应方法，让对象初始化器设置FunnyThingIHave属性，并调用对象的Honk（）方法。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

