---
layout: post
title: "Scala中的语言特性是如何实现的(3) -- trait"
date: 2013-10-13 17:41
comments: true
categories: Desugar_Scala Scala
---

我在Coursera上跟了一门叫做[Functional Programming Principles in Scala](https://www.coursera.org/course/progfun)的课程，是由Scala的作者Martin Odersky讲授的。其中第三周的作业中使用到了Scala的trait这个语言特性。

我以前熟知的语言都没有类似的特性（Ruby的mixin和Scala的trait很像，但是Ruby我不熟），所以这周的博客就分析一下这个语言特性是如何实现的。

###trait
在讲trait的实现机制之前，先看一个使用trait的例子。
假设我们有以下几个类：

```scala
abstract class Plant {
  def photosynthesis = println("Oh, the sunlight!")
}

class Rose extends Plant {
  def smell = println("Good!")

  def makePeopleHappy = println("People like me")
}

class Ruderal extends Plant {
  def grow = println("I take up all the space!")
}

abstract class Animal {
  def move = println("I can move!")
}

class Dog extends Animal {
  def bark = println("Woof!")

  def makePeopleHappy = println("People like me")
}

class Snake extends Animal {
  def bite = println("I am poisonous!")
}
```

植物家族有玫瑰和杂草。

动物家族有狗和毒蛇。

仔细观察可以发现，玫瑰和狗有一个共同的行为，它们都可以取悦人类，这个行为是用完全一样的代码实现的。

如何把Rose和Dog中的重复代码消除掉呢？有一种潜在的解决方案：
把makePeopleHappy提取到一个类中去，让植物和动物都继承自它。

这么做虽然消除了重复代码但有两个明显的缺点：

1. 植物和动物继承自同一个类，不太合理
2. 杂草和毒蛇也具有了取悦于人的能力，也不太合理

这时我们就可以使用trait，它没有上面提到的两个缺点。

```scala
trait PeoplePleaser {
  def makePeopleHappy = println("People like me")
}

class Rose extends Plant with PeoplePleaser {
  def smell = println("Good!")
}

class Dog extends Animal with PeoplePleaser {
  def bark = println("Woof!")
}
```

我们定义一个trait，把makePeopleHappy置于其中，让Rose和Dog都with这个trait。然后就可以写这样的代码来调用它们了：

```scala
  new Rose().makePeopleHappy
  new Dog().makePeopleHappy
```

这样我们就解决了重复代码的问题，而且没有触及已存在的继承关系。

现在看看trait的实现机制吧，我们开始反编译！

```java
public abstract interface PeoplePleaser
{
  public abstract void makePeopleHappy();
}

public abstract class PeoplePleaser$class
{
  public static void makePeopleHappy(PeoplePleaser $this)
  {
    Predef..MODULE$.println("People like me");
  }

  public static void $init$(PeoplePleaser $this)
  {
  }
}

public class Rose extends Plant
  implements PeoplePleaser
{
  public void makePeopleHappy()
  {
    PeoplePleaser$class.makePeopleHappy(this);
  }

  public void smell() { Predef..MODULE$.println("Good!"); }

  public Rose()
  {
    PeoplePleaser.class.$init$(this);
  }
}

public class Dog extends Animal
  implements PeoplePleaser
{
  public void makePeopleHappy()
  {
    PeoplePleaser$class.makePeopleHappy(this);
  }

  public void bark() { Predef..MODULE$.println("Woof!"); }

  public Dog()
  {
    PeoplePleaser.class.$init$(this);
  }
}

```

真相大白了，PeoplePleaser被编译成了一个接口加一个抽象类。Rose和Dog实现这个接口，并通过调用抽象类中的静态方法来实现了makePeopleHappy。

很有趣的一点是Rose和Dog在调用静态方法时都把this传了进去，为什么呢？我们把原来的代码改成这样来看：


```scala
trait PeoplePleaser {
  val moreMessage = ""

  def makePeopleHappy = println("People like me. " + moreMessage)
}

class Rose extends Plant with PeoplePleaser {
  override val moreMessage = "Because I smell nice."

  def smell = println("Good!")
}

class Dog extends Animal with PeoplePleaser {
  override val moreMessage = "Because I fetch balls."

  def bark = println("Woof!")
}
```
我们给makePeopleHappy加上一段额外的信息。
现在再次反编译。

```java
public abstract interface PeoplePleaser
{
  public abstract void objsets$PeoplePleaser$_setter_$moreMessage_$eq(String paramString);

  public abstract String moreMessage();

  public abstract void makePeopleHappy();
}

public abstract class PeoplePleaser$class
{
  public static void makePeopleHappy(PeoplePleaser $this)
  {
    Predef..MODULE$.println(new StringBuilder()
    .append("People like me. ")
    .append($this.moreMessage()).toString());
  }

  public static void $init$(PeoplePleaser $this)
  {
    $this.objsets$PeoplePleaser$_setter_$moreMessage_$eq("");
  }
}

public class Rose extends Plant
  implements PeoplePleaser
{
  private final String moreMessage;

  public void objsets$PeoplePleaser$_setter_$moreMessage_$eq(String x$1)
  {
  }

  public void makePeopleHappy()
  {
    PeoplePleaser$class.makePeopleHappy(this);
  }

  public String moreMessage() { return this.moreMessage; }

  public void smell() {
    Predef..MODULE$.println("Good!");
  }

  public Rose()
  {
    PeoplePleaser.class.$init$(this);
    this.moreMessage = "Because I smell nice.";
  }
}

public class Dog extends Animal
  implements PeoplePleaser
{
  private final String moreMessage;

  public void objsets$PeoplePleaser$_setter_$moreMessage_$eq(String x$1)
  {
  }

  public void makePeopleHappy()
  {
    PeoplePleaser$class.makePeopleHappy(this);
  }

  public String moreMessage() { return this.moreMessage; }

  public void bark() {
    Predef..MODULE$.println("Woof!");
  }

  public Dog()
  {
    PeoplePleaser.class.$init$(this);
    this.moreMessage = "Because I fetch balls.";
  }
}

```

现在就清楚了，抽象类中的静态方法可能会依赖于各个实例不同的状态，所以需要把this传递进去。
这样我们才能够给makePeopleHappy加上一段额外的信息。
