---
layout: post
title: "掀开Scala的糖衣(9) -- function composition"
date: 2013-12-30 22:29
comments: true
categories: 
---

Function composition，顾名思义，就是函数的组合。直接举例：

```scala
  def sayHi(name: String) = "Hi, " + name

  def sayBye(str: String) = str + ", bye"
```

两个方法，一个说你好，一个说再见。然后我们创建很多个人名：

```scala
val names = List("world", "tom", "xiao ming")
```

我们希望对List中的每个人都说你好然后说再见：

```scala
names.map(sayHi).map(sayBye)
```

好，这样我们的目的实现了。但是，我们调用了两次map，会对整个List遍历两次。我们希望只遍历一次：

```scala
    names.map {
      name => sayBye(sayHi(name))
    }
```

嗯，这样可以了，但是看起来有一点点不爽。按照从左到右的阅读习惯，我们先看到了sayBye，然后才看到sayHi，而且括号还是包了两层。需要想那么一小下才能明白：按照eager evaluation的规则，先运行sayHi，然后把结果传入sayBye，最后得到一个我们想要的结果。

其实我们真正想要的是一个链式操作，一个pipe：把数据用某种操作进行处理，然后把处理后的结果传递给第二个操作继续处理。类似于这样：a.pipe(b)，或者是这样：a | b。

而Scala的function composition正是做这件事的：

```scala
names.map(sayHi _ andThen sayBye)
```

从左到右，当成英语来读：先sayHi然后再sayBye，清晰明了。

这个andThen并不是什么神奇的语言关键字。它其实只是定义在Function1上的一个方法而已。我们来看看反编译的结果就知道了：

```scala
        final List names = 
        List$.MODULE$.apply((Seq)Predef$.MODULE$.wrapRefArray(
        (Object[])new String[] { "world", "tom", "xiao ming" }));
        
        return (List)names.map(((Function1)new Serializable() {
            public static final long serialVersionUID = 0L;
            
            public final String apply(final String name) {
                return Hello.this.sayHi(name);
            }
        }).andThen((Function1)new Serializable() {
            public static final long serialVersionUID = 0L;
            
            public final String apply(final String str) {
                return Hello.this.sayBye(str);
            }
        }), List$.MODULE$.canBuildFrom());
```

由于反编译工具的原因，这个反编译的结果并不是合法的Java代码，我们凑合着看一下。

我们看到，sayHi和sayBye都是被包到了Functoin1里面，调一下第一个Function1的andThen方法，把第二个Function1传进去，会返回一个新的Function1。这个返回的新的Function1就是我们想要的链式操作了。