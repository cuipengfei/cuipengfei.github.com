---
title: 自己动手重新实现LINQ to Objects 12 - DefaultIfEmpty
date: 2012-04-10 11:57:25
tags: linq
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ http://msmvps.com/blogs/jon_skeet/archive/2010/12/29/reimplementing-linq-to-
objects-part-12-defaultifempty.aspx
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/29/reimplementing-linq-to-
objects-part-12-defaultifempty.aspx)

上次实现  First  /  Last的时候写了大量的代码，相比起来，今天要讲的  DefaultIfEmpty  就轻松多了。

** DefaultIfEmpty是什么？ **

这个操作符虽然简单，但是还是有  [ 两个重载  ](http://msdn.microsoft.com/en-
us/library/bb360530.aspx) ：

public  static  IEnumerable<TSource> DefaultIfEmpty<TSource>(  
this  IEnumerable<TSource> source)  
  
public  static  IEnumerable<TSource> DefaultIfEmpty<TSource>(  
this  IEnumerable<TSource> source,  
TSource defaultValue)

这个操作符的行为很容易描述：

l  如果输入序列是空序列的话，那么返回序列中仅包含一个默认值。这个默认值在第一个重载中是  default  (T)  ，在第二个重载中是第二个参数的值。

l  如果输入序列不是空序列的话，那么输出序列和输入序列相同。

l  输入序列不能为  null  ，这个参数检验是立即执行的。

l  输出序列是延迟执行的  \--  除非读取输出序列，否则输入序列不会被读取。

l  输入序列是流式处理的；所有被读取的值都是立即  yield  返回的；没有缓存。

非常简单。

** 我们需要测试些什么？ **

虽然天有点晚了，但是我还是决定要对参数检验进行测试  \--
这件事其实不可小视，我第一次试着把参数检验的代码和真正迭代的代码分开到两个方法的尝试就失败了！由此可见，疏忽是多么容易出现的事啊。

除了参数检验外，我只找到四个值得测试的地方：

l  不接受默认值参数的重载，输入序列为空序列的情况

l  接受默认值参数的重载，输入序列为空序列的情况

l  不接受默认值参数的重载，输入序列不为空序列的情况

l  接受默认值参数的重载，输入序列不为空序列的情况

这些就是所有的测试用例。我没有测试流式处理，惰性求值，等等。

** 来动手实现吧！ **

虽然我不愿意依赖于一个操作符来实现另一个操作符，但是这里这两个操作符之间的关系实在是太明显了，所以我决定就破例一次。我甚至给参数检验实施了  DRY
的原则，但是实现还是只有这么简短：

public  static  IEnumerable<TSource> DefaultIfEmpty<TSource>(  
this  IEnumerable<TSource> source)  
{  
// This will perform an appropriate test for source being null first.  
return  source.DefaultIfEmpty(  default  (TSource));  
}  
  
public  static  IEnumerable<TSource> DefaultIfEmpty<TSource>(  
this  IEnumerable<TSource> source,  
TSource defaultValue)  
{  
if  (source ==  null  )  
{  
throw  new  ArgumentNullException(  "source"  );  
}  
return  DefaultIfEmptyImpl(source, defaultValue);  
}  
  
private  static  IEnumerable<TSource> DefaultIfEmptyImpl<TSource>(  
IEnumerable<TSource> source,  
TSource defaultValue)  
{  
bool  foundAny =  false  ;  
foreach  (TSource item  in  source)  
{  
yield  return  item;  
foundAny =  true  ;  
}  
if  (!foundAny)  
{  
yield  return  defaultValue;  
}  
}

现在有人应该会发现一个  bug  ...

除了在较简单的重载中使用  default  (TSource)
来调用较复杂的重载外，唯一有点意思的就是最下面的那个方法了。这个方法让我感觉有些不爽，因为它会在每次迭代时都重新给  foundAny  赋值为  true
...  但是，如果不这么做的话，那实现起来也很难看：

private  static  IEnumerable<TSource> DefaultIfEmptyImpl<TSource>(  
IEnumerable<TSource> source,  
TSource defaultValue)  
{  
using  (IEnumerator<TSource> iterator = source.GetEnumerator())  
{  
if  (!iterator.MoveNext())  
{  
yield  return  defaultValue;  
yield  break  ;  // Like a "return"  
}  
yield  return  iterator.Current;  
while  (iterator.MoveNext())  
{  
yield  return  iterator.Current;  
}  
}  
}

这种实现或许会稍微高效一点，但是看起来很笨拙。我们可以把  yield break  后面的代码放到  else  里面，这样就可以去掉  yield
break  这一句了，但是这种方法我也不是很喜欢。我们可以用  do  /while  循环来替换掉  while  循环，那样可以避免“  yield
return iterator  .Current  ”的重复出现。但是我也不是很喜欢  do/while  循环。我很少用  do/while
，以至于我读  do/while  的代码时要稍微费点力。

** 结论 **

除了最后一部分缺少优雅性而让人有点不爽之外，其他地方都没什么有趣的。不过，我们现在可以通过  DefaultIfEmpty  来实现
FirstOrDefault/LastOrDefault  和  SingleOrDefault  了。比如说，下面是  FirstOrDefault
的实现：

public  static  TSource FirstOrDefault<TSource>(  
this  IEnumerable<TSource> source)  
{  
return  source.DefaultIfEmpty().First();  
}  
  
public  static  TSource FirstOrDefault<TSource>(  
this  IEnumerable<TSource> source,  
Func<TSource,  bool  > predicate)  
{  
// Can't just use source.DefaultIfEmpty().First(predicate)  
return  source.Where(predicate).DefaultIfEmpty().First();  
}

请注意有谓词的重载中的注释，对  DefaultIfEmpty  的调用需要放在谓词的执行之后  ...  不然的话，如果我们传入一个空序列和一个不能让
default  (TSource)  通过的谓词，那就会触发一个异常，而得不到默认值了。  LastOrDefault  和
SingleOrDefault  也可以通过类似的方式来实现。

我现在不确定下次要实现哪个操作符。我明早看看会不会有哪个特殊的方法让我获得灵感。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

