---
title: 自己动手重新实现LINQ to Objects 10 - Any和All
date: 2012-02-15 13:57:57
tags: linq
---
本文翻译自 [ Jon Skeet ](http://stackoverflow.com/users/22656/jon-skeet) 的系列博文“
Edulinq  ”。

本篇原文地址：

[ http://msmvps.com/blogs/jon_skeet/archive/2010/12/28/reimplementing-linq-to-
objects-part-10-any-and-all.aspx
](http://msmvps.com/blogs/jon_skeet/archive/2010/12/28/reimplementing-linq-to-
objects-part-10-any-and-all.aspx)

今天我们介绍两个操作符：Any  和  All  。

Any  和  All  做什么？

Any  有两个重载，而  All  只有一个：

    
    
    public static bool Any<TSource>( 
    
        this IEnumerable<TSource> source) 
    
     
    
    public static bool Any<TSource>( 
    
        this IEnumerable<TSource> source, 
    
        Func<TSource, bool> predicate) 
    
     
    
    public static bool All<TSource>( 
    
        this IEnumerable<TSource> source, 
    
        Func<TSource, bool> predicate)

[ 复制代码 ](http://www.cnblogs.com/cuipengfei/archive/2012/02/15/2352444.html)

这两个方法所做的事情完全可以顾名思义：

l 不接受谓词的  Any  用于判断输入序列中是否存在任何元素

l 接受谓词的  Any  用于判断输入序列中是否存在能够通过谓词检验的元素

l All  用于判断输入序列中的元素是否全部都能通过谓词的检验

这两个操作符都是立即执行的，它们在得出最终结果之前不会返回。

很重要的一点，  All  必须要迭代整个输入序列才能返回  true  ，但是它只要遇到一个不能通过谓词检验的元素就会返回  false  ；而  Any
只要找到一个可以通过谓词检验的元素就会返回  true  ，但是必须要迭代整个输入序列才能返回  false  。这就引出了一个很简单的  LINQ
性能小窍门，下面这种用法几乎在所有情况下都是不可取的：

    
    
    // Don't use this 
    
    if (query.Count() != 0)

[ 复制代码 ](http://www.cnblogs.com/cuipengfei/archive/2012/02/15/2352444.html)

上面的用法会迭代整个输入序列，如果你只想知道序列中是否含有元素的话，这样来做：

    
    
    // Use this instead 
    
    if (query.Any())

[ 复制代码 ](http://www.cnblogs.com/cuipengfei/archive/2012/02/15/2352444.html)

如果这是一个较大的  LINQ to SQL  查询中的一部分，那么这两种做法的区别可能不大，但是对于  LINQ to Objects  来说，区别很大。

我们需要测试什么？

我今晚感觉不错，我甚至把参数校验都做了  ...  虽然说参数校验在这个立即执行的特例下并不困难。

除此之外，我还测试了以下一些场景：

l Any  作用于空序列应该返回  false  ，而  All  则应该返回  true  。（因为无论谓词是什么样的，没有任何一个元素会通不过检验。）

l 一个序列，只要它含有元素，不接受谓词的  Any  就应该返回  true  。

l 如果所有元素都不能通过谓词，那么  Any  和  All  都应该返回  false  。

l 如果部分元素能够通过谓词，  Any  将会返回  true  而  All  会返回  false  。

l 如果所有元素都能够通过谓词，那么  All  会返回  true  。

以上测试都很简洁明了，我就不给出代码了。还有最后一个测试很有趣：我们要证明  Any  会在找到第一个符合条件的元素之后立即返回，证明的手段是通过把
Any  作用在一个被完整迭代时会抛出异常的查询结果上。最简单的方式就是创建一个包含有  0  的整数序列，然后对其做  Select  操作，
Select  中会把每一个元素除以某个常数。以下的测试用例中，序列中会导致异常的元素之前存在一个能够通过谓词的元素：

    
    
    [Test] 
    
    public void SequenceIsNotEvaluatedAfterFirstMatch() 
    
    { 
    
        int[] src = { 10, 2, 0, 3 }; 
    
        var query = src.Select(x => 10 / x); 
    
        // This will finish at the second element (x = 2, so 10/x = 5) 
    
        // It won't evaluate 10/0, which would throw an exception 
    
        Assert.IsTrue(query.Any(y => y > 2)); 
    
    }

[ 复制代码 ](http://www.cnblogs.com/cuipengfei/archive/2012/02/15/2352444.html)

对于  All  ，也有一个类似的测试用例，其中会导致异常的元素前面存在一个不能通过谓词检验的元素。

现在所有测试都有了，下面开始有趣的部分了：

来动手实现吧！

有一点需要提醒，我们可以基于接受谓词的  Any  或者基于  All  来实现另外两个方法。比如说，如果已经实现了  All  的话，那么  Any
就可以这样实现：

    
    
    public static bool Any<TSource>( 
    
        this IEnumerable<TSource> source) 
    
    { 
    
        return source.Any(x => true); 
    
    } 
    
     
    
    public static bool Any<TSource>( 
    
        this IEnumerable<TSource> source, 
    
        Func<TSource, bool> predicate) 
    
    { 
    
        if (predicate == null) 
    
        { 
    
            throw new ArgumentNullException("predicate"); 
    
        } 
    
        return !source.All(x => !predicate(x)); 
    
    }

[ 复制代码 ](http://www.cnblogs.com/cuipengfei/archive/2012/02/15/2352444.html)

基于接受谓词的的  Any  来实现不接受谓词的  Any  是最简单的，我们使用了一个对任何元素都会返回  true
的谓词，这就意味着只要输出序列中含有元素就会返回  true  ，这正是我们想要的行为。

上面调用  All  时的两次否操作会让你费点脑筋，不过这其实就是  [ 德摩根定律
](http://zh.wikipedia.org/wiki/%E5%BE%B7%E6%91%A9%E6%A0%B9%E5%AE%9A%E5%BE%8B)
在  LINQ  中的表现形式：我们对谓词做否操作，来检验是否所有的元素都不能通过谓词，得到结果后，再次做否操作并返回。由于否操作的原因，这种实现方式仍然会
在合适的情况下提前返回。

虽然以上的方式可行，但是我更喜欢给每个方法一个单独的实现，这样做简单明了：

    
    
    public static bool Any<TSource>( 
    
        this IEnumerable<TSource> source) 
    
    { 
    
        if (source == null) 
    
        { 
    
            throw new ArgumentNullException("source"); 
    
        } 
    
                 
    
        using (IEnumerator<TSource> iterator = source.GetEnumerator()) 
    
        { 
    
            return iterator.MoveNext(); 
    
        } 
    
    } 
    
     
    
    public static bool Any<TSource>( 
    
        this IEnumerable<TSource> source, 
    
        Func<TSource, bool> predicate) 
    
    { 
    
        if (source == null) 
    
        { 
    
            throw new ArgumentNullException("source"); 
    
        } 
    
        if (predicate == null) 
    
        { 
    
            throw new ArgumentNullException("predicate"); 
    
        } 
    
     
    
        foreach (TSource item in source) 
    
        { 
    
            if (predicate(item)) 
    
            { 
    
                return true; 
    
            } 
    
        } 
    
        return false; 
    
    } 
    
     
    
     
    
    public static bool All<TSource>( 
    
        this IEnumerable<TSource> source, 
    
        Func<TSource, bool> predicate) 
    
    { 
    
        if (source == null) 
    
        { 
    
            throw new ArgumentNullException("source"); 
    
        } 
    
        if (predicate == null) 
    
        { 
    
            throw new ArgumentNullException("predicate"); 
    
        } 
    
     
    
        foreach (TSource item in source) 
    
        { 
    
            if (!predicate(item)) 
    
            { 
    
                return false; 
    
            } 
    
        } 
    
        return true; 
    
    }

[ 复制代码 ](http://www.cnblogs.com/cuipengfei/archive/2012/02/15/2352444.html)

这样的实现方式很明显的凸显了“提前返回”这一特性。而且，这样做也可以使得堆栈跟踪记录更易读。对于一个下游开发者来说，如果调试  Any
时在堆栈跟踪记录中看到了调用  All  的记录会显得很奇怪；调用  All  时看到了  Any  也会很奇怪。

有一点很有趣，不接受谓词的  Any  中我们没用到  foreach  。而是用了迭代器第一次调用  MoveNext
方法时的返回值来表示序列中是否存在元素。读这个方法可以很明显的（至少对我来说很明显）看出我们根本不关心第一个元素的值是什么，因为我们根本就没有去访问它。

结论

尽量使用  Any  而不是  Count  的建议或许是这篇文章中最重要的一点，余下的部分都比较简单，不过能基于一个操作符来实现另一个操作符总是很有趣的。

下一篇讲什么呢？或许是  Single  、  SingleOrDefault  、  First  、  FirstOrDefault  、  Last
或者  LastOrDefault  。也或许我会把它们都放到一篇文章中来阐释它们的相似同时也强调它们之间的差别。

  

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

