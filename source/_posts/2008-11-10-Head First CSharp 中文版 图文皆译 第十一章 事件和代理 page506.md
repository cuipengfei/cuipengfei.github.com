---
title: Head First C# 中文版 图文皆译 第十一章 事件和代理 page506
date: 2008-11-10 13:23:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/third0.jpg)

<?xml:namespace prefix = o ns = "urn:schemas-microsoft-com:office:office" />

金镶玉蟹

“  平脚板”亨利.霍奇金是一个寻宝者。他热衷于追寻水上最久负盛名的无价之宝：金镶玉蟹。但是还有很多其他的寻宝者。他们都在构造器里面声明了对同一个金镶玉蟹的
引用，但是亨利想要第一个声明占有它。

在一卷被偷的类关系图里面，亨利发现金镶玉蟹类一旦离人太近就触发一个RunForCover事件。更爽的是，这个事件包含NewLocationArgs，指明螃蟹
要去何方。但是其他的寻宝者都不知道这个事件，亨利觉得他要发财了。

亨利在他的代码里的构造器里把他的ListenForClues（）方法注册为他的螃蟹实例的RunForCover事件的事件处理器。然后他通过派一个手下过去，得
知了螃蟹会逃跑，躲藏，并触发事件--给了亨利的ListenForClues（）方法所需要的所有信息。

事情都照着计划进行，直到亨利按照新地点去抓螃蟹。他看见另外三个寻宝者在为争夺金镶玉蟹而大打出手，他彻底被雷到了。

其他寻宝者怎么会比亨利更早的找到金镶玉蟹呢？

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081110/third1.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

