package com.example.advanced

/**
 * Scala 3 高级类型特性演示
 * 任务1.2和2.3的深度扩展：交集类型、并集类型、高级类型lambda
 * 
 * 反编译分析重点：
 * 1. 交集类型在JVM字节码中的实现
 * 2. 并集类型的编译时消除机制
 * 3. 复杂类型lambda的内部表示
 * 4. 类型级别计算的编译策略
 * 
 * 编译指令：
 * scalac Scala3AdvancedTypes.scala
 * javap -v -p Scala3AdvancedTypes.class
 * javap -v -p 'Scala3AdvancedTypes$*.class'
 */

/**
 * 1. 交集类型 (Intersection Types) - A & B
 * 反编译重点：交集类型如何编译为接口实现
 */
object IntersectionTypeDemo:
  
  // 基础trait定义
  trait Readable:
    def read(): String
  
  trait Writable:
    def write(data: String): Unit
  
  trait Serializable:
    def serialize(): Array[Byte]
  
  // 交集类型的使用
  def processReadWrite(rw: Readable & Writable): String =
    rw.write("test data")
    s"Processed: ${rw.read()}"
  
  // 三重交集类型
  def processAll(obj: Readable & Writable & Serializable): Array[Byte] =
    obj.write("serialization data")
    val data = obj.read()
    println(s"Read before serialization: $data")
    obj.serialize()
  
  // 交集类型与泛型结合
  def processGenericIntersection[T <: Readable & Writable](obj: T): T =
    obj.write("generic processing")
    println(s"Generic read: ${obj.read()}")
    obj
  
  // 具体实现类
  class FileHandler extends Readable with Writable with Serializable:
    private var content: String = ""
    
    def read(): String = content
    def write(data: String): Unit = content = data
    def serialize(): Array[Byte] = content.getBytes
  
  def demonstrateIntersectionTypes(): Unit =
    println("=== 交集类型演示 ===")
    
    val fileHandler = FileHandler()
    
    // 使用交集类型
    val result1 = processReadWrite(fileHandler)
    println(s"ReadWrite result: $result1")
    
    val bytes = processAll(fileHandler)
    println(s"Serialized bytes length: ${bytes.length}")
    
    val generic = processGenericIntersection(fileHandler)
    println(s"Generic processing completed")

/**
 * 2. 并集类型 (Union Types) - A | B  
 * 反编译重点：并集类型在运行时的表示和类型检查
 */
object UnionTypeDemo:
  
  // 基础并集类型
  type StringOrInt = String | Int
  type NumberOrBoolean = Double | Boolean
  
  // 复杂并集类型
  type JsonValue = String | Int | Double | Boolean | Null
  
  def processStringOrInt(value: StringOrInt): String = value match
    case s: String => s"String: $s"
    case i: Int => s"Int: $i"
  
  def processJsonValue(value: JsonValue): String = value match
    case s: String => s"JSON String: $s"
    case i: Int => s"JSON Int: $i"
    case d: Double => s"JSON Double: $d"
    case b: Boolean => s"JSON Boolean: $b"
    case null => "JSON Null"
  
  // 并集类型作为函数参数
  def handleMultipleTypes(
    id: String | Int,
    config: Map[String, String] | List[String] | String
  ): String =
    val idStr = id match
      case s: String => s
      case i: Int => i.toString
    
    val configStr = config match
      case m: Map[String, String] => m.toString
      case l: List[String] => l.mkString(",")
      case s: String => s
    
    s"ID: $idStr, Config: $configStr"
  
  // 并集类型与泛型
  def processGenericUnion[T](value: T | String): String = value match
    case s: String => s"String: $s"
    case other => s"Other type: $other"
  
  def demonstrateUnionTypes(): Unit =
    println("=== 并集类型演示 ===")
    
    // 基础并集类型
    println(processStringOrInt("Hello"))
    println(processStringOrInt(42))
    
    // JSON值处理
    println(processJsonValue("test"))
    println(processJsonValue(123))
    println(processJsonValue(3.14))
    println(processJsonValue(true))
    println(processJsonValue(null))
    
    // 复杂参数
    println(handleMultipleTypes("user123", Map("key" -> "value")))
    println(handleMultipleTypes(456, List("a", "b", "c")))
    
    // 泛型并集
    println(processGenericUnion[Int](42))
    println(processGenericUnion("generic"))

/**
 * 3. 高级类型Lambda
 * 反编译重点：类型级别计算的编译时实现
 */
object TypeLambdaDemo:
  
  // 基础类型lambda
  type MapToString[F[_]] = [X] =>> F[String]
  type TupleWithString[F[_]] = [X] =>> (F[X], String)
  
  // 复杂类型lambda - 函数组合
  type Compose[F[_], G[_]] = [X] =>> F[G[X]]
  
  // 类型lambda与Functor
  trait Functor[F[_]]:
    def map[A, B](fa: F[A])(f: A => B): F[B]
  
  // 使用类型lambda的Functor实例
  given listFunctor: Functor[List] with
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  
  given optionFunctor: Functor[Option] with  
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  
  // 复合Functor使用类型lambda
  given [F[_]: Functor, G[_]: Functor]: Functor[Compose[F, G]] with
    def map[A, B](fa: F[G[A]])(f: A => B): F[G[B]] =
      summon[Functor[F]].map(fa)(ga => summon[Functor[G]].map(ga)(f))
  
  // 高级类型lambda - 固定一个类型参数
  type StateT[S] = [F[_]] =>> [A] =>> S => F[(S, A)]
  
  // Either的类型lambda版本
  type EitherT[E] = [F[_]] =>> [A] =>> F[Either[E, A]]
  
  def demonstrateTypeLambdas(): Unit =
    println("=== 类型Lambda演示 ===")
    
    // 基础Functor操作
    val numbers = List(1, 2, 3)
    val doubled = summon[Functor[List]].map(numbers)(_ * 2)
    println(s"List Functor: $doubled")
    
    val option = Some(42)  
    val mapped = summon[Functor[Option]].map(option)(_ * 2)
    println(s"Option Functor: $mapped")
    
    // 复合Functor - List[Option[_]]
    type ListOption[A] = List[Option[A]]
    val listOption: ListOption[Int] = List(Some(1), None, Some(3))
    val composedResult = summon[Functor[Compose[List, Option]]].map(listOption)(_ * 2)
    println(s"Composed Functor: $composedResult")
    
    println("类型lambda允许复杂的类型级别计算")

/**
 * 4. 类型级别编程
 * 反编译重点：编译时类型计算的实现策略
 */
object TypeLevelProgramming:
  
  // 自然数的类型级别表示
  sealed trait Nat
  case object Zero extends Nat
  case class Succ[N <: Nat](n: N) extends Nat
  
  type One = Succ[Zero.type]
  type Two = Succ[One]  
  type Three = Succ[Two]
  
  // 类型级别加法
  type Add[A <: Nat, B <: Nat] <: Nat = A match
    case Zero.type => B
    case Succ[n] => Succ[Add[n, B]]
  
  // 长度类型的向量（编译时长度检查）
  sealed trait Vec[+A, N <: Nat]:
    def length: Int
    def toList: List[A]
  
  case object VNil extends Vec[Nothing, Zero.type]:
    def length: Int = 0
    def toList: List[Nothing] = Nil
  
  case class VCons[+A, N <: Nat](head: A, tail: Vec[A, N]) extends Vec[A, Succ[N]]:
    def length: Int = 1 + tail.length
    def toList: List[A] = head :: tail.toList
  
  // 类型安全的向量操作
  def appendVec[A, M <: Nat, N <: Nat](
    v1: Vec[A, M], 
    v2: Vec[A, N]
  ): Vec[A, Add[M, N]] = v1 match
    case VNil => v2
    case VCons(h, t) => VCons(h, appendVec(t, v2))
  
  def demonstrateTypeLevelProgramming(): Unit =
    println("=== 类型级别编程演示 ===")
    
    // 创建固定长度向量
    val v1: Vec[Int, Two] = VCons(1, VCons(2, VNil))
    val v2: Vec[Int, Three] = VCons(3, VCons(4, VCons(5, VNil)))
    
    println(s"v1: ${v1.toList}, length: ${v1.length}")
    println(s"v2: ${v2.toList}, length: ${v2.length}")
    
    // 类型安全的拼接 - 结果类型在编译时计算
    val combined = appendVec(v1, v2) // 类型为 Vec[Int, Add[Two, Three]]
    println(s"combined: ${combined.toList}, length: ${combined.length}")
    
    println("类型系统保证了向量长度的编译时正确性")

/**
 * 5. 依赖类型的模拟
 * 反编译重点：路径依赖类型的编译表示
 */
object DependentTypeDemo:
  
  // 路径依赖类型
  trait Container:
    type Element
    def get: Element
    def put(elem: Element): Unit
  
  class StringContainer extends Container:
    type Element = String
    private var value: String = ""
    def get: String = value
    def put(elem: String): Unit = value = elem
  
  class IntContainer extends Container:
    type Element = Int  
    private var value: Int = 0
    def get: Int = value
    def put(elem: Int): Unit = value = elem
  
  // 依赖类型的使用
  def processContainer(container: Container)(elem: container.Element): container.Element =
    container.put(elem)
    container.get
  
  // 抽象类型成员
  trait Database:
    type Record
    type Query
    def find(query: Query): Option[Record]
    def save(record: Record): Unit
  
  class UserDatabase extends Database:
    type Record = String // 简化的用户记录
    type Query = String  // 简化的查询
    
    private val users = scala.collection.mutable.Set[String]()
    
    def find(query: String): Option[String] = 
      users.find(_.contains(query))
    
    def save(record: String): Unit = 
      users.add(record)
  
  def demonstrateDependentTypes(): Unit =
    println("=== 路径依赖类型演示 ===")
    
    val stringContainer = StringContainer()
    val intContainer = IntContainer()
    
    // 类型安全的容器操作
    val str = processContainer(stringContainer)("Hello")
    val int = processContainer(intContainer)(42)
    
    println(s"String container: $str")
    println(s"Int container: $int")
    
    // 数据库类型依赖
    val userDb = UserDatabase()
    userDb.save("Alice")
    userDb.save("Bob")
    val found = userDb.find("Alice")
    println(s"Found user: $found")

/**
 * 主演示对象
 */
object Scala3AdvancedTypes:
  
  def main(args: Array[String]): Unit =
    println("=== Scala 3 高级类型特性演示 ===")
    println("展示交集类型、并集类型、类型lambda等特性的编译实现")
    
    // 1. 交集类型
    IntersectionTypeDemo.demonstrateIntersectionTypes()
    println()
    
    // 2. 并集类型
    UnionTypeDemo.demonstrateUnionTypes()
    println()
    
    // 3. 类型lambda
    TypeLambdaDemo.demonstrateTypeLambdas()
    println()
    
    // 4. 类型级别编程
    TypeLevelProgramming.demonstrateTypeLevelProgramming()
    println()
    
    // 5. 依赖类型模拟
    DependentTypeDemo.demonstrateDependentTypes()
    
    println("\n=== 反编译分析重点 ===")
    println("1. 交集类型编译为接口的多重实现")
    println("2. 并集类型的运行时类型检查机制")  
    println("3. 类型lambda的内部类表示")
    println("4. 类型级别计算的编译时消除")
    println("5. 路径依赖类型的字节码实现")
    println("6. 复杂类型系统特性的JVM适配策略")