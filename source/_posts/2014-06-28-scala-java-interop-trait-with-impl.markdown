---
layout: post
title: "论“如果trait有方法实现，那么Java类就不能实现这个trait”这句话是错的"
date: 2014-06-28 12:55
comments: true
categories: 
---
最近还是在看郑大翻译的《Scala程序设计》，其中第十一章还有一句话：

> 如果trait有方法实现，那么Java类就不能实现这个trait

口说还是无凭，还是拍照为证：

![](http://ww1.sinaimg.cn/large/8b1ece2agw1ehtqjokutnj21kw23u7wh.jpg)

我感觉这句话是错的，下面寻根究底地探索一下。

trait这个语言特性[前面的博文](http://cuipengfei.me/blog/2013/10/13/scala-trait/)讲过。

一个含有方法实现的trait会被编译成一个interface，还有一个含有实现的静态方法。

所有extends或者是with这个trait的Scala类，实际上都是implements了这个interface，在具体实现中调用了静态方法。

快速的简单回忆一下：

```scala
trait HappyThoughts {
  def whatAreYouThinking(): Unit = {
    println(" food :D ")
  }
}
```

定义一个含有方法实现的trait。

```scala
class Animal

class Dog extends Animal with HappyThoughts
```

然后让Dog去with这个trait。

之后就可以这样调用：

```scala
new Dog().whatAreYouThinking()
```

这样就能打印出food :D了。虽然Dog本身是空的，但是因为with了一个trait，它也拥有了一些行为。

再来看看反编译出的代码：

```java
public interface HappyThoughts
{
  public abstract void whatAreYouThinking();
}

public abstract class HappyThoughts$class
{
  public static void whatAreYouThinking(HappyThoughts $this)
  {
    Predef..MODULE$.println(" food :D ");
  }

  public static void $init$(HappyThoughts $this)
  {
  }
}
```

HappyThoughts就是上面这样的，一个interface，还有一个含有实现的静态方法。

Dog则是这样的：

```java
public class Dog extends Animal implements HappyThoughts
{
  public void whatAreYouThinking()
  {
    HappyThoughts.class.whatAreYouThinking(this); 
  } 
  
  public Dog() 
  { 
  	HappyThoughts.class.$init$(this); 
  }
}
```

它implements了HappyThoughts，其实现则依赖于上面提到的静态方法。

Ok，足够清晰了。

这么一个trait，当真在Java中不可以利用吗？

写点代码试试看：

```java
public class DogJ implements HappyThoughts {
    @Override
    public void whatAreYouThinking() {
        HappyThoughts$class.whatAreYouThinking(this);
    }
}
```

基本照抄上面反编译的代码。这段Java代码是可以编译的，而且也可以运行，运行结果也是打印出了food :D。

这次，我就不去探寻旧版本的Scala是如何处理trait的了。我们只要知道当前版本（比如我用的2.10.4）的Scala中定义的含有方法实现的trait，拿到Java中依然是可用的就行了。虽说用起来有一点蹩脚，但终归是可用的。