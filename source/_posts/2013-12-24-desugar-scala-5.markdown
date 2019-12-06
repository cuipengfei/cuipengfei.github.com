---
layout: post
title: "剥开Scala的糖衣(5) -- lazy"
date: 2013-12-24 17:48
comments: true
tags:
- Desugar_Scala
- Scala
---

Scala中的lazy关键字是实现延迟加载的好帮手。

在Java中想要做到延迟加载，常规的做法是大抵是这样的：

```java
    private String str = null;

    public String getStr() {
        if (str == null) {
            str = getStrFromWebService();
        }
        return str;
    }
```

以这种方式来保证web service不会被无谓的重复请求。

C#中则可以使用Lazy of T来实现类似的事:

```c#
private Lazy<String> str = new Lazy<string> (() => GetStrFromWebService ());

public String Str
{
	get
	{
		return str.Value;
	}
}
```

Lazy of T保证传入其中的Func只执行一次。

（其实，Java也可以使用Guava中的memoize来实现类似的事情）

要么自己写代码，要么通过库来实现。

而Scala则在语言级别给出了解决方案：

```scala
lazy val str = getStrFromWebService()
```

仅此一行。

只要用lazy关键字修饰一下str，延迟执行的事就搞定了。

其实Scala编译器做的事情和我们手动做的区别不大：

```java
  private String str;
  private volatile boolean bitmap$0;

  private String str$lzycompute()
  {
    synchronized (this) {
    	if (!this.bitmap$0) {
		    this.str = getStrFromWebService();
		    this.bitmap$0 = true;
	    }

    	return this.str;
    }
  }

  public String str() {
    return this.bitmap$0 ? this.str : str$lzycompute();
  }
```

你看编译器多热心，还加了锁哦。

小小总结一下：

对于这样一个表达式：
lazy val t:T = expr
无论expr是什么东西，字面量也好，方法调用也好。Scala的编译器都会把这个expr包在一个方法中，并且生成一个flag来决定只在t第一次被访问时才调用该方法。
