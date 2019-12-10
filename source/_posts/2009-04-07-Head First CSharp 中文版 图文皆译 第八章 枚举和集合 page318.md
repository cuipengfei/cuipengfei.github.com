---
title: Head First C# 中文版 图文皆译 第八章 枚举和集合 page318
date: 2009-04-07 09:02:00
tags: 我翻译的Head First C#（习作）
---
List  比数组更灵活

List  类是内建在.NET Framework中的，它让你可以做很多用简单老式的数组做不到的事情。看看你用List可以做的事情吧。

  

①  你可以创建一个List对象

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090407/2009-04-07_08-49-09.jpg)

List<Egg> myCarton = new List<Egg>();

  

②  向其中添加东西

  

Egg x= new Egg();

myCarton.Add(x);

  

③  再次向其中添加东西

  

Egg y= new Egg();

myCarton.Add(y);

  

④  查明自身中包含多少元素

  

Int theSize = myCarton.Count;

  

⑤  查明是否包含某个特定项

  

Bool Isin = myCarton.Contains(x);

  

⑥  找出特定元素的位置

  

Int idx = myCarton.IndexOf(y);

  

⑦  查明列表可以包含多少元素  ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei
1/EntryImages/20090407/2009-04-07_08-56-06.jpg)

  

Int limit = myCarton.Capacity;

  

⑧  从中取出元素

  

myCarton.Remove(y);



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

