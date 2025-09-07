import scala.util.Try
import scala.collection.mutable.ListBuffer

/**
 * 基础泛型探索实验 - Scala版本
 * 对应Java版本，用于对比分析
 */
class BasicGenericsExploration[T <: Comparable[T]](private val value: T) {
  private val items: ListBuffer[T] = ListBuffer.empty
  
  /**
   * 泛型方法：测试方法级别的类型参数
   */
  def transform[U](mapper: T => U): Option[U] = {
    Option(value).map(mapper)
  }
  
  /**
   * 测试泛型边界约束
   */
  def calculateSum[U <: Number](numbers: List[U]): Double = {
    numbers.map(_.doubleValue()).sum
  }
  
  /**
   * 测试协变：Scala中的协变容器
   */
  def processProducers(producers: List[T]): Unit = {
    items ++= producers
  }
  
  /**
   * 测试逆变：使用函数类型的逆变特性
   */
  def processWithConsumer[U >: T](consumer: U => Unit): Unit = {
    consumer(value)
  }
  
  /**
   * 测试Scala特有的变性声明
   */
  def workWithVariantContainers[U](
    covariantContainer: CovariantContainer[T],
    contravariantContainer: ContravariantContainer[T]
  ): Unit = {
    val result = covariantContainer.get
    contravariantContainer.accept(value)
  }
  
  /**
   * 测试高阶类型
   */
  def higherKinded[F[_]](container: F[T])(implicit functor: Functor[F]): F[String] = {
    functor.map(container)(_.toString)
  }
  
  /**
   * 测试类型类模式
   */
  def processWithTypeClass[U: Numeric](values: List[U]): U = {
    import Numeric.Implicits._
    values.sum
  }
  
  def getValue: T = value
  
  def getOptionalItems: Option[List[T]] = {
    if (items.nonEmpty) Some(items.toList) else None
  }
}

/**
 * 协变容器示例
 */
trait CovariantContainer[+T] {
  def get: T
}

/**
 * 逆变容器示例
 */
trait ContravariantContainer[-T] {
  def accept(t: T): Unit
}

/**
 * 简单的Functor类型类
 */
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

/**
 * Option的Functor实例
 */
object Functor {
  implicit val optionFunctor: Functor[Option] = new Functor[Option] {
    def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }
}

/**
 * 测试对象
 */
object BasicGenericsExploration {
  def main(args: Array[String]): Unit = {
    val exploration = new BasicGenericsExploration("Hello")
    
    // 测试泛型方法
    val length = exploration.transform(_.length)
    println(s"String length: ${length.getOrElse(0)}")
    
    // 测试边界约束
    val numbers = List(1, 2, 3, 4, 5)
    val sum = exploration.calculateSum(numbers)
    println(s"Sum: $sum")
    
    // 测试协变
    val strings = List("A", "B", "C")
    exploration.processProducers(strings)
    
    // 测试逆变
    exploration.processWithConsumer[Any](println)
    
    // 测试高阶类型
    val optionalResult = exploration.higherKinded(Some("World"))
    println(s"Higher-kinded result: $optionalResult")
    
    // 测试类型类
    val intSum = exploration.processWithTypeClass(List(1, 2, 3, 4, 5))
    println(s"Type class sum: $intSum")
  }
}