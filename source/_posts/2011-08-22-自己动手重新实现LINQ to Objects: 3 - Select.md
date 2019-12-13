---
title: 自己动手重新实现LINQ to Objects 3 - Select
date: 2011-08-22 00:13:03
tags: LinQ
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文"Edulinq"。

本篇原文地址：

[http://msmvps.com/blogs/jon_skeet/archive/2010/12/23/reimplementing-linq-to-objects-part-3-quot-select-quot-and-a-rename.aspx](http://msmvps.com/blogs/jon_skeet/archive/2010/12/23/reimplementing-linq-to-objects-part-3-quot-select-quot-and-a-rename.aspx)


距离上次写完本系列博文的  [ 第一篇  ](http://msmvps.com/blogs/jon_skeet/archive/2010/09/03/reimplementing-linq-to-objects-part-1-introduction.aspx) 和  [ 第二篇](http://msmvps.com/blogs/jon_skeet/archive/2010/09/03/reimplementing-linq-to-objects-part-2-quot-where-quot.aspx) 已经有一段日子了，希望接下来的进度会快一些。

现在我给本项目在  [ Google Code  上建立了源码管理  ](http://edulinq.googlecode.com/)
，现在就无需每篇博文包含一个  zip  文件了。创建项目时，我给它取了个显而易见的名字，叫做  Edulinq  。我修改了代码中的命名空间，而且现在
[ 这一系列博文的  tag](http://msmvps.com/blogs/jon_skeet/archive/tags/Edulinq/default.aspx) 也修改为了
Edulinq  了。好了，闲话少叙  ...  我们来开始重新实现  LINQ  吧，这次要实现  Select  操作符。  

# Select  操作符是什么？

和  Where  类似，  [ Select  也有两个重载  ](http://msdn.microsoft.com/en-us/library/bb357126.aspx) ：

```
public static IEnumerable < TResult > Select < TSource, TResult > (this IEnumerable < TSource > source, Func < TSource, TResult > selector)

public static IEnumerable < TResult > Select < TSource, TResult > (this IEnumerable < TSource > source, Func < TSource, int, TResult > selector)
```

其第二个重载让投影操作可以访问到序列元素的  index  。

先说简单的东西：  Select  方法把一个序列 投影成为另一个序列：“  selector
”这个作为参数的委托会被依次应用到输入序列中的每一个元素上，并每次  yield  返回一个输出元素。  Select  的行为和  Where
很类似（实在是太类似了，以至于下面一段文字都是从上一篇文章中复制过来的，只是稍加修改）：  

+ Select  不会对输入序列做任何修改。

+ Select  是延迟执行的  -  在你开始读取输出序列中的元素之前，  Select  不会去输入序列中取元素。

+ 不过也有一点不是延迟执行的，它会立即检查参数是否为  null 。

+ 它以流式处理结果：它每次只处理一个结果元素。

+ 你每在输出序列上迭代一次，  Select  方法就会在输入序列上迭代一次，这二者是严格对应的。

+ 每次  yield  返回结果值的时候，“  selector  ”这个委托就会被调用一次。

+ 如果输出序列的迭代器被  Dispose  掉的话，对应的输入序列的迭代器也会被  Dispose  掉。  

# 我们要测试什么？

对  Select  的测试和对  Where  的测试也是很类似的，之前我们是针对  Where  的过滤功能来做测试，现在我们是针对  Select
的投影功能来做测试。

有几个测试比较有趣。首先，你会发现  Select  方法是泛型的，而且有两个泛型参数，分别是  TSource  和  TResult
。虽然这两个参数的含义不言自明，不过还是得写一个单元测试来测一下  TSource  和  TResult  分别为不同类型的情况，比如说把  int
转换成  string  的情况。  

```
[Test]
public void SimpleProjectionToDifferentType() {
 int[] source = {
  1,
  5,
  2
 };

 var result = source.Select(x => x.ToString());

 result.AssertSequenceEqual("1", "5", "2");
}  
```

然后我们看另一个测试，这个测试给我们展示了使用  LINQ  有可能会遇到的奇怪的副作用。其实我们本可以在  Where
的单元测试中做这个例子的，不过针对  Select  做起来更清晰一些：  
```
[Test]
public void SideEffectsInProjection() {
 int[] source = new int[3];
// Actual values won't be relevant _

 int count = 0;

 var query = source.Select(x => count++);

 query.AssertSequenceEqual(0, 1, 2);

 query.AssertSequenceEqual(3, 4, 5);

 count = 10;

 query.AssertSequenceEqual(10, 11, 12);
}  
```

请注意我们只调用了  Select  一次，但是对  Select  方法返回值的多次迭代结果都不同，这是因为“  count
”这个变量的值被保留住了并在每一次的投影过程中都会被修改。希望您不要写出这种代码。

再然后，我们可以写一些同时包含“  select  ”和“  where  ”的查询表达式：  
```
[Test]
public void WhereAndSelect() {
 int[] source = {
  1,
  3,
  4,
  2,
  8,
  1
 };

 var result = from x in source

 where x < 4

 select x * 2;

 result.AssertSequenceEqual(2, 6, 4, 2);
}
```

如果你用过  LINQ to Objects  的话，那么上面这些东西对你来说应该是很熟悉很亲切的，没有什么令人惊讶的。  

# 来动手实现吧！


我们实现  Select  的方式和实现  Where  的方式差不多。我只是把  Where
的实现的代码复制过来，稍加修改，这二者真的就是如此的相似。详细说来就是：  

+ 我们利用迭代器代码块来轻松实现序列的返回。

+ 要用到迭代器代码块就意味着必须要把参数校验的代码和核心实现代码分离开。（我写完上一篇博文之后了解到  VB11
中将会有匿名迭代器，匿名迭代器可以解决这个问题。哎。羡慕  VB  用户的感觉怪怪的，但是我会学着接受现实的。）

+ 我们在迭代器代码块中使用  foreach  ，这样就可以保证在输出序列的迭代器被  Dispose
时或者输入序列的元素被迭代完时，输入序列的迭代器可以被妥当的  Dispose  掉。  

由于  Select  的实现和  Where  的实现实在是太类似了，下面我直接给出代码。  Select  方法的重载（含有  index
的那一个）的实现代码就不展示了，因为它和下面的代码差别实在太小了。  
```
public static IEnumerable < TResult > Select < TSource, TResult > (this IEnumerable < TSource > source, Func < TSource, TResult > selector)

{
 if (source == null) {
  throw new ArgumentNullException("source");
 }

 if (selector == null) {
  throw new ArgumentNullException("selector");
 }

 return SelectImpl(source, selector);
}

private static IEnumerable < TResult > SelectImpl < TSource, TResult > (this IEnumerable < TSource > source, Func < TSource, TResult > selector) {

 foreach(TSource item in source) {
  yield
  return selector(item);
 }

}
```

很简单，对吧？真正用来实现功能的代码还没有参数校验的代码长呢。  

# 结论


虽然说我不想让我的读者感到无聊（你们中的有些人 可能会感到惊讶），但是我还是得承认本篇文章颇有些无趣。我重复的强调“和  Where
很类似”，强调了那么多次，搞得都有点乏味了，不过这样才足以说明实现  Select  并没有你可能想象的那么复杂。

下次（我希望就在几天之内）我会写点不一样的东西。我还不确定下次要写哪个方法，待选的方法还有很多  ...
