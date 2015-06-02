---
layout: post
title: "为什么必须是final的呢？"
date: 2013-06-22 14:54
comments: true
categories: Java
---

## 一个谜团
如果你用过类似guava这种“伪函数式编程”风格的library的话，那下面这种风格的代码对你来说应该不陌生：

```java
    public void tryUsingGuava() {
        final int expectedLength = 4;
        Iterables.filter(Lists.newArrayList("123", "1234"), new Predicate<String>() {
            @Override
            public boolean apply(String str) {
                return str.length() == expectedLength;
            }
        });
    }
```

这段代码对一个字符串的list进行过滤，从中找出长度为4的字符串。看起来很是平常，没什么特别的。

但是，声明expectedLength时用的那个final看起来有点扎眼，把它去掉试试：

> error: local variable expectedLength is accessed from within inner class; needs to be declared final

结果Java编译器给出了如上的错误，看起来匿名内部类只能够访问final的局部变量。但是，**为什么呢？其他的语言也有类似的规定吗？**

在开始用其他语言做实验之前我们先把问题简化一下，不要再带着guava了，我们去除掉噪音，把问题归结为：

**为什么Java中的匿名内部类只可以访问final的局部变量呢？其他语言中的匿名函数也有类似的限制吗？**

## Scala中有类似的规定吗？

```scala
  def tryAccessingLocalVariable {
    var number = 123
    println(number)

    var lambda = () => {
      number = 456
      println(number)
    }

    lambda.apply()
    println(number)
  }
```

上面的Scala代码是合法的，number变量是声明为var的，不是val（类似于Java中的final）。而且在匿名函数中可以修改number的值。

看来**Scala中没有类似的规定**。

## C#中有类似的规定吗？

```c#
	public void tryUsingLambda ()
	{
		int number = 123;
		Console.WriteLine (number);

		Action action = () => {
			number = 456;
			Console.WriteLine (number);
		};

		action ();
		Console.WriteLine (number);
	}
```

这段C#代码也是合法的，number这个局部变量在lambda表达式内外都可以访问和赋值。

看来**C#中也没有类似的规定**。

## 分析谜团

三门语言中只有Java有这种限制，那我们分析一下吧。先来看一下Java中的匿名内部类是如何实现的：

先定义一个接口：

```java
public interface MyInterface {
    void doSomething();
}
```

然后创建这个接口的匿名子类：

```java
public class TryUsingAnonymousClass {
    public void useMyInterface() {
        final Integer number = 123;
        System.out.println(number);

        MyInterface myInterface = new MyInterface() {
            @Override
            public void doSomething() {
                System.out.println(number);
            }
        };
        myInterface.doSomething();

        System.out.println(number);
    }
}
```

这个匿名子类会被编译成一个单独的类，反编译的结果是这样的：

```java
class TryUsingAnonymousClass$1
        implements MyInterface {
    private final TryUsingAnonymousClass this$0;
    private final Integer paramInteger;

    TryUsingAnonymousClass$1(TryUsingAnonymousClass this$0, Integer paramInteger) {
        this.this$0 = this$0;
        this.paramInteger = paramInteger;
    }

    public void doSomething() {
        System.out.println(this.paramInteger);
    }
}
```

可以看到名为number的局部变量是作为构造方法的参数传入匿名内部类的（以上代码经过了手动修改，真实的反编译结果中有一些不可读的命名）。

如果Java允许匿名内部类访问非final的局部变量的话，那我们就可以在TryUsingAnonymousClass$1中修改paramInteger，但是这不会对number的值有影响，因为它们是不同的reference。

这就会造成数据不同步的问题。

所以，**谜团解开了：Java为了避免数据不同步的问题，做出了匿名内部类只可以访问final的局部变量的限制。**

但是，新的谜团又出现了：

## Scala和C#为什么没有类似的限制呢？它们是如何处理数据同步问题的呢？

上面出现过的那段Scala代码中的lambda表达式会编译成这样：

```java
    public final class TryUsingAnonymousClassInScala$$anonfun$1 extends AbstractFunction0.mcV.sp
            implements Serializable {
        public static final long serialVersionUID = 0L;
        private final IntRef number$2;

        public final void apply() {
            apply$mcV$sp();
        }

        public void apply$mcV$sp() {
            this.number$2.elem = 456;
            Predef..MODULE$.println(BoxesRunTime.boxToInteger(this.number$2.elem));
        }

        public TryUsingAnonymousClassInScala$$anonfun$1(TryUsingAnonymousClassInScala $outer, IntRef number$2) {
            this.number$2 = number$2;
        }
    }

```

可以看到number也是通过构造方法的参数传入的，但是与Java的不同是这里的number不是直接传入的，是被IntRef包装了一层然后才传入的。对number的值修改也是通过包装类进行的：this.number$2.elem = 456;

这样就保证了lambda表达式内外访问到的是同一个对象。

再来看看C#的处理方式，反编译一下，发现C#编译器生成了如下的一个类：

```c#
private sealed class <tryUsingLambda>c__AnonStorey0
{
	internal int number;

	internal void <>m__0 ()
	{
		this.number = 456;
		Console.WriteLine (this.number);
	}
}
```

把number包装在这个类内，这样就保证了lambda表达式内外使用的都是同一个number，即便重新赋值也可以保证内外部的数据是同步的。

## 小结

Scala和C#的编译器通过把局部变量包装在另一个对象中，来实现lambda表达式内外的数据同步。

而Java的编译器由于未知的原因（怀疑是为了图省事儿？）没有做包装局部变量这件事儿，于是就只好强制用户把局部变量声明为final才能在匿名内部类中使用来避免数据不同步的问题。
