---
layout: post
title: "Desugar Scala(16) -- lower bound"
date: 2014-06-21 11:32
comments: true
keywords: scala, lower bound
tags:
- Desugar_Scala
- Scala
---
Lower bound，不知道这个词的确切中文翻译是怎样的。我们直接看例子吧。

```scala
class Pair[T](val first: T, val second: T) {
  def replaceFirst[R >: T](newFirst: R): Pair[R] = new Pair[R](newFirst, second)
}
```

我们定义一个叫做Pair的类，其中可以包含两个元素，元素类型为泛型的T。

Pair类中有一个replaceFirst方法，用来把第二个元素和一个新的元素结合起来组成一个新的Pair。新的元素的类型是泛型的R。新组成的Pair的类型是Pair[R]。

到这里我们就要想了，一个T和一个R，它们俩怎么组成新的Pair呢？新的Pair的类型怎么能是Pair[R]呢？

replaceFirst的签名给我们说明了这一点。[R >: T]。这种标记的含义是说R是T的基类。那么一个T和一个R自然可以组合成一个R的Pair了。

单是这样干说，有点不好理解，我们看一个例子：

```scala
class Vehicle {}

class Car extends Vehicle {}

class Tank extends Vehicle {}
```

汽车和坦克都是机动车。

然后我们可以这样使用它们：

```scala
  val twoCars: Pair[Car] = new Pair(new Car(), new Car())
  val tankAndCar: Pair[Vehicle] = twoCars.replaceFirst(new Tank())
```

首先我们用两辆汽车组成一个Pair，其类型为Pair[Car]。

然后我们用一辆坦克替代原来的Pair中的第一个元素，让坦克和第二辆车组成一个新的Pair。新的Pari的类型是Pair[Vehicle]。

这里有一点tricky。我们调用replaceFirst的时候传递的参数的类型是Tank，这是否意味着在这里R就是Tank呢？

不是的，因为很明显Tank不是Car的基类，然而Tank是一个（is a）Vehicle，Vehicle同时也是Car的基类。于是此处的R就是Vehicle。得到的新的Pair自然就是Pair[Vehicle]。

也就是说R会被什么具体类型替换呢？这取决于T和newFirst的类型。

如果newFirst的类型刚好是T的基类，那太好了，R就直接是newFirst的类型。如果newFirst的类型不是T的基类，那R就会是T和newFirst的类型的共同基类。

### 这个东西挺麻烦的，它有啥用呢？

保证类型安全，Java没有提供给我们的类型安全。

还是刚才的那段代码：

```scala
  val twoCars: Pair[Car] = new Pair(new Car(), new Car())
  val tankAndCar: Pair[Vehicle] = twoCars.replaceFirst(new Tank())
```

其中的第二行，Scala可以很聪明的推断出replaceFirst的返回值类型是Pair[Vehicle]。实际上，如果我们试图把tankAndCar声明为Pair[Tank]的话，会看到编译时错误。

而类似的代码在Java里则没有这么幸运了：

```java
public class PairJ<T> {
    private T first;
    private T second;

    public PairJ(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return this.first;
    }

    public T second() {
        return this.second;
    }

    public <R> Pair<R> replaceFirst(R newFirst) {
        return new Pair(newFirst, second());
    }
}
```

为了标明区别，我们这次称之为PairJ。到这里忍不住要小小的黑Java一下，21行代码，和Scala的3行是等价的：）

我们重点看一下replaceFirst在这里的声明，其中声明了一个泛型参数R，但是R和T是没有任何关系的。实际上，在Java中，我们无法表达方法的泛型参数和类型的泛型参数之间的关系。（其原因请参看[这里](http://www.angelikalanger.com/GenericsFAQ/FAQSections/TypeParameters.html#FAQ107)）

我们写出如下的代码：

```java
        PairJ<Car> twoCars = new PairJ(new Car(), new Car());
        Tank actuallyACar = twoCars.replaceFirst(new Tank()).second();
```

先创建两辆车的Pair，然后把第一辆车替换成坦克。再把新组成的Pair里面的第二个元素（其类型是车）取出来，赋值给一个类型为坦克的变量。

如果我们编译这段代码，Java编译器会允许其通过。但是运行起来就会跑出类型转换异常。原因很明显，Car不能转换成Tank。

这个，就是刚才所说的类型安全性上的差异。

### 等等，脱衣服的部分呢？

之前的每一篇博客都会把Scala代码编译出的bytecode反编译成Java，来探索其语言特性是如何实现的。

而这一次颇为不同。之前的语言特性虽说Scala写起来比Java会简便一些，但是还没有超出Java的能力范围。多费点劲，用Java还是能做到。

而这一次，这种编译时类型安全检验的严格性，实在是在Java中无法表达的。这全靠了Scala编译器的功劳。

这就意味着，上面所定义的Pair这个类，如果你在Java中使用它，就会失去这种类型安全性。

Scala，这次算你牛，没扒掉你。
