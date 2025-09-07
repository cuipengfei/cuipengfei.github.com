package com.example;

import com.example.advanced.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== JVM多语言类型系统深度研究演示 ===");
        
        // 1. 基础泛型反编译实验 (立即启动实验)
        System.out.println("\n=== 1. 基础泛型反编译实验 ===");
        BasicGenericsExploration<String> exploration = new BasicGenericsExploration<>("Test");
        Optional<Integer> lengthResult = exploration.transform(String::length);
        System.out.println("基础泛型演示: " + lengthResult.orElse(0));
        
        // 2. 类型擦除补偿机制演示
        System.out.println("\n=== 2. 类型擦除补偿机制 ===");
        TypeErasureCompensationDemo.main(new String[0]);
        
        // 3. Monad实现对比
        System.out.println("\n=== 3. Monad实现对比 ===");
        MonadImplementationComparison.main(new String[0]);
        
        // 4. 变性实现对比
        System.out.println("\n=== 4. Java通配符变性分析 ===");
        VarianceImplementationComparison.main(new String[0]);
        
        // 原有的基础演示
        System.out.println("\n=== 5. 基础演示 (保持兼容性) ===");
        GenericContainer<Integer> intContainer = new GenericContainer<>(42);
        Optional<String> result = intContainer.transform(i -> i.toString());
        System.out.println("原有泛型结果: " + result.orElse("empty"));
        
        // 跨语言调用演示
        System.out.println("\n=== 6. 跨语言调用演示 ===");
        
        // 调用Scala代码
        try {
            Class<?> scalaDemo = Class.forName("com.example.ScalaBasicGenericsExploration");
            System.out.println("Scala基础泛型实验类加载成功");
            
            Class<?> scalaAdvanced = Class.forName("com.example.advanced.ScalaAdvancedFeatures");
            System.out.println("Scala高级特性类加载成功");
        } catch (Exception e) {
            System.out.println("Scala类加载失败: " + e.getMessage());
        }
        
        // 调用Kotlin代码
        try {
            Class<?> kotlinAdvanced = Class.forName("com.example.KotlinAdvancedGenerics");
            System.out.println("Kotlin高级泛型类加载成功");
        } catch (Exception e) {
            System.out.println("Kotlin类加载失败: " + e.getMessage());
        }
        
        System.out.println("\n=== 反编译分析指令总结 ===");
        System.out.println("1. javac -cp . com/example/*.java com/example/advanced/*.java");
        System.out.println("2. scalac com/example/*.scala com/example/advanced/*.scala");
        System.out.println("3. kotlinc com/example/*.kt");
        System.out.println("4. javap -v -p <ClassName>.class");
        System.out.println("5. 使用CFR反编译: java -jar cfr.jar <ClassName>.class");
        System.out.println("6. 重点观察:");
        System.out.println("   - Signature属性中的泛型信息");
        System.out.println("   - 桥方法的生成");
        System.out.println("   - 类型擦除的具体表现");
        System.out.println("   - 变性规则的字节码实现");
        System.out.println("   - Monad操作的调用模式");
    }
}