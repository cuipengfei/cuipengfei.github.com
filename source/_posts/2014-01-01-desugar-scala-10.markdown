---
layout: post
title: "褪去Scala的糖衣(10) -- implicit function"
date: 2014-01-01 22:56
comments: true
categories: Desugar_Scala Scala
---

Implicit function，中文或许应该叫做隐式函数吧。主要用来作隐式类型转换。例子：

```scala
class Duck {
  def makeDuckNoise() = "gua gua"
}

class Chicken {
  def makeChickenNoise() = "ge ge"
}

class Ducken(chicken: Chicken) extends Duck {
  override def makeDuckNoise() = chicken.makeChickenNoise()
}
```

三个类，鸭子，鸡，还有伪装成鸭子的鸡。如果有这么一个函数：

```scala
def giveMeADuck(duck: Duck) = duck.makeDuckNoise()
```

该函数要求我们给它提供一只鸭子，我们可以这么调用它：

```scala
giveMeADuck(new Duck)
```

要鸭子就给鸭子，没问题。或者是也可以这样：

```scala
giveMeADuck(new Ducken(new Chicken))
```

把一只鸡伪装成鸭子给它，也没问题。但是如果直接把鸡给它就不行了：

```scala
giveMeADuck(new Chicken)
```

编译器会给出type mismatch的错误。

我们已经有一个Ducken类可以用来把鸡化装成鸭子了，那有没有一种方法可以让我们鬼鬼祟祟的就把化妆这件事儿做了？那就不用每次都明火执仗的了。

答案就是用implicit function：

```scala
implicit def chickenToDuck(chicken: Chicken) = new Ducken(chicken)
```

它的用法很简单，给函数加上implicit这个修饰符，函数的参数是鸡，返回类型是鸭子 （或者是任何鸭子的子类都行）。

然后这行代码就可以编译了：

```scala
giveMeADuck(new Chicken)
```

来看一下反编译的结果：

```java
public String giveMeADuck(Duck duck) {
    return duck.makeDuckNoise();
}

public Ducken chickenToDuck(Chicken chicken) {
    return new Ducken(chicken);
}
```

giveMeADuck和chickenToDuck都被编译成了中规中矩的方法。giveMeADuck(new Chicken)则被编译成了这个样子：

```java
giveMeADuck(this.chickenToDuck(new Chicken()));
```

一点意外都没有，implicit在反编译后无影无踪，它就是很纯的一点点糖。

我意淫一下：编译器在发现我们给它的chicken和想要的duck之间存在type mismatch的时候先不着急给出错误，看看有没有标为implicit的函数可以把chicken变成duck或者其子类，如果有则调用该方法，没有再给出错误。
