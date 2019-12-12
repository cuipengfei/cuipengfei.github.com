---
title: 自己动手重新实现LINQ to Objects 11 - First，Last，Single以及它们带有OrDefault的重载
date: 2012-04-09 12:53:50
tags: LinQ
---
本文翻译自 [ Jon Skeet ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ http://msmvps.com/blogs/jon_skeet/archive/2010/12/29/reimplementing-linq-to-
objects-part-11-first-single-last-and-the-ordefault-versions.aspx
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/29/reimplementing-linq-to-
objects-part-11-first-single-last-and-the-ordefault-versions.aspx)

今天我实现了六个操作符，每个操作符都有两个重载。我一开始以为这些操作符的实现会很相似，但是最后发现每个都稍微有些不同...

今天实现了哪些操作符？

以下三个集合的排列  {First, Last, Single}, {  带有  /  不带有  OrDefault }, {  带有  /  不带有谓词
}  ，其结果是十二个不同的方法签名：



    public static TSource First<TSource>(
        this IEnumerable<TSource> source)

    public static TSource First<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)

    public static TSource FirstOrDefault<TSource>(
        this IEnumerable<TSource> source)

    public static TSource FirstOrDefault<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)

    public static TSource Last<TSource>(
        this IEnumerable<TSource> source)

    public static TSource Last<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)

    public static TSource LastOrDefault<TSource>(
        this IEnumerable<TSource> source)

    public static TSource LastOrDefault<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)

    public static TSource Single<TSource>(
        this IEnumerable<TSource> source)

    public static TSource Single<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)

    public static TSource SingleOrDefault<TSource>(
        this IEnumerable<TSource> source)

    public static TSource SingleOrDefault<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)




这些操作符有以下的共同点：

l 它们都是接受一个泛型参数的扩展方法

l 它们都是立即执行的

l 他们都会检查参数是否为  null

l 这些操作符的接受谓词的重载和以下形式等价：source.Where(谓词).该操作符()。换句话说，这些接受谓词的重载就是在执行操作符之前过滤一下

了解了上面这几点之后，对于每个操作符就只需考虑三种可能性：在谓词执行之后（如果有谓词的话），如果源序列是空的怎么处理？源序列只包含一个元素怎么处理？如果源序
列包含多个元素如何处理？我们可以用一个简单的表来描述上述问题的结果：

操作符

如果源序列是空的

源序列只包含一个元素

源序列包含多个元素

First

抛异常

返回该元素

返回第一个元素

FirstOrDefault

返回default(TSource)

返回该元素

返回第一个元素

Last

抛异常

返回该元素

返回最后一个元素

LastOrDefault

返回default(TSource)

返回该元素

返回最后一个元素

Single

抛异常

返回该元素

抛异常

SingleOrDefault

返回default(TSource)

返回该元素

抛异常

很明显，如果输入序列只有一个元素的话，这几个操作符的执行结果是非常一致的:) 类似的，如果输入的序列是空的的话，那么没有“  OrDefault
”的操作符会抛异常（  InvalidOperationException  ），而带有“  OrDefault
”的操作符则会返回元素类型的默认值（引用类型的默认值为  null  ，  int  的默认值为  0  ，等等）。

如果（可能是被过滤过的）输入序列含有多个元素的话，那这些操作符的执行结果的差异是很大的，First和  Last  的结果是顾名思义的，而  Single
抛出异常。值得注意的是，  SingleOrDefault
也会抛出异常，因为它要做的事又不是像这样：如果输入序列只有一个元素的话，返回该元素，否则的话返回默认值。如果你需要能够处理多元素序列的操作符的话，用
First  或  Last  。如果你需要处理可能为空的序列的话，使用  FirstOrDefault  或  LastOrDefault
。请注意，如果使用带有“  OrDefault  ”的操作符的话，那么一个空的序列和一个仅包含默认值的序列的执行结果会是完全一样的。我们稍后会提到
DefaultIfEmpty。

现在我们知道这些操作符做什么了，我们来开始测试吧。

我们要测试什么？

今天早晨，我发了一条推文说我在开始实现之前就写了  72  个测试用例。实际上，我最终写了  80  个，为什么写了  80
我们稍后会说。对每一个操作符，我测试了  12  个用例：

l 源序列是否为  null  的测试（没有谓词的重载）

l 源序列是否为  null  的测试（有谓词的重载）

l 谓词是否为  null  的测试

l 源序列不包含元素的测试（没有谓词的重载）

l 源序列不包含元素的测试（有谓词的重载）

l 源序列只包含一个元素的测试（没有谓词的重载）

l 源序列只包含一个元素，且该元素符合谓词的测试

l 源序列只包含一个元素，且该元素不符合谓词的测试

l 源序列包含多个元素的测试（没有谓词的重载）

l 源序列包含多个元素且只有一个元素符合谓词的测试

l 源序列包含多个元素且多个元素符合谓词的测试

实现这些测试用例时，我做了很多复制粘贴，每个操作符的测试用例用的都是同一组数据，只是预期的结果值不同。

另外，First和  FirstOrDefault  分别有两个额外的测试，  Last和LastOrDefault也分别有两个：

l First和FirstOrDefault：当没有谓词的时候，这两个方法应该在遇到第一个元素时就立刻返回，不应该继续遍历序列的剩余部分

l First  和  FirstOrDefault  ：当有谓词的时候，应该在找到第一个符合谓词的元素时就立刻返回

l Last和  LastOrDefault  ：当源序列实现了  IList<T>且没有谓词时，这两个方法使用源序列的  Count
属性和索引器去访问最后一个元素，这是一个特殊优化

l Last和LastOrDefault：当源序列实现了  IList  <T>且有谓词的时候，则没有上述的优化。这种情况下，这两个方法要彻底遍历整个源序列

上面提到的后两个测试用到了一个叫做  NonEnumerableList  的新集合类型，这个类型的所有方法实现都依赖于一个  List  <T>，除了
GetEnumerator  方法（泛型的和非泛型的）之外，这两个方法仅仅是抛出
NotSupportedException异常。这一点对于测试上述的优化很有帮助，有关优化的问题我们说到的时候再谈。

来动手实现吧！

这几个操作符的实现比我预期的要有趣，所以我下面要把十二个方法实现都列出来。这些方法可不是复制粘贴一下了事的，参数验证的部分除外。

如果我们基于  Where  和没有谓词的重载来实现有谓词的重载，并且基于  DefaultIfEmpty  和名字中不含  Default
的重载来实现名字中含有  Default  的重载的话，那么我们就只需要实现三个没有谓词和名字里没有  Default
的方法。但是，就像我之前说过的那样，单独的实现每个操作符会有一些好处。

为了避免看起来冗余，我把每个方法中的参数检验的部分省略掉了。不过在真实的代码中是含有参数检验的。我们从  First  开始吧：



    public static TSource First<TSource>(
        this IEnumerable<TSource> source)
    {
        // Argument validation elided
        using (IEnumerator<TSource> iterator = source.GetEnumerator())
        {
            if (iterator.MoveNext())
            {
                return iterator.Current;
            }
            throw new InvalidOperationException("Sequence was empty");
        }
    }

    public static TSource First<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)
    {
        // Argument validation elided
        foreach (TSource item in source)
        {
            if (predicate(item))
            {
                return item;
            }
        }
        throw new InvalidOperationException("No items matched the predicate");
    }




这两个实现看起来差异很大，这是我有意为之的。对于没有谓词的重载，我也可以用  foreach  来实现的，就从  foreach
的循环体中无条件的返回就可以了。然而，我想要强调我们在  First
中不需要循环遍历。我们只需要移向第一个元素，然后返回就可以了，如果无法移向第一个元素则抛出异常。没有任何迹象显示我们会再次调用  MoveNext
。而对于有谓词的重载，我们必须一直循环遍历直到找到一个符合谓词的元素，我们只在遍历完所有元素而找不到符合谓词的元素时才抛出异常。

下面我们来看看当序列为空时如何返回默认值：



    public static TSource FirstOrDefault<TSource>(
        this IEnumerable<TSource> source)
    {
        // Argument validation elided
        using (IEnumerator<TSource> iterator = source.GetEnumerator())
        {
            return iterator.MoveNext() ? iterator.Current : default(TSource);
        }
    }

    public static TSource FirstOrDefault<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)
    {
        // Argument validation elided
        foreach (TSource item in source)
        {
            if (predicate(item))
            {
                return item;
            }
        }
        return default(TSource);
    }




这个有谓词的  FirstOrDefault  看起来和有谓词的  First  非常相似，但是没有谓词的  FirstOrDefault  和没有谓词的
First  却有点细微的差别：我们在这里没有用  if  语句而是用了三元运算符（虽然说用  if
也是完全可以的）。不管能否移向第一个元素，我们都需要返回值。如果三元运算符允许第二个或第三个操作对象为  throw
语句就好了，但是即使不能的话也不是什么大问题。

接下来我们来实现  Single  ，它在某些方面与  First  的相似性比  Last  与  First  的相似性更大：



    public static TSource Single<TSource>(
        this IEnumerable<TSource> source)
    {
        // Argument validation elided
        using (IEnumerator<TSource> iterator = source.GetEnumerator())
        {
            if (!iterator.MoveNext())
            {
                throw new InvalidOperationException("Sequence was empty");
            }
            TSource ret = iterator.Current;
            if (iterator.MoveNext())
            {
                throw new InvalidOperationException("Sequence contained multiple elements");
            }
            return ret;
        }
    }

    public static TSource Single<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)
    {
        // Argument validation elided
        TSource ret = default(TSource);
        bool foundAny = false;
        foreach (TSource item in source)
        {
            if (predicate(item))
            {
                if (foundAny)
                {
                    throw new InvalidOperationException("Sequence contained multiple matching elements");
                }
                foundAny = true;
                ret = item;
            }
        }
        if (!foundAny)
        {
            throw new InvalidOperationException("No items matched the predicate");
        }
        return ret;
    }




这个比  First  复杂多了。没有谓词的重载的开头和  First  的开头一样，不同的是如果能够成功的移向第一个元素的话，我们需要记住第一个元素的值（
因为我们可能需要返回该值）然后试着移向第二个元素。如果可以移向第二个元素，就要抛出异常，如果不能移向第二个元素，那就可以返回刚才记录下的值。

有谓词的重载更麻烦。我们依然需要记住第一个找到的符合谓词的值，不过因为这次我们在循环，我们还需要记住是否已经找到过一个符合谓词的值了。如果能够找到第二个符合
谓词的值，就必须抛异常。如果一个符合谓词的值都找不到，也要抛异常。请注意，尽管我们给  ret  赋了一个  default
(TSource)的初值，但是我们的  return  语句执行时  ret  肯定不会是初值的状态。然而，  C#
的明确赋值的语法无法理解这一点，所以我们需要给  ret  一个“傀儡”式的初值，而  default  (T)是唯一可以用的值。有另外一种不用
foreach  的实现方法：遍历序列直到找到第一个符合谓词的元素，此时声明一个局部变量并且把找到的元素赋值给该变量，然后再开始另一个循环，以确保不能再找到
其他符合谓词的值了。我个人觉得这种方式太复杂了，所以我选择了用  foreach  的方式。

下面实现  SingleOrDefault  的两个重载，这两个重载之间的差别就不是那么大了：



    public static TSource SingleOrDefault<TSource>(
        this IEnumerable<TSource> source)
    {
        // Argument validation elided
        using (IEnumerator<TSource> iterator = source.GetEnumerator())
        {
            if (!iterator.MoveNext())
            {
                return default(TSource);
            }
            TSource ret = iterator.Current;
            if (iterator.MoveNext())
            {
                throw new InvalidOperationException("Sequence contained multiple elements");
            }
            return ret;
        }
    }

    public static TSource SingleOrDefault<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)
    {
        // Argument validation elided
        TSource ret = default(TSource);
        bool foundAny = false;
        foreach (TSource item in source)
        {
            if (predicate(item))
            {
                if (foundAny)
                {
                    throw new InvalidOperationException("Sequence contained multiple matching elements");
                }
                foundAny = true;
                ret = item;
            }
        }
        return ret;
    }




这次我们只是把没有谓词的重载里的  throw  语句替换成了  return  语句，并且在有谓词的重载中移除了没有找到符合谓词的元素的判断。我们在这里给
ret  赋以初值是有好处的，因为如果后面的代码不会给  ret  赋以其他值，那么  ret  已经有了正确的返回值了。

下一个是  Last  ：



    public static TSource Last<TSource>(
        this IEnumerable<TSource> source)
    {
        // Argument validation elided
        IList<TSource> list = source as IList<TSource>;
        if (list != null)
        {
            if (list.Count == 0)
            {
                throw new InvalidOperationException("Sequence was empty");
            }
            return list[list.Count - 1];
        }

        using (IEnumerator<TSource> iterator = source.GetEnumerator())
        {
            if (!iterator.MoveNext())
            {
                throw new InvalidOperationException("Sequence was empty");
            }
            TSource last = iterator.Current;
            while (iterator.MoveNext())
            {
                last = iterator.Current;
            }
            return last;
        }
    }

    public static TSource Last<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)
    {
        // Argument validation elided
        bool foundAny = false;
        TSource last = default(TSource);
        foreach (TSource item in source)
        {
            if (predicate(item))
            {
                foundAny = true;
                last = item;
            }
        }
        if (!foundAny)
        {
            throw new InvalidOperationException("No items matched the predicate");
        }
        return last;
    }




我们从没有谓词的方法开头的优化谈起吧。如果输入序列是个列表的话，我们可以获取列表的元素数量，然后根据元素数量的不同，或者是抛出异常或者是返回索引最大的元素。
如果还要再优化一点的话，我可以把  Count  值存到一个局部变量里，但是我假设获取  IList<T>的  Count
的代价是很低的。如果有哪位对这一点持反对意见，我很愿意做出修改：）请注意，我还假设了另一个情况，实现
IList<T>的类型含有的元素不会超过Int32.MaxValue，否则的话，这个优化就会失败。

如果不做优化的话，我们可以遍历整个序列，每次迭代都用最新访问到的元素来更新一个局部变量的值。我在这里没有用  foreach  ，但是并没有什么特别的原因
\--  我们其实也可以用  foreach  ，每次的迭代都把一个叫做  foundAny  的变量设为  true  ，然后在最后测试
foundAny  的值。实际上，上面所描述的就正是有谓词的方法所采用的方式。不许得承认，采用这个方式在一定程度上是不得已而为之  \--  我们不能调用
MoveNext  然后把获取到的值存起来，因为这个值有可能不符合谓词的约束。

Last  的有谓词的重载没有优化，这是  LINQ to Objects  （官方实现）的做法，但是我不知道这么做的原因。我们可以利用索引器来从后向前反向
遍历。有一个可能的解释是：谓词在处理某些值时有可能会抛出异常，如果我们在处理实现了
IList<T>的序列的时候直接跳到序列尾部的话，就会造成可观察的差别。我很想知道这是不是真的原因  \--
如果哪位能够提供内部信息的话，我会更新这篇博文。

现在，我们就只有一个操作符需要实现了  \--LastOrDefault  ：



    public static TSource LastOrDefault<TSource>(
        this IEnumerable<TSource> source)
    {
        // Argument validation elided
        IList<TSource> list = source as IList<TSource>;
        if (list != null)
        {
            return list.Count == 0 ? default(TSource) : list[list.Count - 1];
        }

        TSource last = default(TSource);
        foreach (TSource item in source)
        {
            last = item;
        }
        return last;
    }

    public static TSource LastOrDefault<TSource>(
        this IEnumerable<TSource> source,
        Func<TSource, bool> predicate)
    {
        // Argument validation elided
        TSource last = default(TSource);
        foreach (TSource item in source)
        {
            if (predicate(item))
            {
                last = item;
            }
        }
        return last;
    }




除了优化的部分，有谓词的和没有谓词的方法看起来很相似...比其他操作符的两个重载之间的差异要小。两个重载中，我们都是先定义一个返回值，并赋值为
default  (TSource)，然后遍历整个序列，并更新返回值，如果有谓词的话，就仅当元素符合谓词时才更新返回值。

结论

我今早起床时没料到这篇博文会写的这么长，不过我希望上述的各个实现之间的差异和"Last/LastOrDefault"没有优化的怪事能够值得这么多的辛苦。

虽然前面提到了  DefaultIfEmpty  ，但是我准备下次再实现它。虽然说如果今晚抓紧的话，还是可以做完的  ...

附录

我发现在  LINQ to Objects  （官方实现）中，  Single  和  SingleOrDefault
没有谓词的重载在遇到第二个元素时马上就会抛出异常。但是有谓词的重载即使遇到了第二个元素，还是会继续遍历。这看起来很是荒唐，没有连贯性。我发了一个  [
Connect issue
](https://connect.microsoft.com/VisualStudio/feedback/details/639955)
，我们看看会有怎样的答复。








