---
layout: post
title: "剥掉Scala的糖衣(11) -- structural types"
date: 2014-01-02 22:13
comments: true
categories: 
---

Structural types，中文怎么翻译不确定。我们可以用它来实现类似于鸭子类型的效果。为什么说是“类似”鸭子类型呢？稍后会说到它和鸭子类型的区别。

举一个例子，看看它都可以做什么：

```scala
def makeNoise(quacker: {def quack(): String}) = quacker.quack
```

声明一个方法，叫做makeNoise，接受什么类型的参数呢？不做严格限制，我们只声明说参数必须有一个叫做quack的方法，该quack方法返回值类型为String。然后在makeNoise方法内调用quack方法。请注意我们并没有声明一个含有quack方法签名的接口或者类，我们仅仅是在声明参数的同时声明我们期待参数含有什么样的成员。

然后我们声明一个Duck类：

```scala
class Duck {
  def quack() = "real quack"
}
```

这样就可以调用makeNoise方法了：

```scala
makeNoise(new Duck)
```

或者再声明一个NotADuck类：

```scala
class NotADuck {
  def quack() = "fake quack"
}
```

也可以把它传给makeNoise方法：

```scala
makeNoise(new NotADuck)
```

甚至是匿名对象也可以：

```scala
  makeNoise(new {
    def quack() = "anonymous quack"
  })
```

如果我们把Duck的quack方法改个名字：

```scala
class Duck {
  def quackRenamed() = "real quack"
}
```

那么编译器就会对下面这行代码给出错误：

```scala
makeNoise(new Duck)
```

type mismatch, found : hello.Duck, required: AnyRef{def quack(): String}

是做了编译时类型检查的。

然后我们反编译代码，看看它是如何实现的：

```java
    private static Class[] reflParams$Cache1;
    private static volatile SoftReference reflPoly$Cache1;
    
    static {
        Hello.reflParams$Cache1 = new Class[0];
        Hello.reflPoly$Cache1 = new SoftReference((T)new EmptyMethodCache());
    }
    
    public static Method reflMethod$Method1(final Class x$1) {
        MethodCache methodCache1 = Hello.reflPoly$Cache1.get();
        if (methodCache1 == null) {
            methodCache1 = (MethodCache)new EmptyMethodCache();
            Hello.reflPoly$Cache1 = new SoftReference((T)methodCache1);
        }
        Method method1 = methodCache1.find(x$1);
        if (method1 != null) {
            return method1;
        }
        method1 = ScalaRunTime$.MODULE$.ensureAccessible(x$1.getMethod("quack", Hello.reflParams$Cache1));
        Hello.reflPoly$Cache1 = new SoftReference((T)methodCache1.add(x$1, method1));
        return method1;
    }
    
    public String makeNoise(final Object quacker) {
        final Object invoke;
        try {
            invoke = reflMethod$Method1(quacker.getClass()).invoke(quacker, new Object[0]);
        }
        catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
        return (String)invoke;
    }
```

我们可以看到，makeNoise方法的参数类型被编译成了Object。方法内部通过反射去调用quack方法。

再仔细看一下，方法内做了个catch，如果找不到quack方法就把异常抛出来。我们刚才不是看到有编译时类型检查吗？怎么会找不到quack方法呢？

其实找不到quack方法的情况还是会存在的。假如我们把以上代码打成jar包供别人调用，那别人看到的你这个方法要的是Object啊，随便传一个什么东西进来都可以。如果传入的参数没有quack方法，那自然就会有异常了。这也是一个很好的信号，告诉我们说这个语言特性不适合用在public API中。

刚开始时提到过，这个语言特性不能叫做鸭子类型，为什么呢？我们看两个真正鸭子类型的例子：

```javascript
function makeNoise(quacker){
	return quacker.quack()
}

makeNoise({quack:function(){return "quack quack"}})
```

上面的是JavaScript的，下面的是C#的。

```csharp
	class Duck
	{
		public String Quack ()
		{
			return "quack quack";
		}
	}

	class Hello
	{
		public String MakeNoise (dynamic quacker)
		{
			return quacker.Quack ();
		}
	}

	class MainClass
	{
		public static void Main (string[] args)
		{
			Console.Out.WriteLine (new Hello ().MakeNoise (new Duck ()));
		}
	}
```

在上面两段代码中，如果我们把quack或者Quack改个名字的话，都会导致运行失败。而且在声明参数的时候也没有指明我们期待有一个quack或者Quack成员的存在。所以说Scala的这个语言特性不能称之为鸭子类型的原因在于两点：

* Scala做了编译时类型安全检查
* 声明参数时显式的指明了期待的成员

存疑：虽然上面的C#例子看起来是鸭子类型，但是C#的wiki页面上的typing discipline一项里并没有列出duck typing。原因不明。


=======================================

后记：C#的wiki页面没有把duck typing列为其typing discipline的一项。但是，duck typing的wiki页面上又用C#举了例子。互相矛盾啊，于是我在stackoverflow上问了个问题，引来了C#编译器的程序员之一Eric Lippert，他回答问题之后还写了篇博客来表示对duck typing这个名词的困惑。看完之后我表示更晕了。

所以请不要把上面我说的关于duck typing的东西当真，随便瞄一眼就好了。duck typing是一个没有清晰定义的名词，在我们能够共同认同它的某一种特定的definition之前去讨论它是无谓的。

下面把链接列出：

[C#的wiki](http://en.wikipedia.org/wiki/C_Sharp_programming_language)  （typing discipline在页面右侧的那个表里）

[Duck typing的wiki](http://en.wikipedia.org/wiki/Duck_typing)

[Eric Lippert的博客](http://ericlippert.com/2014/01/02/what-is-duck-typing/)

以上链接，建议别点  :)