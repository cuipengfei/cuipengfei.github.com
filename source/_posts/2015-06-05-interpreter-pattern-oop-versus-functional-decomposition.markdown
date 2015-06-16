---
layout: post
title: "解释器模式：OOP versus Functional Decomposition"
date: 2015-06-05 15:34
comments: true
categories: Scala OODP
---

# 解释器模式

> In computer programming, the interpreter pattern is a design pattern that specifies how to evaluate sentences in a language. The basic idea is to have a class for each symbol (terminal or nonterminal) in a specialized computer language. The syntax tree of a sentence in the language is an instance of the composite pattern and is used to evaluate (interpret) the sentence for a client.

以上是wiki对解释器模式的描述。

这是一个学术性稍强的模式，不太好找到生活化的比喻。

就直接上代码吧。

# Java

```java
interface Expression {
    int interpret(Map<String, Expression> variables);
}
```

首先有一个表达式的接口，定义一个求值的方法，该方法接收一个String -> Expression的map。

可以猜到，这个map就是该表达式求值的时候需要用到的context。

```java
class Plus implements Expression {
    Expression leftOperand;
    Expression rightOperand;

    public Plus(Expression left, Expression right) {
        leftOperand = left;
        rightOperand = right;
    }

    public int interpret(Map<String, Expression> variables) {
        return leftOperand.interpret(variables) + rightOperand.interpret(variables);
    }
}

class Minus implements Expression {
    Expression leftOperand;
    Expression rightOperand;

    public Minus(Expression left, Expression right) {
        leftOperand = left;
        rightOperand = right;
    }

    public int interpret(Map<String, Expression> variables) {
        return leftOperand.interpret(variables) - rightOperand.interpret(variables);
    }
}

class Number implements Expression {
    private int number;

    public Number(int number) {
        this.number = number;
    }

    public int interpret(Map<String, Expression> variables) {
        return number;
    }
}

class Variable implements Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public int interpret(Map<String, Expression> variables) {
        if (null == variables.get(name)) return 0;
        return variables.get(name).interpret(variables);
    }
}
```

然后有表达式的四个实现类：加法表达式，减法表达式，数字表达式，还有变量。

数字表达式在求值的时候就直接返回它被创建时拿到的数字就好了，这是最简单的。

加法和减法的interpret方法在求值的时候仅仅是把行为委托给了两个子表达式，再对子表达式的求值结果做加减法。

变量在求值的时候则是去context里面寻找其name对应的表达式（也就是它所指向的表达式），然后对其求值。

下面是对它们的结合使用：

```java
public class InterpreterExample {
    public static void main(String[] args) {
        Map<String, Expression> context = new HashMap<>();
        context.put("w", new Number(5));
        context.put("x", new Number(10));
        context.put("z", new Number(42));

        Plus expr = new Plus(new Variable("w"),
                new Minus(new Variable("x"),
                        new Variable("z")));

        System.out.println(expr.interpret(context));
    }
}
```

首先构造一个context，里面有w，x，z三个数字。然后计算w+(x-z)的值（看着像不像Lisp？）。

## 不过再想一下

这些代码实际做的是什么事呢？

实际就是把一个以遇到Number为退出条件的递归算法拆碎了。

如果我们不把它拆碎，就写成递归函数会如何呢？

# functions

用Scala试着实现一下：

```scala
trait Expr

case class Plus(left: Expr, right: Expr) extends Expr

case class Minus(left: Expr, right: Expr) extends Expr

case class Number(n: Int) extends Expr

case class Var(name: String) extends Expr

object ExprEval {
  def eval(expr: Expr, context: Map[String, Expr]): Int = {
    expr match {
      case Plus(l, r) => eval(l, context) + eval(r, context)
      case Minus(l, r) => eval(l, context) - eval(r, context)
      case Var(name) => eval(context(name), context)
      case Number(n) => n
    }
  }

  def main(args: Array[String]) {
    val context = Map("w" -> Number(5), "x" -> Number(10), "z" -> Number(42))
    val expr = Plus(Var("w"), Minus(Var("x"), Var("z")))
    println(eval(expr, context))
  }
}
```

以上就是全部代码，与Java版等价。
递归函数很容易看懂，其退出条件也很容易看出来。

69行代码变成了26行。

四个case class代表四种表达式，其中并没有什么方法，只是用来作为数据的承载者。

一个eval函数，用pattern match来对四种表达式进行不同的处理。

不过这次我倒不是要宣扬说解释器模式属于是用不合适的工具解决问题。

而是要介绍两种组织代码的方式：按行组织还是按列组织。

# 按行组织代码与按列组织代码

昨天我在看解释器模式，准备写一个Java实现，再写一个Scala实现，并以此来达到我黑Java的阴暗目的。

但是看了wiki上的示例代码后，马上就想起了去年上过的一门MOOC：[《Programming languages》](https://www.coursera.org/course/proglang)。
（这门课是由华盛顿大学的Dan Grossman教授讲授的，内容极好，强烈推荐。）

这门课里有一节就提到了上面说的两种组织代码的方式：按行组织还是按列组织。
这节课的视频在这里：[https://www.youtube.com/watch?v=uEHnI3pq_FM）](https://www.youtube.com/watch?v=uEHnI3pq_FM)

比如我们上面的两版代码，Java代码把对表达式的求值分散在每个不同的表达式类里。

而Scala代码把求值代码集中写在一个函数里，pattern match每种表达式类型并求值。

如果要做成一个表格的话，就是这样的：

![table](http://ww2.sinaimg.cn/large/8b1ece2agw1esug8rpnudj207o0bwt8v.jpg)

其中的问号代表具体的求值实现。

Java代码横向组织，有一个Plus类，里面有interpret方法，有一个Minus类，里面有interpret方法，等等。这是按照行组织。

而Scala代码则纵向组织，有一个eval函数，纵向把四种表达式的求值都包揽了。这是按列组织。

上面的表格太小，看着不明显，现在假设我们需要打印表达式的功能。那么表格就会变成这样：

![table2](http://ww4.sinaimg.cn/large/8b1ece2agw1esugg4y9wij20ba0c43yu.jpg)

可以想象，Java代码里会在每个表达式类里加一个toString函数的实现。横向扩展，一个类把数据和算法组织在一起。

而在Scala代码里则会写一个toString的递归函数，包揽所有字符串打印的工作。纵向扩展，一个函数去分辨数据类型，并据此选择计算策略。

# OOP versus Functional Decomposition

那到底哪种组织方式更好呢？

并没有确定的答案，Dan Grossman教授在课程中给出的解释是这样的：

> FP and OOP often doing the same thing in exact opposite way: organize the program “by rows” or “by columns”.
> Which is “most natural” may depend on what you are doing (e.g., an interpreter vs. a GUI) or personal taste.

到底如何组织取决于你想要解决什么样的问题，比如你要做一个GUI库，那么数据与算法放在一起，互相接近是最自然的组织方式。这时选择OOP是最好的设计决策。

而如果你要实现的东西类似于本文中的解释器，那么一个递归的算法来统一处理所有表达式类型则是最自然的。这时选择Functional Decomposition是最好的设计决策。

# 结语

OOP与Functional Decomposition，这二者并不是完全对立的。

熟练掌握多种抽象与代码组织方式，正确识别应用场景，据此选择合适的范式，或者是选择多种范式结合使用，才是这一系列博文的真实用意。

只不过由于传统的OO设计模式过于盛行，FP范式接受度不够，才会有这一系列博文黑Java，捧Scala的表象。
