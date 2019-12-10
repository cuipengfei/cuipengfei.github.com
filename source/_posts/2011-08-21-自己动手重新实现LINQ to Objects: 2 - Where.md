---
title: 自己动手重新实现LINQ to Objects 2 - Where
date: 2011-08-21 00:20:14
tags: linq
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ _ http://msmvps.com/blogs/jon_skeet/archive/2010/09/03/reimplementing-linq-
to-objects-part-2-quot-where-quot.aspx _
](http://msmvps.com/blogs/jon_skeet/archive/2010/09/03/reimplementing-linq-to-
objects-part-2-quot-where-quot.aspx)  

** **

提示：本篇文章较长。虽然我选择了一个比较简单的操作符来在本文中实现，不过我们还是会遇到一些特例以及一些与  LINQ
相关的原则。因为我还在试着找出表现本文内容的最佳方式，所以本文的排版方式暂时是实验性的。

我们将要实现“  Where  ”子句（也可以说是方法或操作符）。  Where  在总体上来说比较容易理解，但是涉及到延迟执行和流式处理的部分会有些麻烦。
Where  方法是泛型的，不过只有一个类型参数（在我看来这很重要，因为我觉得一个方法的泛型参数越多就越令人难以理解）。哦，对了，我们将在本文开始涉及查询表
达式，这算是本文的一点额外猛料。  

** Where  是什么？   
**

Where  有两个重载：  

public  static  IEnumerable<TSource> Where(

this  IEnumerable<TSource> source,

Func<TSource,  bool  > predicate)

public  static  IEnumerable<TSource> Where(

this  IEnumerable<TSource> source,

Func<TSource,  int  ,  bool  > predicate)


在开始讲述  Where  方法到底做什么之前，我先指出几点  LINQ  操作符的常识，这些常识适用于几乎所有的  LINQ  操作符：  

l  LINQ  操作符都是  [ 扩展方法  ](http://msdn.microsoft.com/en-
us/library/bb383977.aspx) \-  扩展方法要定义在顶层的，非嵌套的静态类型中而且其第一个参数要带有“  this
”修饰符。简单来说，扩展方法可以被其第一个参数的实例调用，就好像它是该参数类型的实例方法一样。

l  LINQ  操作符是  [ 泛型方法  ](http://msdn.microsoft.com/en-
us/library/twcad0zb.aspx) \-  我们要讲的  Where  操作符只有一个叫做  TSource
的类型参数，该类型参数指明了要处理的序列的类型。比如说，如果要处理一个  string  的序列，  TSource  就是  string  。

l  LINQ  操作符接受  Func<...> 这一族的泛型委托作为参数，通常以  [ lamdba  表达式
](http://msdn.microsoft.com/en-us/library/bb397687.aspx)
的方式提供，不过委托的其他表现形式也都可以作为其参数。

l  LINQ  操作符处理序列。序列以  [ IEnumerable<T> ](http://msdn.microsoft.com/en-
us/library/9eekhta0.aspx) 的形式出现，  IEnumerable<T> 中含有一个类型为  [ IEnumerator<T>
](http://msdn.microsoft.com/en-us/library/78dfe2yb.aspx) 的迭代器。


我希望本文的读者对以上提及的概念有所了解，所以我就不再深入解释了。如果您对上述内容不够熟悉的话，请在继续读下去之前先去做些功课，否则接下来的内容将让您很难理
解。

Where  的目的是去过滤一个序列。它接受一个输入序列及一个  _ 谓词 _
作为参数，返回一个结果序列。输出序列和输入序列的元素类型相同（也就是说如果输入是一个  string  的序列，输出也会是个  string
的序列），输出序列中只会包含输入序列中符合谓词条件的元素。（输入序列中的元素会依次被谓词检验。只有谓词返回  true
时，一个元素才会被包含在输出序列中。）

下面是关于  Where  的几个重要的细节：  

l  Where  不会对输入序列做任何修改：它和  [ List<T>.RemoveAll  ](http://msdn.microsoft.com
/en-us/library/wdka673a.aspx) 之类的方法不一样。

l  Where  是延迟执行的  \-  在你开始读取输出序列中的元素之前，  Where  不会去输入序列中取元素。

l  不过也有一点不是延迟执行的，它会立即检查参数是否为  null  。

l  它以流式处理结果：它每次只处理一个结果元素，它把结果元素  yield  返回而且不会保留其引用。这意味着你可以把  Where
应用在一个无限长的序列上（比如说一个随机数的序列）。

l  你每在输出序列上迭代一次，  Where  方法就会在输入序列上迭代一次。

l  如果输出序列的迭代器被  Dispose  掉的话，对应的输入序列的迭代器也会被  Dispose  掉。（  C#  中的  foreach
语句会用  try/finally  来保障迭代器总是会被  Dispose  调，无论循环是因何种原因结束的。）


以上几点之中的有些对其它的操作符也适用。

Where  的一个重载形式会接受一个  Func<TSource, int, bool> 作为参数，此重载让谓词中不仅可以访问元素值，还可以访问元素的
index  。  Index  总是从  0  开始并且每次递增  1  ，无论之前谓词的结果如何。  

** 我们要测试些什么？   
**

理想情况下，我们要测试上述所有的东西。但是不幸的是，流式处理和序列被迭代多少次的细节测试起来很是麻烦。考虑到我们还要实现那么多的东西，我们以后再去测试那些。

我们来看看一些测试。首先，看一个“正向”测试  \-  有一个整数数组，我们用一个  lambda  表达式来使得输出结果中仅包含小于  4
的元素。（“过滤”这个词无处不在，这真是很不幸。“过滤掉”这个说法比“包含”要好理解得多，但是实际上谓词就是以“正向”的方式来处理的。）  

[Test]

public  void  SimpleFiltering()

{

int  [] source = {  1  ,  3  ,  4  ,  2  ,  8  ,  1  };

var result = source.Where(x => x < 4  );

result.AssertSequenceEqual(  1  ,  3  ,  2  ,  1  );

}  

虽然  NUnit  中已经有了  CollectionAssert  ，我还是在用  MoreLINQ  中的  TestExtension
。有三个原因让我觉得  MoreLINQ  的扩展方法更好用：  

l  扩展方法有助于减轻代码的混乱程度。

l  可以使用变长数组来表示期望的输出，这样更易于表达测试的意图。

l  断言失败的提示信息更清楚。  

AssertSequenceEqual  所做的事情看名字就可以猜出来，它检查输出序列（通常就是你调用  AssertSequenceEqual
方法时所使用的那个变量）和期望的序列（通常就是作为参数传入的变长数组）是否匹配。

目前为止进行的还不错。现在来看看参数校验吧：  

[Test]

public  void  NullSourceThrowsNullArgumentException()

{

IEnumerable< int  > source = null;

Assert.Throws<ArgumentNullException>(() => source.Where(x => x > 5  ));

}

[Test]

public  void  NullPredicateThrowsNullArgumentException()

{

int  [] source = {  1  ,  3  ,  7  ,  9  ,  10  };

Func< int  ,  bool  > predicate = null;

Assert.Throws<ArgumentNullException>(()=> source.Where(predicate));

}  

我就不再费劲去检查  ArgumentNullException
里面的参数名字了，但是我要测试参数是不是立即被校验的，这一点很重要的。我没有迭代输出结果，所以如果参数校验是延迟执行的，这两个测试将不能通过。

最后还有一个有趣的测试也是与延迟执行有关的。我们将用一个叫做  ThrowingEnumerable  的  helper
类来做这个测试，这个类是一个序列，你一旦迭代它，它就会抛出一个  InvalidOperationException  。这个测试是想要测试两点：  

l  仅仅调用  Where  不会开始迭代输入序列。

l  调用  GetEnumerator()  来获取迭代器，然后再调用迭代器的  MoveNext()  的话，就开始迭代了，这就会导致一个异常被抛出。  

对其它的操作符我们也需要做类似的测试，所以我在  ThrowingEnumerable  里写了一个  helper  方法：  

internal  static  void  AssertDeferred<T>(

Func<IEnumerable< int  >, IEnumerable<T>> deferredFunction)

{

ThrowingEnumerable source =  new  ThrowingEnumerable();

var result = deferredFunction(source);

using  (var iterator = result.GetEnumerator())

{

Assert.Throws<InvalidOperationException>(() => iterator.MoveNext());

}

}  

现在我们就可以测试  Where  是不是延迟执行的了：  

[Test]

public  void  ExecutionIsDeferred()

{

ThrowingEnumerable.AssertDeferred(src => src.Where(x => x > 0  ));

}  

以上所示的都是对  Where  的简单重载的测试，也就是那个谓词只能访问元素值而不能访问元素  index  的重载。能够访问  index
的那个重载的测试与上述测试非常类似。  

** 来动手实现吧！   
**

原版的  LINQ to Objects  能够通过所有这些测试，现在来实现我们自己的代码吧。我们将会用到  [ 迭代器代码块
](http://msdn.microsoft.com/en-us/library/dscyy5s0.aspx) ，它在  C# 2  中被引入来简化
IEnumerable<T> 的实现。如果你想了解更多的背景知识的话，我有  [ 几篇
](http://csharpindepth.com/Articles/Chapter11/StreamingAndIterators.aspx) [ 文章
](http://csharpindepth.com/Articles/Chapter6/IteratorBlockImplementation.aspx)
你可以去读一下  ...  或者读  C# in Depth  （第一或第二版都可以）的第六章也可以。迭代器代码块让我们可以很简单的实现延迟执行  ...
不过它也是一把双刃剑，我们马上就会体会到了。

Where  的核心部分是这样的：  

// Naive implementation

public  static  IEnumerable<TSource> Where<TSource>(

this  IEnumerable<TSource> source,

Func<TSource,  bool  > predicate)

{

foreach (TSource item in source)

{

if  (predicate(item))

{

yield  return  item;

}

}

}  

很简单，是吧？用迭代器代码块写出来的代码就和用自然语言描述起来差不多：迭代输入序列中的每一个元素，如果谓词在一个元素上返回  true
的话，这个元素就可以被  yield  （也就是包含）到输出序列中去。

诸位请看，有一些单元测试已经可以通过了。现在我们只需要参数校验了。参数校验很简单的，对吧？我们来试试看：  

// Naive validation - broken!

public  static  IEnumerable<TSource> Where<TSource>(

this  IEnumerable<TSource> source,

Func<TSource,  bool  > predicate)

{

if  (source == null)

{

throw  new  ArgumentNullException(  "source"  );

}

if  (predicate == null)

{

throw  new  ArgumentNullException(  "predicate"  );

}

foreach (TSource item in source)

{

if  (predicate(item))

{

yield  return  item;

}

}

}  

呃。测试亮起了红灯，通不过，在“  throw  ”的那一句上设断点也没用  ...  断点根本就执行不到。怎么回事儿？

我之前已经给出过很明显的提示了。导致问题的就是延迟执行。在返回值被迭代之前，  _ 我们的代码不会被执行 _ 。我们的代码故意的  _ 没有 _
去迭代返回值，所以参数校验也不会被执行。

我们遇到了一个  C#  设计上的缺陷。  C#  中的迭代器代码块不能很好的对“立即执行”（通常用来做参数校验）和“延迟执行”作出分离。我们必须得把我们上
述的实现分为两个方法：第一个方法做参数校验，第二个方法含有迭代器代码块，用来实现延迟执行，第一个方法会调用第二个方法：  

public  static  IEnumerable<TSource> Where<TSource>(

this  IEnumerable<TSource> source,

Func<TSource,  bool  > predicate)

{

if  (source == null)

{

throw  new  ArgumentNullException(  "source"  );

}

if  (predicate == null)

{

throw  new  ArgumentNullException(  "predicate"  );

}

return  WhereImpl(source, predicate);

}

private  static  IEnumerable<TSource> WhereImpl<TSource>(

this  IEnumerable<TSource> source,

Func<TSource,  bool  > predicate)

{

foreach (TSource item in source)

{

if  (predicate(item))

{

yield  return  item;

}

}

}  

这样的代码很丑陋，但是能用：所有的针对于  Where  的简单重载（不含有  index  ）的测试都可以通过了。有了现在的基础，要实现  Where
的含有  index  的重载也就很简单了：  

public  static  IEnumerable<TSource> Where<TSource>(

this  IEnumerable<TSource> source,

Func<T,  int  ,  bool  > predicate)

{

if  (source == null)

{

throw  new  ArgumentNullException(  "source"  );

}

if  (predicate == null)

{

throw  new  ArgumentNullException(  "predicate"  );

}

return  WhereImpl(source, predicate);

}

private  static  IEnumerable<TSource> WhereImpl<TSource>(

this  IEnumerable<TSource> source,

Func<TSource,  int  ,  bool  > predicate)

{

int  index =  0  ;

foreach (TSource item in source)

{

if  (predicate(item, index))

{

yield  return  item;

}

index++;

}

}  

现在所有单元测试都通过了，我们的实现完成了。不过等一下  ...  我们还没有无所不用其极的使用  Where  呢。  

** 查询表达式   
**

到目前为止，我们都是在直接的调用  Where  方法（尽管是以扩展方法的形式出现的），不过  LINQ  可是还给我们提供了查询表达式的。下面是“
SimpleFiltering  ”那个测试的重写版本，其中用到了查询表达式：  

[Test]

public  void  QueryExpressionSimpleFiltering()

{

int  [] source = {  1  ,  3  ,  4  ,  2  ,  8  ,  1  };

var result = from x in source

where x < 4

select x;

result.AssertSequenceEqual(  1  ,  3  ,  2  ,  1  );

}  

（本博文中出现的方法名和下载到的代码中的不同，因为方法名中含有博客服务器的敏感词。呃。）

以上代码会和我们先前的测试产出  _ 完全相同 _ 的  IL  代码。编译器会把这种查询表达式的形式转译成调用方法的形式，并把用  lambda
表达式写出来的条件判断（  x < 4  ）转换成一个委托。你可能会感到有点惊讶，因为我们还没有实现  Select  方法呢  ...
不过我们现在用到的  select
投影操作实际上是不做任何事情的；我们并没有做任何真正的投影变换。这种情况下，只要查询表达式中含有任意其他的查询（上述代码中，这个查询就是  Where
）在内，编译器就会把“  select  ”从句忽略掉，这样的话我们没有实现  select  也就无关紧要了。如果你把“  select x  ”改写成“
select x * 2  ”的话，将无法通过编译，因为我们的  LINQ  实现中只有  Where  。

查询表达式是基于上述这种模式的，这一强大的特性使得它极具灵活性。举例来说，  LINQ to Rx
就是基于这一点才能做到仅需实现对其应用场景有意义的操作符的。与此类似，  C#  编译器在处理查询表达式的时候并不需要知晓任何与
IEnumerable<T> 有关的东西，也正是如此，像  IObservable<T> 这样的完全与  IEnumerable<T>
无关的接口也可以得以应用。  

** 我们学到了什么？   
**

本文中有不少不太好理解的东西，其中与我们的实现和  LINQ  核心原则有关的是：  

l  LINQ to Objects  是基于扩展方法，委托还有  IEnumetable<T> 的。

l  条件允许的话，  LINQ  操作符会尽量利用延迟执行和流式处理。

l  LINQ  操作符不会改变输入序列，而是会返回一个包含符合条件的元素的新序列。

l  查询表达式基于编译器对一些模式的解释；你要用到的查询表达式和哪些模式相关，你就只需要实现那些模式就可以了，无需多劳。

l  迭代器代码块很适合用来实现延迟执行  ...

l  ...  但是它也使得需要立即执行的参数校验变得很难搞。  

** 代码下载   
**

[ Linq-To-Objects-2.zip  ](http://yoda.arachsys.com/blogfiles/csharp/l2o/Linq-
To-Objects-2.zip)

很多人要求给本项目建一个源码管理服务器，这件事正在进行中；大概下一篇博文之前就可以完成。

  * [ 点赞  1  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+
