---
layout: post
title: "策略模式：筷子开啤酒"
date: 2015-05-27 08:55
comments: true
keywords: scala, java, design pattern, strategy pattern, OO, FP, 设计模式
---

# 策略模式

> 策略模式作为一种软件设计模式，指对象有某个行为，但是在不同的场景中，该行为有不同的实现算法。

以上是中文wiki中对策略模式的定义。

> In computer programming, the strategy pattern (also known as the policy pattern) is a software design pattern that enables an algorithm's behavior to be selected at runtime. The strategy pattern:

> * defines a family of algorithms,
> * encapsulates each algorithm, and
> * makes the algorithms interchangeable within that family.

>Strategy lets the algorithm vary independently from clients that use it.

以上是英文版的。

# 鸭子

这种偏学术性的描述实在太绕嘴，来思考一个实例：

我们需要创建一些鸭子，鸭子有什么行为呢？

* 鸭子会飞
* 会叫
* 会游泳

不过，是否所有的鸭子都是这样呢？万一是玩具鸭子呢？万一是猎人放在水里的用来勾引公鸭子的木质母鸭子呢？万一是外星来客太空鸭呢？

你已经知道什么意思了。

鸭子的各个子类的飞和叫的行为不尽相同。所以我们想把飞和叫这两种行为独立开来，让它们可以自由组合在鸭子的不同子类中。

以上例子来自著名的《Head first design patterns》。

# Java

以下是它的代码：

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

不过再想一下，我们想要的不过是把两个家族的不同行为塞到鸭子的子类里去。是否有更容易的办法来做到呢？

### trait

一说到把行为塞到某个类里，就会想到mix in，很自然就想到了Scala的trait。

更多关于Scala的trait的详情请参考我的另一篇博客：
http://cuipengfei.me/blog/2013/10/13/scala-trait/
