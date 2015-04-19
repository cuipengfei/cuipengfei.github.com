---
layout: post
title: "论“如果Scala类有方法接收闭包，这些方法在Java里就不可用，因为Java目前尚不支持闭包。”这句话是错的"
date: 2014-06-27 20:54
comments: true
keywords: scala, java, interop
categories:
---
最近在看郑大翻译的《Scala程序设计》，其中第十一章有一句话：

> 如果Scala类有方法接收闭包，这些方法在Java里就不可用，因为Java目前尚不支持闭包。

口说无凭，拍照为证：

![](http://ww2.sinaimg.cn/large/8b1ece2agw1ehszbfbsj1j20xc18g17s.jpg)

当时看到这句话就感觉不对。因为JVM本身没有对函数式编程提供任何支持，所以无论是Java中常用的Guava，还是Scala，其对闭包的支持都是通过用类来包裹函数实现的。

如果说Java目前（其时Java 8还没面世）尚不支持闭包，那倒是还说得过去，因为毕竟是要用类包裹一层，不算真正的函数传递。

但是说如果Scala类有方法接收闭包，这些方法在Java里就不可用，那就不对了，包一层匿名内部类，我不还是能用吗？虽说不太好看，也不能说不能用啊。

为了验证一下，写点代码来试试吧。

```scala
class OnePluser {
  def plusOne(func: () => Int): Int = {
    func() + 1
  }
}
```

先定义一个Scala类，OnePluser，它有一个plusOne方法，接收一个函数，给函数的返回值加1，然后返回。非常简单。

在Scala里可以这么调用它：

```scala
class OnePluserCaller {
  def callIt(): Int = {
    new OnePluser().plusOne(() => 5)
  }
}
```

一样的简单，一个匿名函数传给它，这个匿名函数返回一个写死的5。这样最后的返回值会是6。

这段代码如果反编译成Java，会是这样的：

```java
public class OnePluserCaller
{
  public int callIt()
  {
    return new OnePluser().plusOne(new AbstractFunction0.mcI.sp() {
      public final int apply() { return apply$mcI$sp(); }
      public int apply$mcI$sp() { return 5; }

    });
  }
}
```

毫不出奇，调用plusOne的地方需要提供一个不接收参数，返回一个Int的函数，这个函数在Scala里是() => 5，编译出来就是一个AbstractFunction0的实例，其中的apply方法返回一个写死的5。可以想见，在plusOne中就会调用这个apply方法（已验证，非臆测）。

既然Scala的编译器可以用这种方式来实现函数的传递，那在Java代码中难道就不可以吗？

我们写点代码来验证一下吧：

```java
import scala.runtime.AbstractFunction0;

public class OnePluserCallerJ {
    public int callIt() {
        return new OnePluser().plusOne(new AbstractFunction0<Object>() {
            @Override
            public Integer apply() {
                return 5;
            }
        });
    }
}
```

这段代码基本就是照上面的反编译结果照抄的，AbstractFunction0的定义在scala.runtime.AbstractFunction0里，在scala-library-xxx.jar里。xxx是版本号。

上面的代码可以编译，可以运行，而且也可以得到6这个返回值。

由此可以证明在当前的Scala版本下（我现在用的是2.10.4）作者的这段话是不成立的。

但是，当时呢？

我查了一下，这本书的出版日期是09年，然后查了一下Scala的版本，08年的版本是2.7.7。于是我下载了scala-library-2.7.7.jar。发现其中确实没有AbstractFunction0的定义，但是现在版本的AbstractFunction0是实现了Function0的，而Function0的定义在2.7.7中是有的。而当时如果在Java中写一个实现了Function0的匿名内部类，不也是可行的吗？

Ok，不关注那么老的事儿了，可以确定的一点是，在当前版本下，在Java中是可以调用Scala中定义的接收闭包的函数的。

THE END
