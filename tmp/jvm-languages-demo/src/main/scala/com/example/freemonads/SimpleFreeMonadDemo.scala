package com.example.freemonads

/**
 * 简化的Free Monad演示
 * 专注于核心概念，避免复杂的类型类依赖
 */

/**
 * 简单的代数数据类型 - 键值存储操作
 */
sealed trait KVStore[A]
case class Put(key: String, value: String) extends KVStore[Unit]
case class Get(key: String) extends KVStore[Option[String]]

/**
 * 简化的Free数据结构
 */
sealed trait Free[F[_], A]
case class Pure[F[_], A](value: A) extends Free[F, A]
case class Suspend[F[_], A](fa: F[A]) extends Free[F, A]

object SimpleFreeMonadDemo {
  
  def put(key: String, value: String): Free[KVStore, Unit] = 
    Suspend(Put(key, value))
    
  def get(key: String): Free[KVStore, Option[String]] = 
    Suspend(Get(key))
    
  def simpleProgram: Free[KVStore, Option[String]] = {
    // 简化的程序：存储然后获取
    Suspend(Put("name", "Alice"))
    Suspend(Get("name"))
  }
  
  def demonstrateSimpleFreeMonad(): Unit = {
    println("=== 简化的Free Monad演示 ===")
    
    val program = simpleProgram
    
    program match {
      case Suspend(Put(k, v)) => println(s"存储操作: $k = $v")
      case Suspend(Get(k)) => println(s"获取操作: $k")
      case Pure(v) => println(s"纯值: $v")
    }
  }
}