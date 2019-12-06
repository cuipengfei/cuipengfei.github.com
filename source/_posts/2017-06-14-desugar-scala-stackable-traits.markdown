---
layout: post
title: "Desugar Scala(18) -- stackable traits"
date: 2017-06-14 14:00
comments: true
tags:
- Desugar_Scala
- Scala
---

Stackable traits是一种怎样的特性呢？

来举一个🌰

```scala
abstract class IntQueue {
  def get(): Int
  def put(x: Int)
}
```

定义一个IntQueue，抽象类，定义了get和put，没有实现。

```scala
class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]

  def get() = buf.remove(0)

  def put(x: Int) = {
    buf += x
  }
}
```

再定义一个BasicIntQueue，把上述IntQueue实现了。
它的实现没有什么花样，就是先进先出。

接下来就有意思了：

```scala
trait Incrementing extends IntQueue {
  abstract override def put(x: Int) = {
    super.put(x + 1)
  }
}

trait Doubling extends IntQueue {
  abstract override def put(x: Int) = {
    super.put(2 * x)
  }
}
```

定义了两个trait，都扩展自IntQueue。
一个是把数字先加一再放进队列，另一个是先把数字加倍再放入队列。

要注意这里的modifier：abstract override，以及在trait中对super的调用。稍后反编译的时候可以看懂它们的真实含义。

那这两个trait可以怎么使用呢？

```scala
class MagicQueue extends BasicIntQueue with Incrementing with Doubling
```

定义一个MagicQueue，它扩展自BasicIntQueue，同时mixin了上面的两个trait。

MagicQueue它自己是一行实现代码都没有的，那么它的行为会是什么样子呢？

```scala
val queue = new MagicQueue

queue.put(100)
queue.get() //会返回201

queue.put(500)
queue.get() //会返回1001
```

可以看到，它会先把数字乘以二，然后加一再放入队列。

MagicQueue继承了BasicIntQueue，混入了Incrementing和Doubling，它的行为就会是先跑Doubling后跑Incrementing最后跑BasicIntQueue（从右到左依序生效）。

这是种很实用的语言特性，你可以写很多个不同的trait，让它们都extend IntQueue。
同时写很多class让它们实现IntQueue。
然后每一个实现了IntQueue的class都可以和任意一个或者任意多个trait随意组合应用。

这给语言的使用者提供了很强的composition的便利性。

那下面看下这个语言特性是如何实现的。

```java
public abstract class IntQueue
{
    public abstract int get();

    public abstract void put(final int p0);
}

public class BasicIntQueue extends IntQueue
{
    private final ArrayBuffer<Object> buf;

    private ArrayBuffer<Object> buf() {
        return this.buf;
    }

    public int get() {
        return BoxesRunTime.unboxToInt(this.buf().remove(0));
    }

    public void put(final int x) {
        this.buf().$plus$eq((Object)BoxesRunTime.boxToInteger(x));
    }

    public BasicIntQueue() {
        this.buf = (ArrayBuffer<Object>)new ArrayBuffer();
    }
}
```

首先，IntQueue和BasicIntQueue反编译之后平淡无奇，一个抽象类，一个实现类。

```java
public interface Doubling
{
    void chap12$Doubling$$super$put(final int p0);

    void put(final int p0);
}

public abstract class Doubling$class
{
    public static void put(final Doubling $this, final int x) {
        $this.chap12$Doubling$$super$put(2 * x);
    }

    public static void $init$(final Doubling $this) {
    }
}
```

Doubling这个trait则被编译成了一个接口加一个抽象类，其中除了put之外还有一个名字有点奇怪的方法声明。
稍后可以看到它有什么用。

```java
public interface Incrementing
{
    void chap12$Incrementing$$super$put(final int p0);

    void put(final int p0);
}

public abstract class Incrementing$class
{
    public static void put(final Incrementing $this, final int x) {
        $this.chap12$Incrementing$$super$put(x + 1);
    }

    public static void $init$(final Incrementing $this) {
    }
}
```

Incrementing则和Doubling是一个路数。

（这里出现的chap12字样是我写代码时package的名字）

最后揭露真相的时候到了：

```java
public class MagicQueue extends BasicIntQueue implements Incrementing, Doubling
{
    public void chap12$Doubling$$super$put(final int x) {
        Incrementing$class.put((Incrementing)this, x);
    }

    public void put(final int x) {
        Doubling$class.put((Doubling)this, x);
    }

    public void chap12$Incrementing$$super$put(final int x) {
        super.put(x);
    }

    public MagicQueue() {
        Incrementing$class.$init$((Incrementing)this);
        Doubling$class.$init$((Doubling)this);
    }
}
```

MagicQueue本身被编译成了以上的样子。

我们看一下它的put方法被调用时会怎样呢？

1. 它去调用Doubling$class.put这个静态方法，把自己和数字都传入

2. Doubling$class.put则会先把数字乘以二，然后把乘积传给MagicQueue的chap12$Doubling$$super$put

3. MagicQueue的chap12$Doubling$$super$put方法则会把MagicQueue自己的实例以及乘积都传给Incrementing$class.put这个静态方法

4. Incrementing$class.put则会把接收到的参数，也就是乘积，加一，然后把加和后的数字传给MagicQueue的chap12$Incrementing$$super$put

5. MagicQueue的chap12$Incrementing$$super$put最终把乘以二又加了一的数字传给了super.put

6. super.put其实就是BasicIntQueue.put了，到这里终于把数字存到ArrayBuffer里面了

这样，Doubling,Incrementing,BasicIntQueue它们三个的行为就堆叠（stackable）在一起了。
