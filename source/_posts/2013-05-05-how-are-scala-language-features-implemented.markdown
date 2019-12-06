---
layout: post
title: "Scala中的语言特性是如何实现的(1)"
date: 2013-05-05 22:02
comments: true
tags:
- Desugar_Scala
- Scala
---

Scala可以编译为Java bytecode和CIL，从而在JVM和CLI之上运行。Scala有很多在Java和C#的世界中显得陌生的语言特性，本文将分析这些语言特性是如何实现的。

## object

Scala中可以像这样创建object：

```scala
object HowIsObjectImplementedInScala {
  def printSomething() {
    println("printSomething")
  }
}
```
然后在代码的其他地方调用printSomething，一个object究竟是什么东西呢？
我们将这段Scala编译为Java bytecode，然后反编译为Java，会发现编译器为HowIsObjectImplementedInScala这个object生成了两个类：

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

和Java代码大同小异，除了静态构造和某几个关键字外，基本一样。一个object就是一个Scala编译器帮我们实现的singleton。

## var和val

var：可变。val：不可变。关于这两个关键字何时该使用哪一个，这里不做讨论，我们只是观察这二者在编译后是如何被表示的。

这段Scala代码：
```scala
class HowAreVarAndValImplementedInScala {
  var v1 = 123
  val v2 = 456

  def method1() = {
    var v3 = 123
    val v4 = 456
    println(v3 + v4)
  }
}
```

定义了两个字段一个var，一个val，方法中定义了两个局部变量，一个var，一个val。

编译为Java bytecode并反编译之后：

```java
public class HowAreVarAndValImplementedInScala
{
  private int v1 = 123;
  private final int v2 = 456;

  public int v1()
  {
    return this.v1;
  }

  public void v1_$eq(int x$1) { this.v1 = x$1; }

  public int v2() { return this.v2; }

  public void method1() {
    int v3 = 123;
    int v4 = 456;
    Predef..MODULE$.println(BoxesRunTime.boxToInteger(v3 + v4));
  }
}
```

声明为字段的v1和v2，一个是普通字段，另一个则被标记为final。编译器为v1生成了getter和setter，为v2则只有getter，因为v2作为immutable的字段是不可以被重新赋值的。

有趣的是方法中的局部变量都是普通的变量，没有被final修饰。

再来看这段Scala编译为CIL再反编译为C#之后的样子：

```c#
public class HowAreVarAndValImplementedInScala : ScalaObject
{
  private int v1;
  private int v2;

  public override int v1()
  {
    return this.v1;
  }

  public override void v1_$eq(int x$1)
  {
    this.v1 = x$1;
  }

  public override int v2()
  {
    return this.v2;
  }

  public override void method1()
  {
    int v3 = 123;
    int v4 = 456;
    Predef$.MODULE$.println(v3 + v4);
  }

  public HowAreVarAndValImplementedInScala()
  {
    this.v1 = 123;
    this.v2 = 456;
  }
}
```

有一个明显的问题，v2没有标为readonly（C#世界中用于声明变量不可以重新赋值的关键字），这是compiler的bug吗？

除此之外，和Java代码一致。但是有趣的是代码中的所有public方法（包括上一段演示object的代码）都被标为了override，原因不明。

## 小结

本来以为研究这么简单的两个语言特性不会有啥收获，仅仅是反编译一下，看看compiler都做了啥，满足下好奇心罢了。

结果还是有意外收获，我在反编译后的代码中发现了三个有趣的问题：

* 在Scala中被声明为val的v4为什么在反编译的Java中不是final的呢？
* 在Scala中被声明为val的v2为什么在反编译的C#中不是readonly的呢？
* 为什么反编译出来的C#代码中的实例级公开方法都是标有override的呢？

为什么呢？为什么呢？为什么呢？答案下期揭晓。
