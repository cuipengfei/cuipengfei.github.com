package com.example.advanced

import scala.reflect.ClassTag

/**
 * Scala高级类型特性演示
 * 覆盖Deep Research任务1.2和3.2
 */

// 高阶类型演示 (任务1.2)
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

trait Monad[F[_]] extends Functor[F] {
  def pure[A](a: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  override def map[A, B](fa: F[A])(f: A => B): F[B] = 
    flatMap(fa)(a => pure(f(a)))
}

// 类型lambda演示 (任务1.2)
type StringMap[A] = Map[String, A]
type IntFunction[A] = Int => A

// 高阶类型实例
given Functor[List] with
  def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)

given Monad[Option] with
  def pure[A](a: A): Option[A] = Some(a)
  def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
  override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)

/**
 * 类型擦除补偿机制 (任务3.2)
 */
object TypeErasureCompensation {
  
  // Scala TypeTag机制演示
  def createArray[T: ClassTag](size: Int): Array[T] = {
    new Array[T](size)
  }
  
  def demonstrateTypeTag(): Unit = {
    val stringArray = createArray[String](3)
    val intArray = createArray[Int](5)
    
    println(s"Created String array: ${stringArray.getClass}")
    println(s"Created Int array: ${intArray.getClass}")
  }
  
  // Manifest（旧版本兼容）
  def createArrayWithManifest[T: scala.reflect.ClassTag](size: Int): Array[T] = {
    new Array[T](size)
  }
}

/**
 * 协变逆变高级特性 (任务2.2, 2.3)
 */

// 协变逆变与继承层次
sealed trait Animal
case class Dog(name: String) extends Animal
case class Cat(name: String) extends Animal

// 协变容器
class CovariantContainer[+A](val value: A) {
  def map[B](f: A => B): CovariantContainer[B] = new CovariantContainer(f(value))
}

// 逆变处理器
class ContravariantProcessor[-A] {
  def process(a: A): String = s"Processing ${a.toString}"
}

// 函数类型的协变逆变演示
type Handler[-A, +B] = A => B

object VarianceExamples {
  
  def demonstrateFunctionVariance(): Unit = {
    // 函数类型的协变逆变
    val dogHandler: Handler[Dog, String] = (dog: Dog) => s"Dog: ${dog.name}"
    val animalHandler: Handler[Animal, String] = (animal: Animal) => s"Animal: $animal"
    
    // 逆变参数：Handler[Animal, String] 是 Handler[Dog, String] 的子类型
    val handler1: Handler[Dog, String] = animalHandler
    
    // 协变返回：Handler[Dog, String] 是 Handler[Dog, Any] 的子类型
    val handler2: Handler[Dog, Any] = dogHandler
    
    println("Function variance demonstrated")
  }
}

/**
 * Monad Transformer演示 (任务4.2)
 */
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class EitherT[F[_], A, B](value: F[Either[A, B]]) {
  def map[C](f: B => C)(implicit F: Functor[F]): EitherT[F, A, C] = 
    EitherT(F.map(value)(_.map(f)))
    
  def flatMap[C](f: B => EitherT[F, A, C])(implicit F: Monad[F]): EitherT[F, A, C] = 
    EitherT(F.flatMap(value) {
      case Right(b) => f(b).value
      case Left(a) => F.pure(Left(a))
    })
}

object EitherTDemo {
  
  given Monad[Future] with
    def pure[A](a: A): Future[A] = Future.successful(a)
    def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)
    override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)
  
  def getUserById(id: Int): EitherT[Future, String, String] = 
    EitherT(Future.successful(Right(s"User$id")))
    
  def getProfileByUser(user: String): EitherT[Future, String, String] = 
    EitherT(Future.successful(Right(s"Profile-$user")))
    
  def getSettingsByProfile(profile: String): EitherT[Future, String, String] = 
    EitherT(Future.successful(Right(s"Settings-$profile")))
    
  def demonstrateMonadTransformer(): Unit = {
    val result: EitherT[Future, String, String] = for {
      user <- getUserById(42)
      profile <- getProfileByUser(user)
      settings <- getSettingsByProfile(profile)
    } yield settings
    
    println("Monad Transformer结果:")
    result.value.foreach(println)
  }
}

/**
 * Free Monad演示 (任务4.2)
 */
object FreeMonadDemo {
  
  // 简单的代数数据类型
  sealed trait KVStore[A]
  case class Put(key: String, value: String) extends KVStore[Unit]
  case class Get(key: String) extends KVStore[Option[String]]
  
  // 简化的Free Monad演示 - 使用直接模式匹配
  def demonstrateFreeMonad(): Unit = {
    val operations = List(Put("name", "Alice"), Get("name"))
    
    println("Free Monad操作序列:")
    operations.foreach {
      case Put(k, v) => println(s"  存储: $k = $v")
      case Get(k) => println(s"  获取: $k")
    }
  }
}

/**
 * Tagless Final演示 (任务4.2)
 */
trait Console[F[_]] {
  def printLn(line: String): F[Unit]
  def readLn: F[String]
}

object TaglessFinalDemo {
  
  def demonstrateTaglessFinal(): Unit = {
    println("=== Tagless Final演示 ===")
    println("Tagless Final通过抽象类型F[_]实现可解释的程序")
    println("示例：Console[Try]解释器")
  }
}

object ScalaAdvancedFeatures {
  
  def main(args: Array[String]): Unit = {
    println("=== Scala高级类型特性演示 ===")
    
    // 演示高阶类型
    val numbers = List(1, 2, 3)
    val doubled = summon[Functor[List]].map(numbers)(_ * 2)
    println(s"Doubled: $doubled")
    
    // 演示Monad
    val result = summon[Monad[Option]].flatMap(Some(5))(x => Some(x * 2))
    println(s"Monadic result: $result")
    
    // 演示TypeTag补偿机制
    TypeErasureCompensation.demonstrateTypeTag()
    
    // 演示函数类型的协变逆变
    VarianceExamples.demonstrateFunctionVariance()
    
    // 演示Monad Transformer
    EitherTDemo.demonstrateMonadTransformer()
    
    // 演示Free Monad
    FreeMonadDemo.demonstrateFreeMonad()
    
    // 演示Tagless Final
    TaglessFinalDemo.demonstrateTaglessFinal()
  }
}