package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== JVM多语言类型系统演示 ===");
        
        // Java泛型演示
        GenericContainer<Integer> intContainer = new GenericContainer<>(42);
        Optional<String> result = intContainer.transform(i -> i.toString());
        System.out.println("Java泛型结果: " + result.orElse("empty"));
        
        // 协变逆变演示
        GenericContainer<Double> doubleContainer = new GenericContainer<>(3.14);
        intContainer.processCovariant(doubleContainer);
        
        // PECS原则
        List<Integer> integers = Arrays.asList(1, 2, 3);
        List<Number> numbers = Arrays.asList(0.0, 0.0, 0.0);
        
        for (int i = 0; i < integers.size(); i++) {
            GenericContainer.copy(
                new GenericContainer<>(integers.get(i)),
                new GenericContainer<>(numbers.get(i))
            );
        }
        
        System.out.println("PECS复制结果: " + numbers);
        
        // 调用其他语言的代码
        System.out.println("\n=== 跨语言调用演示 ===");
        
        // 调用Scala代码 (通过静态方法包装)
        try {
            Class<?> scalaDemo = Class.forName("com.example.ScalaVarianceDemo");
            Object instance = scalaDemo.getDeclaredConstructor().newInstance();
            System.out.println("Scala类加载成功: " + instance.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Scala类加载失败: " + e.getMessage());
        }
        
        // 调用Kotlin代码 (通过静态方法包装)
        try {
            Class<?> kotlinDemo = Class.forName("com.example.KotlinGenericsDemoKt");
            System.out.println("Kotlin类加载成功: " + kotlinDemo.getSimpleName());
        } catch (Exception e) {
            System.out.println("Kotlin类加载失败: " + e.getMessage());
        }
    }
}