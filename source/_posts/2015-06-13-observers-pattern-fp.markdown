---
layout: post
title: "观察者模式 in FP：Mutation vs Transformation"
date: 2015-06-13 22:49
comments: true
categories: Scala OODP
---

# 观察者模式

> 观察者模式（有时又被称为发布/订阅模式）是软件设计模式的一种。在此种模式中，一个目标对象管理所有相依于它的观察者对象，并且在它本身的状态改变时主动发出通知。这通常透过呼叫各观察者所提供的方法来实现。此种模式通常被用来实作事件处理系统。

以上是wiki对观察者模式的解释。

举一个《Head first design pattern》中的例子：

比如说有一个气象站，每当气象有变化的时候就需要显示当前天气。
需要显示历史平均气温，最高气温和最低气温。
还需要根据气压预测晴雨。

这种情况就很适合使用观察者模式，每种需要显示气象的装置作为观察者，气象数据本身作为可以被观察的对象。
每当气象变化的时候，被观察的对象就会通知观察者来根据新的数据作出新的显示。

以下是书中给出的代码：

# Java

```java
public interface Observer {
    void update(float temp, float humidity, float pressure);
}

public interface Subject {
    void registerObserver(Observer o);

    void notifyObservers();
}
```

首先定义两个接口，一个是观察者，接收新的气象数据。一个是被观察者，可以注册观察者以及通知观察者。

```java
public class WeatherData implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>();
    private float temperature;
    private float humidity;
    private float pressure;

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
}
```

接下来定义气象数据本身。代码很容易理解，把观察者保存在一个list里，每当气象数据变化的时候就通知这些观察者去做出新的处理。

```java
public class CurrentConditionsDisplay implements Observer {

    public CurrentConditionsDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }

    public void update(float temperature, float humidity, float pressure) {
        System.out.println("Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }
}

public class StatisticsDisplay implements Observer {
    private float maxTemp = 0.0f;
    private float minTemp = 200;
    private float tempSum = 0.0f;
    private int numReadings;

    public StatisticsDisplay(WeatherData weatherData) {
        weatherData.registerObserver(this);
    }

    public void update(float temp, float humidity, float pressure) {
        tempSum += temp;
        numReadings++;

        if (temp > maxTemp) {
            maxTemp = temp;
        }

        if (temp < minTemp) {
            minTemp = temp;
        }

        System.out.println("Avg/Max/Min temperature = " + (tempSum / numReadings)
                + "/" + maxTemp + "/" + minTemp);
    }
}

public class ForecastDisplay implements Observer {
    private float currentPressure = 29.92f;
    private float lastPressure;

    public ForecastDisplay(WeatherData weatherData) {
        weatherData.registerObserver(this);
    }

    public void update(float temp, float humidity, float pressure) {
        lastPressure = currentPressure;
        currentPressure = pressure;

        System.out.print("Forecast: ");
        if (currentPressure > lastPressure) {
            System.out.println("Improving weather on the way!");
        } else if (currentPressure == lastPressure) {
            System.out.println("More of the same");
        } else if (currentPressure < lastPressure) {
            System.out.println("Watch out for cooler, rainy weather");
        }
    }
}
```

然后有三个观察者，分别负责显示当前气象，气象历史分析和晴雨预测。

CurrentConditionsDisplay是最简单的，没有任何状态，它只是负责在每次气象有变化的时候把最细的气象显示出来。

StatisticsDisplay复杂一点点，它需要记录历史气温，以便于计算平均温度，最高和最低气温。这是一个会有状态变化的对象。

ForecastDisplay也有状态变化，它需要记录上次的气压，以便于根据气压变化来预测晴雨。

```java
public class WeatherStation {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}
```

最后，有一个main函数，把以上所有代码串起来。

三个观察者都在观察同一个气象数据，每当气象有变化的时候，三个观察者都会被通知，并作出相应处理。

如果我们需要其他的更复杂的气象显示装置，只需要实现Observer接口，注册到气象数据上去，那么在每次气象有变化的时候就可以收到通知并作出处理。不需要对已有代码做出任何改变。

很灵活，很强大，对吧？

## 不过再想一下

观察者模式有没有更好地实现方式呢？

答案肯定是有的。

C#的delegate和Event就是一种用来实现观察者模式的很好的语言特性。它在语言级别为添加事件订阅和取消订阅提供了支持。

不过这一篇博客主要是想要讲一个immutable的观察者模式实现，C#就不多讲了。

可以想一下，上面的Java代码里的三个观察者，CurrentConditionsDisplay是没有任何状态变化的，它存在的意义仅在于其update方法。
而这个方法每次都是接受最新的气象数据，并作出输出。

StatisticsDisplay和ForecastDisplay则是截取气象历史数据不同的片段，将其作为可变状态封装在内部，并据其状态的改变决定update方法的行为。

这样看来，如果我们有一种方式，可以提供完整的气象数据历史，那么这三个观察者就都可以各取所需，而不需要拥有自己的可变状态了。

具体该怎么做呢？

#functions

以下是Scala的实现：

```scala
case class WeatherData(temperature: Float = 0,
                       humidity: Float = 0,
                       pressure: Float = 0,
                       observers: Seq[Observer] = Nil,
                       history: Seq[WeatherData] = Seq(WeatherData(history = Nil))) {

  def register(observer: Observer) =
    this.copy(temperature, humidity, pressure, observers :+ observer, history)

  def weatherChanged(weatherData: WeatherData) = {
    val newHistory = history :+ weatherData
    observers.foreach(observer => observer(newHistory))
    this.copy(temperature, humidity, pressure, observers, newHistory)
  }
}

object Observers {
  type Observer = Seq[WeatherData] => Unit

  val currentConditionsDisplay: Observer = history =>
    println(s"Current conditions: " +
      s"${history.last.temperature} F degrees and " +
      s"${history.last.humidity} % humidity")

  val statisticsDisplay: Observer = history =>
    println(s"Avg/Max/Min temperature = " +
      s"${history.map(_.temperature).sum / history.size}" +
      s"/${history.map(_.temperature).max}" +
      s"/${history.map(_.temperature).max}")

  val forecastDisplay: Observer = history => {
    val currentPressure = history.last.pressure
    val lastPressure = history.dropRight(1).last.pressure

    print("Forecast: ")
    if (currentPressure > lastPressure) println("Improving weather on the way!")
    else if (currentPressure == lastPressure) println("More of the same")
    else if (currentPressure < lastPressure) println("Watch out for cooler, rainy weather")
  }
}

object WeatherStation {
  def main(args: Array[String]) {
    val weatherData = WeatherData()
      .register(currentConditionsDisplay)
      .register(statisticsDisplay)
      .register(forecastDisplay)

    weatherData
      .weatherChanged(WeatherData(80, 65, 30.4f))
      .weatherChanged(WeatherData(82, 70, 29.2f))
      .weatherChanged(WeatherData(78, 90, 29.2f))
  }
}
```

以上就是Scala实现的全部代码了。

开始分析之前，先做一个极其复杂的数学运算：
106行的Java代码，等价于54行Scala代码。
7个类，变成了3个。

下面开始正经的分析。

首先有一个叫做WeatherData的case class，它是完全不可变的。

其register方法，接受一个新的Observer作为参数，并产生一个新的包含比原来多一个Observer的WeatherData实例。

其weatherChanged方法接受一个新的气象数据，生成一个新的历史数据Seq，并把目前为止包含所有历史气象数据的Seq传递给每一个Observer去做处理。最后返回一个包含最新历史数据的新的WeatherData实例。

那么这些Observer具体是怎么定义的呢？

首先Observer只是一个type，不是一个class，它是没有状态的，用来定义函数签名。

三个具体的display仅仅是三个符合Observer签名的函数，它们都接受气象历史数据作为参数，在历史数据中各取所需，作出处理。都是没有任何副作用的。
这很合理，毕竟只是display，仅需要对数据进行分析和显示，只读不写，没有什么要改变已有数据的必要性。

最后一个main函数把所有代码串起来，就得到了一份没有任何可变性的代码。

# Mutation vs Transformation

在Java版的代码中，不同的显示设备不断地根据最新的气象数据改变自己的状态，并根据改变之后的状态来决定其update的行为。

而在Scala代码中，不同的显示设备没有状态，它们都仅仅是函数而已。它们在每次气象变化时根据全部气象历史数据决定自己的行为。

全部代码中没有重新赋值语句，所有的赋值操作都是对局部变量的赋值，程序员可以感知到的变化就只在于observers列表和history列表的增长。而即便是这两个数据结构的增长都是通过不断生成新的不可变的Seq来实现的。

总结来说，Java版代码通过改变已有数据来达成行为的改变。而Scala代码则通过利用不可变的函数和不断生成不可变的数据来实现行为的改变。

这种不可变的代码于什么优势呢？

其好处在于需要程序员操心的事情更少。变化的点越少，麻烦事越少。

如果以上的Java代码有问题，程序员除了需要检查计算平均气温，最高最低气温，气压变化的算法之外，还需要检查重新赋值语句所造成的效果。气温的sum是否算对了？测温次数是否算错了？气压变化是否记录对了？这些都是变化的点，这些都是导致错误的可能性之所在。

而在Scala代码中，如果代码有问题，同样需要检查算法的正确性，也就是检查不可变的函数的正确性。除此之外，只需要检查history列表的增长就可以了。而一个列表的增长是很难出错的。

Java中所有对象状态的改变分散在代码中不同的地方，到了Scala代码中它们都集中到了一个列表的增长上。减少了变化的点，就减少了出错的可能情况的数量，减少了程序员的负担。
