---
title: Desugar Scala(19) -- Partial Function
date: 2020-01-05 19:58:10
tags:
- Desugar_Scala
- Scala
---

先看这么一段Scala代码：

```Scala
object PFBlog {

  def usePF(pf: PartialFunction[Option[Int], Int]) = {
    pf(Some(11))
  }

  usePF {
    case Some(x) => x + 1
    case None => 0
  }
}
```

声明一个usePF方法，接受一个PartialFunction作为参数，它的实现就是传一个 `Some(11)` 给pf。 
pf的具体类型是 `PartialFunction[Option[Int], Int]` 所以传递给它一个`Some(11)`可以期待它会返回一个Int。

然后调用usePF，传递给usePF的是一个pattern match表达式。给Some加一，给None返回0。

这时问题就来了，这个pattern match表达式是怎么能够符合usePF需要的参数类型的呢？
这么单纯的一个pattern match表达式怎么变成`PartialFunction[Option[Int], Int]`的呢？

为了探寻答案，先把这几行Scala代码编译成class文件，然后把byte code反编译成Java来一探究竟吧。

以上Scala代码会编译出3个class文件：
+ PFBlog.class
+ PFBlog$.class
+ PFBlog$$anonfun$1.class

逐个反编译出来看一下。

```java
public final class PFBlog
{
    public static int usePF(final PartialFunction<Option<Object>, Object> pf) {
        return PFBlog$.MODULE$.usePF((PartialFunction)pf);
    }
}
```

首先是PFBlog，里面声明了一个usePF方法，它的实现完全代理给PFBlog$。那么它就只是对应Scala代码内的object PFBlog的对外声明。

接下来，那就看下PFBlog$吧：

```java
public final class PFBlog$
{
    public static final PFBlog$ MODULE$;
    
    static {
        (MODULE$ = new PFBlog$()).usePF((PartialFunction<Option<Object>, Object>)new PFBlog$$anonfun.PFBlog$$anonfun$1());
    }
    
    public int usePF(final PartialFunction<Option<Object>, Object> pf) {
        return BoxesRunTime.unboxToInt(pf.apply((Object)new Some((Object)BoxesRunTime.boxToInteger(11))));
    }
    
    private PFBlog$() {
        super();
    }
}
```

这里面的usePF就有真的实现了，对应原Scala的usePF。调用pf.apply，并传递Some(11)。
并且，它的静态块里自己调用了usePF。这就对应了原Scala中对usePF的调用。
值得注意的调用usePF时传递的参数，是一个`new PFBlog$$anonfun.PFBlog$$anonfun$1()`，这就是第三个class文件的内容了。
那这个`new PFBlog$$anonfun.PFBlog$$anonfun$1()`就一定是对应原本的pattern match表达式了。

接下来看下`PFBlog$$anonfun$1.class` :

```java
public final class PFBlog$$anonfun$1 extends AbstractPartialFunction<Option<Object>, Object> implements Serializable {
    private static final long serialVersionUID = 0L;
    
    public final <A1 extends Option<Object>, B1> B1 applyOrElse(final A1 x1, final Function1<A1, B1> default) {
        Object o;
        if (x1 instanceof Some) {
            final int x2 = BoxesRunTime.unboxToInt(((Some)x1).value());
            o = BoxesRunTime.boxToInteger(x2 + 1);
        }
        else if (None$.MODULE$.equals(x1)) {
            o = BoxesRunTime.boxToInteger(0);
        }
        else {
            o = default.apply((Object)x1);
        }
        return (B1)o;
    }
    
    public final boolean isDefinedAt(final Option<Object> x1) {
        return x1 instanceof Some || None$.MODULE$.equals(x1);
    }
    
    public PFBlog$$anonfun$1() {
        super();
    }
}
```

可以看到，PFBlog$$anonfun$1继承了AbstractPartialFunction。
这个AbstractPartialFunction是在Scala标准库里定义了的，它mixin了PartialFunction。
所以，PFBlog$$anonfun$1自然就符合了usePF对参数要求的类型。

再看PFBlog$$anonfun$1内的具体实现，isDefinedAt对于Some或者None返回true。applyOrElse则做了原Scala中pattern match表达式给Some加一，给None返回零的逻辑。

由于AbstractPartialFunction中的apply方法是这样的：

```scala
def apply(x: T1): R = applyOrElse(x, PartialFunction.empty)
```

这就确保了当PFBlog$$anonfun$1的apply被调用到的时候，我们原Scala中的pattern match表达式的逻辑可以得到执行。

到这里就明白了，Scala编译器很勤劳，吭哧吭哧的给：
```scala
    case Some(x) => x + 1
    case None => 0
```
产出了一个PFBlog$$anonfun$1并产出了isDefinedAt和applyOrElse方法。
从而让这个pattern match表达式可以符合usePF的参数类型。

最后，很久前写过一个关于partial application的博客： https://cuipengfei.is-a.dev/blog/2013/12/25/desugar-scala-6/
值得注意的是，虽然partial function和partial application名字里都有partial这个字，但是**他俩其实没啥关系**。
一个是关于把pattern match表达式编译成PartialFunction的实现类的。另一个是关于柯里化的。

这篇博客只关心Scala编译器是怎么支持Partial Function这个语言特性的。
关于Partial Function的更多信息，可以看看老人家的文章： https://www.jianshu.com/p/b0b4e3a349c3