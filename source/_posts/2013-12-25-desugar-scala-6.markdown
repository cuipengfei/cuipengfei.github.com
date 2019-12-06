---
layout: post
title: "褪去Scala的糖衣(6) -- partial application"
date: 2013-12-25 14:40
comments: true
tags:
- Desugar_Scala
- Scala
---

这篇博客介绍一下Scala中的partial application，局部应用，或者叫做柯里化。

所谓柯里化就是指把一个接受多个参数的函数的一部分参数写死，剩下的一部分由调用者提供。

用Java代码来表述，大概可以写成这样：

```java
    public String greet(String greeting, String name) {
        return greeting + " " + name;
    }

    public String sayHello(String name) {
        return greet("Hello", name);
    }

    public String greetXiaoMing(String greeting) {
        return greet(greeting, "Xiao Ming");
    }
```

greet用来给某个不确定的人打个不确定的招呼。

sayHello用来给某个不确定的人说一句固定的Hello。

greetXiaoMing用来给一个固定的人小明打一个不确定的招呼。

如果用Scala来表达同样的含义的话，可以这样写：

```scala
  def greet(greeting: String, name: String) = greeting + " " + name

  def sayHello = greet("hello", _: String)

  def greetXiaoMing = greet(_: String, "Xiao Ming")
```

其实比Java代码也简单不了多少。只是把暂时不确定的参数用下划线指代出来。

然后我们就可以在稍后需要调用它们的时候再把参数传入：

```scala
  sayHello("world")
  greetXiaoMing("Ni Hao")
```

然后我们看一下这个语言特性是怎么实现的呢？

```java
    public String greet(String greeting, String name) {
        return new StringBuilder().append(greeting).append(" ").append(name).toString();
    }

    public Function1<String, String> sayHello() {
        return new AbstractFunction1() {
            public static final long serialVersionUID = 0L;

            public final String apply(String x$1) {
                return Hello.this.greet("hello", x$1);
            }
        };
    }

    public Function1<String, String> greetXiaoMing() {
        return new AbstractFunction1() {
            public static final long serialVersionUID = 0L;

            public final String apply(String x$2) {
                return Hello.this.greet(x$2, "Xiao Ming");
            }
        };
    }
```

可以看到sayHello和greetXiaoMing并不是返回String的，它们返回的是Function1 of String, String。也就是说直接调用它们俩是得不到我们想要的结果的，必须把这个Function1上的apply再调一下才行。实际上正是如此，这段代码：

```scala
  sayHello("world")
  greetXiaoMing("Ni Hao")
```

会被编译成：

```java
sayHello().apply("world");
greetXiaoMing().apply("Ni Hao");
```

除此之外，partial application还可以有另一种稍微另类一些的写法：

```scala
  def greet(greeting: String)(name: String) = greeting + " " + name

  def sayHello = greet("hello")(_)

  def greetXiaoMing = greet(_: String)("Xiao Ming")
```

反编译的结果和上面的代码是完全一致的。

我不太清楚这种写法存在的意义是不是仅仅起一个向外界宣称“我这个函数之所以出现就是给你局部应用的，不要一下子把两个参数都给我”的作用。
