---
layout: post
title: "褪去Scala的糖衣(12) -- implicit function（补）"
date: 2014-01-05 00:42
comments: true
categories: 
---

上次[博客谈到了implicit function](http://cuipengfei.me/blog/2014/01/01/desugar-scala-10/)，但是漏掉了一些东西，今天补上。

由于上次已经讲过implicit function的实现细节，这次就不再重复了。今天只补充上次漏掉了的implicit function的一种很好的实践。

先看一段specs2的测试代码：

```scala
import org.specs2.mutable._

class HelloWorldSpec extends Specification {

  "The 'Hello world' string" should {

    "contain 11 characters" in {
      "Hello world" must have size 11
    }
  }
}
```

我们试着理解这个测试代码在做什么的时候，无须多少思考，因为它和人类语言一样的亲近和自然。但是如果我们想知道specs2如何做到这一点时，就有点费解了。

我们知道xObject yMethod zParameter的写法是一个语法糖，它和xObject.yMethod(zParameter)是一样的。也就是说should和in都是方法名。于是，问题来了，should和in前面是个String啊，String上哪有这两个方法的定义？

想必答案你已经猜到了，specs2定义了一些implicit functions来把String转换成能够调用should和in的对象。至于是哪些对象，因为涉及到了specs2的细节，我们暂不讨论。我们试着模仿specs2来写出一段类似人类语言的代码：

```scala
class Person(name: String) {
  def eat(food: String) = println("I just ate " + food)
}
```

定义一个Person类，Person有一个名字，只会做一件事：吃。

然后我们可以这样调用它：

```scala
new Person("Xiao ming").eat("steamed dumplings")
```

我们让小明吃点蒸饺。或者也可以这样：

```scala
new Person("Xiao ming") eat "boiled dumplings"
```

再煮点饺子让小明吃。这就是我们前面提到过的xObject yMethod zParameter的写法了。让小明吃点东西还得new一个Person出来，这太麻烦了，我们定义一个implicit function：

```scala
implicit def stringToPerson(name: String) = new Person(name)
```

这样就可以把String转换成Person了。然后就可以像这样写：

```scala
"Xiao ming" eat "more dumplings"
```

虽然我们写的是“Xiao ming”，但是编译器会把stringToPerson的调用加上去，然后才调用eat方法。

这行代码看起来已经很接近人类语言了。

连吃三顿饺子，明哥，您饱了吧？