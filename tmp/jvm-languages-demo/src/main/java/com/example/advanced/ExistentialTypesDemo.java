package com.example.advanced;

import java.util.*;
import java.util.function.*;
import java.lang.reflect.*;

/**
 * 存在类型和通配符高级用法演示 - 任务2.3的深度扩展
 * Java通配符作为存在类型的实现
 * 
 * 反编译分析重点：
 * 1. 复杂通配符的Signature表示
 * 2. 存在类型的约束传播
 * 3. 通配符捕获机制
 * 4. 嵌套存在类型的处理
 * 
 * 编译分析指令：
 * javac ExistentialTypesDemo.java
 * javap -v -p ExistentialTypesDemo.class
 * javap -v -p 'ExistentialTypesDemo$*.class'
 */
public class ExistentialTypesDemo {
    
    /**
     * 1. 基础存在类型 - 通配符的存在性
     * 反编译重点：? 通配符在字节码中的存在类型表示
     */
    public static class BasicExistentialTypes {
        
        /**
         * 无界存在类型 - List<?> 
         * 反编译重点：无界通配符的Signature属性
         */
        public static void processUnknownList(List<?> unknownList) {
            // 只能进行类型安全的操作
            System.out.println("List size: " + unknownList.size());
            System.out.println("Is empty: " + unknownList.isEmpty());
            
            // 可以获取元素但只能作为Object处理
            if (!unknownList.isEmpty()) {
                Object first = unknownList.get(0);
                System.out.println("First element: " + first);
                System.out.println("Element class: " + first.getClass());
            }
            
            // 不能添加除null之外的任何元素
            // unknownList.add("string");  // 编译错误
            unknownList.add(null);  // 可以添加null
        }
        
        /**
         * 有界存在类型 - List<? extends Number>
         * 反编译重点：有界通配符的约束表示
         */
        public static double sumNumbers(List<? extends Number> numbers) {
            double sum = 0.0;
            for (Number num : numbers) {  // 可以安全读取为Number
                if (num != null) {
                    sum += num.doubleValue();
                }
            }
            return sum;
        }
        
        /**
         * 逆变存在类型 - List<? super Integer>
         * 反编译重点：super通配符的下界约束
         */
        public static void addIntegers(List<? super Integer> list, Integer... values) {
            for (Integer value : values) {
                list.add(value);  // 可以安全写入Integer
            }
            // Object obj = list.get(0);  // 只能读取为Object
        }
        
        /**
         * 存在类型的传播
         * 反编译重点：存在类型在方法调用中的传播
         */
        public static List<?> createUnknownList() {
            // 返回存在类型
            return Arrays.asList("hello", 42, 3.14);
        }
        
        /**
         * 存在类型的组合使用
         */
        public static void demonstrateBasicExistentials() {
            System.out.println("=== 基础存在类型演示 ===");
            
            // 处理未知类型列表
            List<?> mixed = createUnknownList();
            processUnknownList(mixed);
            
            // 处理数字列表（协变）
            List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
            List<Double> doubles = Arrays.asList(1.1, 2.2, 3.3);
            
            System.out.println("Integer sum: " + sumNumbers(integers));
            System.out.println("Double sum: " + sumNumbers(doubles));
            
            // 处理超类型列表（逆变）
            List<Number> numberList = new ArrayList<>();
            addIntegers(numberList, 10, 20, 30);
            System.out.println("Added integers: " + numberList);
        }
    }
    
    /**
     * 2. 嵌套存在类型 - 多层通配符嵌套
     * 反编译重点：复杂嵌套结构的Signature复杂性
     */
    public static class NestedExistentialTypes {
        
        /**
         * 二层嵌套存在类型
         * 反编译重点：List<List<?>>的存在类型语义
         */
        public static void processNestedUnknown(List<List<?>> nestedList) {
            System.out.println("Processing nested unknown types");
            for (List<?> innerList : nestedList) {
                System.out.println("  Inner list size: " + innerList.size());
                BasicExistentialTypes.processUnknownList(innerList);  // 递归处理
            }
        }
        
        /**
         * 混合协变嵌套
         * 反编译重点：List<? extends List<? extends Number>>的复杂度
         */
        public static double sumNestedNumbers(List<? extends List<? extends Number>> nestedNumbers) {
            double total = 0.0;
            for (List<? extends Number> numberList : nestedNumbers) {
                total += BasicExistentialTypes.sumNumbers(numberList);
            }
            return total;
        }
        
        /**
         * 逆变嵌套类型
         * 反编译重点：List<? super List<? super Integer>>的语义
         */
        public static void addNestedIntegers(
            List<? super List<? super Integer>> target, 
            List<? super Integer> source
        ) {
            target.add(source);
        }
        
        /**
         * 极端复杂的嵌套存在类型
         * 反编译重点：多层通配符的编译器处理极限
         */
        public static void extremeNesting(
            Map<String, ? extends List<? extends Set<? extends Number>>> extremeType
        ) {
            System.out.println("Processing extreme nesting");
            for (Map.Entry<String, ? extends List<? extends Set<? extends Number>>> entry : extremeType.entrySet()) {
                String key = entry.getKey();
                List<? extends Set<? extends Number>> value = entry.getValue();
                
                System.out.println("Key: " + key + ", Sets count: " + value.size());
                for (Set<? extends Number> numberSet : value) {
                    System.out.println("  Set size: " + numberSet.size());
                }
            }
        }
        
        /**
         * 嵌套存在类型演示
         */
        public static void demonstrateNestedExistentials() {
            System.out.println("=== 嵌套存在类型演示 ===");
            
            // 二层嵌套
            List<List<?>> nested = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList("a", "b", "c"),
                Arrays.asList(true, false)
            );
            processNestedUnknown(nested);
            
            // 数字嵌套求和
            List<List<Integer>> intLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6)
            );
            double sum = sumNestedNumbers(intLists);
            System.out.println("Nested sum: " + sum);
            
            // 极端嵌套
            Map<String, List<Set<Integer>>> extreme = new HashMap<>();
            extreme.put("group1", Arrays.asList(
                new HashSet<>(Arrays.asList(1, 2, 3)),
                new HashSet<>(Arrays.asList(4, 5, 6))
            ));
            extremeNesting(extreme);
        }
    }
    
    /**
     * 3. 通配符捕获 - Wildcard Capture
     * 反编译重点：编译器的通配符捕获机制
     */
    public static class WildcardCapture {
        
        /**
         * 简单通配符捕获
         * 反编译重点：捕获助手方法的生成
         */
        public static void swapFirst(List<?> list) {
            if (list.size() >= 2) {
                swapFirstHelper(list);
            }
        }
        
        /**
         * 捕获助手方法 - 编译器生成的类型捕获点
         * 反编译重点：类型变量的引入机制
         */
        private static <T> void swapFirstHelper(List<T> list) {
            T first = list.get(0);
            T second = list.get(1);
            list.set(0, second);
            list.set(1, first);
        }
        
        /**
         * 复杂通配符捕获
         * 反编译重点：多个通配符的同时捕获
         */
        public static void transferElements(List<?> source, List<?> target) {
            // 编译器无法直接处理，需要使用捕获
            // transfer(source, target);  // 编译错误
            transferWithCapture(source, target);
        }
        
        /**
         * 通配符捕获的助手方法
         */
        @SuppressWarnings("unchecked")
        private static void transferWithCapture(List<?> source, List<?> target) {
            if (!source.isEmpty() && source.get(0) != null) {
                // 通过Object类型进行不安全的转换
                Object element = source.remove(0);
                try {
                    ((List<Object>) target).add(element);
                    System.out.println("Transferred element: " + element);
                } catch (ClassCastException e) {
                    System.err.println("Type mismatch during transfer: " + e.getMessage());
                }
            }
        }
        
        /**
         * 函数式接口中的通配符捕获
         * 反编译重点：lambda表达式与通配符捕获的交互
         */
        public static void processWithFunction(List<?> list, Function<Object, String> processor) {
            // 无法直接调用processor.apply，需要捕获
            processWithFunctionHelper(list, processor);
        }
        
        @SuppressWarnings("unchecked")
        private static void processWithFunctionHelper(List<?> list, Function<Object, String> processor) {
            for (Object element : list) {
                String result = processor.apply(element);
                System.out.println("Processed: " + result);
            }
        }
        
        /**
         * 通配符捕获演示
         */
        public static void demonstrateWildcardCapture() {
            System.out.println("=== 通配符捕获演示 ===");
            
            // 交换演示
            List<String> strings = new ArrayList<>(Arrays.asList("first", "second", "third"));
            System.out.println("Before swap: " + strings);
            swapFirst(strings);
            System.out.println("After swap: " + strings);
            
            // 转移演示
            List<String> source = new ArrayList<>(Arrays.asList("a", "b", "c"));
            List<String> target = new ArrayList<>();
            System.out.println("Before transfer - Source: " + source + ", Target: " + target);
            transferElements(source, target);
            System.out.println("After transfer - Source: " + source + ", Target: " + target);
            
            // 函数处理演示
            List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
            Function<Object, String> objToString = obj -> "Number: " + obj;
            processWithFunction(numbers, objToString);
        }
    }
    
    /**
     * 4. 反射中的存在类型信息
     * 反编译重点：WildcardType反射API的实现
     */
    public static class ExistentialReflection {
        
        /**
         * 用于反射分析的方法 - 包含各种通配符类型
         */
        public void methodWithWildcards(
            List<? extends Number> covariant,
            List<? super Integer> contravariant,
            Map<String, ?> unbounded,
            Set<? extends Comparable<? extends Number>> nested
        ) {
            // 方法体用于反射分析
        }
        
        /**
         * 分析方法参数中的存在类型信息
         * 反编译重点：反射API对WildcardType的处理
         */
        public static void analyzeWildcardTypes() {
            System.out.println("=== 存在类型反射分析 ===");
            
            try {
                Method method = ExistentialReflection.class.getDeclaredMethod(
                    "methodWithWildcards", 
                    List.class, List.class, Map.class, Set.class
                );
                
                Type[] paramTypes = method.getGenericParameterTypes();
                String[] paramNames = {"covariant", "contravariant", "unbounded", "nested"};
                
                for (int i = 0; i < paramTypes.length; i++) {
                    System.out.println("\n参数 " + paramNames[i] + ":");
                    analyzeType(paramTypes[i], 1);
                }
                
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        
        /**
         * 递归分析类型结构
         */
        private static void analyzeType(Type type, int depth) {
            String indent = "  ".repeat(depth);
            
            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) type;
                System.out.println(indent + "ParameterizedType: " + pType.getRawType());
                
                Type[] typeArgs = pType.getActualTypeArguments();
                for (int i = 0; i < typeArgs.length; i++) {
                    System.out.println(indent + "TypeArg[" + i + "]:");
                    analyzeType(typeArgs[i], depth + 1);
                }
                
            } else if (type instanceof WildcardType) {
                WildcardType wType = (WildcardType) type;
                System.out.println(indent + "WildcardType:");
                
                Type[] upperBounds = wType.getUpperBounds();
                Type[] lowerBounds = wType.getLowerBounds();
                
                System.out.println(indent + "  UpperBounds: " + Arrays.toString(upperBounds));
                System.out.println(indent + "  LowerBounds: " + Arrays.toString(lowerBounds));
                
                // 分析边界类型
                for (Type bound : upperBounds) {
                    if (bound != Object.class) {
                        System.out.println(indent + "  Upper bound detail:");
                        analyzeType(bound, depth + 2);
                    }
                }
                
                for (Type bound : lowerBounds) {
                    System.out.println(indent + "  Lower bound detail:");
                    analyzeType(bound, depth + 2);
                }
                
            } else if (type instanceof Class<?>) {
                System.out.println(indent + "Class: " + ((Class<?>) type).getName());
                
            } else {
                System.out.println(indent + "Unknown type: " + type.getClass().getSimpleName() + " - " + type);
            }
        }
        
        /**
         * 创建复杂的存在类型实例用于分析
         */
        public static void createComplexExistentialTypes() {
            System.out.println("=== 复杂存在类型创建 ===");
            
            // 运行时构造复杂的存在类型
            List<Integer> numbers = Arrays.asList(1, 2, 3);
            Map<String, List<Integer>> complex = new HashMap<>();
            complex.put("numbers", numbers);
            
            System.out.println("Created complex existential type");
            System.out.println("Type erasure means runtime type is: " + complex.getClass());
            
            // 通过反射获取字段的泛型类型
            try {
                Field[] fields = ExistentialReflection.class.getDeclaredFields();
                for (Field field : fields) {
                    System.out.println("Field: " + field.getName() + ", Type: " + field.getGenericType());
                }
            } catch (Exception e) {
                System.err.println("反射分析失败: " + e.getMessage());
            }
        }
    }
    
    /**
     * 5. 存在类型的实际应用场景
     * 反编译重点：存在类型在实际编程中的使用模式
     */
    public static class PracticalExistentialUsage {
        
        /**
         * 异构集合的处理
         * 反编译重点：存在类型在异构数据处理中的应用
         */
        public static void processHeterogeneousData(List<? extends Object> data) {
            System.out.println("=== 异构数据处理 ===");
            
            for (Object item : data) {
                if (item instanceof String) {
                    System.out.println("String: " + item);
                } else if (item instanceof Number) {
                    System.out.println("Number: " + item + " (double value: " + ((Number) item).doubleValue() + ")");
                } else if (item instanceof Boolean) {
                    System.out.println("Boolean: " + item);
                } else {
                    System.out.println("Unknown type: " + item.getClass().getSimpleName() + " - " + item);
                }
            }
        }
        
        /**
         * 回调函数的存在类型参数
         */
        public interface GenericCallback<T> {
            void onSuccess(T result);
            void onError(Exception error);
        }
        
        /**
         * 使用存在类型的异步处理
         * 反编译重点：回调中的存在类型约束
         */
        public static void processAsync(Object data, GenericCallback<?> callback) {
            try {
                // 模拟异步处理
                Thread.sleep(100);
                
                // 类型擦除导致无法安全调用callback.onSuccess(data)
                // 需要使用原始类型或Object
                @SuppressWarnings("unchecked")
                GenericCallback<Object> rawCallback = (GenericCallback<Object>) callback;
                rawCallback.onSuccess(data);
                
            } catch (Exception e) {
                callback.onError(e);
            }
        }
        
        /**
         * 工厂方法中的存在类型
         */
        public static List<?> createListOfUnknownType(String typeHint, Object... elements) {
            switch (typeHint.toLowerCase()) {
                case "string":
                    List<String> stringList = new ArrayList<>();
                    for (Object elem : elements) {
                        stringList.add(elem.toString());
                    }
                    return stringList;
                    
                case "number":
                    List<Number> numberList = new ArrayList<>();
                    for (Object elem : elements) {
                        if (elem instanceof Number) {
                            numberList.add((Number) elem);
                        }
                    }
                    return numberList;
                    
                default:
                    return Arrays.asList(elements);
            }
        }
        
        /**
         * 实际应用演示
         */
        public static void demonstratePracticalUsage() {
            System.out.println("=== 存在类型实际应用 ===");
            
            // 异构数据处理
            List<Object> mixedData = Arrays.asList("Hello", 42, 3.14, true, new Date());
            processHeterogeneousData(mixedData);
            
            // 异步回调
            GenericCallback<String> stringCallback = new GenericCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println("Async success: " + result);
                }
                
                @Override
                public void onError(Exception error) {
                    System.err.println("Async error: " + error.getMessage());
                }
            };
            
            processAsync("测试数据", stringCallback);
            
            // 动态工厂
            List<?> stringList = createListOfUnknownType("string", 1, 2, 3);
            List<?> numberList = createListOfUnknownType("number", 1, 2.5, 3);
            
            System.out.println("String list: " + stringList);
            System.out.println("Number list: " + numberList);
        }
    }
    
    /**
     * 演示入口
     */
    public static void main(String[] args) {
        System.out.println("=== 存在类型和通配符高级用法演示 ===");
        System.out.println("Java通配符作为存在类型的实现机制分析\n");
        
        // 基础存在类型
        BasicExistentialTypes.demonstrateBasicExistentials();
        System.out.println();
        
        // 嵌套存在类型
        NestedExistentialTypes.demonstrateNestedExistentials();
        System.out.println();
        
        // 通配符捕获
        WildcardCapture.demonstrateWildcardCapture();
        System.out.println();
        
        // 反射分析
        ExistentialReflection.analyzeWildcardTypes();
        ExistentialReflection.createComplexExistentialTypes();
        System.out.println();
        
        // 实际应用
        PracticalExistentialUsage.demonstratePracticalUsage();
        
        System.out.println("\n=== 反编译分析重点 ===");
        System.out.println("1. 通配符在Signature属性中的存在量化表示");
        System.out.println("2. 通配符捕获助手方法的编译器生成");
        System.out.println("3. 嵌套存在类型的复杂度指数增长");
        System.out.println("4. WildcardType反射API的内部实现");
        System.out.println("5. 存在类型约束的编译时检查 vs 运行时擦除");
        System.out.println("6. 类型安全性与灵活性的权衡体现");
    }
}