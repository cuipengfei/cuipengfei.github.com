---
title: Future-异步函数的两个视角
date: 2019-12-29 21:02:39
tags:
- Scala
- Future
- Reactive
- 🐸
---

# 前言

最近在看Scala Reactive的一些内容
想起了很久之前写过一篇叫做**自己动手实现Promises/A+规范**的博客，用JS实现了一个简版的Promise: 
https://cuipengfei.me/blog/2016/05/15/promise/

我在当时的一段演示代码里面写了两句注释：

> Promise的作用在于
> 1. 给异步算法的编写者和使用者之间提供一种统一的交流手段
> 2. 给异步算法的使用者提供一种组织代码的手段,以便于将一层又一层嵌套的业务主流程变成一次一次的对then的调用

不过当时的博客里只讲了实现Promise规范的事情,并没有详细解释过这两句话。

既然又遇到了这个话题，不妨写点Scala来把当时没展开写到的内容补充一下吧。

看一下两个程序员的故事。

# 我是异步函数的编写者

我写了两个异步函数,来提供给其他程序员同事使用。

```scala
  type CallBack = Try[String] => Unit

  def pretendCallAPI(callBack: CallBack, okMsg: String, failedMsg: String) = {
    val task = new TimerTask {
      override def run() = {
        val percentage = Random.between(1, 100)

        if (percentage >= 50)
          callBack(Success(okMsg))
        else if (percentage <= 30)
          callBack(Failure(new Exception(failedMsg)))
        else
          callBack(Failure(new Exception("network problem")))
      }
    }

    new Timer().schedule(task, Random.between(200, 500))
  }

  val searchTB = pretendCallAPI(_, "product price found", "product not listed")
  val buyFromTB = pretendCallAPI(_, "product bought", "can not buy, no money left")
```

这两个异步函数: `searchTB`用来从淘宝搜索物品,另一个`buyFromTB`用来购买搜到的物品。

由于仅仅是为了演示而写的,他们两个都是基于一个叫做`pretendCallAPI`的函数实现的。
顾名思义,`pretendCallAPI`并不会真的去调用淘宝的API,而只是模拟API的行为。

这个`pretendCallAPI`函数有几个行为特征:
+ 每次耗时200到500毫秒之间
+ 每次执行有50%的几率成功
+ 20%的几率遇到网络故障
+ 另外30%的几率虽然网络没问题但是服务器会给你一个非正常的结果

当然,由于我写的是异步算法,需要避免block caller thread。
所以当你调用`pretendCallAPI`的时候,这个函数是**瞬间立即返回的**。
那么当然我就**无法在函数返回的时候return什么有用的东西**给你了。

如果你想知道执行的结果到底是啥,你需要传给我一个`CallBack`,在我执行完后,通过`CallBack`来告知你执行的结果。
这个`CallBack`的完整签名表达式展开是`Try[String] => Unit`

大家看`searchTB`和`buyFromTB`可能觉得他们长的有点奇怪,这是Scala里柯里化的写法。
也就是通过把`pretendCallAPI`包一层来构造新的函数,锁死两个参数,剩下的一个参数(也就是`CallBack`)就变成了新构造出来的函数的唯一参数了。
也就是说`searchTB`和`buyFromTB`的签名是`(Try[String] => Unit) => Unit`。

关于柯里化这个语言特性的更多信息:
https://cuipengfei.me/blog/2013/12/25/desugar-scala-6/

好了,现在这两个函数可以提供给大家使用了。

# 我是异步函数的调用者

听说异步函数已经写好了,我终于可以用他们来实现剁手业务了。

听函数作者讲了一下,用起来应该不会很难,那我来实现一下吧。

```scala
    def searchPriceThenBuy() = {
      searchTB {
        case Success(searchMsg) =>
          println(searchMsg)
          buyFromTB {
            case Success(buyMsg) => println(buyMsg)
            case Failure(err) => println(err.getMessage)
          }
        case Failure(err) => println(err.getMessage)
      }
    }
```
使用`searchTB`和`buyFromTB`并不难. 他们两个都是接受`CallBack`作为参数的函数。 
`CallBack`本身是个函数,它的签名是```Try[String] => Unit```。
而```Try```有两种形式,分别是```Success```和```Failure```。

所以在调用`searchTB`和`buyFromTB`的时候,必须把两个分支都给到(以免pattern match不到)。
这样在异步函数有结果的时候(无论成败)才能call back过来到我的代码,以便我能够在合适的时机做后续的处理(无论是基于成功做后续业务,还是做error handling)。

关于pattern match,可以参考这里:
https://cuipengfei.me/blog/2013/12/29/desugar-scala-8/
https://cuipengfei.me/blog/2015/06/16/visitor-pattern-pattern-match/

这段代码跑一下的话,会有这么几种结果:

+ 搜到了,也买到了
+ 搜到了,购买时遇到了网络故障
+ 搜到了,由于支付宝钱不够而没买到
+ 没搜到,购买行为未触发
+ 搜索遇到网络故障,购买行为未触发

一共就这么几种可能,因为`pretendCallAPI`是跑概率的,多跑几次这些情况都能遇到。

虽然实现出来不难,执行结果也没问题,但是总有点**隐忧**。

这里只有`searchTB`和`buyFromTB`两个函数,如果其他场景下我需要把更多的异步函数组合起来使用呢?岂不是要缩进很多层?

当然,缩进只是个视觉审美问题,是个表象,不是特别要紧.关键是**我的业务逻辑很容易被这样的代码给割裂的鸡零狗碎**,那就不好了。

# 镜头切回到异步函数编写者

之前写的两个函数反馈不太好，主要是因为`CallBack`的原因。

如果只有一个异步函数单独使用，用`CallBack`也没什么太大的问题，如果是很多个异步函数组合使用确实会比较麻烦。

既然如此，那我改版一下吧。

```scala
  type CallBackBasedFunction = (CallBack) => Unit

  def futurize(f: CallBackBasedFunction) = () => {
    val promise = Promise[String]()

    f {
      case Success(msg) => promise.success(msg)
      case Failure(err) => promise.failure(err)
    }

    promise.future
  }

  val searchTBFutureVersion = futurize(searchTB)
  val buyFromTBFutureVersion = futurize(buyFromTB)
```

先定义一个`CallBackBasedFunction`，它代表一个接受`CallBack`为参数的函数的签名。

表达式展开后就是：　`(Try[String] => Unit) => Unit`
这就符合了`searchTB`和`buyFromTB`两个函数的签名。

`futurize`算是个higher order function,它接受一个`CallBackBasedFunction`作为参数，返回一个`() => Future[String]`。
(`Future`是Scala标准库的内容，可以认为和JS Promises/A+是类似的概念)

也就是说`futurize`可以把`searchTB`和`buyFromTB`改造成返回`Future`的函数。上面代码最后两行就是改造的结果。

这样，原本接受`CallBack`做为参数且没有返回值的函数，就变成了不接受参数且返回`Future`的函数。

再看`futurize`的具体实现，它使用了Scala的`Promise`，让返回的`Future`在原版函数成功时成功，在原版函数失败时失败。

这样，我就得到了`searchTBFutureVersion`和`buyFromTBFutureVersion`这两个**仍然是立即瞬间返回，不会block caller thread**的函数。

关于Scala中Promise和Future的更多信息：
https://docs.scala-lang.org/overviews/core/futures.html

# 镜头再切到异步函数调用者

现在有了`searchTBFutureVersion`和`buyFromTBFutureVersion`，我来试着重新实现一次：

```scala
    def searchPriceThenBuyFutureVersion() = {
      val eventualResult = for {
        searchResult <- searchTBFutureVersion().map(msg => println(msg))
        buyResult <- buyFromTBFutureVersion().map(msg => println(msg))
      } yield (searchResult, buyResult)

      eventualResult.onComplete {
        case Failure(err) => println(err.getMessage)
        case _ =>
      }
    }
```

这里用到了Scala的for comprehension，编译后会变成map，flatMap等等monadic operator。
而map,flatMap等操作符正是Scala中Future拿来做组合用的。

这样，用for把两个返回Future的异步函数组织起来，形成一个新的Future，然后在新的Future complete时统一处理异常。

关于for的更多信息：
https://cuipengfei.me/blog/2014/08/30/options-for/

这次实现的代码与上次的行为是一致的,没什么两样。
不过我的**业务代码从鸡零狗碎变成了平铺直叙平易近人**。
(这种效果在这里表现的并不是特别突出，不过很容易想象如果需要组合使用的异步函数更多一些的话，这种效果的好处就显露出来了)

当然了，让业务代码易读易懂主要还是要靠**个人奋斗**，而有了Promise和Future这种**历史进程**的推力，则更有增益作用。

# 总结

上面用四个镜头展现了两个角色的视角。
现在可以回到最开头的那两句话了：

> Promise的作用在于
> 1. 给异步算法的**编写者**和**使用者**之间提供一种统一的**交流手段**
> 2. 给异步算法的**使用者**提供一种**组织**代码的手段,以便于将一层又一层**嵌套**的业务主流程变成一次一次的对then的调用

统一的交流手段，其实就是异步函数的签名问题。
由于需要处理的业务五花八门，异步函数接受的参数列表没法统一，但是返回值是可以的。

一个异步函数，接受了外界给的参数，立即瞬间返回一个Js的Promise或者Scala的Future(或者是任何语言中类似概念的叫法)。
然后在异步任务执行完的时候把Promise resolve/reject掉(让Future success或者failure),借此来让调用方的代码知道该到了它跑后续处理的时候了。

这样我们就获得了一个**sensible default**，无需在每次设计异步函数的时候都去商议该返回什么东西，该怎么获得异步执行的结果。

组织代码的手段，就是第四个镜头的内容了，不再赘述。