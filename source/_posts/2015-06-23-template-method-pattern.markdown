---
layout: post
title: "模板方法模式：子类型多态和高阶函数"
date: 2015-06-23 15:42
comments: true
tags:
- Scala
- OODP
---

> 模板方法模式定义了一个算法的步骤，并允许次类别为一个或多个步骤提供其实践方式。让次类别在不改变算法架构的情况下，重新定义算法中的某些步骤。

以上是wiki对模板方法的定义。

比较容易理解，我们有一个算法，其中某些步骤是确定的不太会变的代码。而另外一些步骤则需要变化并且自由组合。

《Head First Design Patterns》里有一个🌰：

假设我们需要制作咖啡因饮品，其实就是咖啡和茶。制作步骤有些类似，分为四步：1烧水，2泡，3装杯，4加调料。

其中第一步和第三步是稳定的代码，变化可能性不大，而第二步和第四步则每种饮品有自己的风味。

这样就有了下面的代码：

# Java

<script src="http://gist-it.appspot.com/https://github.com/cuipengfei/BlogCode/blob/master/OODPFP/src/main/java/templatemethodJ/CaffeineBeverage.java?slice=1:&footer=minimal">
</script>

首先有一个咖啡因饮品的抽象类，定义一个算法骨架：1烧水，2泡，3装杯，4加调料。
其中的第二步和第四步是有待实现的抽象方法，留给子类决定怎么搞。第一步和第三步是写死的。

<script src="http://gist-it.appspot.com/https://github.com/cuipengfei/BlogCode/blob/master/OODPFP/src/main/java/templatemethodJ/Coffee.java?slice=1:">
</script>

接下来是咖啡，它实现了过滤咖啡和添加牛奶、糖的步骤。这样当它的实例的prepareRecipe方法被调用时就会执行父类的烧水、装杯，以及自己的泡和加调料。

<script src="http://gist-it.appspot.com/https://github.com/cuipengfei/BlogCode/blob/master/OODPFP/src/main/java/templatemethodJ/Tea.java?slice=1:">
</script>

还有，就是茶了。它和咖啡不一样，是用浸泡而不是过滤，加的是柠檬而不是牛奶和糖。

<script src="http://gist-it.appspot.com/https://github.com/cuipengfei/BlogCode/blob/master/OODPFP/src/main/java/templatemethodJ/BeverageTestDrive.java?slice=1:">
</script>

最后用一个main函数来执行制作咖啡和茶的代码。

很好，如果再有其他的咖啡因饮品，只需要增加一个子类，并且实现两个方法就好了。只要我们对于四个步骤的定义在该领域中足够稳定，这份代码就是很好很强大，易于扩展的。

有代码如此，夫复何求呢？

##  不过再想一下

这个模式想要达到的，不过是将一个算法的某些部分做的灵活一些，可以自由替换和组合。

那这个，不就是函数组合吗？如果我们使用的是允许高阶函数的语言的话，那还有什么必要把这些函数包装在类里呢？

# functions

接下来是用Scala实现的版本：

<script src="http://gist-it.appspot.com/https://github.com/cuipengfei/BlogCode/blob/master/OODPFP/src/main/scala/templatemethodS/Beverages.scala?slice=1:">
</script>

首先，定义三个type，分别是泡和加调料这两个步骤，还有饮品本身（这三个type其实是一样的，看起来有点傻）。

然后有一个算法骨架，把第一和第三步锁死，把第二和第四步空出来，分别用一个参数来实现注入不同的实现。

接下来有泡和加调料的四种不同实现，分别是一个函数，符合各自的函数签名。

最后，用一个main函数来执行。可以看到，泡和加调料的函数是作为参数传入的。如果我们需要加牛奶和糖的茶，或者是柠檬味的咖啡的话，也会变得非常容易。

就这样，51行代码变成了28行。四个类变成了一个object。

而如果是要用子类型多态（subtype polymorphism）来做到这样的自由组合，那么我们需要的或许就是策略模式，把泡和加调料分别写成接口并提供不同的实现类来组合。可以想象，这会导致很多的boilerplate。

# 结语

Java代码中实现多态的方式是通过子类继承父类并且实现抽象方法来实现的。而Scala代码中则是通过把不同的函数传入骨架组合出一个新的函数来实现的。

子类型多态（subtype polymorphism）是个好东西，但是在某些场景下显得有点重。能用高阶函数这种轻量级的方式来实现的时候，就没有必要选择子类型多态这种过重的方式。
