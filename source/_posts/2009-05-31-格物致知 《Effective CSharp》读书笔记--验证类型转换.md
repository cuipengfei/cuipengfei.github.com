---
title: "格物致知 《Effective C#》读书笔记--验证类型转换"
date: 2009-05-31 19:02:00
tags: 格物致知 《Effective C#》读书笔记
---
as  看起来很好用的样子，不会抛出异常，只要根据返回值是否为  null  来做不同的处理就可以了。但是它也不是万金油，以下的情况  as  就不适用：



①值类型



比如：



double  d = 100.0;

int  valueType = d  as  int  ;



这两句代码就直接通不过编译，给出的原因是：  as  运算符必须用于引用类型或可以为  null  类型  (“int”  是一种不可以为  null
值的类型  )



想一下也合理，  as  在失败时会返回  null  ，而值类型是不可以为  null  的（  INullable  除外）。



由上面的出错信息也可以看出来，  as  只可以作用于引用类型。



但是也并非所有值类型都适用，存在于一条继承链上的自不必说。但是自定义类型转换呢？



②自定义类型转换



假设有两个类定义如下：



class  ClassOne

{

private  int  filedOne;

public  int  FiledOne

{

get  {  return  filedOne; }

set  { filedOne =  value  ; }

}

public  static  explicit  operator  ClassTwo  (  ClassOne  co)

{

return  new  ClassTwo  { FiledTwo = co.FiledOne };

}

}

class  ClassTwo

{

private  int  filedtwo;

public  int  FiledTwo

{

get  {  return  filedtwo; }

set  { filedtwo =  value  ; }

}

}



ClassOne  中定义了向  ClassTwo  的转换。



试用一下：



ClassOne  co =  new  ClassOne  { FiledOne=10};

ClassTwo  ct = (  ClassTwo  )co;



这样是没问题的，但是再写下面一句：



ct = co  as  ClassTwo  ;



就会报告：  无法通过引用转换、装箱转换、取消装箱转换、包装转换或  Null  类型转换将类型  “CastAsIs.ClassOne”  转换为
“CastAsIs.ClassTwo”



由此可见  as  对于自定义类型转换不感冒。



实际上，  foreach  内部也是使用的强制类型转换的，看下面：



ClassOne  [] arr =

{  new  ClassOne  { FiledOne = 1 },

new  ClassOne  { FiledOne = 2 } };



foreach  (  ClassTwo  item  in  arr)

{

Console  .WriteLine(item.FiledTwo.ToString());

}



这段代码编译和运行都没问题（把  arr  中的  ClassOne  强制转换为  ClassTwo  自然没问题），但是如果改写成下面这样：



foreach  (  ClassTwo  item  in  arr  as  IEnumerable  )

{

Console  .WriteLine(item.FiledTwo.ToString());

}



就会报告一个  InvalidCastException  ，原因就是  IEnumerator.Current  （）返回的是  Object
类型，而在  foreach  中试图将  Object  转化为  ClassTwo  就会出错。





[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+
