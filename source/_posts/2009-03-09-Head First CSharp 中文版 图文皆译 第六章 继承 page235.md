---
title: Head First C# 中文版 图文皆译 第六章 继承 page235
date: 2009-03-09 09:17:00
tags: 我翻译的Head First C#（习作）
---
②  创建Party类

  

确保Party类是public的。你需要仔细的观察类图，来分析出要把DinnerParty和BirthdayParty中哪些的方法、属性移动到Party中去
。

  

*  把NumberOfPeople和CostOfDecoration移动进去，以此来与DinnerParty和BirthdayParty保持兼容。 

  

*  也把CalculatorCostOfDecorations（）和CalculateCost（）方法移动进去。如果这些方法用到了某些私有字段，也要把该字段一起移动。（记住，子类只继承公有字段--一旦你把私有字段移动到Party类，DinnerParty和BirthdayParty类就不能访问该字段了。） 

  

*Party  类还要有构造方法。仔细观察一下DinnerParty和BirthdayParty的构造方法--它们相同的部分要移动到Party的构造方法里面去。 

  

*  现在添加向超过12人的聚会加收费$100的功能。毕竟，我们就是因此才在做这些改变的！这个特性同时属于DinnerParty和BirthdayParty，所以它需要处于Party类中。 

  

③  让DinnerParty继承自Party

  

由于现在Party类可以做很多原来DinnerParty做的工作，所以你可以把重叠部分从DinnerParty中消除掉了，只把DinnerParty中独有的
部分保留下来。

  

*  确保构造方法运行良好。它做Party的构造方法不做的事儿吗？如果做的话，把它保留下来，其他相同部分留给父类的构造方法去完成。 

  

*  关于健康选择的部分要留在DinnerParty中。 

  

*  你至少需要覆写一个方法，因为它做DinnerParty中以独特的方式工作。 

  

④  让BirthdayParty继承Party

  

对于BirthdayParty也一样，把共性的东西移动到父类，只把特性的东西保留在BirthdayParty中。

  

*BirthdayParty  的构造方法需要做什么Party的构造方法没有完成的事情吗？ 

  

*  你要在BirthdayParty内处理蛋糕花费。它触及到一个方法和一个属性，你需要覆写它们。 

  

*  没错，可以覆写属性！和覆写方法一样。你设置base.NumberOfPeople的值的时候，会调用这个属性父类中的的set访问器。使用set和get访问器都需要用到base关键字。 



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

