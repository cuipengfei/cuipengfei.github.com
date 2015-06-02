---
layout: post
title: "Scala中Stream的应用场景及其实现原理"
date: 2014-10-23 17:21
comments: true
keywords: scala, stream
categories: ScalaInAction Scala
---

#假设一个场景

需要在50个随机数中找到前两个可以被3整除的数字。

听起来很简单，我们可以这样来写：

```scala
def randomList = (1 to 50).map(_ => Random.nextInt(100)).toList

def isDivisibleBy3(n: Int) = {
  val isDivisible = n % 3 == 0
  println(s"$n $isDivisible")
  isDivisible
}

randomList.filter(isDivisibleBy3).take(2)
```

一个产生50个随机数的函数；

一个检查某数字是否能被3整除的函数；

最后，对含有50个随机数的List做filter操作，找到其中所有能够被3整除的数字，取其中前两个。

把这段代码在Scala的console里面跑一下，结果是这样的：

```scala
scala> randomList.filter(isDivisibleBy3).take(2)
31 false
71 false
95 false
7 false
38 false
48 true
88 false
52 false
2 false
27 true
90 true
55 false
96 true
91 false
82 false
83 false
8 false
51 true
96 true
27 true
12 true
76 false
17 false
53 false
54 true
70 false
29 false
49 false
12 true
83 false
18 true
6 true
7 false
76 false
51 true
95 false
76 false
85 false
87 true
84 true
44 false
44 false
89 false
84 true
42 true
44 false
0 true
23 false
35 false
55 false
res34: List[Int] = List(48, 27)
```

其最终结果固然是没有问题，找到了48和27这两个数字。但是非常明显的可以看出，isDivisibleBy3被调用了50次，找到了远多于两个的能被3整除的数字，但是最后我们只关心其中前两个结果。

这似乎有点浪费，做了很多多余的运算。

对于这个例子来说，这还没什么，我们的List很小，判断整除于否也不是什么耗时操作。

但是如果List很大，filter时所做的运算很复杂的话，那这种做法就不可取了。

#现有解法的优缺点

```scala
randomList.filter(isDivisibleBy3).take(2)
```
这行代码有一个优点：

用描述性、声明性的语言描述了我们要做的事是什么，而无需描述怎么做。我们只需说先用filter过滤一下，然后拿前两个，整件事就完成了。

但是它同时也有一个缺点：

做了多余的运算，浪费资源，而且这个缺点会随着数据量的增大以及计算复杂度的增加而更为凸显。

#试着解决其缺点

解决多余运算的思路很简单，不要过滤完整个List之后再取前两个。而是在过滤的过程中如果发现已经找到两个了，那剩下的就忽略掉不管了。

顺着这个思路很容易写出如下很像Java的代码：

```scala
  def first2UsingMutable: List[Int] = {
    val result = ListBuffer[Int]()

    randomList.foreach(n => {
      if (isDivisibleBy3(n)) result.append(n)
      if (result.size == 2) return result.toList
    })

    result.toList
  }
```

创建一个可变的List，开始遍历随机数，找到能被3整除的就把它塞进可变List里面去，找够了两个就返回。

执行的结果如下：

```scala
scala> first2UsingMutable
31 false
89 false
21 true
29 false
12 true
res35: List[Int] = List(21, 12)
```

可以看到，运算量确实变少了，找够了两个就直接收工了。

但是这实在很糟糕，显式使用了return同时还引入了可变量。

有什么东西像是一个foreach循环而又可以不引入可变量呢？fold

```scala
  def first2UsingFold: List[Int] = {
    randomList.foldLeft(Nil: List[Int])((acc, n) => {
      if (acc.size == 2) return acc
      if (isDivisibleBy3(n)) n :: acc
      else acc
    })
  }
```

执行：

```scala
scala> first2UsingFold
98 false
77 false
68 false
93 true
93 true
res36: List[Int] = List(93, 93)
```

效果和上面一段代码类似，没有多余的运算。但是由于需要early termination，所以还是摆脱不了return。

这两种解法在去除多余运算这个缺点的同时也把原来的优点给丢掉了，我们又退化回了描述如何做而不是做什么的程度了。

#如何保持代码的表意性而又不用做多余运算呢？

其实类似的问题是有套路化的解决方案的：使用Stream。

```scala
randomList.toStream.filter(isDivisibleBy3).take(2).toList
```

这行代码执行的结果：

```scala
scala> randomList.toStream.filter(isDivisibleBy3).take(2).toList
86 false
15 true
53 false
20 false
93 true
res42: List[Int] = List(15, 93)
```

可见没有多余运算了，而且这行代码和最初代码极为相似，都是通过描述先做filter再做take来完成任务的。缺点没有了，优点也保留了下来。

这同样都是filter和take，代码跟代码的差距咋就这么大呢？

答案就是：因为Stream利用了惰性求值（lazy evaluation），或者也可以称之为延迟执行（deferred execution）。

接下来就看一下这两个晦涩的名词是如何帮助Stream完成工作的吧。

#实现原理

在这里我借用一下Functional programming in Scala这本书里对Stream实现的代码，之所以不用Scala标准库的源码是因为我们只需要实现filter，take和toList这三个方法就可以展示Stream的原理，就不需要动用重型武器了。

先假设我们自己实现了一个MyStream，它的用法和Stream是类似的：

```scala
MyStream(randomList: _*).filter(isDivisibleBy3).take(2).toList
```

以这一行代码为引子，我们来开始解剖MyStream是如何工作的。

#类型签名

```scala
trait MyStream[+A] {
	. . . . . .
}

case object Empty extends MyStream[Nothing]

case class Cons[+A](h: () => A, t: () => MyStream[A]) extends MyStream[A]
```

一个trait叫做MyStream，其中的内容我们暂时忽略掉。

它有两个子类，一个Cons，一个Empty。Empty当然是代表空Stream了。

而Cons则是头尾结构的，头是Stream中的一个元素，尾是Stream中余下的元素。请注意头和尾这两个参数的类型并不是A，头的类型是一个能够返回A的函数，尾的类型是一个能够返回MyStream[A]的函数。

#初始化

有了以上的类型定义以及头尾结构，我们就可以把很多个Cons加一个Empty（或者是无限多个Cons，没有Empty）连起来就构成一个Stream了，比如这样：

```scala
Cons(()=>1,()=>Cons(()=>2,()=>Empty))
```

这样就可以构造一个含有1，2的Stream了。

不过，请注意，上面的说法并不严谨，实际上它是一个包含着两个分别会返回1和2的函数的Stream。

也就是说当上面的代码在构造Cons的时候，1和2还没有“出生”，它们被包在一个函数里，等着被释放出来。

如果说我们通常熟知的一些集合包含的是花朵的话，那Stream所包含的就是花苞，它本身不是花，但是有开出花来的能力。

#Smart初始化

当然，如果直接暴露Cons的构造函数出去给别人用的话，那这API也未免太不友好了，所以Stream需要提供一个易用的初始化的方式：

```scala
object MyStream {

  def apply[A](elems: A*): MyStream[A] = {
    if (elems.isEmpty) empty
    else cons(elems.head, apply(elems.tail: _*))
  }

  def cons[A](hd: => A, tl: => MyStream[A]): MyStream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: MyStream[A] = Empty
}
```

这个没有太多好解释的，我们就是用apply和小写的cons这两个方法来把客户代码原本要写的一大堆匿名函数给代劳掉。

需要注意的一点是apply方法看似是递归的，好像是你调用它的时候如果给它n个元素的话，它会自己调用自己n-1次。事实上它确实会调用自己n-1次，但是并不是立即发生的，为什么呢？

因为小写的cons方法所接受的第二个参数不是eager evaluation的，这就会使得apply(elems.tail: _*)这个表达式不会立即被求值。这就意味着，apply缺失会被调用n次，但是这n次并不是一次接一次连续发生的，它只会在我们对一个Cons的尾巴求值时才会发生一次。

如果说普通的集合中包含的是数据的话，那Stream中所包含的就是能够产生数据的算法。

如何？是不是花朵花苞的感觉又回来了？

还记得我们开始剖析的时候那句代码是什么吗？

```scala
MyStream(randomList: _*).filter(isDivisibleBy3).take(2).toList
```

现在我们算是把MyStream(randomList: _*)这一小点说清了。

接下来看MyStream(randomList: _*).filter(isDivisibleBy3)是如何work的。

#filter

```scala
trait MyStream[+A] {

  def filter(p: A => Boolean): MyStream[A] = {
    this match {
      case Cons(h, t) =>
        if (p(h())) cons(h(), t().filter(p))
        else t().filter(p)
      case Empty => empty
    }
  }

. . . . . .

}

```

这个方法定义在基类里，又是一个看似递归的实现。

为什么说是看似呢？因为在

```scala
if (p(h())) cons(h(), t().filter(p))
```

这行代码中我们又用到了小写的cons，它所接受的参数不会被立即求值。也就是说filter一旦找到一个合适的元素，它就不再继续跑了，剩下的计算被延迟了。

比较值得提一下的是：这里的h()是什么呢？h是构造Cons时的第一个参数，它是什么类型的？()=>A。它就是之前提到的能够生产数据的算法，就是那个能够开出花朵的花苞。在这里我们说h()，就是在调用这个函数来拿到它所生产的数据，就是让一个花苞开出花朵。

#take

```scala
MyStream(randomList: _*).filter(isDivisibleBy3).take(2)
```

接下来就该说take是如何work的了。在这里我们可以回顾一下，MyStream(randomList: _*)返回一个类型为MyStream[Int]，其中包含很多个可以返回Int的函数的容器。然后我们调用了这个容器的filter方法，filter又返回一个包含很多个可以返回Int的函数的容器。请注意，到这里为止，真正的计算还没有开始，真正的计算被包含到了一个又一个的函数（花苞）中，等待着被调用（绽放）。

那对filter的结果调用take又会怎样呢？

```scala
trait MyStream[+A] {

  . . . . . .

  def take(n: Int): MyStream[A] = {
    if (n > 0) this match {
      case Cons(h, t) if n == 1 => cons(h(), MyStream.empty)
      case Cons(h, t) => cons(h(), t().take(n - 1))
      case _ => MyStream.empty
    }
    else MyStream()
  }

  . . . . . .

}

```

看过了前面的apply和filter之后，take就显得顺眼了很多。我们又见到了小写的cons，条件反射一般，我们就可以意识到，只要看见cons，那就意味着作为它的参数的表达式不会被立即求值，那这就意味着计算被放到了函数里，稍后再执行。那稍后到底是什么时候呢？

那就得看下面的toList了。

#toList

```scala
trait MyStream[+A] {

  . . . . . .

  def toList: List[A] = {
    this match {
      case Cons(h, t) => h() :: t().toList
      case Empty => Nil
    }
  }

}

```

又是一个递归实现，但是这次可不是看似递归了，这次是实打实的递归：只要还没有遇到空节点，就继续向后遍历。这次没有使用cons，没有任何计算被延迟执行，我们通过不断地对h()求值，来把整个Stream中每一个能够生产数据的函数都调用一遍以此来拿到我们最终想要的数据。

#总结

要把以上的代码细节全部load进脑子跑一遍确实不太容易，我们人类的大脑栈空间太浅了。

所以我们试着从上面所罗列出的纷繁的事实中抽象出一些适合人脑理解的描述性语句吧：

* List(1,2,3)会构造一个容器，容器中包含数据
* List(1,2,3).filter(n=>n>1)会构造出一个新的容器，其中包含2和3，这两块具体的数据
* List(1,2,3).filter(n=>n>1).take(1)会把上一步中构造成的容器中的第一块数据取出，放入一个新容器



* MyStream(1,2,3)也会构造一个容器，但是这个容器中不包含数据，它包含能够生产数据的算法
* MyStream(1,2,3).filter(n=>n>1)也会构造出一个新的容器，这个容器中所包含的仍然是算法，是基于上一步构造出的能生产1，2，3的算法之上的判断数字是否大于1的算法
* MyStream(1,2,3).filter(n=>n>1).take(1)会把上一步中构造成的算法容器中的第一个算法取出，放入一个新容器
* MyStream(1,2,3).filter(n=>n>1).take(1).toList终于把上面所有步骤构造出的算法执行了，从而得到了最终想要的结果


上面对List和Stream的应用的区别在哪儿呢？

就在于List是先把数据构造出来，然后在一堆数据中挑选我们心仪的数据。

而Stream是先把算法构造出来，挑选心仪的算法，最后只执行一大堆算法中我们需要的那一部分。

这样，自然就不会执行多余的运算了。
