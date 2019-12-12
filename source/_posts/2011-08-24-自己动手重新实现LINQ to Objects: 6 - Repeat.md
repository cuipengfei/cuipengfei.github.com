---
title: 自己动手重新实现LINQ to Objects 6 - Repeat
date: 2011-08-24 22:27:15
tags: LinQ
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ _ http://msmvps.com/blogs/jon_skeet/archive/2010/12/24/reimplementing-linq-
to-objects-part-6-repeat.aspx _
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/24/reimplementing-linq-to-
objects-part-6-repeat.aspx)  
  

**   
**

本文的主题是个无关紧要的方法，  Repeat  。关于  Repeat  ，值得讨论的内容比  Empty
还要少。写这篇博文只是为了保证这个系列的完整性。

**   
Repeat  是什么？  **

  
Repeat  是一个静态的泛型方法，不是扩展方法，它只有一个签名形式：

```
public  static  IEnumerable<TResult> Repeat<TResult>(TResult element,int  count)
```
  
它返回一个序列，该序列中反复的包含“  count  ”个指定的元素，。  Repeat  只需要一个参数校验：检验“  count  ”不是负数。

**   
我们要测试什么呢？ **

  
需要测试的东西真的不多。我只想到了四个场景：  

l  一个简单的测试，把一个字符串重复三次

l  一个空序列（把一个元素重复  0  次）

l  一个多次包含  null  的序列（仅仅是为了证明“  element  ”可以为  null  ）

l  用负数作为“  count  ”来证明参数校验会被执行，而且是立即执行的

  
以上这几点恐怕都不怎么令人兴起。

**   
来动手实现吧！ **

  
在实现的时候我们唯一有可能做错的事就是把参数校验的代码和迭代器代码块写到一起。不过我们已经多次的做过“分割实现”了，所以我们肯定不会犯这个错误的。下面的代码
就是乏善可陈的  Repeat  方法的全部了：

```
public static IEnumerable < TResult > Repeat < TResult > (TResult element, int count) {

 if (count < 0) {

  throw new ArgumentOutOfRangeException("count");

 }

 return RepeatImpl(element, count);
}

private static IEnumerable < TResult > RepeatImpl < TResult > (TResult element,
 int count) {

 for (int i = 0; i < count; i++) {

  yield
  return element;

 }

}
```
  
这就是今天的全部内容了。其中值得指出的部分就是  ...  没有什么值得指出的。

**   
结论 **

  
其实没有什么结论可写。下一次我们讲  Count  和  LongCount  ，那里面倒是有些有趣的东西可以细说。



