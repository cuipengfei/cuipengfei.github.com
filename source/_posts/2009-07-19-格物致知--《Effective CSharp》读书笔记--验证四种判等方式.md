---
title: "格物致知--《Effective C#》读书笔记--验证四种判等方式"
date: 2009-07-19 22:47:00
tags: undefined
---
原地址在我的CSDN
Blog：http://blog.csdn.net/cuipengfei1/archive/2009/07/19/4362245.aspx

①  Object  的静态方法  ReferenceEquals  ：



只适用于判断两个引用是否指向同一个实例，不适用于值类型（或者说用于值类型是没意义的，因为永远返回  false  ）。如下：



TestEqual  te =  new  TestEqual  ();

bool  b1=  object  .ReferenceEquals(te,te);

bool  b2 =  object  .ReferenceEquals(1, 1);



b1  为  true  ，  b2  为  false  。  b2  为  false  的原因是两个整型值  1  装箱之后是两个不同的
Object  实例。



②  Object  中定义的实例级虚方法  Equals  ：



其  默认  行为是判断引用是否相等，引用类型从  Object  中继承了这一行为，如下：



TestEqual  te1=  new  TestEqual  ();

TestEqual  te2 =  new  TestEqual  ();

bool  b4= te1.Equals(te2);

bool  b5 = te1.Equals(te1);

Console  .WriteLine(b4);

Console  .WriteLine(b5)  ;



输出结果很明显第一个假，第二个真。



但是对于值类型来说，由于  ValueType  重写了这个方法，所以其行为不同，可以判断值是否相同，而不是引用。



如下：



int  num1 = 15;

int  num2 = 15;

bool  b3 = num1.Equals(num2);

Console  .WriteLine(b3);



虽然  num1  和  num2  是两个变量，但是只要它们的值一样，  b3  结果就为  true  。



③  = =  运算符：



应用于值类型的时候其行为是判断值是否相等。应用于引用类型的时候判断引用是否相同。



④  Object  中的静态方法  Equals  ：



它接受两个  Object  类型的参数，它会调用第一个参数的实例级  Equals
方法，以第二个参数作为该方法的参数来进行判等。所以其行为表现出来和实例级的  Equals
方法一样。只是其内部添加了对于两个参加判等的参数是否本身已经是同一个引用的判断，还有两个参数是否为  null  的判断。





2009  年  7  月  19  日  22:35:15  于  SWPU 19#625





[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+
