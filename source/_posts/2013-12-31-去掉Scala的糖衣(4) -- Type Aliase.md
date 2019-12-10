---
title: 去掉Scala的糖衣(4) -- Type Aliase
date: 2013-12-31 20:55:30
tags: 杂7杂8
---
我的新博客地址： [ http://cuipengfei.me/blog/2013/12/23/desugar-scala-4/
](http://cuipengfei.me/blog/2013/12/23/desugar-scala-4/)

Scala中有一个type关键字，用来给类型或者是操作起别名，用起来很是方便。

比如这样：

    
    
    1
    
    
    
    type People = List[Person]
    

这样就是给List[Person]（方括号是Scala的类型参数的写法）声明了一个别名，叫做People。

接下来就可以这样使用它：

    
    
    1
    2
    3
    
    
    
      def teenagers(people: People): People = {
        people.filter(person => person.age < 20)
      }
    

这个代码编译之后没有什么神奇的，仅仅是把所有出现People这个字眼的地方都用List of Person替代了。

    
    
    1
    2
    3
    4
    5
    6
    7
    
    
    
      public List<Person> teenagers(List<Person> people)
      {
        return (List)people.filter(new AbstractFunction1() { public static final long serialVersionUID = 0L;
          public final boolean apply(Person person) { return person.age() < 20; }
        });
      }
    

这种给类型一个别名的特性只是一个小糖豆，不太甜，真正有趣的是给一类操作命名（联想C#中定义delegate）。

比如这样：

    
    
    1
    
    
    
    type PersonPredicate = Person => Boolean
    

接受一个Person，返回一个Boolean，我们把这一类用来判断一个人是否符合某个条件的操作统称为PersonPredicate。

然后我们可以定义以下predicate：

    
    
    1
    
    
    
    val teenagerPred: PersonPredicate = person => person.age < 20
    

然后前面写过的teenagers方法就可以这样重新定义：

    
    
    1
    2
    3
    
    
    
      def teenagers(people: People): People = {
        people.filter(teenagerPred)
      }
    

按照这个思路下去，我们就可以开始composite functions了。比如说，我们跟人收税，就可以这么做：

    
    
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
    
    
    
      type Tax = Person => Double
      val incomeTax: Tax = person => person.income * 5 / 100
      val kejuanzaTax: Tax = person => person.income * 20 / 100
      def giveMeYourMoney(p: Person) = {
        calculateTax(p, List(incomeTax, kejuanzaTax))
      }
      def calculateTax(person: Person, taxes: List[Tax]): Double = {
        taxes.foldLeft(0d) {
          (acc, curTax) => acc + curTax(person)
        }
      }
    

从一个人那里拿到钱，这种操作，我们称之为Tax。然后定义个税和苛捐杂税，或者也可以有任意多的税种。

然后就可以把任意的几个税种放在一个List里面，和calculateTax去composite了。

当然，没有type这个关键字，我们也可以composite functions。只不过就得写成这样：

    
    
    1
    2
    
    
    
    val teenagerPred: (Person) => Boolean = person => person.age < 20
    def incomeTax: (Person) => Double = person => person.income * 5 / 100
    

看着稍微有点眼花。

这种用type关键字给一种操作命名的代码反编译之后是这样的：

    
    
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
    18
    19
    20
    
    
    
      public Function1<Person, Object> teenagerPred()
      {
        return new AbstractFunction1() { public static final long serialVersionUID = 0L;
          public final boolean apply(Person person) { return person.age() < 20; }  } ;
      }
      public Function1<Person, Object> incomeTax()
      {
        return new AbstractFunction1() { public static final long serialVersionUID = 0L;
          public final double apply(Person person) { return person.income() * 5 / 100; }  } ;
      }
      public Function1<Person, Object> kejuanzaTax()
      {
          return new AbstractFunction1() { public static final long serialVersionUID = 0L;
        public final double apply(Person person) { return person.income() * 20 / 100; } } ;
      }
    

可以看到所有这种接受一个参数，返回一个值的操作都是Function1<Person, Object>。

推测一下，接受两个参数，返回一个值的是不是该叫做Function2呢？

    
    
    1
    2
    3
    
    
    
    type TwoToOne = (String, Int) => Double
    def twoToOneImpl: TwoToOne = (str, i) => 1
    

反编译之后，果不其然：

    
    
    1
    2
    3
    4
    
    
    
    public Function2<String, Object, Object> twoToOneImpl()
    {
      return new Hello..anonfun.twoToOneImpl.1(this);
    }
    

那不接收参数，只有返回值的呢？

    
    
    1
    2
    3
    
    
    
      type NoInJustOut = () => String
      def noInJustOutImpl: NoInJustOut = () => "hello world"
    

反编译之后，其实是变成了Function0 of String:

    
    
    1
    2
    3
    4
    
    
    
      public Function0<String> noInJustOutImpl()
      {
        return new Hello..anonfun.noInJustOutImpl.1(this);
      }
    

到这里，我们可以总结一下type alia这个糖衣：

一个类型的type alias，类似于这样的：type t = x。编译器将在所有使用到t的地方把t替换为x。

对于一种操作的type alias，编译器将会根据参数列表和返回值类型的不同将其替换为对应的Function0,Function1,Function2 ……
一直到Function22。

如果我们真的定义一个超过二十二个参数的操作会如何呢？

    
    
    1
    2
    3
    4
    5
    6
    7
    8
    
    
    
      type twentyThree = (
          String, String, String, String,
          String, String, String, String,
          String, String, String, String,
          String, String, String, String,
          String, String, String, String,
          String, String, String
        ) => String
    

Scala编译器会直接告诉我们： type Function23 is not a member of package scala

  * [ 点赞  1  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

