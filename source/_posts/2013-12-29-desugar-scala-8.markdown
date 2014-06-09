---
layout: post
title: "揭开Scala的糖衣(8) -- pattern matching"
date: 2013-12-29 23:20
comments: true
categories: 
---

Pattern matching是Scala中很好用的一个语言特性。先举一个最简单的例子：

```scala
    val number = 1

    number match {
      case 1 => doSomething()
      case 2 => doSomethingElse()
      case _ => doDefault()
    }
```

这个代码和我们熟悉的switch case看起来很像，其实，这段代码反编译之后和Java的switch case确实就是一样的：

```java
    int number = 1;

    int i = number; switch (i)
    {
    default:
      doDefault(); break;
    case 2:
      doSomethingElse(); break;
    case 1:
      doSomething();
    }
```

但是和Java的switch case不一样的是，Scala的pattern matching作为一个expression是可以evaluate一个值出来的，我们把上面的代码改一下，让doSomething,doSomethingElse和doDefault都返回点东西：

```scala
    val number = 1

    val result = number match {
      case 1 => doSomething()
      case 2 => doSomethingElse()
      case _ => doDefault()
    }
```

这样，result就承接了能够match上的那个case的返回值。而无需像普通的swtich case一样在每个case中给result赋值。

单是这样看，pattern matching的魅力还不算怎么大，我们再看一下下面这个例子：

```scala
abstract class Animal

case class Cat(name: String) extends Animal

case class Dog(name: String) extends Animal
```

首先声明几个case classes。这些case classes会被编译成一些比较复杂的classes，我们暂时不去关心。

然后看一下如何match类型及其属性：

```scala
    val animal = createAnimal
    animal match {
      case Dog(anyName) => "this is a dog"
      case Cat("kitty") => "this is a cat named kitty"
      case _ => "other animal"
    }
```

这段代码很容易懂，如果创建出来的animal是狗的话，无论它的名字叫什么，我们都返回this is a dog，如果是一只名叫kitty的猫，则返回this is a cat named kitty。如果都不是的话，则返回other animal。

很简单的几行代码，就做出了类型判断而且还有属性判断。

如果没有pattern matching，那么就要写if去判断类型，如果类型符合还要做类型转换，然后把转换后的变量中的属性取出来，再然后才能对属性的值做判断，最后才能返回点东西......

类型判断，类型转换，取属性，属性值判断，返回值。这么五件事我们用这样一行代码就都解决了：

```scala
case Cat("kitty") => "this is a cat named kitty"
```

这样的Scala代码会被编译成什么样呢？其实就是我们上面描述的很复杂的样子：

```java
        Animal animal;
        String string;
        Animal animal2 = (animal = this.createAnimal());
        if ((animal2 instanceof Dog)) {
            return "this is a dog";
        }
        if (!((animal2 instanceof Cat))) return "other animal";
        Cat cat = (Cat)(animal2);
        String string2 = (string = cat.name());
        if ("kitty" == null) {
            if (string2 == null) return "this is a cat named kitty";
            return "other animal";
        } else {
            if (!("kitty".equals(string2))) return "other animal";
            return "this is a cat named kitty";
        }
```

这段反编译出来的代码不很可读。我们就凑合着粗看一下。里面和我们前面说的一样，都是if else，类型判断，转型，判等......

当然，用反编译工具给出的Java代码和上面的Scala代码作比较并不公平。我们自己把它写一遍：

```java
        Animal animal = createAnimal();
        String result = "other animal";

        if (animal instanceof Dog) {
            result = "this is a dog";
        } else if (animal instanceof Cat) {
            Cat cat = (Cat) animal;
            if (cat.name() == "kitty") {
                result = "this is a cat named kitty";
            }
        }

        return result;

```

这个样子再和上面的Scala代码比较，可以看到Scala编译器帮我们省掉了局部变量，类型判断和判等这些噪音。

Pattern matching还有很多其他用法，比如用来match tuple：

```scala
    val hostPort = ("localhost", 80)
    hostPort match {
      case ("localhost", port) => "this is localhost address"
      case (host, port) => "some other address"
    }
```

或者是用来match option：

```scala
    val map = Map(1 -> "one", 2 -> "two")
    map.get(1) match {
      case Some(str) => "get something from map: " + str
      case None => "no result"
    }
```

Scala标准库中的Map的get方法的返回类型是Option，如果能够get到东西则返回Some，其中包着get到的值。如果get不到东西，则返回一个None。

由于Tuple和Option本身也是case class，所以上面的两段代码反编译出来和上面的Java代码是大同小异的。就不再赘述了。