---
title: 自己动手重新实现LINQ to Objects 8 - Concat
date: 2011-09-14 22:40:28
tags: linq
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ _ http://msmvps.com/blogs/jon_skeet/archive/2010/12/27/reimplementing-linq-
to-objects-part-8-concat.aspx _
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/27/reimplementing-linq-to-
objects-part-8-concat.aspx)

  

  

** **

上文讲的  Count  和  LongCount  返回的是数值类型，本文我们讲的  Concat  返回的是一个序列。  

** Concat  是什么？  **

[  
Concat  ](http://msdn.microsoft.com/en-us/library/bb302894.aspx)
只有一种签名形式，这让它使用起来很简单：

  
public  static  IEnumerable<TSource> Concat<TSource>(

this  IEnumerable<TSource> first,

IEnumerable<TSource> second)

  
Concat  的返回值依次包含了两个序列中的元素，也就是说把两个序列串联起来了。

  
我有时会觉得  .NET  没有提供  Prepend/Append  这样的方法是个遗憾，这两个方法应该可以和  Concat
做类似的事情，只不过它们把一个序列和一个单个的元素串联起来。如果要做一个填充着国家名和一个“  None
”值的下拉列表的话，这两个方法是很有用的。当然，向  Concat
中传入一个单元素的数组也可以达到同样的目的，但是我个人认为用特定的方法名做特定的事会让代码的可读性更高。  [ MoreLINQ
](http://code.google.com/p/morelinq/) 中的  [ Concat  方法  ](http://code.google.c
om/p/morelinq/source/browse/trunk/MoreLinq/Concat.cs?r=171) 可以做这件事，不过  Edulinq
的目的只是要实现  LINQ to Objects  中已有的方法。

和往常一样，我们列出  Concat  的行为：

  
l  参数校验需要立即执行：两个参数都不允许为  null

l  返回值是延迟执行的：当  Concat  被调用时，两个参数不会立即被迭代

l  输入序列只有在需要的时候才会被迭代：如果你停止迭代输出序列时第一个输入序列还没有被耗尽的话，那么第二个序列根本就不会被迭代

  
这几点描述基本就涵盖了  Concat  的所有行为。

**   
我们需要测试什么呢？ **

  
Concat  的串联行为很容易被测试，只需要一个用例就够了。我们或许也可以测试输入空序列会如何，但是那种测试基本没有不通过的可能。

参数校验的测试方式和往常一样：调用方法时传入非法的参数，然后不去迭代方法的返回值。

最后，还有一个单元测试用来测试两个输入序列被迭代的时机。这个测试中用到了我们在测试  Where  时用过的  ThrowingEnumerable  ：

  
[Test]

public  void  FirstSequenceIsntAccessedBeforeFirstUse()

{

IEnumerable< int  > first =  new  ThrowingEnumerable();

IEnumerable< int  > second =  new  int  [] {  5  };

_ // No exception yet... _

var query = first.Concat(second);

_ // Still no exception... _

using  (var iterator = query.GetEnumerator())

{

_ // Now it will go bang _

Assert.Throws<InvalidOperationException>(() => iterator.MoveNext());

}

}

[Test]

public  void  SecondSequenceIsntAccessedBeforeFirstUse()

{

IEnumerable< int  > first =  new  int  [] {  5  };

IEnumerable< int  > second =  new  ThrowingEnumerable();

_ // No exception yet... _

var query = first.Concat(second);

_ // Still no exception... _

using  (var iterator = query.GetEnumerator())

{

_ // First element is fine... _

Assert.IsTrue(iterator.MoveNext());

Assert.AreEqual(  5  , iterator.Current);

_ // Now it will go bang, as we move into the second sequence _

Assert.Throws<InvalidOperationException>(() => iterator.MoveNext());

}

}

  
我们写测试来检查迭代器是否被  Dispose  掉了。但是我们可以预测到输入序列的迭代器应该会被合理的  Dispose
掉。实际上，第一个序列的迭代器会在第二个序列开始被迭代之前就被  Dispose  掉。

**   
开始动手实现吧！ **

  
Concat  的实现虽然比较简单，但是我写完之后还是觉得  F#  更值得拥有  ...  实现分为参数校验和迭代器代码块两部分，每一部分都不复杂：

  
public  static  IEnumerable<TSource> Concat<TSource>(

this  IEnumerable<TSource> first,

IEnumerable<TSource> second)

{

if  (first == null)

{

throw  new  ArgumentNullException(  "first"  );

}

if  (second == null)

{

throw  new  ArgumentNullException(  "second"  );

}

return  ConcatImpl(first, second);

}

private  static  IEnumerable<TSource> ConcatImpl<TSource>(

IEnumerable<TSource> first,

IEnumerable<TSource> second)

{

foreach (TSource item in first)

{

yield  return  item;

}

foreach (TSource item in second)

{

yield  return  item;

}

}

  
如果不能利用迭代器代码块的话，这个实现会变得很麻烦。虽然不会特别难，但是我们需要记住当前正在迭代的是哪个序列。

如果是在用  F#  的话，我们可以使用  yield!  表达式来把它实现的更简单，  yield!
表达式作用于一整个序列而不是单个的元素。必需得承认在这种场景下使用  yield!
并不会带来什么性能上的提升（如果是在递归的场景下就很可能会有性能提升），但是能够用一个语句来  yield  返回整个序列确实是一种更优雅的风格。（
Spec#  中也有一个类似的结构叫做嵌套迭代器，用  [ yield foreach  ](http://research.microsoft.com
/en-us/projects/specsharp/iterators.pdf) 来表示。）我对  F#  和  Spec#
了解的都不够深入，所以就不做更深入的比较了。不过我们在以后实现  Edulinq  的过程中还会遇到好几次“  yield
返回一个序列中的每个元素”的模式。请记住，我们不能把  yield  返回的代码抽取到一个单独的方法中去，因为“  yield  ”表达式需要  C#
编译器的特殊处理。

**   
结论 **

  
虽然我用的实现方式还是蛮简单的，但是我还是吐槽一下：） 如果  C#  里面也有嵌套迭代器那多好啊，虽然说没有它也没有令我太苦恼。

Concat  是一个很有用的操作符，不过它也不过是  SelectMany  的一个特例罢了。  Concat  只能把两个序列连接成一个序列，而
SelectMany  则可以把很多个序列连接成一个序列，而且  SelectMany  在有时还更有普遍性。下次我们会实现  SelectMany
，而且会展示一些基于  SelectMany  来实现其他操作符的例子。（等实现  Aggregate  的时候，我们会再次见到操作符只返回一个值的例子。）

**   
附录：避免不必要的保持引用 **

  
有一条留言建议说要在遍历完第一个序列后把它设为  null  。这样，在遍历完第一个序列后，它就可以被垃圾回收了。如果采取这个建议，那么实现起来会是这样的：

private  static  IEnumerable<TSource> ConcatImpl<TSource>(

IEnumerable<TSource> first,

IEnumerable<TSource> second)

{

foreach (TSource item in first)

{

yield  return  item;

}

_ // Avoid hanging onto a reference we don't really need _

first = null;

foreach (TSource item in second)

{

yield  return  item;

}

}

在普通情况下，把一个不再使用的局部变量设为  null  这种做法是没用的。因为当  CLR
在执行优化过的代码，并且没有挂上调试器时，垃圾收集器只关心在方法内部可能还会被访问的变量。

但是在我们这个特例中，这么做还是有用的。因为第一个参数并不是一个简单的局部变量，在  C#  编译器生成的隐藏类型中，它是一个实例字段，而  CLR
无法判断实例字段是否会被再次使用。

或许我们可以在调用  GetEnumerator  之前清空掉我们对“  first  ”这个参数的唯一引用。我们可以写一个这样的方法：

public  static  T ReturnAndSetToNull<T>(ref T value) where T :  class

{

T tmp = value;

value = null;

return  tmp;

}

然后这样调用它：

foreach (TSource item in ReturnAndSetToNull(ref first))

我认为这样做绝对是有点过了，因为迭代器有可能还会持有对集合的引用。不过在遍历之后把“  first  ”这个参数设为  null  在我看来是说得通的。

需要提醒你一下，我觉得  .NET  的  LINQ to Objects  的实现里面是不会这样做的。（以后我可能会用一个有  finalizer
的集合类来测试一下。）



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





