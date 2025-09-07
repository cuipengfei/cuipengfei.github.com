package com.example

/**
 * Groovy泛型演示 - 展示Groovy的动态类型系统与泛型支持
 */
class GroovyGenericsDemo {
    
    /**
     * Groovy泛型容器 - 展示声明处协变逆变
     */
    static class Container<T> {
        private T value
        
        Container(T value) {
            this.value = value
        }
        
        T getValue() { value }
        void setValue(T value) { this.value = value }
    }
    
    /**
     * 协变容器 - Groovy的声明处协变支持
     */
    static class CovariantContainer<+T> {
        private final T value
        
        CovariantContainer(T value) {
            this.value = value
        }
        
        T getValue() { value }
    }
    
    /**
     * 逆变处理器 - Groovy的声明处逆变支持
     */
    static class ContravariantProcessor<-T> {
        void process(T item) {
            println "Processing: ${item}"
        }
    }
    
    /**
     * 高阶函数示例 - 展示函数类型系统的泛型
     */
    static <T, R> R transform(T input, Closure<R> transformer) {
        transformer.call(input)
    }
    
    /**
     * 类型擦除演示 - Groovy中的运行时行为
     */
    static void demonstrateTypeErasure() {
        def stringList = ["a", "b", "c"]
        def numberList = [1, 2, 3]
        
        // Groovy的动态类型检查
        println "String list type: ${stringList.getClass()}"
        println "Number list type: ${numberList.getClass()}"
        
        // 运行时类型检查
        println "Is stringList instanceof List: ${stringList instanceof List}"
        println "Is stringList instanceof List<String>: ${stringList instanceof List<String>}"
    }
    
    /**
     * 泛型方法与闭包结合
     */
    static <T> List<T> filterGeneric(List<T> items, Closure<Boolean> predicate) {
        items.findAll(predicate)
    }
    
    /**
     * 动态类型与静态泛型的交互
     */
    static void demonstrateDynamicTyping() {
        def container = new Container<String>("Hello Groovy")
        
        // 静态类型检查
        String value = container.value
        println "Static type: ${value}"
        
        // 动态类型转换
        container.value = 42  // Groovy允许这种转换
        def dynamicValue = container.value
        println "Dynamic type: ${dynamicValue} (${dynamicValue.getClass()})"
    }
    
    /**
     * 泛型数组创建 - Groovy的灵活处理
     */
    static <T> T[] createGenericArray(Class<T> clazz, int size) {
        java.lang.reflect.Array.newInstance(clazz, size) as T[]
    }
    
    static void main(String[] args) {
        println "=== Groovy泛型系统演示 ==="
        
        // 1. 基本泛型使用
        def container = new Container<Integer>(42)
        println "Container value: ${container.value}"
        
        // 2. 协变逆变演示
        def stringContainer = new CovariantContainer<String>("Hello")
        def objContainer = stringContainer as CovariantContainer<Object>
        println "Covariant value: ${objContainer.value}"
        
        // 3. 高阶函数
        def result = transform("Groovy", { it.toUpperCase() })
        println "Transformed: ${result}"
        
        // 4. 类型擦除演示
        demonstrateTypeErasure()
        
        // 5. 动态类型演示
        demonstrateDynamicTyping()
        
        // 6. 泛型数组
        def stringArray = createGenericArray(String, 3)
        stringArray[0] = "Groovy"
        println "Generic array: ${Arrays.toString(stringArray)}"
        
        // 7. 泛型过滤
        def numbers = [1, 2, 3, 4, 5, 6]
        def evens = filterGeneric(numbers, { it % 2 == 0 })
        println "Even numbers: ${evens}"
    }
}