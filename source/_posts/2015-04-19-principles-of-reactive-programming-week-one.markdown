---
layout: post
title: "Principles of Reactive Programming Week One作业导学"
date: 2015-04-19 13:59
comments: true
keywords: scala, reactive, coursera
tags:
- MOOC
- Reactive
- Scala
---

#前尘
Principles of Reactive Programming在4月13号又开课了。
[https://www.coursera.org/course/reactive](https://www.coursera.org/course/reactive)

上次开课是在2013年的11月，当时我刚第一次上完Functional programming principles in Scala，热情很高于是就报名参加了这门课。
还群发了一个邮件找人一起上课。

但是上了几周发现有点难，于是就放弃了。现在去bitbucket看，最后一次push停留在了2013-11-18。

后来还在上海被8x鄙视于无形之中。

# 后世
14年做了几个月的Scala开发，后来Functional programming principles in Scala再次开课又上了一遍，拿了个认证证书。

感觉似乎可以再挑战一次。

# 今生

上课习得的知识放在脑子里是不牢靠的。大脑有遗忘周期。

需要有成文或者成代码的产出，作为日后回忆和做spaced repetition的资料。

于是就有了这个即将成为系列的博文中的第一篇《Principles of Reactive Programming Week One作业导学》。

这系列博文的目标读者仅限于报名参加了这门课并且看完了视频，看完了作业的instruction之后仍有困难的同学。

这系列博文不会公布作业的答案，那是违反Coursera的code of honor的。

我只会试着解释作业中已有的代码，以及应该如何入手。

其实，写这个系列博文对我的帮助比对读者的帮助要大。

# 正文

## Heap.scala
第一周的代码下载下来之后，先来看一下Heap.scala这个文件。

这个文件里定义了很多个trait。现在只需要关注其中一个Heap。

这个就是所有其他trait都会去extend的基类（这个说法合适吗？）。
它定义了所有Heap的实现者都需要实现的方法。

然后BinomialHeap完整实现了Heap定义的所有东西。

Bogus1BinomialHeap到Bogus5BinomialHeap都是继承自BinomialHeap，其中各自覆盖了BinomialHeap的不同方法，以不同的方式引入了bug。
第一周作业的目的就是用ScalaCheck把其中的bug找出来。

这个文件里还有一个IntHeap，这个稍后再说。

实现代码其实就只有这一个文件，接下来看测试代码。

## QuickCheckSuite.scala
这个文件里主要定义了QuickCheckSuite这个测试类。

这个测试类继承自FunSuite，这是ScalaTest的测试基类。同时mix in了Checkers，这是ScalaTest为了与ScalaCheck集成而提供的trait。

接下来看测试的case：

```scala
def checkBogus(p: Prop) {
  var foundBug = false
  try {
    check(p)
  } catch {
    case e: TestFailedException =>
      foundBug = true
  }
  assert(foundBug, "A bogus heap should NOT satisfy all properties. Try to find the bug!")
}

test("Binomial heap satisfies properties.") {
  check(new QuickCheckHeap with BinomialHeap)
}

test("Bogus (1) binomial heap does not satisfy properties.") {
  checkBogus(new QuickCheckHeap with Bogus1BinomialHeap)
}

test("Bogus (2) binomial heap does not satisfy properties.") {
  checkBogus(new QuickCheckHeap with Bogus2BinomialHeap)
}
```

可以看到，每个case都调用了check这个方法，或者是check的变体-checkBogus。

checkBogus里面则调用了check，并且assert说一定要出现TestFailedException异常了，测试才算成功。也就是说checkBogus的目的就是要在某些Heap的实现中找到bug。

现在来看check这个方法本身。它接受一个类型为Prop的参数，这些参数从哪儿来呢？这些参数就是：
```scala
new QuickCheckHeap with BinomialHeap

new QuickCheckHeap with Bogus1BinomialHeap
```
这些代码。

这就意味着QuickCheckHeap一定要是一个Prop，是不是这样呢？

## QuickCheckHeap.scala
那就到QuickCheckHeap.scala这个文件中来看一下。

可以看到QuickCheckHeap这个抽象类确实是extends了Properties，而properties又extends了Prop。那么，没问题，这个类型是匹配的。

QuickCheckHeap里可以定义任意多个property，这些property将会检查Heap的实现正确与否。

而且它还mix in了IntHeap，就是前面略过的那个trait。它的目的是锁定Heap这个trait里所定义的A这个元素的类型到Int。

## 全部连起来
第一周作业的已有代码很少，有用的就是这三个文件。

Heap.scala定义了很多个Heap的不同实现。有些是正确的，有些是有bug的。

QuickCheckSuite.scala则是测试的入口点，它由JunitRunner拽着跑起来。
其中的test case使用ScalaCheck去检查对于Heap这种数据结构恒定为true的properties是不是hold住的。

对于Heap这种数据结构恒定为true的properties从哪儿来呢？就来自于QuickCheckHeap.scala。
QuickCheckHeap本身是一个抽象类，不可以被实例化。但是由于有了牛逼的trait，就可以用这种代码：
```scala
new QuickCheckHeap with BinomialHeap

new QuickCheckHeap with Bogus1BinomialHeap
```
创建出实例，进行测试了。

最后，我们的任务就是在QuickCheckHeap.scala添加更多的properties，把所有实现有误的Heap都揪出来。

# 题外话

有没有发现QuickCheckHeap.scala里面有些奇怪的代码？

```scala
property("min1") = forAll { (heap: H, a: A) =>
  val min = if (isEmpty(heap)) a else findMin(heap)
  findMin(insert(min, heap)) == min
}
```

property("min should be min")，这看起来像是一个方法调用啊。

尼玛，方法调用后面怎么跟着一个等号啊？等号后面还有一个有返回值的表达式啊？

这是啥啊？

这是个乍一看很自然，但是仔细一想很费解的Scala语言特性-update方法。

详情请见我之前的一篇博客：[http://cuipengfei.is-a.dev/blog/2014/06/12/scala-update-method/](http://cuipengfei.is-a.dev/blog/2014/06/12/scala-update-method/)
