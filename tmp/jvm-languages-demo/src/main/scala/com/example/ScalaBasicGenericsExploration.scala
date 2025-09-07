package com.example

import scala.util.Try

/**
 * Scala版本的基础泛型反编译实验 - 与Java版本对比
 * 任务1.1：对比相同功能在不同语言中的字节码表示
 * 
 * 反编译分析重点：
 * 1. Scala泛型如何编译为JVM字节码
 * 2. 与Java版本的字节码差异分析
 * 3. Scala特有的类型系统特性在字节码中的体现
 * 4. implicit参数和类型类在字节码中的表示
 * 
 * 反编译指令：
 * scalac ScalaBasicGenericsExploration.scala
 * javap -v -p ScalaBasicGenericsExploration.class
 * javap -v -p ScalaBasicGenericsExploration$.class  // companion object
 */
class ScalaBasicGenericsExploration[T <: Comparable[T]](private val value: T) {
  private val items: List[T] = List.empty
  
  /**
   * Scala版本的transform方法
   * 反编译重点：函数类型 T => U 在字节码中的表示
   * 
   * 预期发现：
   * - Function1接口的使用
   * - SAM类型的优化
   * - Option vs Optional的实现差异
   */
  def transform[U](mapper: T => U): scala.Option[U] = {
    scala.Option(value).map(mapper)
  }
  
  /**
   * 方法重载演示 - 与Java相同的限制
   * 反编译重点：Scala编译器如何处理类型擦除冲突
   */
  def process(strings: List[String]): Unit = {
    println(s"处理字符串列表，大小: ${strings.size}")
  }
  
  // 同样会导致编译错误
  // def process(integers: List[Int]): Unit = {
  //   println(s"处理整数列表，大小: ${integers.size}")
  // }
  
  /**
   * 通过不同方法名解决重载冲突
   */
  def processIntegers(integers: List[Int]): Unit = {
    println(s"处理整数列表，大小: ${integers.size}")
  }
  
  /**
   * Scala的类型推断演示
   * 反编译重点：类型推断结果在字节码中的体现
   */
  def demonstrateTypeInference(): Unit = {
    // Scala强大的类型推断
    val numbers = List(1, 2, 3, 4, 5)  // 推断为List[Int]
    val strings = numbers.map(_.toString)  // 推断为List[String]
    val lengths = strings.map(_.length)  // 推断为List[Int]
    
    println(s"类型推断链: $numbers -> $strings -> $lengths")
  }
  
  /**
   * Scala的高阶函数演示 - 任务1.2的一部分
   * 反编译重点：高阶函数在字节码中的实现
   */
  def higherOrderDemo(): Unit = {
    // 函数作为参数
    def apply[A, B](f: A => B, value: A): B = f(value)
    
    // 函数组合
    val f: Int => String = _.toString
    val g: String => Int = _.length
    val composed = f andThen g
    
    val result = apply(composed, 12345)
    println(s"函数组合结果: $result")
  }
  
  /**
   * 隐式参数演示 - Scala特有特性
   * 反编译重点：隐式参数在字节码中的传递机制
   */
  def implicitDemo()(implicit ordering: Ordering[T]): T = {
    val sorted = (value :: items).sorted
    sorted.head
  }
  
  /**
   * Case Class和模式匹配演示
   * 反编译重点：case class的伴生对象和unapply方法
   */
  def patternMatchingDemo(): Unit = {
    sealed trait Result[+A]
    case class Success[A](value: A) extends Result[A]
    case class Failure(error: String) extends Result[Nothing]
    
    val result: Result[String] = Success("Hello")
    
    result match {
      case Success(v) => println(s"成功: $v")
      case Failure(e) => println(s"失败: $e")
    }
  }
  
  /**
   * For推导式演示
   * 反编译重点：for推导式的flatMap/map展开
   */
  def forComprehensionDemo(): Unit = {
    val result = for {
      x <- List(1, 2, 3)
      y <- List(10, 20, 30)
      if x + y > 15
    } yield x * y
    
    println(s"For推导式结果: $result")
  }
  
  // Getter方法
  def getValue: T = value
  def getItems: List[T] = items
}

/**
 * 继承演示 - 桥方法生成
 * 反编译重点：Scala继承的桥方法与Java的差异
 */
class ScalaBridgeMethodDemo(value: String) extends ScalaBasicGenericsExploration[String](value) {
  
  /**
   * 重写泛型方法
   * 反编译重点：Scala编译器生成的桥方法结构
   */
  override def transform[U](mapper: String => U): scala.Option[U] = {
    println("Scala子类重写的transform方法")
    super.transform(mapper)
  }
}

/**
 * 伴生对象 - Scala特有特性
 * 反编译重点：伴生对象的字节码实现
 */
object ScalaBasicGenericsExploration {
  
  /**
   * 工厂方法
   * 反编译重点：apply方法的字节码结构
   */
  def apply[T <: Comparable[T]](value: T): ScalaBasicGenericsExploration[T] = {
    new ScalaBasicGenericsExploration(value)
  }
  
  /**
   * 类型类演示 - Scala高级特性
   * 反编译重点：类型类的编译策略
   */
  trait Show[T] {
    def show(t: T): String
  }
  
  // 隐式实例
  implicit val stringShow: Show[String] = (t: String) => s"String($t)"
  implicit val intShow: Show[Int] = (t: Int) => s"Int($t)"
  
  /**
   * 使用类型类的方法
   * 反编译重点：隐式解析的字节码实现
   */
  def displayValue[T](value: T)(implicit show: Show[T]): Unit = {
    println(show.show(value))
  }
  
  /**
   * 演示入口
   */
  def main(args: Array[String]): Unit = {
    println("=== Scala基础泛型反编译实验 ===")
    println("与Java版本对比的Scala实现")
    
    // 创建实例
    val stringExploration = ScalaBasicGenericsExploration("Hello Scala")
    
    // 演示transform方法
    val length = stringExploration.transform(_.length)
    println(s"字符串长度: ${length.fold(0)(identity)}")
    
    // 演示方法重载限制
    stringExploration.process(List("a", "b", "c"))
    stringExploration.processIntegers(List(1, 2, 3))
    
    // 演示类型推断
    stringExploration.demonstrateTypeInference()
    
    // 演示高阶函数
    stringExploration.higherOrderDemo()
    
    // 演示隐式参数（需要隐式Ordering[String]）
    implicit val stringOrdering: Ordering[String] = Ordering.String
    val orderedResult = stringExploration.implicitDemo()
    println(s"排序结果: $orderedResult")
    
    // 演示模式匹配
    stringExploration.patternMatchingDemo()
    
    // 演示for推导式
    stringExploration.forComprehensionDemo()
    
    // 演示桥方法
    val bridgeDemo = new ScalaBridgeMethodDemo("Bridge Test Scala")
    val result = bridgeDemo.transform(_.toUpperCase)
    println(s"Scala桥方法演示结果: ${result.fold("")(identity)}")
    
    // 演示类型类
    displayValue("Hello")
    displayValue(42)
    
    // 演示工厂方法
    val factoryCreated = ScalaBasicGenericsExploration("Factory Created")
    println(s"工厂创建的实例: ${factoryCreated.getValue}")
    
    println("\n=== Scala反编译分析指令 ===")
    println("1. scalac ScalaBasicGenericsExploration.scala")
    println("2. javap -v -p ScalaBasicGenericsExploration.class")
    println("3. javap -v -p ScalaBasicGenericsExploration$.class")
    println("4. javap -v -p ScalaBridgeMethodDemo.class")
    println("5. 对比与Java版本的字节码差异")
    println("6. 特别关注Function1、Option、隐式参数的实现")
  }
}