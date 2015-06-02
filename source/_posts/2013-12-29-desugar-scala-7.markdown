---
layout: post
title: "剥掉Scala的糖衣(7) -- apply method"
date: 2013-12-29 19:20
comments: true
categories: Desugar_Scala Scala
---

apply method是一个很简单的语言特性。如果一个class或者是object有一个主要的方法，那么与其每次显式的调用这个主要的方法，还不如隐式调用。举个例子：

```scala
class Kettle {
  def boil(water: Water) = {
    water.isWarm = true
    water
  }
}
```

一个水壶的主要作用就是烧开水，于是我们每次都要调用boil方法来烧开水:

```scala
  val kettle: Kettle = new Kettle()
  kettle.boil(new Water())
```

如果要把它改写成apply method的方式，只需要给boil改个名字就好了：

```scala
class Kettle {
  def apply(water: Water) = {
    water.isWarm = true
    water
  }
}
```

然后需要烧开水时，就只需把水倒进壶里就好了：

```scala
val kettle: Kettle = new Kettle()
kettle(new Water())
```

这个语言特性的实现很简单，不用说也可以猜到，无非就是把kettle(water)编译成kettle.apply(water)。反编译一下，Kettle class的定义毫无出奇之处：

```java
public class Kettle
{
  public Water apply(Water water)
  {
    water.isWarm_$eq(true);
    return water;
  }
}
```

烧水的代码被编译成了这样：

```java
    Kettle kettle = new Kettle();
    kettle.apply(new Water());
```

我们刚开始说过apply method也可以用在object里。下面举个例子，我们把Kettle烧水的能力移到它的companion object里面去：

```scala
object Kettle {
  def apply(water: Water) = {
    water.isWarm = true
    water
  }
}
```

然后烧水的时候就可以这样调用：

```scala
Kettle(new Water())
```

反编译出来的结果大同小异，就不再赘述了，唯一的区别就是apply变成了静态方法。

上面这个水壶烧水的例子并不是最佳实践的作法。apply method的一个最佳实践是用来做工厂。比如Scala标准库中的List就提供了apply方法来给我们创建List：

```scala
    List(1, 2, 3)
    List("a", "b", "c")
```

或者是Map也有类似的用法：

```scala
Map(1 -> "a", 2 -> "b", 3 -> "c")
```

以上的两段代码并不是在调用List和Map的constructor，而是在调用List和Map的companion objects的apply方法。

Map的创建可以用apply method，而Map最常用的一个方法就是通过key来取得value，这个也有apply method来做：

```scala
val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
map(2)
```
