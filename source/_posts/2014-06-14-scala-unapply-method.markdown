---
layout: post
title: "Desugar Scala(15) -- unapply和unapplySeq方法"
date: 2014-06-14 14:46
comments: true
tags:
- Desugar_Scala
- Scala
---
实在想不到什么动词可以当做脱衣服来讲了，所以从现在开始这系列博文就叫做Desugar Scala了。除非哪天才思泉涌，又想到了新词：）

开始正文。

名字叫做unapply和unapplySeq的方法在Scala里也是有特殊含义的。

我们前面说过case class在做pattern match时很好用，而除case class之外，有unapply或unapplySeq方法的对象在pattern match时也有很好的应用场景。

比如这段代码：

```scala
object Square {
  def unapply(z: Double): Option[Double] = Some(math.sqrt(z))
}
```

我们定义了一个unapply方法，用来计算平方根。
我们可以像调用普通方法一样的调用它：

```scala
val number: Double = 36.0
Square.unapply(number)
```

这样会得到36的平方根:6。实际上返回值是Some(6)。

上面的方式是对unapply的浪费，unapply真正的好处是这样的：

```scala
val number: Double = 36.0
number match {
	case Square(n) => println(s"square root of $number is $n")
	case _ => println("nothing matched")
}
```

这样我们无需显式调用unapply方法，而把是它用在pattern match中，让编译器替我们调用它。

当我们写下这段pattern match的代码时，编译器其实替我们做了好几件事：

1. 调用unapply，传入number
2. 接收返回值并判断返回值是None，还是Some
3. 如果是Some，则将其解开，并将其中的值赋值给n（就是case Square(n)中的n）

这段代码反编译出来是这个样子的：

```java
	  double number = 36.0D;
      double d1 = number;
      Option localOption = Square..MODULE$.unapply(d1);
      //调用unapply，传入number
      BoxedUnit localBoxedUnit;
      if (localOption.isEmpty()) {//判断返回值是None
        Predef..MODULE$.println("nothing matched");
        localBoxedUnit = BoxedUnit.UNIT;
      }
      else {//判断返回值是Some
        double n = BoxesRunTime.unboxToDouble(localOption.get());
        //将Some解开，并将其中的值赋值给n
        Predef..MODULE$.println(new StringContext(Predef..MODULE$.wrapRefArray((Object[]) new String[] {
          "square root of ", " is ", ""
        }) ).s(Predef..MODULE$.genericWrapArray(new Object[] {
          BoxesRunTime.boxToDouble(number), BoxesRunTime.boxToDouble(n)
        })));
        localBoxedUnit = BoxedUnit.UNIT;
      }
```

如果没有unapply方法和pattern match语法之间的这种结合，我们自己写代码要写成什么样子呢？

或许会比上面反编译的代码简单一些，但是显式地调用开平方的方法，用if else来判断Option，以及将真正的返回值从Option里面解出来这三件事是免不掉的。

unapplySeq和unapply的作用很是类似，例如这样：

```scala
object Names {
  def unapplySeq(str: String): Option[Seq[String]] = {
    if (str.contains(",")) Some(str.split(","))
    else None
  }
}
```

我们定义一个unapplySeq方法，用逗号作为分隔符来把字符串拆开。

然后我们可以这样应用它：

```scala
val namesString = "xiao ming,xiao hong,tom"
namesString match {
  case Names(first, second, third) => {
    println("the string contains three people's names")
    println(s"$first $second $third")
  }
  case _ => println("nothing matched")
}
```

与上面的例子很是类似，不过编译器在这里替我们做的事情更多了：

1. 调用unapplySeq，传入namesString
2. 接收返回值并判断返回值是None，还是Some
3. 如果是Some，则将其解开
4. 判断解开之后得到的sequence中的元素的个数是否是三个
5. 如果是三个，则把三个元素分别取出，赋值给first，second和third

如果没有unapplySeq方法和pattern match语法之间的这种结合，我们自己写代码来做这五件事会显得很是繁琐。
