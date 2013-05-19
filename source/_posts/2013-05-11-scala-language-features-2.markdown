---
layout: post
title: "Scala中的语言特性是如何实现的(2)"
date: 2013-05-11 23:37
comments: true
categories: 
---

[上篇博文](http://cuipengfei.me/blog/2013/05/05/how-are-scala-language-features-implemented/)的末尾留了三个问题，现在自问自答一下。

###在Scala中被声明为val的v4为什么在反编译的Java中不是final的呢？

在方法中声明局部变量时，如果用Scala的val关键字（或者是Java中的final）来修饰变量，则代表着此变量在赋过初始值之后不可以再被重新赋值。这个val或者final只是给编译器用的，编译器如果发现你给此变量重新赋值会抛出错误。

而bytecode不具备表达一个局部变量是immutable的能力，也就是说对于JVM来说，不存在不可变的局部变量这个概念。所以v4在反编译之后，就和普通的局部变量无异了。

###在Scala中被声明为val的v2为什么在反编译的C#中不是readonly的呢？

这是个挺tricky的问题，我试着解释一下。Scala .NET是基于IKVM实现的，IKVM可以把Java bytecode翻译为CIL。
所以Scala编译为CIL的过程实际是这样的：
	                
Scala -----Scala编译器-----> bytecode -----IKVM-----> CIL

Scala编译器编译出的bytecode实际是用final修饰了v2的，但是bytecode中的final和CIL中的initonly（对应C#的readonly）是不一样的。

Java中，final实例变量定义的时候，可以先声明，而不给初值，然后我们可以在任何一个方法中给它赋初值。这提供了更大的灵活性，一个Java类中的final成员可以依对象而不同，却保持其immutable的特征。

而CIL的initonly则要严格一点，CLI标准（ECMA-334）这样描述：
> initonly marks fields which are constant after they are initialized. These fields shall only be mutated inside a constructor. If the field is a static field, then it shall be mutated only inside the type initializer of the type in which it was declared. If it is an instance field, then it shall be mutated only in one of the instance constructors of the type in which it was defined. It shall not be mutated in any other method or in any other constructor, including constructors of derived classes.

可见，一个initonly的成员，不是随便在哪儿都可以赋初值的。由于这点不同IKVM就没有直接把final翻译成initonly。如果想让v2在C#代码中变成readonly的，可以给IKVM加上strictfinalfieldsemantics这个参数。

###为什么反编译出来的C#代码中的实例级公开方法都是标有override的呢？

这个问题还没搞明白。

但是有个有趣的现象，如果用Scala .NET来编译Scala源码，编译出的实例级方法都是标有override的；而如果先把Scala代码编译为.class然后再用IKVM把.class文件转换为CIL的话，方法则是标有virtual的。我猜这可能和Java中的方法默认是可以被overirde的有关。

下面开始正文，前面填坑用了不少篇幅，所以这次只分析一个语言特性：Scala中的constructor。

##Constructor

Scala中可以在声明class的同时声明一个constructor，比如这样：

```scala
class ScalaConstructorExample(val x: Double, y: String) {
  println(x + y)
}
```

构造函数接收两个参数x和y，然后把x和y拼在一起打印出来。反编译为Java：

```java
public class ScalaConstructorExample
{
  private final double x;

  public double x()
  {
    return this.x; 
  } 
  
  public ScalaConstructorExample(double x, String y) 
  { 
    Predef..MODULE$.println(new StringBuilder().append(x).append(y).toString()); 
  }
}
```

可以发现编译器给标为val的x生成了一个getter，很方便的语法糖。而直接写在类内的打印语句则被放到了构造函数内。下面是反编译为C#的代码：

```c#
public class ScalaConstructorExample : ScalaObject
{
	private double x = x;

	public override double x()
	{
		return this.x;
	}
	
	public ScalaConstructorExample(double x, string y)
	{
		Predef$.MODULE$.println(new StringBuilder().Append(x).Append(y).ToString());
	}
}
```
和Java代码基本无异。比较一下，Scala用3行代码表达的含义，Java和C#要用14行才行。

现在加一个重载的构造函数：

```scala
class ScalaConstructorExample(val x: Double, y: String) {
  println(x + y)

  def this(x: Double) = {
  	this(x, "hello")
  }
}
```

这个构造函数给了y一个默认值“hello”。反编译为Java：

```java
public class ScalaConstructorExample
{
  private final double x;

  public double x()
  {
    return this.x; 
  } 
  
  public ScalaConstructorExample(double x, String y) 
  { 
  	Predef..MODULE$.println(new StringBuilder().append(x).append(y).toString()); 
  }

  public ScalaConstructorExample(double x) 
  {
    this(x, "hello");
  }
}
```

对应的C#代码：

```c#
public class ScalaConstructorExample : ScalaObject
{
	private double x = x;

	public override double x()
	{
		return this.x;
	}

	public ScalaConstructorExample(double x, string y)
	{
		Predef$.MODULE$.println(new StringBuilder().Append(x).Append(y).ToString());
	}
	
	public ScalaConstructorExample(double x) : this(x, "hello")
	{
	}
}
```

构造函数重载这个特性就显得平淡无奇了，不过还是比较一下行数。定义两个构造函数，打印出构造函数的参数，声明一个getter，这三件事Scala只用7行代码就完成了，Java和C#都需要将近20行。