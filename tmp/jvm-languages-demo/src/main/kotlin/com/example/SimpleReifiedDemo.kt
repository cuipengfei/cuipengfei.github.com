package com.example

/**
 * 专门用于分析 reified 内联机制的简单演示
 */
object SimpleReifiedDemo {
    
    // 普通泛型函数 - 会被类型擦除
    fun <T> checkTypeNormal(obj: Any): Boolean {
        // 这里无法直接检查 T 类型，因为类型被擦除了
        // 只能通过传递额外的 Class 参数来实现
        println("Normal function: Cannot determine type T at runtime")
        return false
    }
    
    // reified 内联函数 - 保留类型信息
    inline fun <reified T> checkTypeReified(obj: Any): Boolean {
        // 这里可以直接使用 T 类型进行检查
        println("Reified function: Checking if obj is ${T::class.simpleName}")
        return obj is T
    }
    
    // 用于调用这些函数的测试方法
    fun testBothApproaches() {
        val stringObj = "Hello"
        val intObj = 42
        
        println("=== Testing Normal Generic Function ===")
        checkTypeNormal<String>(stringObj)
        checkTypeNormal<Int>(intObj)
        
        println("=== Testing Reified Generic Function ===") 
        val isString1 = checkTypeReified<String>(stringObj)
        val isString2 = checkTypeReified<String>(intObj)
        
        println("'Hello' is String: $isString1")
        println("42 is String: $isString2")
    }
}

fun main() {
    SimpleReifiedDemo.testBothApproaches()
}