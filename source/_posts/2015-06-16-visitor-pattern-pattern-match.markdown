---
layout: post
title: "访问者模式 in FP：Pattern Matching"
date: 2015-06-16 19:11
comments: true
categories: Scala OODP
---

> 访问者模式是一种将算法与对象结构分离的软件设计模式。

> 这个模式的基本想法如下：首先我们拥有一个由许多对象构成的对象结构，这些对象的类都拥有一个accept方法用来接受访问者对象；访问者是一个接口，它拥有一个visit方法，这个方法对访问到的对象结构中不同类型的元素作出不同的反应；在对象结构的一次访问过程中，我们遍历整个对象结构，对每一个元素都实施accept方法，在每一个元素的accept方法中回调访问者的visit方法，从而使访问者得以处理对象结构的每一个元素。我们可以针对对象结构设计不同的实在的访问者类来完成不同的操作。

以上是wiki对访问者模式的定义。

这个定义着实难读。我们来看wiki给出的例子：

假设我们要为汽车建模，汽车有不同的组成部件，轮胎，车身，和引擎。

在开车之前需要先检查车辆每个部件的状况，然后依次启动所有部件以启动汽车。

在这里我们很容易识别出车的组件各自应该是一个实体。而对车辆组件进行检查和启动的代码应该分别处于不同的实体中。

这样就有了访问者的代码（来自wiki）：

# Java

```java
interface ICarElement {
    void accept(ICarElementVisitor visitor);
}

class Wheel implements ICarElement {
    private String name;

    public Wheel(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void accept(ICarElementVisitor visitor) {
        visitor.visit(this);
    }
}

class Engine implements ICarElement {
    public void accept(ICarElementVisitor visitor) {
        visitor.visit(this);
    }
}

class Body implements ICarElement {
    public void accept(ICarElementVisitor visitor) {
        visitor.visit(this);
    }
}

class Car implements ICarElement {
    ICarElement[] elements;

    public Car() {
        this.elements = new ICarElement[]{new Wheel("front left"),
                new Wheel("front right"), new Wheel("back left"),
                new Wheel("back right"), new Body(), new Engine()};
    }

    public void accept(ICarElementVisitor visitor) {
        for (ICarElement elem : elements) {
            elem.accept(visitor);
        }
        visitor.visit(this);
    }
}
```

首先是汽车部件的实体。它们都实现同一个ICarElement的接口。
该接口定义一个accept方法，用来接受访问者然后用访问者来访问所有汽车部件。

```java
interface ICarElementVisitor {
    void visit(Wheel wheel);

    void visit(Engine engine);

    void visit(Body body);

    void visit(Car car);
}

class CarElementPrintVisitor implements ICarElementVisitor {
    public void visit(Wheel wheel) {
        System.out.println("Visiting " + wheel.getName() + " wheel");
    }

    public void visit(Engine engine) {
        System.out.println("Visiting engine");
    }

    public void visit(Body body) {
        System.out.println("Visiting body");
    }

    public void visit(Car car) {
        System.out.println("Visiting car");
    }
}

class CarElementDoVisitor implements ICarElementVisitor {
    public void visit(Wheel wheel) {
        System.out.println("Kicking my " + wheel.getName() + " wheel");
    }

    public void visit(Engine engine) {
        System.out.println("Starting my engine");
    }

    public void visit(Body body) {
        System.out.println("Moving my body");
    }

    public void visit(Car car) {
        System.out.println("Starting my car");
    }
}
```

然后就是访问者的实体。它们都实现ICarElementVisitor接口。
这个接口里定义的方法有点多，分别对应每个汽车部件定义了一个visit方法的重载。

在实现的时候自然是做检查的实体实现每个部件的检查，启动的实体实现每个部件的启动。

这里就有一个陷阱，如果代码发展的趋势是汽车部件的种类会增加的话，那这个接口就很不稳定。每增加一种汽车部件就要修改接口并且修改每个实现类。

而如果代码发展的趋势是在自检和启动之外加一些保养啊，洗车啊之类的话就没问题，不需要对已有代码进行修改。

所以使用访问者模式的时候要注意识别被访问者是否是相对稳定而访问者是有扩展趋势的，这样用这个模式才合适。

接下来的代码把以上所有代码串起来执行：

```java
public class VisitorDemo {
    public static void main(String[] args) {
        ICarElement car = new Car();
        car.accept(new CarElementPrintVisitor());
        car.accept(new CarElementDoVisitor());
    }
}
```

从最后的main函数来看，只要能确保汽车部件的数量不会增加，而只有访问者增加，那么客户代码只需要增加一行就能够增加对整车进行清洗或者保养的行为。

车的部件和对部件的操作相互分离，独立发展。很灵活，很巧妙，对吧？

## 不过再想一下

其实也不需要使劲想了，如果你看过这一系列博文前面的几篇的话，想必已经能够猜到我的用意了。

这些访问者存在的意义就在于承载对汽车部件的某些具体操作，操作是个好听的词儿，说白了就是函数啊。

那既然这些类只是承载函数而已，何不直接就用函数而不费劲去用类包裹一层呢？

# functions

那接下来就是用Scala的实现：

```scala
trait CarElement {
  def accept(visitor: Visitor) = visitor(this)
}

case class Body() extends CarElement

case class Engine() extends CarElement

case class Wheel(name: String) extends CarElement

case class Car() extends CarElement {
  val elements: Seq[CarElement] = Seq(
    Wheel("front left"), Wheel("front right"),
    Wheel("back left"), Wheel("back right"),
    Body(), Engine())

  override def accept(visitor: Visitor) = {
    elements.foreach(_.accept(visitor))
    visitor(this)
  }
}
```

以上是汽车各种部件的定义，和Java代码没有太大区别。

```scala
object Visitors {
  type Visitor = CarElement => Unit

  val printVisitor: Visitor = {
    case Wheel(name) => println(s"Visiting $name wheel")
    case Body() => println("Visiting Body")
    case Engine() => println("Visiting Engine")
    case Car() => println("Visiting Car")
  }

  val doVisitor: Visitor = {
    case Wheel(name) => println(s"Kicking my $name wheel")
    case Body() => println("Moving my body")
    case Engine() => println("Starting my engine")
    case Car() => println("Starting my car")
  }
}
```

上面这一段定义了一个叫做Visitor的type，它只是一个函数签名。任何接受一个汽车部件作为参数并且没有返回值的函数都符合它的签名，也就可以被视作Visitor。

接下来是两个符合Visitor签名的函数，都是用pattern match实现的。

pattern match这种神奇的语言特性是如何实现的呢？背后的原因并不神奇，更多详情请参考我之前的另一篇博客：[http://cuipengfei.me/blog/2013/12/29/desugar-scala-8/](http://cuipengfei.me/blog/2013/12/29/desugar-scala-8/)

```scala
object VisitorDemo {
  def main(args: Array[String]) {
    val car = Car()
    car.accept(printVisitor)
    car.accept(doVisitor)
  }
}
```

最后定义一个main函数，与Java的main函数做的事情是等价的。

这样，100行变成了45行。Visitor不再作为臃肿的实体存在，而只是函数。

而且如果遵照同样的假设，认为车的部件是稳定的，而访问者是会增多的，那这段Scala代码的增长趋势是每加一个访问者就加一个函数。与Java代码的增长趋势相同。

# 结语

这次分析的访问者模式和之前的一些模式很类似，当我们需要的实体仅仅是作为承载某种行为的一个载具，那就可以考虑将实体消去，而换用函数这种更简单，更轻量级的抽象方式来实现我们想要的东西。

当年OO模式出现的时候，FP并不盛行，原作者提出的方案无可厚非。不过我们今天有了FP这种更趁手的工具，就可以考虑在合适的时候将其与OO结合使用来达到更好的设计的目的。
