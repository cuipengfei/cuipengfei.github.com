/**
 * 基础泛型探索实验 - Kotlin版本
 * 对应Java/Scala版本，展示Kotlin的泛型特性
 */
class BasicGenericsExploration<T>(private val value: T) where T : Comparable<T> {
    private val items: MutableList<T> = mutableListOf()
    
    /**
     * 泛型方法：测试方法级别的类型参数
     */
    fun <U> transform(mapper: (T) -> U): U? {
        return value?.let(mapper)
    }
    
    /**
     * 测试具体化类型参数 - Kotlin特有特性
     */
    inline fun <reified U> transformReified(mapper: (T) -> U): U? {
        println("Actual type: ${U::class.simpleName}")
        return value?.let(mapper)
    }
    
    /**
     * 测试边界约束
     */
    fun <U : Number> calculateSum(numbers: List<U>): Double {
        return numbers.sumOf { it.toDouble() }
    }
    
    /**
     * 测试协变声明处变性 (out)
     */
    fun processProducers(producers: List<out T>) {
        producers.forEach { items.add(it) }
    }
    
    /**
     * 测试逆变声明处变性 (in)
     */
    fun processConsumers(consumers: MutableList<in T>) {
        consumers.add(value)
    }
    
    /**
     * 测试星投影
     */
    fun processUntypedList(list: List<*>) {
        println("Untyped list size: ${list.size}")
        // 无法获取具体类型信息，只能当作Any?处理
        list.forEach { item ->
            println("Item: $item (type: ${item?.javaClass?.simpleName})")
        }
    }
    
    /**
     * 测试协变接口
     */
    fun workWithProducer(producer: Producer<T>): T {
        return producer.produce()
    }
    
    /**
     * 测试逆变接口
     */
    fun workWithConsumer(consumer: Consumer<T>) {
        consumer.consume(value)
    }
    
    /**
     * 测试类型别名
     */
    fun processWithAlias(processor: StringProcessor) {
        if (value is String) {
            processor(value as String)
        }
    }
    
    fun getValue(): T = value
    
    fun getOptionalItems(): List<T>? {
        return if (items.isNotEmpty()) items.toList() else null
    }
}

/**
 * 协变接口示例
 */
interface Producer<out T> {
    fun produce(): T
}

/**
 * 逆变接口示例
 */
interface Consumer<in T> {
    fun consume(t: T)
}

/**
 * 类型别名示例
 */
typealias StringProcessor = (String) -> Unit

/**
 * 具体的Producer实现
 */
class StringProducer(private val str: String) : Producer<String> {
    override fun produce(): String = str
}

/**
 * 具体的Consumer实现
 */
class StringConsumer : Consumer<String> {
    override fun consume(t: String) {
        println("Consuming: $t")
    }
}

/**
 * 测试函数
 */
fun main() {
    val exploration = BasicGenericsExploration("Hello")
    
    // 测试泛型方法
    val length = exploration.transform { it.length }
    println("String length: $length")
    
    // 测试具体化类型参数
    val reifiedLength = exploration.transformReified<Int> { it.length }
    println("Reified length: $reifiedLength")
    
    // 测试边界约束
    val numbers = listOf(1, 2, 3, 4, 5)
    val sum = exploration.calculateSum(numbers)
    println("Sum: $sum")
    
    // 测试协变和逆变
    val strings: List<String> = listOf("A", "B", "C")
    exploration.processProducers(strings)
    
    val objects: MutableList<Any> = mutableListOf()
    exploration.processConsumers(objects)
    println("Objects after processing: $objects")
    
    // 测试星投影
    val mixedList: List<*> = listOf("Hello", 42, true, 3.14)
    exploration.processUntypedList(mixedList)
    
    // 测试Producer和Consumer
    val producer = StringProducer("World")
    val result = exploration.workWithProducer(producer)
    println("Producer result: $result")
    
    val consumer = StringConsumer()
    exploration.workWithConsumer(consumer)
    
    // 测试类型别名
    exploration.processWithAlias { str ->
        println("Processing with alias: ${str.uppercase()}")
    }
}