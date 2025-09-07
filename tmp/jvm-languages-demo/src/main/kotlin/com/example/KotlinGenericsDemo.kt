package com.example

/**
 * Kotlin泛型演示 - 展示声明处变性和使用处变性
 */

// 声明处协变
class Producer<out T>(private val value: T) {
    fun produce(): T = value
    // 不能接收T类型参数，因为T是协变的
    // fun consume(t: T) {} // 编译错误
}

// 声明处逆变
class Consumer<in T> {
    fun consume(t: T): Unit = println("Consumed: $t")
    // 不能返回T类型，因为T是逆变的
    // fun produce(): T {} // 编译错误
}

// 使用处变性
class Container<T>(private var value: T) {
    fun get(): T = value
    fun set(newValue: T) { value = newValue }
}

/**
 * 具体化类型参数 - Kotlin对类型擦除的补偿
 */
inline fun <reified T> createArray(size: Int, noinline init: (Int) -> T): Array<T> {
    return Array(size, init)
}

inline fun <reified T> isInstance(value: Any): Boolean {
    return value is T
}

/**
 * 协变逆变的实际应用
 */
interface Repository<out T> {
    fun findById(id: Long): T?
    fun findAll(): List<T>
}

interface Writer<in T> {
    fun write(entity: T)
    fun writeAll(entities: List<T>)
}

/**
 * 高阶函数和泛型的结合
 */
fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
    val result = mutableListOf<R>()
    for (item in this) {
        result.add(transform(item))
    }
    return result
}

/**
 * 类型投影的演示
 */
fun processNumbers(numbers: List<out Number>) {
    // 可以读取Number类型
    val sum = numbers.sumOf { it.toDouble() }
    println("Sum: $sum")
    
    // 不能添加元素，因为不知道具体类型
    // numbers.add(42) // 编译错误
}

fun addIntegers(integers: MutableList<in Int>) {
    // 可以添加Int类型
    integers.add(42)
    integers.add(100)
    
    // 读取时类型不确定
    // val first: Int = integers[0] // 编译错误
}

/**
 * 泛型约束
 */
fun <T : Number> processNumeric(value: T): Double {
    return value.toDouble()
}

fun <T> ensureNotNull(value: T?): T where T : Any {
    return value ?: throw IllegalArgumentException("Value cannot be null")
}

/**
 * 协程和泛型的结合
 */
suspend fun <T> asyncOperation(block: suspend () -> T): T {
    return block()
}

/**
 * 密封类和泛型
 */
sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Result.Success -> value
    is Result.Error -> null
}