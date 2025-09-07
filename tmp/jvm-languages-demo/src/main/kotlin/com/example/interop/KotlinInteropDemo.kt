package com.example.interop

import java.util.*
import java.util.function.Function as JavaFunction
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

/**
 * Kotlin与Java/Scala互操作演示
 * 目标：展示Kotlin的空安全、协程、内联函数在跨语言调用中的表现
 * 
 * 反编译分析重点：
 * 1. 空安全类型在字节码中的实现（Nullable注解）
 * 2. 内联函数的字节码内联机制
 * 3. 协程状态机的字节码生成
 * 4. Reified类型参数的实现原理
 * 5. 扩展函数的静态方法映射
 */

/**
 * 空安全互操作容器
 * 反编译重点：可空性注解和运行时检查
 */
class NullSafeContainer<T : Any>(private val items: MutableList<T?> = mutableListOf()) {
    
    /**
     * 添加非空项目
     * 反编译重点：非空断言的字节码实现
     */
    fun addNonNull(item: T) {
        items.add(item)
    }
    
    /**
     * 添加可空项目
     * 反编译重点：可空类型的处理
     */
    fun addNullable(item: T?) {
        items.add(item)
    }
    
    /**
     * 获取非空项目列表
     * 反编译重点：filterNotNull的内联优化
     */
    fun getNonNullItems(): List<T> = items.filterNotNull()
    
    /**
     * 安全获取项目
     * 反编译重点：安全调用操作符的实现
     */
    fun safeGet(index: Int): T? = items.getOrNull(index)
    
    /**
     * Elvis操作符演示
     * 反编译重点：Elvis操作符的字节码实现
     */
    fun getWithDefault(index: Int, default: T): T = 
        safeGet(index) ?: default
        
    /**
     * 转换为Java Optional
     * 反编译重点：Kotlin到Java类型的转换
     */
    fun toJavaOptional(index: Int): Optional<T> = 
        Optional.ofNullable(safeGet(index))
    
    /**
     * let作用域函数演示
     * 反编译重点：作用域函数的内联实现
     */
    fun processIfNotNull(index: Int): String? = 
        safeGet(index)?.let { item ->
            "处理项目: $item (类型: ${item::class.simpleName})"
        }
}

/**
 * 内联函数演示
 * 反编译重点：内联函数的字节码替换机制
 */
inline fun <T> measureTime(block: () -> T): Pair<T, Long> {
    val start = System.nanoTime()
    val result = block()
    val end = System.nanoTime()
    return result to (end - start)
}

/**
 * Reified类型参数演示
 * 反编译重点：运行时类型信息的保留机制
 */
inline fun <reified T> checkType(value: Any): Boolean = value is T

inline fun <reified T> castSafely(value: Any): T? = value as? T

inline fun <reified T> createList(): MutableList<T> = mutableListOf()

inline fun <reified T : Any> getKClass(): KClass<T> = T::class

/**
 * 高阶函数与内联组合
 * 反编译重点：高阶函数参数的内联处理
 */
inline fun <T, R> List<T>.mapNotNull(transform: (T) -> R?): List<R> {
    val result = mutableListOf<R>()
    for (element in this) {
        transform(element)?.let { result.add(it) }
    }
    return result
}

/**
 * 扩展函数演示
 * 反编译重点：扩展函数的静态方法实现
 */
fun String.toTitleCase(): String = 
    split(" ").joinToString(" ") { word ->
        word.lowercase().replaceFirstChar { it.uppercase() }
    }

fun <T> List<T>.second(): T? = if (size >= 2) this[1] else null

fun <T> MutableList<T>.swap(i: Int, j: Int) {
    if (i in indices && j in indices) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }
}

/**
 * 属性委托演示
 * 反编译重点：委托属性的字节码实现
 */
class LazyPropertyDemo {
    val expensiveValue: String by lazy {
        println("计算昂贵的值...")
        "计算完成的结果"
    }
    
    var observedProperty: String = "初始值"
        set(value) {
            println("属性从 '${field}' 变更为 '$value'")
            field = value
        }
}

/**
 * 密封类演示 - 用于类型安全的多态
 * 反编译重点：密封类的继承限制实现
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
    
    /**
     * 模式匹配演示
     * 反编译重点：when表达式的优化和跳转表生成
     */
    fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Throwable) -> R,
        onLoading: () -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Error -> onError(exception)
        is Loading -> onLoading()
    }
}

/**
 * 数据类演示
 * 反编译重点：自动生成的equals、hashCode、toString、copy方法
 */
data class Person(
    val name: String,
    val age: Int,
    val email: String? = null
) {
    /**
     * 自定义方法
     * 反编译重点：与自动生成方法的字节码差异
     */
    fun displayName(): String = name.toTitleCase()
    
    /**
     * 解构声明支持
     * 反编译重点：componentN方法的生成
     */
    operator fun component4(): String? = email
}

/**
 * 对象声明演示（单例）
 * 反编译重点：单例模式的字节码实现
 */
object InteropUtilities {
    
    /**
     * 与Java Function互操作
     */
    fun <T, R> kotlinToJavaFunction(f: (T) -> R): JavaFunction<T, R> = 
        JavaFunction { t -> f(t) }
    
    fun <T, R> javaToKotlinFunction(f: JavaFunction<T, R>): (T) -> R = 
        { t -> f.apply(t) }
    
    /**
     * 集合转换工具
     */
    fun <T> kotlinListToJava(list: List<T>): MutableList<T> = 
        ArrayList(list)
    
    fun <T> javaListToKotlin(javaList: MutableList<T>): List<T> = 
        javaList.toList()
    
    /**
     * 可空性转换
     */
    fun <T : Any> kotlinToJavaOptional(value: T?): Optional<T> = 
        Optional.ofNullable(value)
    
    fun <T> javaOptionalToKotlin(optional: Optional<T>): T? = 
        if (optional.isPresent) optional.get() else null
}

/**
 * 协程相关的互操作演示（简化版，不包含实际协程）
 * 反编译重点：suspend函数的状态机实现
 */
class CoroutineInteropDemo {
    
    /**
     * 模拟suspend函数的结构
     * 注意：实际的suspend需要kotlin-coroutines依赖
     */
    fun simulateSuspendFunction(): String {
        // 这里模拟suspend函数的状态保存和恢复
        return "模拟的异步操作结果"
    }
    
    /**
     * 回调到Lambda的转换模拟
     */
    fun <T> callbackToLambda(
        operation: (callback: (T) -> Unit) -> Unit
    ): T {
        var result: T? = null
        var completed = false
        
        operation { value ->
            result = value
            completed = true
        }
        
        // 这里应该是真正的挂起等待
        while (!completed) {
            Thread.sleep(1) // 简化的等待
        }
        
        @Suppress("UNCHECKED_CAST")
        return result as T
    }
}

/**
 * 类型别名演示
 * 反编译重点：类型别名在字节码中的表示（应该是透明的）
 */
typealias StringProcessor = (String) -> String
typealias PersonValidator = (Person) -> Boolean
typealias ResultCallback<T> = (Result<T>) -> Unit

/**
 * 主演示函数
 */
fun main() {
    println("=== Kotlin跨语言互操作演示 ===")
    
    // 1. 空安全容器演示
    val container = NullSafeContainer<String>()
    container.addNonNull("有效项目")
    container.addNullable(null)
    container.addNullable("另一个项目")
    
    println("非空项目: ${container.getNonNullItems()}")
    println("安全获取: ${container.safeGet(0)}")
    println("带默认值: ${container.getWithDefault(10, "默认值")}")
    println("处理结果: ${container.processIfNotNull(0)}")
    
    // 2. 内联函数演示
    val (result, time) = measureTime {
        (1..1000).map { it * it }.sum()
    }
    println("计算结果: $result, 耗时: ${time}纳秒")
    
    // 3. Reified类型参数演示
    val mixedList = listOf("string", 42, 3.14, true)
    println("字符串检查: ${mixedList.filter { checkType<String>(it) }}")
    
    val stringList = createList<String>()
    stringList.add("reified演示")
    println("Reified列表: $stringList")
    println("KClass信息: ${getKClass<Person>()}")
    
    // 4. 扩展函数演示
    val title = "hello world from kotlin"
    println("标题格式: ${title.toTitleCase()}")
    
    val numbers = mutableListOf(1, 2, 3, 4, 5)
    println("第二个元素: ${numbers.second()}")
    numbers.swap(0, 4)
    println("交换后: $numbers")
    
    // 5. 延迟属性演示
    val lazyDemo = LazyPropertyDemo()
    println("首次访问: ${lazyDemo.expensiveValue}")
    println("再次访问: ${lazyDemo.expensiveValue}")
    
    lazyDemo.observedProperty = "新值"
    
    // 6. 密封类演示
    val results: List<Result<String>> = listOf(
        Result.Success("成功数据"),
        Result.Error(RuntimeException("错误信息")),
        Result.Loading
    )
    
    results.forEach { result ->
        val message = result.fold(
            onSuccess = { "成功: $it" },
            onError = { "错误: ${it.message}" },
            onLoading = { "加载中..." }
        )
        println("结果: $message")
    }
    
    // 7. 数据类演示
    val person = Person("john doe", 30, "john@example.com")
    println("原始: $person")
    println("显示名称: ${person.displayName()}")
    
    val (name, age, email, extraEmail) = person
    println("解构: name=$name, age=$age, email=$email, extra=$extraEmail")
    
    val olderPerson = person.copy(age = 31)
    println("复制修改: $olderPerson")
    
    // 8. 工具对象演示
    val kotlinFunc: (String) -> Int = { it.length }
    val javaFunc = InteropUtilities.kotlinToJavaFunction(kotlinFunc)
    println("函数转换测试: ${javaFunc.apply("test")}")
    
    val kotlinOptional: String? = "可空值"
    val javaOptional = InteropUtilities.kotlinToJavaOptional(kotlinOptional)
    println("Optional转换: ${javaOptional.orElse("默认")}")
    
    // 9. 类型别名演示
    val processor: StringProcessor = { it.uppercase() }
    val validator: PersonValidator = { it.age >= 18 }
    
    println("处理器测试: ${processor("hello")}")
    println("验证器测试: ${validator(person)}")
    
    // 10. 协程互操作模拟
    val coroutineDemo = CoroutineInteropDemo()
    println("模拟协程结果: ${coroutineDemo.simulateSuspendFunction()}")
}