package com.example

import scala.language.higherKinds

/**
 * Scala协变逆变演示 - 展示声明处变性
 */

// 协变类型
sealed trait Option[+A] {
  def map[B](f: A => B): Option[B]
  def flatMap[B](f: A => Option[B]): Option[B]
}

case class Some[+A](value: A) extends Option[A] {
  def map[B](f: A => B): Option[B] = Some(f(value))
  def flatMap[B](f: A => Option[B]): Option[B] = f(value)
}

case object None extends Option[Nothing] {
  def map[B](f: Nothing => B): Option[B] = None
  def flatMap[B](f: Nothing => Option[B]): Option[B] = None
}

// 逆变类型
trait Consumer[-T] {
  def consume(t: T): Unit
}

// 不变类型
class Container[A](private var value: A) {
  def get: A = value
  def set(newValue: A): Unit = value = newValue
}

/**
 * 高阶类型和类型构造函数演示
 */
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit val optionFunctor: Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }
}

/**
 * 类型lambda演示
 */
type StringMap[A] = Map[String, A]
type IntFunction[A] = Int => A

// 高阶类型示例
class HigherKind[F[_]] {
  def process[A](fa: F[A]): String = fa.toString
}

/**
 * 协变逆变与继承的交互
 */
trait Animal
trait Pet extends Animal
trait Dog extends Pet

// 协变示例
trait Cage[+A] {
  def animal: A
}

// 逆变示例  
trait Feeder[-A] {
  def feed(animal: A): Unit
}

// 协变逆变组合
trait ZooKeeper[+A, -B] {
  def careFor(animal: B): A
}