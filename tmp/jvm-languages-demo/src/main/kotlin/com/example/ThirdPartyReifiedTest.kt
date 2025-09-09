package com.example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 测试 Kotlin 调用第三方库中的 reified 函数
 * 重点测试：Jackson Kotlin 模块的 reified 扩展函数
 * 这些函数来自第三方 JAR，不是标准库
 */

data class Person(val name: String, val age: Int)
data class Company(val name: String, val employees: List<Person>)

fun main() {
    println("=== Testing Third-Party Library Reified Functions ===")
    
    // 1. 测试 Jackson Kotlin 模块的 reified 函数
    testJacksonReifiedFunctions()
    
    // 2. 对比：标准 Java 方式 vs Kotlin reified 方式
    compareJavaVsReified()
    
    // 3. 测试复杂泛型类型的 reified 处理
    testComplexGenericTypes()
    
    println("\n=== Third-Party Reified Analysis Complete ===")
}

/**
 * 测试 Jackson Kotlin 模块的 reified 扩展函数
 * 这些是第三方库（jackson-module-kotlin）提供的 inline reified 函数
 */
fun testJacksonReifiedFunctions() {
    println("\n1. Testing Jackson Kotlin Module Reified Functions:")
    
    val mapper = jacksonObjectMapper()
    val personJson = """{"name":"Alice","age":30}"""
    val companyJson = """{"name":"TechCorp","employees":[{"name":"Bob","age":25},{"name":"Carol","age":35}]}"""
    
    try {
        // 核心测试：这是第三方库提供的 inline reified 函数
        // Jackson-module-kotlin 中定义：inline fun <reified T> ObjectMapper.readValue(content: String): T
        val person: Person = mapper.readValue<Person>(personJson)
        println("   Jackson reified readValue<Person>: $person")
        
        val company: Company = mapper.readValue<Company>(companyJson)
        println("   Jackson reified readValue<Company>: $company")
        
        // 测试嵌套泛型类型 - 这对编译器来说更具挑战性
        val employeesJson = """[{"name":"Dave","age":28},{"name":"Eve","age":32}]"""
        val employees: List<Person> = mapper.readValue<List<Person>>(employeesJson)
        println("   Jackson reified readValue<List<Person>>: $employees")
        
        // 测试 Map 类型
        val mapJson = """{"key1":{"name":"Frank","age":27},"key2":{"name":"Grace","age":29}}"""
        val personMap: Map<String, Person> = mapper.readValue<Map<String, Person>>(mapJson)
        println("   Jackson reified readValue<Map<String, Person>>: $personMap")
        
    } catch (e: Exception) {
        println("   Jackson reified test failed: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * 对比传统 Java 方式与第三方库 Kotlin reified 方式
 */
fun compareJavaVsReified() {
    println("\n2. Comparing Java Way vs Third-Party Kotlin Reified Way:")
    
    val personJson = """{"name":"Comparison","age":25}"""
    
    try {
        // Java 传统方式：需要传递 Class 对象
        val javaMapper = ObjectMapper()
        val personJavaWay = javaMapper.readValue(personJson, Person::class.java)
        println("   Java way (Class parameter): $personJavaWay")
        
        // Gson 传统方式：需要 TypeToken
        val gson = Gson()
        val personGsonWay: Person = gson.fromJson(personJson, object : TypeToken<Person>() {}.type)
        println("   Gson way (TypeToken): $personGsonWay")
        
        // 第三方库 Kotlin reified 方式：直接使用类型参数
        val kotlinMapper = jacksonObjectMapper()
        val personReifiedWay: Person = kotlinMapper.readValue<Person>(personJson)
        println("   Third-party reified way: $personReifiedWay")
        
        println("   -> 第三方库的 reified 函数提供了与标准库相同的简洁性")
        
    } catch (e: Exception) {
        println("   Comparison test failed: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * 测试复杂泛型类型，观察编译器如何处理
 */
fun testComplexGenericTypes() {
    println("\n3. Testing Complex Generic Types with Third-Party Reified:")
    
    val mapper = jacksonObjectMapper()
    
    try {
        // 测试嵌套的复杂类型
        val complexJson = """
        {
            "departments": {
                "engineering": [{"name":"Alice","age":30}],
                "marketing": [{"name":"Bob","age":25}]
            }
        }
        """
        
        // 这是一个非常复杂的泛型类型
        data class Organization(val departments: Map<String, List<Person>>)
        
        val org: Organization = mapper.readValue<Organization>(complexJson)
        println("   Complex type readValue<Organization>: $org")
        
        // 直接解析复杂的嵌套 Map
        val deptJson = """{"eng":[{"name":"Carol","age":28}],"sales":[{"name":"Dave","age":33}]}"""
        val departments: Map<String, List<Person>> = mapper.readValue<Map<String, List<Person>>>(deptJson)
        println("   Complex nested type: $departments")
        
    } catch (e: Exception) {
        println("   Complex types test failed: ${e.message}")
        e.printStackTrace()
    }
}