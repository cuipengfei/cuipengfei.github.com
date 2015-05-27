---
layout: post
title: 策略模式的尴尬就像用菜刀开啤酒
date: '2015-05-27 08:55'
comments: true
keywords: 'scala, java, design pattern, strategy pattern, OO, FP, 设计模式'
---

# 策略模式
> 策略模式作为一种软件设计模式，指对象有某个行为，但是在不同的场景中，该行为有不同的实现算法。

以上是中文wiki中对策略模式的定义。

> In computer programming, the strategy pattern (also known as the policy pattern) is a software design pattern that enables an algorithm's behavior to be selected at runtime. The strategy pattern:
  - defines a family of algorithms,
  - encapsulates each algorithm, and
  - makes the algorithms interchangeable within that family.

> Strategy lets the algorithm vary independently from clients that use it.

以上是英文版的。

# 鸭子
这种偏学术性的描述实在太绕嘴，来思考一个实例：

我们需要创建一些鸭子，鸭子有什么行为呢？

- 鸭子会飞
- 会叫
- 会游泳

不过，是否所有的鸭子都是这样呢？万一是玩具鸭子呢？万一是猎人放在水里的用来勾引公鸭子的木质母鸭子呢？万一是外星来客太空鸭呢？

你已经知道什么意思了。

鸭子的各个子类的飞和叫的行为不尽相同。所以我们想把飞和叫这两种行为独立开来，让它们可以自由组合在鸭子的不同子类中。

以上例子来自著名的《Head first design patterns》。

# Java
以下是《Head first design patterns》附带的代码：

```java
public interface FlyBehavior {
    void fly();
}

public class FlyWithWings implements FlyBehavior {
    public void fly() {
        System.out.println("fly with wings");
    }
}

public class FlyNoWay implements FlyBehavior {
    public void fly() {
        System.out.println("can not fly");
    }
}
```

飞行的接口，以及两个实现：一个真会飞，一个不会飞。

```java
public interface QuackBehavior {
    void quack();
}

public class Quack implements QuackBehavior {
    public void quack() {
        System.out.println("Quack");
    }
}

public class MuteQuack implements QuackBehavior {
    public void quack() {
        System.out.println("<<silence>>");
    }
}
```

叫的接口，两个实现，一个真会叫，一个不会叫。

```java
public abstract class Duck {
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public Duck() {
    }

    public void performFly() {
        flyBehavior.fly();
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    public void swim() {
        System.out.println("All ducks float, even decoys!");
    }
}

public class MallardDuck extends Duck {
    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }
}

public class DecoyDuck extends Duck {
    public DecoyDuck() {
        setFlyBehavior(new FlyNoWay());
        setQuackBehavior(new MuteQuack());
    }
}
```

最后，终于到了鸭子。鸭子的顶层抽象类声明两个字段，一个用来飞，一个用来叫。

这样在子类里就可以把这两个字段锁定到某个特定的实现，以实现任意的组合。

可以看到，绿头鸭（mallard）组合了真会飞和真会叫。而诱饵鸭（decoy，猎人用来勾引鸭子上钩的那个）则组合了不会飞和不会叫。

可以想象随着飞和叫这两个家族的扩大，我们可以组合出更多种类的鸭子来。

很好，很灵活，很强大，对吧？

## 不过再想一下
我们想要的不过是把两个家族的不同行为塞到鸭子的子类里去。是否有更容易的办法来做到呢？

# trait
一说到把行为塞到某个类里，就会想到mix in，很自然就想到了Scala的trait。

更多关于Scala的trait的详情请参考我的另一篇博客： [http://cuipengfei.me/blog/2013/10/13/scala-trait/](http://cuipengfei.me/blog/2013/10/13/scala-trait/)

```scala
trait Fly {
  def fly()
}

trait FlyWithWings extends Fly {
  def fly() = println("fly with wings")
}

trait FlyNoWay extends Fly {
  def fly() = println("can not fly")
}
```

飞行家族。

```scala
trait Quack {
  def quack()
}

trait RealQuack {
  def quack() = println("Quack")
}

trait MuteQuack {
  def quack() = println("<<silence>>")
}
```

叫的行为的家族。

```scala
abstract class Duck extends Fly with Quack {
  def swim = println("all ducks float")
}

class MallardDuck extends Duck with FlyWithWings with RealQuack

class DecoyDuck extends Duck with FlyNoWay with MuteQuack
```

最后，鸭子的各种实现。

貌似和Java版的实现差距不大，飞和叫的interface和class变成了trait。

Duck原来是持有Fly和Quack的实例，现在则是变成了混入Fly和Quack这两个trait。

这个代码比Java短一些，紧凑一些，构造函数中的赋值变成了类型声明时的混入。

## 不过再想一下
我们不过是想要把某种行为塞入到某个类里面去，真的有必要用interface，class，trait来把这些行为包裹起来吗？

行为通常是以哪种形式承载的呢？

# functions
行为通常是以函数承载的。

也就是说我们想要做的不过是把符合某个签名的函数塞到鸭子的子类里去而已，而却用interface，class，trait来把这些行为包裹起来了。有些臃肿不是吗？

下面是直接把函数塞入鸭子子类的做法：

```scala
object Duck {
  type Fly = () => Unit
  val flyWithWings = () => println("fly with wings")
  val flyNoWay = () => println("can not fly")

  type Quack = () => Unit
  val realQuack = () => println("Quack")
  val muteQuack = () => println("<<silence>>")
}

abstract class Duck(f: Fly, q: Quack) {
  def swim() = println("all ducks float")

  def fly() = f()

  def quack() = q()
}

class MallardDuck extends Duck(flyWithWings, realQuack)

class DecoyDuck extends Duck(flyNoWay, muteQuack)
```

Fly和Quack不再是interface或者是trait。而是type aliase。

Scala的type aliase就类似于C#的delegate，用来声明function signature。

更多关于type aliase的更多详情请参考我的另一篇博客： [http://cuipengfei.me/blog/2013/12/23/desugar-scala-4/](http://cuipengfei.me/blog/2013/12/23/desugar-scala-4/)

这样，会飞不会飞，会叫不会叫就无需被class或者trait包裹着了，直接就是一个个的函数。

鸭子的子类通过构造函数接收飞和叫的两个函数作为参数，就能够组合不同的行为了。

如果说之前triat的实现方式与Java实现版相比偏重了inheritance而不是composition，这一版的实现则又回到了纯composition的路上了。

紧凑程度，实体数量都比以上两版有改进。这一点从行数上可以窥见：Java版63行，trait版29行，最后一版21行。

# 菜刀开啤酒

最后回到标题上去：菜刀开啤酒，意即用不合适的工具解决问题。

strategy patten要解决的问题其实就是如何把一族行为的不同实现注入到某个类里去。

这一点，最开头的wiki定义已经说的很明白了：
> Strategy lets the algorithm vary independently from clients that use it.

无论是class，还是function，都是为程序员提供抽象的手段。当我们想要抽象的东西就是一段algorithm（正如wiki所说）的时候，用function来做抽象就是更加轻量且合适的选择。

该模式提出的时候FP并不如今日盛行，其作者选用纯OO的方式解决了问题，并广为传播，实为功德。

不过今天我们有了开瓶器，就无需一定要用菜刀了。

最后是一个Java 8的实现：

```java
public interface Fly {
    void fly();
}

public interface Quack {
    void quack();
}

public class BehaviorsRepo {
    public static Fly flyWithWings = () -> System.out.println("fly with wings");
    public static Fly canNotFly = () -> System.out.println("can not fly");

    public static Quack realQuack = () -> System.out.println("Quack");
    public static Quack muteQuack = () -> System.out.println("<<silence>>");
}

public class Duck {
    private final Fly f;
    private final Quack q;

    public Duck(Fly f, Quack q) {
        this.f = f;
        this.q = q;
    }

    public void fly() {
        f.fly();
    }

    public void quack() {
        q.quack();
    }
}

public class MallardDuck extends Duck {
    public MallardDuck() {
        super(flyWithWings, realQuack);
    }
}

public class DecoyDuck extends Duck {
    public DecoyDuck() {
        super(canNotFly, muteQuack);
    }
}
```

看起来比最开始的那一版好一些，但是我还是看它不顺眼。

为什么呢？

一定是由于我强烈的偏见而没有其他任何原因，一定是这样的。
