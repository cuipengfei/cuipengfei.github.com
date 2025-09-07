package com.example.advanced

import scala.reflect.runtime.universe.{TypeTag, typeOf}

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
  
  def map[A, B](fa: F[A])(f: A => B): F[B] = 
    flatMap(fa)(a => pure(f(a)))
}

// 类型lambda演示 (任务1.2)
type StringMap[A] = Map[String, A]
type IntFunction[A] = Int => A

// 高阶类型实例
implicit val listFunctor: Functor[List] = new Functor[List] {
  def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
}

implicit val optionMonad: Monad[Option] = new Monad[Option] {
  def pure[A](a: A): Option[A] = Some(a)
  def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
}

/**
 * 类型擦除补偿机制 (任务3.2)
 */
object TypeErasureCompensation {
  
  // Scala TypeTag机制演示
  def createArray[T: TypeTag](size: Int): Array[T] = {
    val tpe = typeOf[T]
    val runtimeClass = scala.reflect.classTag[T].runtimeClass
    java.lang.reflect.Array.newInstance(runtimeClass, size).asInstanceOf[Array[T]]
  }
  
  def demonstrateTypeTag(): Unit = {
    val stringArray = createArray[String](3)
    val intArray = createArray[Int](5)
    
    println(s"Created String array: ${stringArray.getClass}")
    println(s"Created Int array: ${intArray.getClass}")
  }
  
  // Manifest（旧版本兼容）
  def createArrayWithManifest[T: scala.reflect.Manifest](size: Int): Array[T] = {
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
  
  implicit val futureMonad: Monad[Future] = new Monad[Future] {
    def pure[A](a: A): Future[A] = Future.successful(a)
    def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] = fa.flatMap(f)
  }
  
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
    
    result.value.foreach(println)
  }
}

/**
 * Free Monad演示 (任务4.2)
 */
sealed trait Free[F[_], A]
case class Pure[F[_], A](a: A) extends Free[F, A]
case class FlatMap[F[_], A, B](fa: F[A], f: A => Free[F, B]) extends Free[F, B]

object FreeMonadDemo {
  
  // 简单的代数数据类型
  sealed trait KVStore[A]
  case class Put(key: String, value: String) extends KVStore[Unit]
  case class Get(key: String) extends KVStore[Option[String]]
  
  // Free Monad构造器
  def put(key: String, value: String): Free[KVStore, Unit] = 
    FlatMap(Put(key, value), (_: Unit) => Pure(()))
    
  def get(key: String): Free[KVStore, Option[String]] = 
    FlatMap(Get(key), (result: Option[String]) => Pure(result))
    
  def program: Free[KVStore, Option[String]] = for {
    _ <- put("name", "Alice")
    name <- get("name")
  } yield name
}

/**
 * Tagless Final演示 (任务4.2)
 */
trait Console[F[_]] {
  def printLn(line: String): F[Unit]
  def readLn: F[String]
}

object TaglessFinalDemo {
  
  def program[F[_]](implicit C: Console[F]): F[String] = for {
    _ <- C.printLn("What's your name?")
    name <- C.readLn
    _ <- C.printLn(s"Hello, $name!")
  } yield name
  
  // 解释器实现
  implicit val consoleIO: Console[scala.util.Try] = new Console[scala.util.Try] {
    def printLn(line: String): scala.util.Try[Unit] = scala.util.Try(println(line))
    def readLn: scala.util.Try[String] = scala.util.Try(scala.io.StdIn.readLine())
  }
}

object ScalaAdvancedFeatures {
  
  def main(args: Array[String]): Unit = {
    println("=== Scala高级类型特性演示 ===")
    
    // 演示高阶类型
    val numbers = List(1, 2, 3)
    val doubled = listFunctor.map(numbers)(_ * 2)
    println(s"Doubled: $doubled")
    
    // 演示Monad
    val result = optionMonad.flatMap(Some(5))(x => Some(x * 2))
    println(s"Monadic result: $result")
    
    // 演示TypeTag补偿机制
    TypeErasureCompensation.demonstrateTypeTag()
    
    // 演示函数类型的协变逆变
    VarianceExamples.demonstrateFunctionVariance()
    
    // 演示Monad Transformer
    EitherTDemo.demonstrateMonadTransformer()
  }
}