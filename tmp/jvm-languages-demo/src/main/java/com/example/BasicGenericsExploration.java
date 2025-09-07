package com.example;

import java.util.*;
import java.util.function.Function;

/**
 * 基础泛型反编译实验 - 深度研究文档中的立即启动实验
 * 任务1.1：理解JVM字节码层面泛型的真实面貌
 * <p>
 * 反编译分析重点：
 * 1. 类型签名(Signature)与描述符(Descriptor)的差异
 * 2. 桥方法(Bridge Method)的生成时机和作用
 * 3. 泛型类的字节码结构分析
 * 4. 类型检查在编译期 vs 运行期的分工
 * <p>
 * 反编译指令：
 * javac BasicGenericsExploration.java
 * javap -v -p BasicGenericsExploration.class
 * java -jar cfr.jar BasicGenericsExploration.class
 */
public class BasicGenericsExploration<T extends Comparable<T>> {
    private T value;
    private List<T> items;

    /**
     * 基础泛型构造器
     * 反编译重点：泛型擦除后构造器签名的变化
     */
    public BasicGenericsExploration(T value) {
        this.value = value;
        this.items = new ArrayList<>();
    }

    /**
     * 有界泛型方法 - 演示类型擦除机制
     * 反编译重点：方法签名中的Signature属性 vs 方法描述符
     * <p>
     * 预期发现：
     * - T被擦除为Comparable
     * - U被擦除为Object
     * - Signature属性保留完整泛型信息
     * - LocalVariableTypeTable保留局部变量类型信息
     */
    public <U> Optional<U> transform(Function<T, U> mapper) {
        return Optional.ofNullable(value).map(mapper);
    }

    /**
     * 方法重载演示 - 类型擦除导致的冲突
     * 反编译重点：为什么下面的重载方法无法共存
     * <p>
     * 原因：擦除后方法签名变为：
     * - process(List)
     * - process(List) <- 重复！
     */
    public void process(List<String> strings) {
        System.out.println("处理字符串列表，大小: " + strings.size());
    }

    // 这个方法会导致编译错误：name clash
    // public void process(List<Integer> integers) {
    //     System.out.println("处理整数列表，大小: " + integers.size()); 
    // }

    /**
     * 通过不同方法名解决重载冲突
     * 反编译重点：方法名改变后的字节码结构
     */
    public void processIntegers(List<Integer> integers) {
        System.out.println("处理整数列表，大小: " + integers.size());
    }

    /**
     * 原始类型兼容性演示 - 任务1.2的一部分
     * 反编译重点：原始类型和泛型类型的字节码差异
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void demonstrateRawTypeCompatibility() {
        // 原始类型List（无泛型参数）
        List rawList = new ArrayList();
        rawList.add("字符串");
        rawList.add(123);
        rawList.add(new Date());

        // 泛型类型到原始类型的赋值（类型擦除使其成为可能）
        List<String> typedList = new ArrayList<>();
        typedList.add("Hello");

        rawList = typedList;  // 合法：泛型到原始类型
        typedList = rawList;  // 警告但合法：原始类型到泛型

        System.out.println("原始类型兼容性演示完成");
    }

    /**
     * 泛型数组限制演示 - 任务3.1
     * 反编译重点：编译器为什么禁止泛型数组创建
     */
    public void demonstrateGenericArrayLimitations() {
        // 编译错误：Cannot create a generic array of T
        // T[] genericArray = new T[10];

        // 编译错误：Cannot create a generic array of List<String>
        // List<String>[] genericListArray = new List<String>[10];

        // 类型不安全的"解决方案"
        @SuppressWarnings("unchecked")
        List<String>[] unsafeArray = (List<String>[]) new List[10];

        // Object数组可以工作，但类型不安全
        Object[] objectArray = new Object[10];
        objectArray[0] = Arrays.asList("a", "b", "c");

        System.out.println("泛型数组限制演示完成");
    }

    /**
     * instanceof检查的限制 - 任务3.1
     * 反编译重点：类型擦除如何影响运行时类型检查
     */
    public void demonstrateInstanceofLimitations(Object obj) {
        // 编译错误：Cannot perform instanceof check against parameterized type
        // if (obj instanceof List<String>) { }
        // if (obj instanceof Optional<Integer>) { }

        // 只能检查原始类型
        if (obj instanceof List) {
            System.out.println("这是一个List，但不知道元素类型");
        }

        if (obj instanceof Optional) {
            System.out.println("这是一个Optional，但不知道包装的类型");
        }

        // 通配符类型也不能检查
        // if (obj instanceof List<?>) { } // 编译错误
    }

    /**
     * 桥方法生成演示 - 任务1.1的重点
     * 反编译重点：继承时桥方法的自动生成
     */
    public static class BridgeMethodDemo extends BasicGenericsExploration<String> {

        public BridgeMethodDemo(String value) {
            super(value);
        }

        /**
         * 重写泛型方法 - 会生成桥方法
         * 反编译重点：编译器生成的synthetic bridge方法
         * <p>
         * 预期生成桥方法：
         * public Optional transform(Function mapper) {
         * return this.transform((Function<String, Object>) mapper);
         * }
         */
        @Override
        public <U> Optional<U> transform(Function<String, U> mapper) {
            System.out.println("子类重写的transform方法");
            return super.transform(mapper);
        }
    }

    /**
     * 类型签名信息提取演示
     * 反编译重点：Signature属性的结构和解析
     */
    public void demonstrateSignatureInformation() {
        System.out.println("=== 类型签名信息演示 ===");
        System.out.println("当前类的泛型签名包含：");
        System.out.println("- 类型参数: T extends Comparable<T>");
        System.out.println("- 字段签名: List<T> items");
        System.out.println("- 方法签名: <U> Optional<U> transform(Function<T,U>)");

        System.out.println("\n反编译时重点观察：");
        System.out.println("1. 类的Signature属性");
        System.out.println("2. 方法的Signature属性");
        System.out.println("3. 字段的Signature属性");
        System.out.println("4. LocalVariableTypeTable表");
    }

    // Getter方法 - 用于演示桥方法
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<T> getItems() {
        return new ArrayList<>(items);
    }

    public void addItem(T item) {
        items.add(item);
    }

    /**
     * 演示入口
     */
    public static void main(String[] args) {
        System.out.println("=== 基础泛型反编译实验 ===");
        System.out.println("这是深度研究文档中的立即启动实验");

        // 创建实例
        BasicGenericsExploration<String> stringExploration =
                new BasicGenericsExploration<>("Hello");

        // 演示transform方法
        Optional<Integer> length = stringExploration.transform(String::length);
        System.out.println("字符串长度: " + length.orElse(0));

        // 演示方法重载限制
        stringExploration.process(Arrays.asList("a", "b", "c"));
        stringExploration.processIntegers(Arrays.asList(1, 2, 3));

        // 演示原始类型兼容性
        stringExploration.demonstrateRawTypeCompatibility();

        // 演示泛型数组限制
        stringExploration.demonstrateGenericArrayLimitations();

        // 演示instanceof限制
        stringExploration.demonstrateInstanceofLimitations(Arrays.asList("test"));

        // 演示桥方法
        BridgeMethodDemo bridgeDemo = new BridgeMethodDemo("Bridge Test");
        Optional<String> result = bridgeDemo.transform(s -> s.toUpperCase());
        System.out.println("桥方法演示结果: " + result.orElse(""));

        // 演示签名信息
        stringExploration.demonstrateSignatureInformation();

        System.out.println("\n=== 反编译分析指令 ===");
        System.out.println("1. javac BasicGenericsExploration.java");
        System.out.println("2. javap -v -p BasicGenericsExploration.class | grep -A 10 Signature");
        System.out.println("3. javap -v -p BasicGenericsExploration\\$BridgeMethodDemo.class");
        System.out.println("4. 查找synthetic bridge方法");
        System.out.println("5. 对比方法描述符与Signature属性的差异");
    }
}