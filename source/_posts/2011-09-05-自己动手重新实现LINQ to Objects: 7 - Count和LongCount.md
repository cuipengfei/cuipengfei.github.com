---
title: 自己动手重新实现LINQ to Objects 7 - Count和LongCount
date: 2011-09-05 12:25:38
tags: LinQ
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ _ http://msmvps.com/blogs/jon_skeet/archive/2010/12/26/reimplementing-linq-
to-objects-part-7-count-and-longcount.aspx _
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/26/reimplementing-linq-to-
objects-part-7-count-and-longcount.aspx)  
  
  

今天的文章要介绍两个  LINQ  操作符，因为它们实在是太类似了，所以放到一起来讲。  Count  和  LongCount
的实现非常相像，不同的只是方法名，返回值类型和几个变量。

**   
Count  和  LongCount  是什么呢？  **

[  
Count  ](http://msdn.microsoft.com/en-
us/library/system.linq.enumerable.count.aspx) 和  [ LongCount
](http://msdn.microsoft.com/en-
us/library/system.linq.enumerable.longcount.aspx)
各自有两个重载：一个重载接受谓词，另一个不接受。下面是这四个方法的签名：

  
public  static  int  Count<TSource>(

this  IEnumerable<TSource> source)

public  static  int  Count<TSource>(

this  IEnumerable<TSource> source,

Func<TSource,  bool  > predicate)

public  static  long  LongCount<TSource>(

this  IEnumerable<TSource> source)

public  static  long  LongCount<TSource>(

this  IEnumerable<TSource> source,

Func<TSource,  bool  > predicate)  

可以看到，  Count  和  LongCount  的方法签名的差别仅在于返回值类型，一个是  int  （  Int32  ），一个是  long
（  Int64  ）。

不接受谓词的重载返回输入序列中元素的个数；而接受谓词的重载则返回能够通过谓词验证的元素的个数。

这些方法有一些有趣的行为：  

l  这四个方法都是扩展  IEnumerable<T> 的方法，你有可能会认为对于不接受谓词的那个重载来说，扩展  IEnumerable
会来得更好，因为没有什么会限制元素的类型。

l  Count  的不接受谓词的那个重载对于  ICollection<T> 和  ICollection  （  .NET4
中的接口）做了优化，因为这两个接口都定义有  Count  这个属性，这个属性的实现应该比遍历整个集合要快。  LongCount
则没有做优化，稍后的一节中我将会谈到这点。

l  接受谓词的重载中没有做任何优化，因为不迭代每一个元素就无法知道到底有多少个元素可以通过谓词的检验。

l  这四个方法都是立即执行的，都不涉及延迟执行。（仔细想想就明白了，这些方法仅仅返回一个  int  或  long  值，确实没什么可延迟执行的）。

l  所有的参数都只做非  null  的校验。

l  当输入集合的元素个数超出了  int  或  long  的上限值时，应该抛出  OverflowException  。  

** 我们要测试什么呢？ **

  
我们需要对方法的优化做测试，这件事做起来比说起来难，因为我们需要测试以下四种情况：  

l  输入序列同时实现了  ICollection<T> 和  ICollection  （这个简单，直接用  List<T> ）

l  输入序列实现了  ICollection<T> 但没有实现  ICollection  （还算简单，可以用  HashSet<T> ）

l  输入序列实现了  ICollection  但没有实现  ICollection<T> ，我还要要求这个类型实现了  IEnumerable<T>
（这样才能用到我们的扩展方法）。

l  输入序列既不实现  ICollection  也不实现  ICollection<T> （简单，用我们已经实现了的  Range  来生成）

  
其中第三点比较麻烦。虽然有很多类型是实现了  ICollection  但没有实现  ICollection<T> 的（比如  ArrayList
），但是它们通常也不实现  IEnumerable<T> ，而我们的扩展方法是针对于  IEnumerable<T> 的。所以我只得自己写了一个叫做
SemiGenericCollection  的类。

上述的四种输入序列的类型都找到了，我们现在需要考虑到底怎么测试了。你可能会说我们可以通过检查输入序列是否被迭代过来测试方法是不是真的被优化了。但是要做这个测
试就需要写一个有  Count  值但是其  GetEnumerator  方法会抛异常的集合类型。这个测试确实可行，但是我并没有做它。

对于接受谓词的重载来说，我们无需考虑那几个不同的集合接口，因为我们反正都不会优化这两个方法。

参数值为  null  的几个测试都比较简单，但是有另一个测试比较重要：溢出。我给  Count
创建了一个检验溢出行为的单元测试。很不幸，我们现在还不能在  Edulinq  的环境里运行它，因为我们还没有实现  Concat
。不过我还是把它写在这里：

  
[Test]

[Ignore(  "Takes an enormous amount of time!"  )]

public  void  Overflow()

{

var largeSequence = Enumerable.Range(  0  ,  int  .MaxValue)

.Concat(Enumerable.Range(  0  ,  1  ));

Assert.Throws<OverflowException>(() => largeSequence.Count());

}

  
如果  Count  的实现在应该抛出异常的时候把返回值溢出到了  Int.MinValue  的话，这个测试可以发现到它。

你可以看到，即使在实现了  Concat  并反注释了这段代码之后，这个测试也是被忽略掉了的，因为它需要遍历  20
亿个元素，对于几个简单快速的单元测试来说，这可不妙。其实  20  亿还不算太坏，因为  LongCount  的溢出测试需要遍历  2  的  63  次
方个元素呢。要生成那么长的序列并不难，难的是遍历它，那要花很长的时间。对于接受谓词的重载来说，我们也需要做溢出测试，直到写这篇文章之前我都忘记了要写这个测试
，而写测试的时候还发现了一个方法实现中的  bug :)

**   
来动手实现吧！ **

  
我们来看看接受谓词的那个重载的实现吧，它其实挺简单的：

  
public  static  int  Count<TSource>(  this  IEnumerable<TSource> source,

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

_ // No way of optimizing this _

checked

{

int  count =  0  ;

foreach (TSource item in source)

{

if  (predicate(item))

{

count++;

}

}

return  count;

}

}

  
请注意，在这里我们不需要返回一个序列，所以就没有用到迭代器代码块，因而也就无需把实现拆分到两个方法中去。

参数校验之后的方法主体部分相当简单，只有一点需要注意：整个的迭代过程都在“  checked  ”代码块中。这样，如果  count
值溢出的话，就会抛出异常，而不会令  count  值成为负数。也有其他的方式可以实现这点：  

l  可以只把给  count  加一的代码放在  checked  代码块中。

l  可以在每次给  count  加一之前检查  count==int.MaxValue  ，如果确实相等，则抛出异常

l  可以给整个程序集都应用  checked

  
我觉得把这段代码显式的放在  checked  代码块中是很有益的，因为这样可以很明显的凸显出对溢出的检查是方法正确性的需求这一事实。你可能更倾向于只把
count++  这一句代码放在  checked  代码块中，不过我个人觉得现在的做法更容易引起读代码的人对  checked
的注意，当然这只是我的主观偏好。还有，显式的  checked  代码块有可能会快一些，这一点我不确定，我还没有测试过。

除了与谓词有关的部分，上面的代码会全部出现在  Count  的优化过的实现中，我们就不再讲解了，直接写出代码：

  
public  static  int  Count<TSource>(  this  IEnumerable<TSource> source)

{

if  (source == null)

{

throw  new  ArgumentNullException(  "source"  );

}

_ // Optimization for ICollection<T> _

ICollection<TSource> genericCollection = source as ICollection<TSource>;

if  (genericCollection != null)

{

return  genericCollection.Count;

}

_ // Optimization for ICollection _

ICollection nonGenericCollection = source as ICollection;

if  (nonGenericCollection != null)

{

return  nonGenericCollection.Count;

}

_ // Do it the slow way - and make sure we overflow appropriately _

checked

{

int  count =  0  ;

using  (var iterator = source.GetEnumerator())

{

while  (iterator.MoveNext())

{

count++;

}

}

return  count;

}

}

  
这个实现里面唯一的“新”代码就是关于优化的那段。优化代码中的两段基本是一样的，它们检查不同的集合接口类型，哪个检查通过就返回哪个的  Count
属性。我不知道  .NET Framework  的实现中是先检查  ICollection  还是先检查  ICollection<T>
，我可以写一个同时实现了这两个接口，但是在两个  Count
属性中返回不同的值的类型来测试一下，但是那么做有点太过了。对于良好实现的集合来说，这点性能差异算不了什么，我们先检查“最有可能”的接口，也就是泛型的
ICollection<T> 。

**   
优化还是不优化？ **

  
LongCount  的实现和  Count  的实现几乎完全一样，只是  LongCount  中使用  long  而不是  int  。

我对  ICollection  和  ICollection<T> 做了优化，但是我不认为  .NET  是如此实现的。（只要创建一个很大的  byte
数组，并比较一下  Count  和  LongCount  应用到这个数组上的耗时差距就可以了。）

关于  [ Array.GetLongCount  ](http://msdn.microsoft.com/en-
us/library/system.array.getlonglength.aspx) 的使用存在一些争议，但是我觉得既然现在的  CLR
并不支持长度超过  Int32.MaxValue  的数组，那么这个问题现在就没什么好争议的，只有留待以后证明。除此之外，我不知道  .NET
的实现为什么没有优化。对一个实现了  ICollection  或  ICollection<T> 接口的类型来说，如果该集合中包含了超过
Int32.MaxValue  个元素的话，它的  Count  属性应该返回什么值呢？这一点并不明确。

欢迎提出各种建议。不过我还是要指出  LongCount  方法可能会更多的应用在  Queryable  中而不是  Enumerable
中，获取一个数据库表的长度的场景会比较多见，而获取一个内存中的集合的长度的场景则不那么常见。

**   
结论 **

  
这是我们第一次接触返回数值而不是返回一个序列的  LINQ  操作符，很自然，这样的操作符更容易理解。这些方法很简单的执行，做一些优化，然后返回结果值。这些
方法虽然简单，但是还是有一些东西很值得思考，比如说优化的问题，可惜优化的问题并没有一个确定的答案。

下一次我想我会去实现  Concat  ，主要是因为实现了  Concat  就可以把对  Count  进行溢出测试的代码反注释了。  Concat
是一个会返回一个序列的操作符，不过它很简单。



