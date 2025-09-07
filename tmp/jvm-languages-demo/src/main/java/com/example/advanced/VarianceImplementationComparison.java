package com.example.advanced;

import java.util.*;
import java.util.function.*;

/**
 * Java通配符 vs Scala变性的字节码对比分析 - 任务2.1重点
 * 使用处变性 vs 声明处变性的实现机制深度分析
 * 
 * 反编译分析重点：
 * 1. 通配符在字节码中的表示
 * 2. 变性注解如何影响类型检查
 * 3. 各语言变性规则的实现算法
 * 4. 桥方法在变性继承中的作用
 * 
 * 分析指令：
 * javac VarianceImplementationComparison.java
 * javap -v -p VarianceImplementationComparison.class
 * javap -v -p VarianceImplementationComparison\$*.class
 */
public class VarianceImplementationComparison {
    
    /**
     * 1. Java使用处变性演示
     * 反编译重点：通配符类型在Signature属性中的表示
     */
    public static class JavaWildcardVariance {
        
        /**
         * 协变通配符 - ? extends T
         * 反编译重点：extends通配符的字节码表示
         */
        public static void demonstrateCovariance() {
            // 协变：可以读取，不能写入（除了null）
            List<? extends Number> numbers = new ArrayList<Integer>();
            
            // 合法：读取操作
            Number first = numbers.isEmpty() ? null : numbers.get(0);
            
            // 编译错误：写入操作（除了null）
            // numbers.add(42);           // 不能添加Integer
            // numbers.add(3.14);         // 不能添加Double
            numbers.add(null);            // 只能添加null
            
            System.out.println("协变通配符演示: " + first);
        }
        
        /**
         * 逆变通配符 - ? super T
         * 反编译重点：super通配符的字节码表示
         */
        public static void demonstrateContravariance() {
            // 逆变：可以写入，不能安全读取（只能读取Object）
            List<? super Integer> numbers = new ArrayList<Number>();
            
            // 合法：写入操作
            numbers.add(42);              // 可以添加Integer
            numbers.add(Integer.valueOf(100)); // 可以添加Integer子类型
            
            // 读取限制：只能读取为Object
            Object first = numbers.get(0);    // 只能作为Object读取
            // Integer typed = numbers.get(0); // 编译错误：不能安全转换
            
            System.out.println("逆变通配符演示: " + first);
        }
        
        /**
         * 无界通配符 - ?
         * 反编译重点：无界通配符的特殊处理
         */
        public static void demonstrateUnboundedWildcard() {
            List<?> unknownList = Arrays.asList(1, 2, 3);
            
            // 只能读取为Object，不能写入（Arrays.asList返回不可变列表）
            Object element = unknownList.get(0);
            // unknownList.add(null);  // Arrays.asList返回的列表不支持修改
            
            // 使用可变列表演示
            List<?> mutableUnknownList = new ArrayList<>(Arrays.asList(1, 2, 3));
            mutableUnknownList.add(null);        // 只能添加null
            // mutableUnknownList.add("test");   // 编译错误
            
            System.out.println("无界通配符演示: " + element);
        }
        
        /**
         * 复杂嵌套通配符
         * 反编译重点：嵌套通配符的Signature复杂性
         */
        public static void demonstrateNestedWildcards() {
            // 嵌套协变
            List<? extends List<? extends Number>> nestedCovariant = 
                Arrays.asList(Arrays.asList(1, 2, 3));
            
            // 混合变性
            Map<String, ? extends List<? super Integer>> complexVariance = 
                new HashMap<>();
            
            System.out.println("嵌套通配符演示完成");
        }
        
        /**
         * PECS原则演示 - Producer Extends, Consumer Super
         * 反编译重点：不同变性模式的使用场景
         */
        public static class PECSDemo {
            
            /**
             * Producer使用extends - 生产者协变
             * 反编译重点：生产者模式的方法签名
             */
            public static <T> void copyFrom(List<? extends T> source, List<T> dest) {
                for (T item : source) {    // 可以安全读取
                    dest.add(item);        // 写入到具体类型
                }
            }
            
            /**
             * Consumer使用super - 消费者逆变  
             * 反编译重点：消费者模式的方法签名
             */
            public static <T> void copyTo(List<T> source, List<? super T> dest) {
                for (T item : source) {    // 从具体类型读取
                    dest.add(item);        // 可以安全写入
                }
            }
            
            /**
             * PECS组合使用
             * 反编译重点：复杂变性约束的编译检查
             */
            public static <T> void transfer(List<? extends T> source, List<? super T> dest) {
                for (T item : source) {
                    dest.add(item);
                }
            }
        }
    }
    
    /**
     * 2. 函数接口的变性演示
     * 反编译重点：函数类型的协变逆变规则
     */
    public static class FunctionVarianceDemo {
        
        /**
         * Function<T, R>的变性分析
         * 反编译重点：函数类型在继承中的变性行为
         */
        public static void demonstrateFunctionVariance() {
            // Java不支持函数类型的直接变性赋值，这正是设计限制的体现
            Function<Number, Integer> numToInt = Number::intValue;
            
            // 以下赋值在Java中是不合法的，但在理论上应该是安全的
            // Function<Object, Number> objToNum = numToInt;  // 编译错误
            
            // 必须通过显式转换或方法引用来实现
            Function<Object, Number> objToNum = obj -> numToInt.apply((Number) obj);
            
            System.out.println("函数变性演示（通过转换）: " + objToNum.apply(42.5));
            
            // 这展示了Java使用处变性的限制
            System.out.println("注意：Java不支持函数类型的声明处变性");
        }
        
        /**
         * Consumer的逆变特性
         * 反编译重点：消费者函数的变性规则
         */
        public static void demonstrateConsumerContravariance() {
            Consumer<Number> numberConsumer = num -> System.out.println("Number: " + num);
            
            // Java不允许逆变赋值，这是类型系统的限制
            // Consumer<Integer> integerConsumer = numberConsumer;  // 编译错误
            
            // 必须通过显式方式实现逆变行为
            Consumer<Integer> integerConsumer = number -> numberConsumer.accept(number);
            
            integerConsumer.accept(42);
            System.out.println("Consumer逆变演示（通过包装实现）");
        }
        
        /**
         * Supplier的协变特性
         * 反编译重点：生产者函数的变性规则
         */
        public static void demonstrateSupplierCovariance() {
            Supplier<Integer> intSupplier = () -> 42;
            
            // Java不允许协变赋值，即使理论上是安全的
            // Supplier<Number> numberSupplier = intSupplier;  // 编译错误
            
            // 通过方法引用或lambda实现协变行为
            Supplier<Number> numberSupplier = intSupplier::get;
            
            Number result = numberSupplier.get();
            System.out.println("Supplier协变演示（通过方法引用）: " + result);
        }
        
        /**
         * 复杂函数组合的变性
         * 反编译重点：高阶函数的变性传播
         */
        public static void demonstrateComplexFunctionVariance() {
            // 函数组合中的变性传播
            Function<String, Integer> strToInt = String::length;
            Function<Integer, Double> intToDouble = Integer::doubleValue;
            Function<String, Double> composed = strToInt.andThen(intToDouble);
            
            System.out.println("函数组合变性: " + composed.apply("Hello"));
        }
    }
    
    /**
     * 3. 继承层次中的变性传播
     * 反编译重点：继承如何影响变性约束
     */
    public static class InheritanceVarianceDemo {
        
        /**
         * 基础容器接口
         * 反编译重点：接口声明的字节码结构
         */
        interface Container<T> {
            T get();
            void set(T value);
        }
        
        /**
         * 只读容器 - 协变候选
         * 反编译重点：只读接口的变性可能性
         */
        interface ReadOnlyContainer<T> {
            T get();
            // 注意：Java无法声明协变，但概念上应该是协变的
        }
        
        /**
         * 只写容器 - 逆变候选  
         * 反编译重点：只写接口的变性可能性
         */
        interface WriteOnlyContainer<T> {
            void set(T value);
            // 注意：Java无法声明逆变，但概念上应该是逆变的
        }
        
        /**
         * 具体实现类
         * 反编译重点：实现类的泛型继承
         */
        static class SimpleContainer<T> implements Container<T> {
            private T value;
            
            @Override
            public T get() { return value; }
            
            @Override
            public void set(T value) { this.value = value; }
            
            /**
             * 协变返回类型演示
             * 反编译重点：返回类型的协变重写
             */
            public T getValue() { return value; }
        }
        
        /**
         * 数字容器 - 特化版本
         * 反编译重点：泛型特化的字节码表示
         */
        static class NumberContainer extends SimpleContainer<Number> {
            
            /**
             * 协变返回类型重写
             * 反编译重点：桥方法的生成
             */
            @Override
            public Number getValue() {
                System.out.println("NumberContainer.getValue调用");
                return super.getValue();
            }
            
            /**
             * 添加特化方法
             * 反编译重点：方法重载与泛型的交互
             */
            public void addNumber(Number num) {
                set(num);
            }
        }
        
        /**
         * 演示继承变性的使用
         */
        public static void demonstrateInheritanceVariance() {
            NumberContainer nc = new NumberContainer();
            nc.set(42);
            
            // 使用处变性
            Container<? extends Number> covariantContainer = nc;
            Number value = covariantContainer.get();    // 可以读取
            // covariantContainer.set(3.14);            // 不能写入
            
            Container<? super Integer> contravariantContainer = nc;
            contravariantContainer.set(100);            // 可以写入Integer
            Object obj = contravariantContainer.get();  // 只能读取为Object
            
            System.out.println("继承变性演示: " + value);
        }
    }
    
    /**
     * 4. 数组的协变性 - Java特殊情况
     * 反编译重点：数组协变性的运行时检查
     */
    public static class ArrayCovarianceDemo {
        
        /**
         * 数组的协变特性
         * 反编译重点：数组存储的运行时类型检查
         */
        public static void demonstrateArrayCovariance() {
            // Java数组是协变的（与泛型不同）
            Number[] numbers = new Integer[10];
            numbers[0] = 42;                    // 合法：Integer是Number的子类型
            
            try {
                numbers[1] = 3.14;              // 运行时异常：ArrayStoreException
            } catch (ArrayStoreException e) {
                System.out.println("数组协变运行时检查: " + e.getMessage());
            }
        }
        
        /**
         * 数组vs泛型的变性对比
         * 反编译重点：编译时vs运行时的类型安全
         */
        public static void compareArrayAndGenericVariance() {
            // 数组：协变，运行时检查
            Number[] numberArray = new Integer[5];
            
            // 泛型：不变，编译时检查
            // List<Number> numberList = new ArrayList<Integer>(); // 编译错误
            List<Number> numberList = new ArrayList<>();
            
            // 通配符提供使用处变性
            List<? extends Number> covariantList = new ArrayList<Integer>();
            
            System.out.println("数组vs泛型变性对比完成");
        }
    }
    
    /**
     * 5. 反射中的变性信息
     * 反编译重点：泛型信息在反射API中的表示
     */
    public static class ReflectionVarianceDemo {
        
        /**
         * 获取通配符类型信息
         * 反编译重点：WildcardType的运行时表示
         */
        public static void examineWildcardTypes() {
            try {
                // 获取方法参数的泛型类型信息
                java.lang.reflect.Method method = ReflectionVarianceDemo.class
                    .getMethod("wildcardMethod", List.class, List.class);
                
                java.lang.reflect.Type[] paramTypes = method.getGenericParameterTypes();
                
                for (int i = 0; i < paramTypes.length; i++) {
                    System.out.println("参数 " + i + " 类型: " + paramTypes[i]);
                    
                    if (paramTypes[i] instanceof java.lang.reflect.ParameterizedType) {
                        java.lang.reflect.ParameterizedType pt = 
                            (java.lang.reflect.ParameterizedType) paramTypes[i];
                        java.lang.reflect.Type[] typeArgs = pt.getActualTypeArguments();
                        
                        for (java.lang.reflect.Type typeArg : typeArgs) {
                            if (typeArg instanceof java.lang.reflect.WildcardType) {
                                java.lang.reflect.WildcardType wt = 
                                    (java.lang.reflect.WildcardType) typeArg;
                                System.out.println("  通配符上界: " + 
                                    Arrays.toString(wt.getUpperBounds()));
                                System.out.println("  通配符下界: " + 
                                    Arrays.toString(wt.getLowerBounds()));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        /**
         * 用于反射分析的方法
         * 反编译重点：复杂泛型签名的Signature属性
         */
        public void wildcardMethod(List<? extends Number> covariant, 
                                 List<? super Integer> contravariant) {
            // 方法体用于反射分析
        }
    }
    
    /**
     * 演示入口
     */
    public static void main(String[] args) {
        System.out.println("=== Java通配符变性实现分析 ===");
        
        // 1. 基础通配符演示
        System.out.println("\n1. 基础通配符变性:");
        JavaWildcardVariance.demonstrateCovariance();
        JavaWildcardVariance.demonstrateContravariance();
        JavaWildcardVariance.demonstrateUnboundedWildcard();
        
        // 2. PECS原则演示
        System.out.println("\n2. PECS原则:");
        List<Integer> source = Arrays.asList(1, 2, 3);
        List<Number> dest = new ArrayList<>();
        JavaWildcardVariance.PECSDemo.copyFrom(source, dest);
        System.out.println("PECS复制结果: " + dest);
        
        // 3. 函数变性演示
        System.out.println("\n3. 函数变性:");
        FunctionVarianceDemo.demonstrateFunctionVariance();
        FunctionVarianceDemo.demonstrateConsumerContravariance();
        FunctionVarianceDemo.demonstrateSupplierCovariance();
        
        // 4. 继承变性演示
        System.out.println("\n4. 继承变性:");
        InheritanceVarianceDemo.demonstrateInheritanceVariance();
        
        // 5. 数组协变演示
        System.out.println("\n5. 数组协变:");
        ArrayCovarianceDemo.demonstrateArrayCovariance();
        ArrayCovarianceDemo.compareArrayAndGenericVariance();
        
        // 6. 反射变性信息
        System.out.println("\n6. 反射变性信息:");
        ReflectionVarianceDemo demo = new ReflectionVarianceDemo();
        demo.examineWildcardTypes();
        
        System.out.println("\n=== 反编译分析重点 ===");
        System.out.println("1. 查看Signature属性中的通配符表示");
        System.out.println("2. 观察桥方法在变性继承中的生成");
        System.out.println("3. 分析运行时类型检查的字节码实现");
        System.out.println("4. 比较数组协变与泛型变性的不同机制");
        System.out.println("5. 研究WildcardType的反射实现");
    }
}