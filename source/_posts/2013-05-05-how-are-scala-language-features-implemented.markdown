---
layout: post
title: "Scala中的语言特性是如何实现的"
date: 2013-05-05 22:02
comments: true
categories: 
---

Scala可以编译为Java ByteCode和CIL，从而在JVM和CLI之上运行。Scala有很多不同于Java和C#的语言特性，本文将分析这些语言特性都是如何实现的。

##object

Scala中可以像这样创建object：

```scala
object HowIsObjectImplementedInScala {
  def printSomething() {
    println("printSomething")
  }
}
```
然后在代码的其他地方随意调用printSomething，一个object究竟是什么东西呢？
我们将这段Scala编译为Java ByteCode，然后反编译为Java，会发现HowIsObjectImplementedInScala这个object会产生两个类：

```java
public final class HowIsObjectImplementedInScala
{
  public static void printSomething()
  {
    HowIsObjectImplementedInScala..MODULE$.printSomething();
  }
}

public final class HowIsObjectImplementedInScala$
{
  public static final  MODULE$;

  static
  {
    new ();
  }

  public void printSomething()
  {
    Predef..MODULE$.println("printSomething");
  }

  private HowIsObjectImplementedInScala$()
  {
    MODULE$ = this;
  }
}
```

第一个类只包含一个静态方法，其实现依赖于第二个叫做HowIsObjectImplementedInScala$的类。

HowIsObjectImplementedInScala$是一个单例，其静态块实例化自己并把this赋值给MODULE$这个public static的成员，从而可以被外界访问。

同样，我们可以把这段代码编译为CIL，然后反编译为C#:

```c#
public sealed class HowIsObjectImplementedInScala
{
  public static void printSomething()
  {
    HowIsObjectImplementedInScala$.MODULE$.printSomething();
  }
}

public sealed class HowIsObjectImplementedInScala$ : ScalaObject
{
  public static HowIsObjectImplementedInScala$ MODULE$;
  public override void printSomething()
  {
    Predef$.MODULE$.println("printSomething");
  }
  private HowIsObjectImplementedInScala$()
  {
    HowIsObjectImplementedInScala$.MODULE$ = this;
  }
  static HowIsObjectImplementedInScala$()
  {
    new HowIsObjectImplementedInScala$();
  }
}
```

和Java代码大同小异，除了静态构造和某几个关键字外，基本一样。