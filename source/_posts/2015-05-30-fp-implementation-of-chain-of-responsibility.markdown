---
layout: post
title: "fp implementation of chain of responsibility"
date: 2015-05-30 09:40
comments: true
categories:
---

# 职责链模式

> 责任链模式在面向对象程式设计里是一种软件设计模式，它包含了一些命令对象和一系列的处理对象。每一个处理对象决定它能处理哪些命令对象，它也知道如何将它不能处理的命令对象传递给该链中的下一个处理对象。该模式还描述了往该处理链的末尾添加新的处理对象的方法。

以上是wiki对职责链模式的定义。

举个例子来说，我们的系统中需要记录日志的功能。而日志有需要根据优先级被发送到不同的地方。

低优先级的日志输出到命令行就好了。而高优先级的错误信息则需要通过邮件通知相关人员并且输出到命令行。

这个例子也是来自wiki的。

以下是wiki提供的实现：

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

首先定义一个Logger抽象类。从其setNext和message这两个方法可以看出，我们后面会把具有不同writeMessage实现的Logger的链到一起，并且依次让它们处理某件需要被记录的事件。

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

最后，有一个main函数，创建三个Logger的实例，把它们通过setNext链在一起。
只需要调用一次message就可以让三个Logger依次工作。

如果以后再有更多的Logger呢，还是可以通过同样的方式把它们链接起来协同工作。

很好，很强大，很易于扩展，对吧？

## 不过再想一下

这三个Logger的实现类看起来都非常的单薄，弱不禁风。

一个接收mask的构造函数，其唯一职责就是把接收到的mask传递给父类的构造函数。

然后父类根据mask和所发生事件优先级的大小关系决定到底要不要调用子类实现了的writeMessage方法。

也就是说，子类完全没有定义自己的实例级状态，其实例级方法的行为也就谈不上随着其状态的变化而变化了。

换句话说，这几个子类存在的价值就在于为父类提供writeMessage这个函数。

啊。。。。。。！

一说到提供函数，我就想到了。。。。。。

# functions

我想到的自然是FP了，既然需要的是函数，那我们就使用函数好了。

何必用更重的抽象手段：类，去包裹函数呢？

下面就是比较偏函数式的实现：

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
