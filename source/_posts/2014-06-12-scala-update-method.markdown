---
layout: post
title: "抹掉Scala的糖衣(14) -- update method"
date: 2014-06-12 18:42
comments: true
categories: Desugar_Scala Scala
---
在Scala中，名字叫做update的方法是有特殊作用的。

比如：

```scala
val scores = new scala.collection.mutable.HashMap[String, Int]
scores("Bob") = 100
val bobsScore = scores("Bob")
```

以上三行代码，我们创建了一个可变的map来存储得分情况，然后我们记录了Bob的得分是100分，最后我们又把Bob的分数取出来了。

这三行代码看似平淡无奇，实则暗藏了一点点玄机。

第二行实际是调用了HashMap的update方法。

第三行实际是调用了HashMap的apply方法。

我们可以把上面的代码改写成下面的等价形式：

```scala
val scores = new scala.collection.mutable.HashMap[String, Int]
scores.update("Bob", 100)
val bobsScore = scores.apply("Bob”)
```

虽然等价，但是可读性却降低了一些。

apply方法我们之前讲过，就不再赘述。

update方法也不太复杂，它的规则就是：

```scala
x(y) = z
```

这样的代码会被编译为：

```scala
x.update(y, z)
```

这次博文名字虽然以抹掉糖衣开头，实则有点名不符实，因为这个语言特性过于简单，糖衣很薄，一抹就透。

这次的目的主要是介绍一个update方法的适用场景。

我们来看用来修改某个人地址的一段代码：

```scala
class AddressChanger {

  def update(name: String, age: Int, newAddress: String) = {
    println(s"changing address of $name, whose age is $age to $newAddress")
    //actually change the address
  }

}
```

我们可以这样来调用它：

```scala
val changer = new AddressChanger()
changer.update("xiao ming", 23, "beijing")
```

或者，我们也可以这样来调用它：

```scala
val addressOf = new AddressChanger()
addressOf(name = "xiao ming", age = 23) = "beijing"
```

这两段代码是等价的。

比较一下，前一种用法显得中规中矩，没什么特别好的，也没啥特大的毛病。

可是后一种用法就不同了，读起来很通顺，有读英语语句的感觉：把名字叫做小明，年龄23岁的人的地址改为北京。

如果再给AddressChanger加上一个apply方法，我们还可以写这样的代码：

```scala
val currentAddress = addressOf(name = "xiao ming", age = 23)
```

这样，读取和更新的代码都看起来非常自然。

如果我们把这两段代码连起来看：

```scala
val currentAddress = addressOf(name = "xiao ming", age = 23)
addressOf(name = "xiao ming", age = 23) = "beijing"
```

感觉甚好。

addressOf(name = "xiao ming", age = 23)可以看做一个整体，它就如同一个可读可写的属性。

我们把它放到赋值语句的右侧，就能取到小明的当前住址。

我们把它放到赋值语句的左侧，就能修改小明的住址。

apply和update都是蛮简单的语言特性，但是加以合适的应用，却能得到可读性极强的代码。
