---
title: 除去Scala的糖衣(13) -- Default Parameter Value
date: 2014-06-21 11:06:13
tags: 杂7杂8
---
欢迎关注我的新博客地址： [ http://cuipengfei.me/ ](http://cuipengfei.me/)

好久没有写博客了，上一次更新竟然是一月份。

说工作忙都是借口，咋有空看美剧呢。

这半年荒废掉博客说到底就是懒，惯性的懒惰。写博客这事儿，一丢掉就很久捡不起来。

闲话到此为止，下面进入正题。

Default parameter value，默认参数值。
这个很容易理解，给参数一个默认值，如果调用者不显式指明参数值，则使用默认值。如果显式指明了，那就用显式指明的值。

举个例子：

    
    
    1
    
    
    
    def hello(name: String = "world") = println("hello " + name)
    

这个函数，如果我们不给它传参数，它就会打印hello world。就像这样：

    
    
    1
    
    
    
    hello()
    

如果给了参数：

    
    
    1
    
    
    
    hello("everybody")
    

则打印hello everybody。

这个语言特性都有哪些应用场景呢？

它经常用来避免过多的重载。一个很常见很典型的例子就是构造函数重载。

在Java中，为了让调用者能够比较容易的创建某个类的实例，我们通常会提供几个参数列表比较短的构造函数。而这些构造函数存在的唯一意义就是为了写死某几个参数值。
而在Scala中，有了这个语言特性，我们就无需那么麻烦了。

那这个语言特性是如何实现的呢？实际上简单的一塌糊涂。

这样一段代码：

    
    
    1
    2
    3
    4
    5
    6
    7
    
    
    
    class Greeter {
      def hello(name: String = "world") = println("hello " + name)
    }
    
    class AnotherClass {
      new Greeter().hello()
    }
    

我们的Greeter类含有前面提到过的hello方法。在AnotherClass里调用了hello，并且没有显式指明参数值。

上面的Scala代码生成的bytecode反编译成Java是这样的：

    
    
    1
    2
    3
    4
    5
    6
    7
    8
    9
    10
    11
    12
    13
    14
    15
    16
    17
    
    
    
    public class Greeter {
        public void hello(String name) {
            Predef..MODULE$.println(new StringBuilder().append("hello ").append(name).toString());
        }
    
        public String hello$default$1() {
            return "world";
        }
    }
    
    public class AnotherClass {
        public AnotherClass() {
            Greeter qual$1 = new Greeter();
            String x$1 = qual$1.hello$default$1();
            qual$1.hello(x$1);
        }
    }
    

可以看到，我们所定义的hello方法反编译出来看起来很普通，就是个接受一个参数的方法。

而在Greeter类中，编译器为我们加入了另一个方法hello$default$1，这个方法就是返回一个写死的字符串，其值为world。

在AnotherClass中调用hello时，写死的字符串被取到，然后传进了hello里。

这样，被调用者提供了参数的默认值，调用者在调用时取得该值，然后传入方法。

###  题外话

到这里我不禁联想起C#中的默认参数值的实现方式。

在C#中，默认参数的值会被编译成调用者的一个常量，而不是像Scala一样的由被调用者提供。

这样看起来貌似没啥区别，不就是写死的值换个地方吗？

其实不然，如果被调用者在A程序集内，调用者在B程序集内，那么A更新时，B就必须重新编译才能得到最新的默认值。也就是说，如果当前部署环境中同时存在A和B，而后
我们拿一个新版的A来替换老的，这时B仍然在传递老的默认参数值给A。这样就会造成一些看似很诡异的行为偏差。

如果对C#的默认参数值的实现有兴趣，请看 [ 我很久很久之前写的博客
](http://www.cnblogs.com/cuipengfei/archive/2011/04/13/2014325.html)

现在想来，C#这一语言特性的设计者为什么要把它设计成如此容易出错的样子呢？

思而不得其解。

  * [ 点赞  1  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

