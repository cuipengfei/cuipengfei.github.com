---
title: 自己动手重新实现LINQ to Objects 9 - SelectMany
date: 2011-12-15 23:27:44
tags: linq
---
本文翻译自 [ Jon Skeet ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ http://msmvps.com/blogs/jon_skeet/archive/2010/12/27/reimplementing-linq-to-
objects-part-9-selectmany.aspx
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/27/reimplementing-linq-to-
objects-part-9-selectmany.aspx)

我们接下来要实现的这个操作符是LINQ  中最重要的操作符。大多数（或者是全部？）其他的返回一个序列的操作符都可以通过调用  SelectMany
来实现，这是后话按下不表。现在我们首先来实现它吧。

SelectMany  是什么？

SelectMany  有四个重载，看起来一个比一个吓人：

public static IEnumerable<TResult> SelectMany<TSource, TResult>(

this IEnumerable<TSource> source,

Func<TSource, IEnumerable<TResult>> selector)

public static IEnumerable<TResult> SelectMany<TSource, TResult>(

this IEnumerable<TSource> source,

Func<TSource, int, IEnumerable<TResult>> selector)

public static IEnumerable<TResult> SelectMany<TSource, TCollection, TResult>(

this IEnumerable<TSource> source,

Func<TSource, IEnumerable<TCollection>> collectionSelector,

Func<TSource, TCollection, TResult> resultSelector)

public static IEnumerable<TResult> SelectMany<TSource, TCollection, TResult>(

this IEnumerable<TSource> source,

Func<TSource, int, IEnumerable<TCollection>> collectionSelector,

Func<TSource, TCollection, TResult> resultSelector)

其实还不算太坏，这些重载只是同一个操作的不同形式而已。

无论是哪个重载，都需要一个输入序列。然后用一个委托来处理输入序列中的每个元素以生成一个子序列，这个委托可能会接受一个代表元素  index  的参数。

再然后，我们或者把每个子序列中的元素直接返回，或者再用另一个委托来做处理，这个委托接受输入序列中的元素并接受其对应的子序列中的元素。

以我的经验来说，使用  index  两个重载不太常用，而另外两个重载（上面列出的第一个和第三个）则比较常用。还有，当  C#  编译器处理一个含有多个
from  子句的查询表达式的时候，它会把出第一个  from  之外的其他  from  子句转译为上面的第三个重载。

为了把上面的说法放入实例中理解，我们假设有这样一个查询表达式：

var query = from file in Directory.GetFiles("logs")

from line in File.ReadLines(file)

select Path.GetFileName(file) + ": " + line;

上面的查询表达式会被转译为下面的“正常”调用：

var query = Directory.GetFiles("logs")

.SelectMany(file => File.ReadLines(file),

(file, line) => Path.GetFileName(file) + ": " + line);

这个例子中，编译器会把表达式中的  select  子句转译为投影操作；如果表达式后面还跟有  where  子句或其他子句，编译器会把  file  和
line  包装在一个匿名类型中传递给投影操作。这是查询表达式转译中最令人难理解的一点，因为这涉及到了透明标识符（  transparent
identifiers  ）。就现在来说，我们只分析上面给出的简单例子。

上例中的  SelectMany  接受三个参数：

l 输入序列，也就是一个字符串序列（  Directory.GetFiles  所返回的文件名）

l 一个初始投影操作，它把一个文件名转化为该文件中包含的一行行的字符串

l 一个结束投影操作，它把一个文件名和一行文件内容转化为一个由冒号分隔的字符串

表达式的最后结果会是一个字符串的序列，其中包含所有  log  文件的每一行，每一行会以文件名作为前缀。如果把结果打印出来，大概会是这样的：

test1.log: foo

test1.log: bar

test1.log: baz

test2.log: Second log file

test2.log: Another line from the second log file

要理解  SelectMany  可能会费点脑子，我当时理解它就费了点力，不过理解它是很重要的。

在讲测试之前，还有几点关于  SelectMany  的行为细节需要说明：

l 参数校验是立即执行的，每个参数都不能是  null

l 整个过程都是流式处理的。每次只会从输入序列中读取一个元素，然后生成一个子序列。然后每次只会返回子序列中的一个元素，返回子序列中的全部元素之后再去读取输入
序列中的下一个元素，用它来生成下一个子序列，如此循环往复。

l 每个迭代器在使用完之后都会被关闭，正如你会预期的一样。

我们要测试什么呢？

我有一点变懒了，我不想再写参数为  null  的测试了。我给  SelectMany
的每一个重载都写了一个测试。我发现我无法把这些测试写得很清晰，不过还是拿出一个例子来，下面的代码是针对  SelectMany  的最复杂的重载的测试：

[Test]

public void FlattenWithProjectionAndIndex()

{

int[] numbers = { 3, 5, 20, 15 };

var query = numbers.SelectMany((x, index) => (x +
index).ToString().ToCharArray(),

(x, c) => x + ": " + c);

// 3 => "3: 3"

// 5 => "5: 6"

// 20 => "20: 2", "20: 2"

// 15 => "15: 1", "15: 8"

query.AssertSequenceEqual("3: 3", "5: 6", "20: 2", "20: 2", "15: 1", "15: 8");

}

给这个测试做一点解释：

l 每一个数字都和它的序号相加  (3+0, 5+1, 20+2, 15+3)

l 相加的结果转成字符串，然后转成字符数组。（我们原本不需要调用  ToCharArray  的，因为  String  本身就实现了
IEnumerable<char> ，不过现在这样写比较清晰。）

l 然后把子序列中的每一个字符和原元素以“原元素：子序列字符”的形式组合在一起

注释部分是每一个输入元素对应的输出结果，测试最后一句代码给出了完整的输出序列。

是不是一团乱麻？希望你看了上面逐步分解的解释很清楚一点。好了，现在想办法让测试可以通过吧。

开始动手实现吧！

我们可以通过实现一个最复杂的重载并让其他的重载都调用它来实现  SelectMany  ，或者也可以写一个没有参数校验的“  Impl
”方法，然后让四个重载都调用它。比如说，最简单重载可以这样实现：

public static IEnumerable<TResult> SelectMany<TSource, TResult>(

this IEnumerable<TSource> source,

Func<TSource, IEnumerable<TResult>> selector)

{

if (source == null)

{

throw new ArgumentNullException("source");

}

if (selector == null)

{

throw new ArgumentNullException("selector");

}

return SelectManyImpl(source,

(value, index) => selector(value),

(originalElement, subsequenceElement) => subsequenceElement);

}

不过我还是选择为每一重载写一个签名相同的“  SelectManyImpl  ”方法。我觉得这样做可以让以后单步调试时更简单一些  ...
而且这样让我们可以注意到不同重载之间的区别，代码是这样的：

// Simplest overload

private static IEnumerable<TResult> SelectManyImpl<TSource, TResult>(

IEnumerable<TSource> source,

Func<TSource, IEnumerable<TResult>> selector)

{

foreach (TSource item in source)

{

foreach (TResult result in selector(item))

{

yield return result;

}

}

}

// Most complicated overload:

// - Original projection takes index as well as value

// - There's a second projection for each original/subsequence element pair

private static IEnumerable<TResult> SelectManyImpl<TSource, TCollection,
TResult>(

IEnumerable<TSource> source,

Func<TSource, int, IEnumerable<TCollection>> collectionSelector,

Func<TSource, TCollection, TResult> resultSelector)

{

int index = 0;

foreach (TSource item in source)

{

foreach (TCollection collectionItem in collectionSelector(item, index++))

{

yield return resultSelector(item, collectionItem);

}

}

}

这两个方法之间的相似性很是明显  ...  不过我还是觉得保留着第一种形式很有用，如果我搞不清楚  SelectMany
的作用的话，通过第一种最简单的重载就可以很容易的弄懂。以此为基础再去理解余下的重载，跳跃性就不会那么大了。第一个重载在一定程度上起到了一个理解
SelectMany  的概念的垫脚石的作用。

有两点需要指出：

如果  C#  中可以使用“  yield foreach selector(item)
”这种表达式的话，上面的第一个方法就可以实现的稍简单一点。如果要在第二个方法中使用这种做法的话就会难一些，而且可能还要涉及到对  Select
的调用，这样的话就有点得不偿失了。

在第二个方法中，我没有显式的使用“  checked  ”代码块，虽然说“  index  ”是有可能溢出的。我没有看过  BCL
的实现是什么样的，但是我认为他们不会写“  checked  ”的。考虑到前后一致性，我或许应该在每一个处理  index  的方法中都是用“
checked  ”代码块，或者给整个程序集开启“  checked  ”。

通过调用  SelectMany  来实现其他操作符

之前我提到过很多的  LINQ  操作符都可以通过调用  SelectMany  来实现。下面的代码就是这一观点的实例，我们通过调用  SelectMany
实现了  Select  ，  Where  和  Concat  ：

public static IEnumerable<TResult> Select<TSource, TResult>(

this IEnumerable<TSource> source,

Func<TSource, TResult> selector)

{

if (source == null)

{

throw new ArgumentNullException("source");

}

if (selector == null)

{

throw new ArgumentNullException("selector");

}

return source.SelectMany(x => Enumerable.Repeat(selector(x), 1));

}

public static IEnumerable<TSource> Where<TSource>(

this IEnumerable<TSource> source,

Func<TSource, bool> predicate)

{

if (source == null)

{

throw new ArgumentNullException("source");

}

if (predicate == null)

{

throw new ArgumentNullException("predicate");

}

return source.SelectMany(x => Enumerable.Repeat(x, predicate(x) ? 1 : 0));

}

public static IEnumerable<TSource> Concat<TSource>(

this IEnumerable<TSource> first,

IEnumerable<TSource> second)

{

if (first == null)

{

throw new ArgumentNullException("first");

}

if (second == null)

{

throw new ArgumentNullException("second");

}

return new[] { first, second }.SelectMany(x => x);

}

Select  和  SelectMany  使用  Enumerable.Repeat
来很方便的创建含有一个元素或不包含任何元素的序列。你也可以通过创建一个数组来代替使用  Repeat  的这种做法。  Concat
直接使用了一个数组：如果你理解了  SelectMany  的作用就是把多个序列组合为一个序列这一点的话，  Concat
这样实现看起来就很自然了。我估计  Empty  和  Repeat  可以通过递归来实现，尽管这样的话性能会很差。

现在，上面的代码是放在条件编译块里面的。如果大家希望我多写一些借助于  SelectMany
来实现的操作符的话，我可能会考虑把它单独分离一个项目出来。不过我感觉以上的代码已经足以显示  SelectMany  的灵活性了，再利用
SelectMany  来实现更多的其他操作符也未必能更加充分的说明这一点。

在理论的意义上，  SelectMany  也很重要，因为它为  LINQ  提供了  monadic  的特性。我不想在这一话题上说的更多，你可以读一读
[ Wes Dyer  的博客  ](http://blogs.msdn.com/b/wesdyer/archive/2008/01/11/the-
marvels-of-monads.aspx) ，或者直接搜索“  bind monad SelectMany  ”就可以找到很多比我更聪明的人写的文章。

结论

SelectMany  是  LINQ  中的基础之一，初看上去它很是令人生畏。但是一旦你理解了  SelectMany
的作用就是把多个序列组合起来这一点之后，它就很容易搞懂了。

下一次我们讨论  All  和  Any  ，这两个操作符很适合放在一起来讲解。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

