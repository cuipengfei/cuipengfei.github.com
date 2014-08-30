---
layout: post
title: "Desugar Scala(17) -- Option和for，以及脑子里发生的事情"
date: 2014-08-30 11:36
comments: true
categories: 
---

Scala里的for关键字是个很有趣的东西。可以用来把多层嵌套for循环写成一层。比如这样：

```scala
for(i<-1 to 10;j<-1 to 10;k<-1 to 10) yield(s"$i $j $k")
```

这行代码执行的结果是这样的：

```scala
1 1 1
1 1 2
1 1 3
1 1 4
1 1 5
1 1 6
1 1 7
1 1 8
1 1 9
1 1 10
1 2 1
1 2 2
1 2 3
1 2 4
1 2 5
1 2 6
1 2 7
1 2 8
1 2 9
1 2 10
......
......
```

这样，就可以用一行代码写出三层循环的效果。代码看起来非常紧凑，噪音很少。

但是今天主要要说的不是这种for，而是它和Option结合的写法。

Option本身是一个抽象类，代表一个可能存在，也可能不存在的值（那谁谁的喵？）。它有两个实现类，分别是Some和None。顾名思义，Some代表有值，None代表没有。

实际上，上面的说法不够准确，Some是一个实现类，而None实际是一个单例，不过这点对后面的内容没影响。

现在设想一个很简单的场景，需要用单价和数量来算总价，而单价和数量未必拿得到，那代码大概会是这样的：

```scala
  def calculateTotal: Option[Int] = {
    val price: Option[Int] = getPrice
    val amount: Option[Int] = getAmount

    if (price.isEmpty || amount.isEmpty) {
      None
    } else {
      Some(price.get * amount.get)
    }
  }
```

getPrice和getAmount都返回一个Option[Int]，就类似Java中Integer可以为null一样。计算出来的总价也是一个Option[Int]，说不定会有，也说不定没有。

在这段代码中先检查单价和数量是否存在，如果二者中任意一个不存在，那就返回None，代表无法求得总价。如果二者都存在，那就将二者的乘积用Some包起来返回。

这代码看起来还ok，很常规的写法，但是稍显啰嗦。如果用上for的话，可以大大简化这段代码：

```scala
  def calculateTotalWithFor: Option[Int] = {
    for (price <- getPrice; amount <- getAmount) yield price * amount
  }
```

这个方法体只有一行了，而它实现出来的行为和上面那段代码是完全一致的。

这感觉好神奇啊，不用判断价格和数量是否存在，也不需要根据判断结果决定到底返回None还是Some。它是怎么搞的呢？

看一下反编译的结果吧：

```java
    public Option<Object> calculateTotalWithFor() {
        return getPrice().flatMap(new AbstractFunction1() {
            public final Option<Object> apply(final int price) {
                return OptionAndFor..MODULE$.account$of$OptionAndFor$$getAmount().map(new AbstractFunction1.mcII.sp() {
                    private final int price$1;

                    public final int apply(int amount) {
                        return apply$mcII$sp(amount);
                    }

                    public int apply$mcII$sp(int amount) {
                        return price * amount;
                    }
                });
            }
        });
    }
```

这个反编译的结果很不好读，不过还是可以看出个大概。它先是对getPrice的返回值调用了flatMap，给其传入一个匿名函数（AbstractFunction1），在这个匿名函数里面又对getAmount的返回值调用了map，也给其传入了一个匿名函数，再在这第二层匿名函数里做了乘法运算。

如果用Scala把它表达出来，是这样的：

```scala
  def calculateTotalWithFlatMapAndMap: Option[Int] = {
    getPrice.flatMap(price => getAmount.map(amount => amount * price))
  }
```

由此可见，上面使用for的代码的神奇之处在于它利用了Option的flatMap和map方法。

这两个方法具有一个共同特征：如果被调用flatMap或者map的当前Option实例为None的话，则忽略传入的匿名函数，直接返回None。

这很容易理解，要参与运算的成员之一已经是None了，那就不用管剩下的成员到底是啥了，它随便是啥，最终的计算结果都会是None。这和最初写出的用 || 运算符的代码的逻辑是一致的。

到此为止，我们给Option和for的结合使用脱光了衣服，它就是利用Option的flatMap和map来实现紧凑的代码的。

###神奇之处不仅在于更短的代码，还在于它提高了信噪比，给我们提供了更加简化的思考模型

最初那段用if else的代码，在写它或者读它的时候，我们的脑子里面发生了什么呢？

	1. 要获取价格和数量
	2. 要判断价格是否为空，要判断数量是否为空        （与业务关联较小，属于技术范畴）
	3. 如果任意一个为空，结果是空                 （与业务关联较小，属于技术范畴）
	4. 如果两个都不是空，再做乘法运算
	
而在写或者读用for的那段代码的时候，脑子里又是怎么想的呢？

	1. 获取价格和数量
	2. 做乘法运算

我们写这段代码的目的是要表述业务逻辑，是要给未来读代码的人传递和业务相关的信息。

而空值判断是偏技术的，把这种代码消掉，我们传递给其他程序员的信息里就含有更少的与业务无关的噪音。而且我们自己写起来的时候，脑子里也不需要考虑那么多的东西。

对自己，对他人都有利。这实在是一个美妙的语言特性。