---
layout: post
title: "为什么必须是final的呢？"
date: 2013-06-22 14:54
comments: true
categories: 
---

## 一个谜团
如果你用过类似guava这种“伪函数式编程”风格的library的话，那下面这种风格的代码应该对你来说不陌生：

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

结果Java编译器给出了如上的错误，看起来匿名内部类只能够访问局部变量。但是，**为什么呢？其他的语言也有类似的规定吗？**

在开始用其他语言做实验之前我们先把问题简化一下，不要再带着guava了，我们去除掉噪音，把问题归结为：

**为什么Java中的匿名内部类只可以访问final的局部变量呢？**

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

三门语言中只有Java有这种限制，那我们分析一下吧。先来看一下Java中的匿名内部类是如何实现的，