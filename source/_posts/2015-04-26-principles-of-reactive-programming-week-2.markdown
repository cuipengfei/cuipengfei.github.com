---
layout: post
title: "Principles of Reactive Programming Week Two作业导学"
date: 2015-04-26 17:56
comments: true
tags:
- MOOC
- Reactive
- Scala
---

#声明
这系列博文的目标读者仅限于报名参加了这门课并且看完了视频，看完了作业的instruction之后仍有困难的同学。

这系列博文不会公布作业的答案，那是违反Coursera的code of honor的。

我只会试着解释作业中已有的代码，以及应该如何入手。

其实，写这个系列博文对我的帮助比对读者的帮助要大。


这周的作业不太难，主要就是一个观察者模式。

#Signal是怎么work的？

```scala
scala> val a = Var(1)
a: calculator.Var[Int] = calculator.Var@7ca6f5b9

scala> val b = Var(2)
b: calculator.Var[Int] = calculator.Var@2286d26

scala> val c = Signal(a() + b())
c: calculator.Signal[Int] = calculator.Signal@5c60548d

scala> c()
res8: Int = 3

scala> a()=10

scala> c()
res10: Int = 12

scala> b()=20

scala> c()
res12: Int = 30
```

如果能搞懂上面的代码是如何work的，作业题中需要用到Signal的地方就不会有太大问题了。

a=1，b=2，c=a+b，所以c就是3。

a变成10之后c就变成了12（10+2）。

b再变成20之后，c就变成了30（10+29）。

这个级联的变化是如何发生的呢？

有两个关键点：

* Signal的constructor
* Signal的update方法

先看Signal的constructor。

```scala
class Signal[T](expr: => T) {
  //......
}
```

以上是它的签名，关键在于expr的类型签名，expr的类型不是T，而是=>T。

这就意味着expr可以是任何类型为T的表达式，可以是一个字面量，也可以是任意复杂的代码块。

比如Signal(123)是可以的，Signal(complicatedMethodCall())也可以。

最上面那块代码中的val c = Signal(a() + b())就属于后一种。

a() + b()不会被立即求值成3然后传入Signal的constructor，而是整体作为一个可以被反复求值的表达式被记录在Signal的实例中。

constructor的入口参数可以被反复求值是级联变化的基础，那是什么触发了真正的变化呢？

那就是关键点之二：update方法。

update方法的妙处在于，如果一个类A有update方法，那么：

```scala
val x = new A()
x(y)=z
```

在编译之后会变成这样：

```scala
val x = new A()
x.update(y,z)
```

详情请见我之前的一篇博客：[http://cuipengfei.me/blog/2014/06/12/scala-update-method/](http://cuipengfei.me/blog/2014/06/12/scala-update-method/)

Signal的update方法是protected的，不可访问，所以它只可以从变，不可自变。

而Var把update方法public出来了，这样，在下面这样的代码执行时：

```scala
a()=10
//a.update(10)
```

a就会通知它的observers去重新求值。
这样就实现了a或者b这样的Var变化的时候，c这样的Signal跟着变化的效果。

搞懂了上面的内容就足以去做作业了。

#怎么和html页面结合起来的？

执行instruction里提到的webUI/fastOptJS这个task就会把Scala作业代码编译成js。

这个task是scalajs这个dependency带进来的（在webui.sbt里）。

webui这个项目里有一个CalculatorUI.scala文件，也会被编译成js。其中的代码就把作业代码和html的UI结合起来了。

就是这样了，这周的作业不难懂也不太难做。
