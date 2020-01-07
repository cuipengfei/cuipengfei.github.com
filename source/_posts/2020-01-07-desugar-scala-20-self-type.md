---
title: Desugar Scala 20 -- Self Type
date: 2020-01-07 18:57:20
tags:
- Desugar_Scala
- Scala
---

Scala里有一个很有趣的语言特性叫做Self Type，可以用来限定一个trait可以被mixin到哪里去。

看个例子：

```Scala
trait User {
  def username: String
}

trait Tweeter {
  self: User =>
  def tweet(tweetText: String) = println(s"$username: $tweetText")
}

class VerifiedTweeter(val username_ : String) {
  def username = s"real $username_"
}

object SelfTypeBlog {
  def main(args: Array[String]): Unit = {
    val realBeyoncé = new VerifiedTweeter("Beyoncé") with User with Tweeter
    realBeyoncé.tweet("Just spilled my glass of lemonade")
  }
}
```

User就仅仅相当于一个Interface，定义一个username。

Tweeter内的第一行是重点 `self: User =>` 就限定了Tweeter只能被mixin到实现了User的类里面去。
由于可以确定Tweeter只能被mixin到实现了User的类里面去，这样Tweeter的tweet方法内就可以放心大胆地用 `username` 了。

VerifiedTweeter是一个很普通的class，别人new它的时候给什么字符串，它的username就是啥。

最后，在main函数里new一个VerifiedTweeter，把User和Tweeter都mixin进去。
然后就可以调用tweet方法了。

而如果没有mixin User，直接试图mixin Tweeter，就会出一个编译错误：
```
Illegal inheritance, self-type VerifiedTweeter with Tweeter does not conform to User
```

到这里，其实就可以猜到了：
由于Self Type是在编译时限定一个trait可以被mixin到哪里去的，并且我们知道在bytecode level上没有限定一个Interface可以被谁实现的机制。
由此可知，反编译这段Scala对应的class文件是看不到任何神奇的东西的。

不过，**很久之前**写过“Scala中的语言特性是如何实现的(3) -- trait”：
https://cuipengfei.me/blog/2013/10/13/scala-trait/

其中对于Scala如何编译trait做了逆向工程的分析，当时的trait是编译成了一个抽象类加一个接口。这个信息已经过时了。
现在更新版的Scala可以编译出bytecode version 52，这一版是有interface default method的。

所以，还是反编译一下吧。

```java
public interface User
{
    String username();
}

public interface Tweeter
{
    default /* synthetic */ void tweet$(final Tweeter $this, final String tweetText) {
        $this.tweet(tweetText);
    }
    
    default void tweet(final String tweetText) {
        Predef$.MODULE$.println((Object)new StringBuilder(2).append(((User)this).username()).append(": ").append(tweetText).toString());
    }
    
    default void $init$(final Tweeter $this) {
    }
}

public class VerifiedTweeter
{
    private final String username_;
    
    public String username_() {
        return this.username_;
    }
    
    public String username() {
        return new StringBuilder(5).append("real ").append(this.username_()).toString();
    }
    
    public VerifiedTweeter(final String username_) {
        this.username_ = username_;
        super();
    }
}
```

上面是User Tweeter VerifiedTweeter他们三个的反编译结果，都比较单纯。
只是Tweeter这个含有一个实现方法的trait被编译成了有default method的Interface，而不像老版本的Scala要编译成一个抽象类加一个Interface。

然后看一下main函数反编译出来的样子：

```java
    public void main(final String[] args) {
        final VerifiedTweeter realBeyonc\u00e9 = (VerifiedTweeter)new SelfTypeBlog$$anon.SelfTypeBlog$$anon$1();
        ((Tweeter)realBeyonc\u00e9).tweet("Just spilled my glass of lemonade");
    }
```

可以看到这里new的是一个叫做SelfTypeBlog$$anon$1的类。这个类反编译出来是这样的：

```java
public final class SelfTypeBlog$$anon$1 extends VerifiedTweeter implements User, Tweeter {
    public void tweet(final String tweetText) {
        Tweeter.tweet$((Tweeter)this, tweetText);
    }
    
    public SelfTypeBlog$$anon$1() {
        super("Beyonc\u00e9");
    }
    
    {
        Tweeter.$init$((Tweeter)this);
    }
}
```

它继承了VerifiedTweeter，实现了User和Tweeter。
由此可见Scala中在对象创建时才with trait的写法，Scala编译器会产出一个类来表达。

如同前面推测的一样，在bytecode level上，没有神奇的东西，self type这个语言特性是仰赖于Scala compiler来做到的。

不过，既然全靠Scala compiler来做到的，那就意味着如果我用别的compiler的话，这个限定就无法在**编译时**生效了。

来试一下：

```java
public class JNotUser implements Tweeter {
    private String abc() {
        return "abc";
    }
}
```

这段Java代码编译起来完全没问题，没人强迫我去实现User，毕竟Tweeter对于Java来说就是个普通的Interface嘛。

然后跑一下
```java
new JNotUser().tweet("hello");
```
会怎样呢？

结果是执行的时候出现一个类型转换异常：
```
Exception in thread "main" java.lang.ClassCastException: class TraitSelfTypeBlog.JNotUser cannot be cast to class TraitSelfTypeBlog.User
```

为啥会这样呢？仔细看下上面Tweeter反编译的结果中tweet方法的内容：
```java
    default void tweet(final String tweetText) {
        Predef$.MODULE$.println((Object)new StringBuilder(2).append(((User)this).username()).append(": ").append(tweetText).toString());
    }
```
关键就在这个 `(User)this` 了。

虽然编译时限制不住，运行时终归逃不过。