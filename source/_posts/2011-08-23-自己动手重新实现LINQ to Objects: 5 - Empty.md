---
title: 自己动手重新实现LINQ to Objects 5 - Empty
date: 2011-08-23 23:41:53
tags: LinQ
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ _ http://msmvps.com/blogs/jon_skeet/archive/2010/12/24/reimplementing-linq-
to-objects-part-5-empty.aspx _
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/24/reimplementing-linq-to-
objects-part-5-empty.aspx)  
  

**   
**

这一篇继续讲非扩展方法。这次我们要讲的是  Empty  ，它有可能是最简单的  LINQ  操作符了。  

** Empty  是什么？   
**

[ Empty  ](http://msdn.microsoft.com/en-us/library/bb341042.aspx)
是一个泛型的，静态的方法，它只有一个签名形式，不接受任何参数：  

public  static  IEnumerable<TResult> Empty<TResult>()  

它返回一个特定类型的空序列。这就是它的唯一作用。

它的行为只有一点比较有趣：文档上说  Empty  会对空序列做缓存。换句话说，对于同一个类型参数来讲，它每次都会返回同一个空序列。  

** 我们要测试什么？   
**

能够测试的东西也就只有两点：  

l  返回序列为空。

l  对每个类型参数来说，返回值会被缓存起来。  

和测试  Range  的时候的方法一样，我们用一个叫做  EmptyClass  的别名来引用包含  Empty  的类型。下面是测试代码：  

```
[Test]
public void EmptyContainsNoElements() {
 using(var empty = EmptyClass.Empty < int > ().GetEnumerator()) {
  Assert.IsFalse(empty.MoveNext());
 }
}

[Test]
public void EmptyIsASingletonPerElementType() {

 Assert.AreSame(EmptyClass.Empty < int > (), EmptyClass.Empty < int > ());

 Assert.AreSame(EmptyClass.Empty < long > (), EmptyClass.Empty < long > ());

 Assert.AreSame(EmptyClass.Empty < string > (), EmptyClass.Empty < string > ());

 Assert.AreSame(EmptyClass.Empty < object > (), EmptyClass.Empty < object > ());

 Assert.AreNotSame(EmptyClass.Empty < long > (), EmptyClass.Empty < int > ());

 Assert.AreNotSame(EmptyClass.Empty < string > (), EmptyClass.Empty < object > ());

}
```

当然，以上代码并不能证明缓存不是每个线程一份。不过，这些测试也够了。  

** 来动手实现吧！   
**

现在看来，  Empty  的实现要比它的描述更有趣。如果不是要做缓存，我们可以这样实现  Empty  ：  

```
// Doesn't cache the empty sequence _

public static IEnumerable < TResult > Empty < TResult > ()
{

 yield
 break;

}  
```

不过我们需要遵守关于缓存的文档。要实现缓存其实也不难。有一个很方便的事实可以为我们所用，  ** 空数组是不可变的 ** 。数组的长度是固定的，通常无法使一
个数组是只读的。数组中的任何一个元素都是可以改变的。不过一个空数组是不包含任何元素的，所以也就没有什么可被改变的。这样，我们就可以反复的重用同一个数组了。

现在你可能会猜我会用  Dictionary<Type, Array>
来实现，不过我们可以利用一个小手段。在一个泛型类型中，可以用一个静态变量来实现针对类型参数的缓存，因为每一个传入了类型参数的泛型类型的静态变量都是不同的。

很不幸，  Empty  是一个非泛型类型中的方法。所以我们需要创建另一个泛型类型来包含缓存。这很容易做到，而且  CLR
还帮我们做到了线程安全的类型初始化。所以，我们最后的实现会是这样的：  
```
public static IEnumerable < TResult > Empty < TResult > () {

 return EmptyHolder < TResult > .Array;
}

private static class EmptyHolder < T > {

 internal static readonly T[] Array = new T[0];

}
```

以上的实现遵守了所有的关于缓存的文档，而且代码行数也很少。不过这个实现方式需要你很好的了解  .NET  中泛型的工作方式。这种做法和我们上一篇采取的策略相
反，我们选择了一种比较难懂的方式，而没有选择使用字典的易懂的方式。不过我很满意这种方案，因为一旦你了解了泛型类型和静态变量的工作方式，这段代码就很简单了。  

** 结论   
**

Empty  的实现就是这样的。下一个操作符  Repeat  有可能会更简单，虽然它也要分成两个方法来实现。  

** 附录   
**

因为以上讲解的方法有点难懂，所以下面再提供另一种实现：  
```
public static IEnumerable < TResult > Empty < TResult > ()
{

 return EmptyEnumerable < TResult > .Instance;
}

# if AVOID_RETURNING_ARRAYS

private class EmptyEnumerable < T >: IEnumerable < T > , IEnumerator < T >
 {

  internal static IEnumerable < T > Instance = new EmptyEnumerable < T > ();

  _ // Prevent construction elsewhere _

  private EmptyEnumerable()
  {

  }

  public IEnumerator < T > GetEnumerator()
  {

   return this;

  }

  IEnumerator IEnumerable.GetEnumerator()
  {

   return this;
  }

  public T Current
  {

   get {
    throw new InvalidOperationException();
   }

  }

  object IEnumerator.Current
  {

   get {
    throw new InvalidOperationException();
   }

  }

  public void Dispose()
  {

   _ // No-op _

  }

  public bool MoveNext()
  {

   return false;
   _ // There's never a next entry _

  }

  public void Reset()
  {

   _ // No-op _

  }

 }

# else

 private static class EmptyEnumerable < T >

 {

  internal static readonly T[] Instance = new T[0];

 }

# endif
```

这下大家都满足了吧：）



