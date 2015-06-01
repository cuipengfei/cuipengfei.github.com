---
layout: post
title: 职责链模式的别扭就像用门框夹核桃
date: '2015-05-30 09:40'
comments: true
categories: scala
keywords: 'scala, java, design pattern, Chain Of Responsibility pattern, OO, FP, 设计模式'
---

# 职责链模式
> 责任链模式在面向对象程式设计里是一种软件设计模式，它包含了一些命令对象和一系列的处理对象。每一个处理对象决定它能处理哪些命令对象，它也知道如何将它不能处理的命令对象传递给该链中的下一个处理对象。该模式还描述了往该处理链的末尾添加新的处理对象的方法。

以上是wiki对职责链模式的定义。

举个例子来说，我们的系统中需要记录日志的功能。日志需要根据优先级被发送到不同的地方。

低优先级的日志输出到命令行就好了。而高优先级的错误信息则需要通过邮件通知相关人员并且输出到命令行。

这个例子也是来自wiki的。

以下是wiki提供的Java实现：

# Java

```java
abstract class Logger {
    public static int ERR = 3;
    public static int NOTICE = 5;
    public static int DEBUG = 7;
    private int mask;

    private Logger next;

    public Logger(int mask) {
        this.mask = mask;
    }

    public void setNext(Logger logger) {
        next = logger;
    }

    public void message(String msg, int priority) {
        if (priority <= mask) {
            writeMessage(msg);
        }
        if (next != null) {
            next.message(msg, priority);
        }
    }

    abstract protected void writeMessage(String msg);
}
```

首先定义一个Logger抽象类。从其setNext和message这两个方法可以看出，我们后面会把多个具有不同writeMessage实现的Logger链到一起，并且依次让它们处理某件需要被记录的事件。

```java
class StdoutLogger extends Logger {
    public StdoutLogger(int mask) {
        super(mask);
    }

    protected void writeMessage(String msg) {
        System.out.println("Writing to stdout: " + msg);
    }
}

class EmailLogger extends Logger {
    public EmailLogger(int mask) {
        super(mask);
    }

    protected void writeMessage(String msg) {
        System.out.println("Sending via e-mail: " + msg);
    }
}

class StderrLogger extends Logger {
    public StderrLogger(int mask) {
        super(mask);
    }

    protected void writeMessage(String msg) {
        System.err.println("Sending to stderr: " + msg);
    }
}
```

然后有三个Logger的实现，分别为向命令行输出消息，发送邮件（当然是假的），向命令行输出错误。

```java
public class ChainOfResponsibilityExample {

    private static Logger createChain() {
        Logger logger = new StdoutLogger(Logger.DEBUG);

        Logger logger1 = new EmailLogger(Logger.NOTICE);
        logger.setNext(logger1);

        Logger logger2 = new StderrLogger(Logger.ERR);
        logger1.setNext(logger2);

        return logger;
    }

    public static void main(String[] args) {
        Logger chain = createChain();
        chain.message("Entering function y.", Logger.DEBUG);
        chain.message("Step1 completed.", Logger.NOTICE);
        chain.message("An error has occurred.", Logger.ERR);
    }
}
```

最后，有一个main函数，创建三个Logger的实例，把它们通过setNext链在一起。 只需要调用一次message就可以让三个Logger依次工作。

如果以后再有更多的Logger呢，还是可以通过同样的方式把它们链接起来协同工作。

很好，很强大，很易于扩展，对吧？

## 不过再想一下
这三个Logger的实现类看起来都非常的单薄，弱不禁风。

一个接收mask的构造函数，其唯一职责就是把接收到的mask传递给父类的构造函数。

然后父类根据mask和所发生事件优先级的大小关系决定到底要不要调用子类实现的writeMessage方法。

也就是说，子类完全没有定义自己的实例级状态，其实例级方法的行为也就谈不上随着其状态的变化而变化了。

换句话说，这几个子类存在的价值就在于为父类提供writeMessage这个函数。

啊。。。。。。！

一说到提供函数，我就想到了。。。。。。

# functions
我想到的自然是FP了，既然需要的是函数，那我们就使用函数好了。

何必用更重的抽象手段：类，去包裹函数呢？

下面就是比较偏函数式的Scala实现：

```scala
object Loggers {
  val ERR = 3
  val NOTICE = 5
  val DEBUG = 7

  case class Event(message: String, priority: Int)

  type Logger = Event => Event

  def stdOutLogger(mask: Int): Logger = event => handleEvent(event, mask) {
    println(s"Writing to stdout: ${event.message}")
  }

  def emailLogger(mask: Int): Logger = event => handleEvent(event, mask) {
    println(s"Sending via e-mail: ${event.message}")
  }

  def stdErrLogger(mask: Int): Logger = event => handleEvent(event, mask) {
    System.err.println(s"Sending to stderr: ${event.message}")
  }

  private def handleEvent(event: Event, mask: Int)(handler: => Unit) = {
    if (event.priority <= mask) handler
    event
  }
}
```

这个代码已经简短到我不想解释的程度了。不过还是解释一下吧。

三个log的的等级ERR，NOTICE和DEBUG和之前Java的实现是一样的。

一个case class Event，用来包裹需要被log的事件。

type Logger则是声明了一个函数签名，凡是符合这个签名的函数都可以作为logger被使用。

然后便是三个函数实现，它们将mask通过闭包封进函数内。这三个函数共同依赖一个私有handleEvent函数，其作用和Java代码中的message类似，判断mask和正在发生的事件之间优先级大小关系，并以此决定当前logger是否需要处理该事件。

哎？等一下，这个是职责链模式啊，那个啥，链在哪儿呢？

很简单，这样就可以链起来了：

```scala
object ChainRunner {

  import chain.Loggers._

  def main(args: Array[String]) {
    val chain = stdOutLogger(DEBUG) andThen emailLogger(NOTICE) andThen stdErrLogger(ERR)

    chain(Event("Entering function y.", DEBUG))
    chain(Event("Step1 completed.", NOTICE))
    chain(Event("An error has occurred.", ERR))
  }
}
```

以上代码中的andThen就可以把三个logger链在一起。

这个andThen是个什么东西？何以如此神奇？

欲知详情，请参考我之前的另一篇博客： [http://cuipengfei.me/blog/2013/12/30/desugar-scala-9/](http://cuipengfei.me/blog/2013/12/30/desugar-scala-9/)

而链接之后的结果本身也是一个函数，于是我们就可以调用chain并传入Event了。

这份代码和前面Java版的行为是等价的，输出是一致的。

# 门框夹核桃

最后回到标题上去：门框夹核桃，意即用不合适的工具解决问题。

职责链模式想要做到的事情其实就是把多个函数链起来调用。

该模式提出的时候FP并不如今日盛行，其作者选用类来包装需要被链接的多个函数，这无可厚非。

无论是class，还是function，都是为程序员提供抽象的手段。当我们想要链接的东西就是多个function，选择直接用function而非class就会显得更加自然，也更加轻量且合适。

当年design pattern的作者广为传播各种patterns，实为功德。

不过今天我们有了核桃夹，就无需一定要用门框了。

最后，依照惯例，羞辱Java一小下下。
以上wiki提供的实现有77行，偏FP风的实现只有38行，只有一个实体Event。
