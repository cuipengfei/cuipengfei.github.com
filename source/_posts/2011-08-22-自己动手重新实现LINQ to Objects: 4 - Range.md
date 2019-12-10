---
title: 自己动手重新实现LINQ to Objects 4 - Range
date: 2011-08-22 22:09:45
tags: linq
---
本文翻译自  [ Jon Skeet  ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ _ http://msmvps.com/blogs/jon_skeet/archive/2010/12/24/reimplementing-linq-
to-objects-part-4-range.aspx _
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/24/reimplementing-linq-to-
objects-part-4-range.aspx)  

** **

本篇博文较短，接下来的几篇估计也会比较短。我觉得只有  _ 很相似 _ 的几个  LINQ  操作符才适合放到同一篇博文里面，比如  Count  和
LongCount  就比较适合放在一起讲。不过我也要采纳读者的意见，如果你喜欢“肥胖”一点的博文的话，请留言说明。

本文将要讲解  Range  操作符。  

** Range  操作符是什么？   
**

[ Range  ](http://msdn.microsoft.com/en-
us/library/system.linq.enumerable.range.aspx) 只有一种方法签名：

public  static  IEnumerable< int  > Range(

int  start,

int  count)

和  LINQ  中的其他操作符不同，  Range  不是扩展方法，它就是一个普通的静态方法。  Range  返回一个可枚举的对象，该对象会
yield  返回“  count  ”个整数，返回的整数序列从“  start  ”开始，逐次加一。举例来说，
Enumerable.Range(6,3)  会返回  6  ，  7  ，  8  。

由于  Range  不会接受输入序列，所以也就无所谓对输入的流式处理和缓冲了。不过它有以下几点行为：  

l  参数的校验需要立即执行；参数  count  不可以为负数，也不可以让输出值中包含超出  Int32  范围的值。

l  输出值是被延迟输出的。  Range  的执行效率应该比创建并返回一个“  count  ”长度的数组来得更高。  

** 我们要如何测试呢？   
**

要测试普通的静态方法就给我们带来了一个新的挑战，因为我们要在原版的  LINQ  实现和  Edulinq
的实现之间做切换。这是由我所使用的命名空间所带来的缺陷，单元测试写在  Edulinq.Tests  这个命名空间内，我们的实现写在  Edulinq
这个命名空间中。在编译器寻找一个类型的时候，父级命名空间会比其他的通过  using  引入的命名空间拥有更高的优先级，即使是使用  using
来显式引入一个类型的别名时也是如此。

我选择的解决方式是用一个  using  指令来引入一个叫做  RangeClass  的别名。  using  指令要么指向
System.Linq.Enumerable  要么指向  Edulinq.Enumerable  。测试代码中用到  Range  时都是这样写：
RangeClass.Range  。为此我创建了两个项目配置，其中一个定义了叫做  NORMAL_LINQ
的预处理符号，另一个则没有定义任何预处理符号，这样就可以在两种  LINQ  实现之间做切换了。  RangeTest.cs  中会包含如下的代码：  

#if NORMAL_LINQ

using  RangeClass =  System  .Linq.Enumerable;

#else

using  RangeClass = Edulinq.Enumerable;

#endif  

当然了，也有别的办法可以替代以上的方式：  

l  可以把单元测试代码换到另外一个命名空间中去。

l  也可以让项目的引用项依赖于不同的项目配置，用于测试原版  LINQ  的项目配置不包含对  Edulinq  的引用，而用于测试  Edulinq
的项目配置则不引用  System.Core  。这样就可以在  NORMAL_LINQ  的预处理符号下直接使用  using System.Linq
，并直接使用  Enumerable.Range  。测试默认的  LINQ  实现时亦是如此。  

我喜欢上面提到的第二种方式，但是那需要手动修改测试工程的工程文件，因为  Visual Studio
没有提供任何根据不同条件引用不同引用项的功能。我以后或许会用到这个方法，欢迎提供建议。  

** 我们要测试什么呢？   
**

Range  需要的测试并不多，我只有八个方面需要测试，而且都不怎么很特别：  

l  一个简单有效的  Range  操作应该可以通过  AssertSequenceEqual  的测试。

l  起始值应该可以是负数。

l  Range(Int32.MinValue, 0)  会返回一个空序列。

l  Range(Int32.MaxValue, 1)  会返回仅包含  Int32.MaxValue  的序列。

l  count  不可以为负数。

l  count  可以为  0  。

l  start+count-1  不可以超过  Int32.MaxValue  （所以  Range(Int32.MaxValue, 2)
应该是一个无效操作）。

l  start+count-1  可以等于  Int32.MaxValue  （所以  Range(Int32.MaxValue, 1)
应该是一个有效操作）。  

最后两项要分别被几组不同的数据测试，这几组数据是：大的  start  值和小的  count  值，小的  start  值和大的  count
值，还有一组是  start  和  count  都相当大。

请注意我没有做针对于惰性求值的测试，我确实可以测一下返回值是否实现了任何其他的集合接口，不过那会显得有点奇怪。不过我们有一些测试中用到的  count
值非常大，给这么大的集合分配内存几乎肯定要失败。  

** 开始实现吧！   
**

你肯定早就猜到了，我们将会用分隔开的两个方法来实现  Range  。一个  public  的方法用来立即执行参数校验，还有一个  private
的方法用来做核心部分的工作，其中包含迭代器代码块。

由于一开始就确定了参数不会超过  Int32  的上下限，我们在实现的主体部分就可以随意一点了。  

public  static  IEnumerable< int  > Range(  int  start,  int  count)

{

if  (count < 0  )

{

throw  new  ArgumentOutOfRangeException(  "count"  );

}

_ // Convert everything to long to avoid overflows. There are other ways of
checking _

_ // for overflow, but this way make the code correct in the most obvious way.
_

if  ((  long  )start + (  long  )count -  1L  > int  .MaxValue)

{

throw  new  ArgumentOutOfRangeException(  "count"  );

}

return  RangeImpl(start, count);

}

private  static  IEnumerable< int  > RangeImpl(  int  start,  int  count)

{

for  (  int  i =  0  ; i < count; i++)

{

yield  return  start + i;

}

}  

有几点需要说明：  

l  第二段参数校验的代码检查的是“  start  ”和“  count  ”这二者的合法性，而不是仅仅检查“  count  ”。如果能给
ArgumentOutOfRangeException  （或者是  ArgumentException
）传入多个参数名就好了，这样就能在异常信息中指出多个参数不合法。话虽这么说，  .NET Framework  的实现也是只会指出“  count
”不合法。

l  第二段参数校验的代码还可以有很多其他的写法，把所有的操作数都转换成  long
并不是我们仅有的选择。不过我觉得我选择的这个方法是最简单的，而且也是正确的。采取这种方式，我无须考虑多种情况并保证每一种都是正确的。使用  Int64
可以确保不会有溢出，也无需去考虑  checked  或  unchecked  。

l  Private  方法中的循环也有其他的实现方式，不过我认为我用的这个是最简单的。一种很容易想到的替代方案就是用两个变量，一个是已经返回过的值的个数，
另一个是下一个要返回的值，每次循环都把这两个值各自加一。还有一种比较复杂的替代方案是只使用一个循环变量，但是那样的话你就不能写“  value <
start + count  ”了，因为最后一个返回值有可能是  Int32.MaxValue  ，你也不能写“  value <= start +
count - 1  ”了，因为传入的参数有可能是  Int32.MaxValue  和  0
。我不想在代码中处理这么多种临界情况，所以我选择了一个简单正确的方式。如果你真的非常非常注重  Range  的效率的话，你应该去调查一下其他的可行方案。  

写本文之前，我没有给  Range(Int32.MaxValue, 1)  和  Range(Int32.MinValue, 0)
写出好的单元测试。不过写了上面的那段关于替代性方案的话之后，我找到了合适的测试用例。寻找替代方案可以帮助我想到更多的测试用例，这事儿多有趣。  

** 结论   
**

实现  Range  有助于测试其他的操作符，比如说  Count  。现在既然我已经实现了一个非扩展方法的  LINQ  操作符了，那无妨把另外两个（
Empty  和  Repeat  ）也实现了。其实我已经实现了“  Empty  ”了，希望今天就能把它整理成文。  Repeat
也不需要多久就能完工，然后我们就可以开始着手于  Count  和  LongCount  了。

本文中的代码很好的诠释了这种情况：有时，写“傻”一点的代码会比写短一点，快一点的代码更好，因为它更易于读懂。无疑，以后博文中我还会写更多的“傻”代码的。

  * [ 点赞  1  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





