package com.example

import kotlin.reflect.typeOf

/**
 * 测试调用 Kotlin 标准库中的 reified 函数
 * 这些函数来自真实的 kotlin-stdlib JAR 文件
 */
object StdLibReifiedTest {
    
    fun testStandardLibraryReified() {
        println("=== Testing Kotlin Standard Library Reified Functions ===")
        
        // 1. 测试 typeOf<T>() - 获取类型信息
        println("1. Testing typeOf<T>():")
        val stringType = typeOf<String>()
        val listType = typeOf<List<String>>()
        println("   typeOf<String>(): $stringType")
        println("   typeOf<List<String>>(): $listType")
        
        // 2. 测试 listOf<T>() - 创建类型安全的 List
        println("2. Testing listOf<T>():")
        val stringList = listOf<String>()
        val intList = listOf<Int>()
        println("   listOf<String>(): ${stringList.javaClass}")
        println("   listOf<Int>(): ${intList.javaClass}")
        
        // 3. 测试 arrayOf<T>() - 创建类型数组
        println("3. Testing arrayOf<T>():")  
        val stringArray = arrayOf<String>()
        val intArray = arrayOf<Int>()
        println("   arrayOf<String>(): ${stringArray.javaClass}")
        println("   arrayOf<Int>(): ${intArray.javaClass}")
        
        // 4. 测试 emptyArray<T>() - 创建空数组
        println("4. Testing emptyArray<T>():")
        val emptyStringArray = emptyArray<String>()
        val emptyIntArray = emptyArray<Int>()
        println("   emptyArray<String>(): ${emptyStringArray.javaClass}")
        println("   emptyArray<Int>(): ${emptyIntArray.javaClass}")
        
        // 5. 测试集合过滤 - filterIsInstance<T>()
        println("5. Testing filterIsInstance<T>():")
        val mixedList: List<Any> = listOf("hello", 42, "world", 3.14, "kotlin")
        val strings = mixedList.filterIsInstance<String>()
        val numbers = mixedList.filterIsInstance<Number>()
        println("   Original: $mixedList")
        println("   Strings: $strings")  
        println("   Numbers: $numbers")
        
        // 6. 测试 Gson 风格的 reified - 如果可用的话
        println("6. Testing reified type parameter preservation:")
        testGenericPreservation<String>()
        testGenericPreservation<List<Int>>()
    }
    
    // 我们自己的函数，用来测试与标准库的交互
    inline fun <reified T> testGenericPreservation() {
        val kClass = T::class
        println("   T::class = ${kClass.simpleName}")
        println("   T::class.java = ${kClass.java.name}")
        
        // 测试能否创建数组
        val array = java.lang.reflect.Array.newInstance(kClass.java, 0) as Array<T>
        println("   Created array: ${array.javaClass}")
    }
}

fun main() {
    StdLibReifiedTest.testStandardLibraryReified()
}