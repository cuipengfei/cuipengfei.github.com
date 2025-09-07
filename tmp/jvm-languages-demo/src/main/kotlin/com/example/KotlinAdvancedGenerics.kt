package com.example

import kotlin.reflect.KClass

/**
 * Kotlin高级泛型特性演示
 * 展示reified类型参数、声明处/使用处变性、类型安全等特性
 */
class KotlinAdvancedGenerics {

    object TypeSafeStorage {
        val storage = mutableMapOf<KClass<*>, Any>()

        /**
         * 类型安全的存储
         */
        inline fun <reified T : Any> store(key: String, value: T) {
            storage[T::class] = value
        }

        /**
         * 类型安全的获取
         */
        inline fun <reified T : Any> retrieve(key: String): T? {
            return storage[T::class] as? T
        }
    }

    /**
     * 1. reified类型参数 - 类型擦除补偿机制
     */
    companion object {
        /**
         * 创建泛型数组 - 利用reified类型参数
         */
        inline fun <reified T> createArray(size: Int): Array<T?> {
            return arrayOfNulls<T>(size)
        }

        /**
         * 运行时类型检查 - reified的优势
         */
        inline fun <reified T> isInstance(value: Any): Boolean {
            return value is T
        }

        /**
         * 获取运行时类型信息
         */
        inline fun <reified T : Any> getTypeInfo(): KClass<T> {
            return T::class
        }

        /**
         * 类型安全的反序列化
         */
        inline fun <reified T> parseJson(json: String): T {
            // 模拟JSON解析，实际使用Gson或Kotlinx.serialization
            println("解析JSON为类型: ${T::class.simpleName}")
            return when (T::class) {
                String::class -> json as T
                Int::class -> json.toInt() as T
                else -> throw IllegalArgumentException("不支持的类型")
            }
        }
    }

    /**
     * 2. 声明处协变逆变
     */
    // 协变生产者
    interface Producer<out T> {
        fun produce(): T
    }

    // 逆变消费者
    interface Consumer<in T> {
        fun consume(item: T)
    }

    // 不变类型
    interface Processor<T> {
        fun process(item: T): T
    }

    /**
     * 3. 使用处协变逆变（类型投影）
     */
    class Container<T>(val value: T) {
        fun get(): T = value
        fun set(newValue: T) { /* 实现省略 */ }
    }

    /**
     * 使用处协变 - 只读投影
     */
    fun printContainer(container: Container<out String>) {
        println("Container value: ${container.get()}")
    }

    /**
     * 使用处逆变 - 只写投影
     */
    fun fillContainer(container: Container<in String>, value: String) {
        // container.set(value) // 可以设置
        // val x = container.get() // 不能安全获取
    }

    /**
     * 4. 高阶类型支持的简化版本
     */
    // Functor接口
    interface Functor<F> {
        fun <A, B> map(fa: F, f: (A) -> B): F
    }

    // 具体的List Functor实现
    object ListFunctor : Functor<List<*>> {
        override fun <A, B> map(fa: List<*>, f: (A) -> B): List<B> {
            return (fa as List<A>).map(f)
        }
    }

    /**
     * 5. 具体化类型参数的高级用法
     */
    class TypeSafeRepository {
        val storage = mutableMapOf<KClass<*>, Any>()

        /**
         * 类型安全的存储
         */
        inline fun <reified T : Any> store(key: String, value: T) {
            storage[T::class] = value
        }

        /**
         * 类型安全的获取
         */
        inline fun <reified T : Any> retrieve(key: String): T? {
            return storage[T::class] as? T
        }

        /**
         * 类型安全的列表存储
         */
        inline fun <reified T : Any> storeList(key: String, list: List<T>) {
            storage[T::class] = list
        }

        /**
         * 类型安全的列表获取
         */
        inline fun <reified T : Any> retrieveList(key: String): List<T> {
            return storage[T::class] as? List<T> ?: emptyList()
        }
    }

    /**
     * 6. 类型投影与边界
     */
    // 上界约束
    fun <T : Number> sum(numbers: List<T>): Double {
        return numbers.sumOf { it.toDouble() }
    }

    // 多重边界
    fun <T> maxNumber(numbers: List<T>): T where T : Number, T : Comparable<T> {
        return numbers.maxOrNull()!!
    }

    /**
     * 7. 星投影的使用场景
     */
    fun processAnyList(list: List<*>) {
        when (list.firstOrNull()) {
            is String -> println("字符串列表")
            is Int -> println("整数列表")
            else -> println("未知类型列表")
        }
    }

    /**
     * 8. DSL构建器模式结合reified
     */
    class QueryBuilder {
        val conditions = mutableListOf<String>()

        inline fun <reified T> where(field: String, value: T) {
            conditions.add("$field = '${value}' (类型: ${T::class.simpleName})")
        }

        inline fun <reified T : Comparable<T>> whereGreaterThan(field: String, value: T) {
            conditions.add("$field > '${value}' (类型: ${T::class.simpleName})")
        }

        fun build(): String = conditions.joinToString(" AND ")
    }
}

/**
 * 演示类
 */
fun main() {
    println("=== Kotlin高级泛型特性演示 ===")

    // 1. reified类型参数演示
    println("\n1. reified类型参数:")
    val stringArray = KotlinAdvancedGenerics.createArray<String>(5)
    println("创建字符串数组: ${stringArray.size}个元素")
    
    val isString = KotlinAdvancedGenerics.isInstance<String>("Hello")
    println("'Hello'是字符串: $isString")
    
    val typeInfo = KotlinAdvancedGenerics.getTypeInfo<Int>()
    println("Int类型信息: $typeInfo")

    // 2. 声明处协变逆变
    println("\n2. 声明处协变逆变:")
    val stringProducer: KotlinAdvancedGenerics.Producer<String> = object : KotlinAdvancedGenerics.Producer<String> {
        override fun produce(): String = "Hello Kotlin"
    }
    
    val objectProducer: KotlinAdvancedGenerics.Producer<Any> = stringProducer
    println("协变示例: ${objectProducer.produce()}")

    // 3. 使用处类型投影
    println("\n3. 使用处类型投影:")
    val container = KotlinAdvancedGenerics.Container("test")
    KotlinAdvancedGenerics().printContainer(container)

    // 4. Functor演示
    println("\n4. Functor演示:")
    val numbers = listOf(1, 2, 3, 4, 5)
    val doubled = KotlinAdvancedGenerics.ListFunctor.map(numbers) { it: Int -> it * 2 }
    println("原始: $numbers, 处理后: $doubled")

    // 5. 类型安全Repository
    println("\n5. 类型安全Repository:")
    val repo = KotlinAdvancedGenerics.TypeSafeRepository()
    repo.store<String>("name", "Kotlin")
    repo.store<Int>("count", 42)
    repo.storeList<Double>("prices", listOf(1.99, 2.99, 3.99))
    
    val name: String? = repo.retrieve<String>("name")
    val prices: List<Double> = repo.retrieveList<Double>("prices")
    println("存储的名称: $name")
    println("存储的价格: $prices")

    // 6. 边界约束
    println("\n6. 边界约束:")
    val numbers2 = listOf(1.5, 2.5, 3.5)
    val sum = KotlinAdvancedGenerics().sum(numbers2)
    println("数字列表求和: $sum")

    // 7. DSL构建器
    println("\n7. DSL构建器:")
    val query = KotlinAdvancedGenerics.QueryBuilder().apply {
        where("name", "Kotlin")
        whereGreaterThan("age", 18)
        where("active", true)
    }
    println("构建的查询: ${query.build()}")

    // 8. 星投影
    println("\n8. 星投影:")
    KotlinAdvancedGenerics().processAnyList(listOf("a", "b", "c"))
    KotlinAdvancedGenerics().processAnyList(listOf(1, 2, 3))
}