package com.example.interop

import scala.reflect.runtime.universe.{TypeTag, typeOf}
import scala.util.{Try, Success, Failure}
import java.util.{List => JavaList, Optional}
import java.util.function.{Function => JavaFunction}
import scala.jdk.CollectionConverters.*

/**
 * Scala与Java互操作演示
 * 目标：展示Scala的高级类型特性在跨语言调用中的表现
 * 
 * 反编译分析重点：
 * 1. Scala函数类型转换为Java SAM接口的实现
 * 2. 隐式转换在字节码中的实现
 * 3. 高阶函数调用的桥方法生成
 * 4. TypeTag信息在字节码中的保存方式
 */
object ScalaInteropDemo {

  /**
   * 协变容器实现 - 供Java调用
   * 反编译重点：协变类型参数的字节码签名
   */
  class CovariantContainer[+T](private val items: List[T]) {
    def get(index: Int): Option[T] = items.lift(index)
    def getAll: List[T] = items
    def size: Int = items.size
    
    // 协变方法 - 返回更具体类型的容器
    def map[U](f: T => U): CovariantContainer[U] = 
      new CovariantContainer(items.map(f))
    
    // 供Java使用的便利方法
    def toJavaList: JavaList[T] = items.asJava
    def toJavaOptional(index: Int): Optional[T] = 
      get(index).fold(Optional.empty[T]())(Optional.of)
  }
  
  /**
   * 逆变函数接口 - 演示逆变在互操作中的行为
   * 反编译重点：逆变参数的字节码表示
   */
  trait ContravariantProcessor[-T] {
    def process(item: T): String
  }
  
  /**
   * 具体实现 - 处理Any类型
   */
  class UniversalProcessor extends ContravariantProcessor[Any] {
    def process(item: Any): String = s"处理项目: ${item.toString}"
  }
  
  /**
   * 高阶函数 - 供Java调用
   * 反编译重点：函数参数的桥方法和类型擦除
   */
  def transformWithFunction[A, B](
    items: List[A]
  )(f: A => B): List[B] = {
    items.map(f)
  }
  
  /**
   * 柯里化函数 - 展示Scala特有特性的Java互操作
   * 反编译重点：柯里化的字节码实现和部分应用
   */
  def curriedTransform[A, B, C](
    items: List[A]
  )(f1: A => B)(f2: B => C): List[C] = {
    items.map(f1).map(f2)
  }
  
  /**
   * 隐式参数方法 - 演示隐式解析在互操作中的挑战
   * 反编译重点：隐式参数的传递机制
   */
  def processWithEvidence[T: TypeTag](items: List[T]): String = {
    val tag = summon[TypeTag[T]]
    s"处理类型 ${tag.tpe} 的 ${items.size} 个元素"
  }
  
  /**
   * 类型类模式 - 展示Scala特有的类型抽象
   * 反编译重点：隐式证据的字节码实现
   */
  trait Show[T] {
    def show(value: T): String
  }
  
  object Show {
    implicit val stringShow: Show[String] = (value: String) => s"'$value'"
    implicit val intShow: Show[Int] = (value: Int) => value.toString
    implicit def listShow[T: Show]: Show[List[T]] = (list: List[T]) =>
      list.map(implicitly[Show[T]].show).mkString("[", ", ", "]")
  }
  
  def showValue[T: Show](value: T): String = {
    implicitly[Show[T]].show(value)
  }
  
  /**
   * Monad变换器的互操作实现
   * 反编译重点：高阶类型的字节码表示
   */
  case class OptionT[F[_], A](value: F[Option[A]]) {
    def map[B](f: A => B)(implicit F: Functor[F]): OptionT[F, B] =
      OptionT(F.map(value)(_.map(f)))
      
    def flatMap[B](f: A => OptionT[F, B])(implicit F: Monad[F]): OptionT[F, B] =
      OptionT(F.flatMap(value) {
        case Some(a) => f(a).value
        case None => F.pure(None)
      })
  }
  
  /**
   * 简化的Functor和Monad类型类
   */
  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }
  
  trait Monad[F[_]] extends Functor[F] {
    def pure[A](a: A): F[A]
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  }
  
  /**
   * Try的Monad实例
   */
  implicit val tryMonad: Monad[Try] = new Monad[Try] {
    def pure[A](a: A): Try[A] = Success(a)
    def map[A, B](fa: Try[A])(f: A => B): Try[B] = fa.map(f)
    def flatMap[A, B](fa: Try[A])(f: A => Try[B]): Try[B] = fa.flatMap(f)
  }
  
  /**
   * 与Java Function接口的互操作
   * 反编译重点：SAM类型转换的实现
   */
  def useJavaFunction[T, R](
    items: List[T], 
    javaFunc: JavaFunction[T, R]
  ): List[R] = {
    // Scala函数与Java Function的自动转换
    items.map(item => javaFunc.apply(item))
  }
  
  /**
   * 提供给Java的工厂方法
   */
  def createCovariantContainer[T](items: T*): CovariantContainer[T] = 
    new CovariantContainer(items.toList)
  
  def createProcessor(): ContravariantProcessor[String] = 
    new UniversalProcessor()
  
  /**
   * 复杂的嵌套类型结构
   * 反编译重点：复杂类型签名的字节码表示
   */
  def complexNestedOperation[F[_]: Monad, G[_]: Functor, A, B](
    nested: F[G[A]]
  )(f: A => B): F[G[B]] = {
    val F = implicitly[Monad[F]]
    val G = implicitly[Functor[G]]
    F.map(nested)(ga => G.map(ga)(f))
  }
  
  /**
   * 演示程序入口
   */
  def main(args: Array[String]): Unit = {
    println("=== Scala跨语言互操作演示 ===")
    
    // 1. 协变容器演示
    val container = createCovariantContainer("a", "b", "c")
    println(s"协变容器大小: ${container.size}")
    println(s"容器内容: ${container.getAll}")
    
    // 2. 逆变处理器演示
    val processor = createProcessor()
    println(processor.process("测试字符串"))
    
    // 3. 高阶函数演示
    val numbers = List(1, 2, 3, 4, 5)
    val doubled = transformWithFunction(numbers)(_ * 2)
    println(s"高阶函数结果: $doubled")
    
    // 4. 柯里化函数演示
    val strings = List("hello", "world")
    val result = curriedTransform(strings)(_.toUpperCase)(_.reverse)
    println(s"柯里化函数结果: $result")
    
    // 5. TypeTag演示
    val evidence = processWithEvidence(List(1, 2, 3))
    println(evidence)
    
    // 6. 类型类演示
    println("类型类演示:")
    println(showValue("Hello"))
    println(showValue(42))
    println(showValue(List(1, 2, 3)))
    
    // 7. Monad变换器演示
    val optionT = OptionT(Success(Some(42)))
    val mapped = optionT.map(_ * 2)
    println(s"OptionT结果: ${mapped.value}")
    
    // 8. 复杂嵌套类型演示
    val nestedData: Try[List[Int]] = Success(List(1, 2, 3))
    implicit val listFunctor: Functor[List] = new Functor[List] {
      def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
    }
    val complexResult = complexNestedOperation(nestedData)(_ * 10)
    println(s"复杂嵌套操作结果: $complexResult")
  }
}

/**
 * 伴随对象 - 用于存放Java互操作的静态方法
 */
object ScalaJavaInterop {
  
  /**
   * 为Java提供的静态方法
   * 反编译重点：object方法的静态方法映射
   */
  def convertToJavaOptional[T](option: Option[T]): Optional[T] = 
    option.fold(Optional.empty[T]())(Optional.of)
  
  def convertFromJavaOptional[T](optional: Optional[T]): Option[T] = 
    if (optional.isPresent) Some(optional.get()) else None
  
  def convertToJavaList[T](list: List[T]): JavaList[T] = list.asJava
  
  def convertFromJavaList[T](javaList: JavaList[T]): List[T] = 
    javaList.asScala.toList
  
  /**
   * 函数转换工具
   */
  def scalaToJavaFunction[A, B](f: A => B): JavaFunction[A, B] = 
    new JavaFunction[A, B] {
      def apply(a: A): B = f(a)
    }
  
  def javaToScalaFunction[A, B](f: JavaFunction[A, B]): A => B = 
    (a: A) => f.apply(a)
}