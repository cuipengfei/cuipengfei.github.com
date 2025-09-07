package com.example.interop;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 跨语言互操作测试 - Java端
 * 目标：展示JVM语言间的类型系统互操作性
 * <p>
 * 反编译分析重点：
 * 1. 观察Java调用Scala高阶函数时的桥方法
 * 2. 分析泛型类型在语言边界的传递方式
 * 3. 研究协变逆变在互操作中的表现
 */
public class JavaInteropDemo {

    /**
     * 调用Scala的协变容器
     * 反编译重点：观察协变类型的转换
     */
    public static void useScalaCovariantContainer() {
        // 这将调用Scala的协变容器
        // 编译后观察是否有类型转换和桥方法
        System.out.println("Java调用Scala协变容器");
    }

    /**
     * Java方法供Scala调用 - 演示桥方法生成
     * 反编译重点：实现泛型接口时的桥方法生成
     */
    public static class JavaGenericImpl<T> implements GenericInterface<T> {
        private T value;

        public JavaGenericImpl(T value) {
            this.value = value;
        }

        @Override
        public T process(T input) {
            // 这里会生成桥方法：public Object process(Object input)
            System.out.println("Java处理: " + input);
            return input;
        }

        @Override
        public List<T> batch(List<? extends T> inputs) {
            // 协变参数在桥方法中的处理
            return inputs.stream().map(this::process).toList();
        }
    }

    /**
     * 泛型接口 - 用于观察桥方法
     */
    public interface GenericInterface<T> {
        T process(T input);

        List<T> batch(List<? extends T> inputs);
    }

    /**
     * 协变函数接口实现
     * 反编译重点：函数类型的协变逆变处理
     */
    public static class CovariantFunction<T, R> implements Function<T, R> {
        private final Function<T, R> delegate;

        public CovariantFunction(Function<T, R> delegate) {
            this.delegate = delegate;
        }

        @Override
        public R apply(T input) {
            // 观察函数类型在字节码中的表示
            return delegate.apply(input);
        }
    }

    /**
     * 复杂泛型互操作
     * 反编译重点：嵌套泛型在语言边界的处理
     */
    public static <T extends Number & Comparable<T>>
    Optional<List<T>> complexGenericMethod(List<Optional<T>> inputs) {
        // 多重边界 + 嵌套泛型的字节码表示
        return inputs.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toList(),
                        list -> list.isEmpty() ? Optional.empty() : Optional.of(list)
                ));
    }

    /**
     * 类型擦除边界测试
     * 反编译重点：类型擦除导致的方法冲突
     */
    public static class TypeErasureBoundaryTest {

        // 方法重载冲突演示
        public static void processData(List<String> strings) {
            System.out.println("处理字符串列表: " + strings.size());
        }

        // 注意：这个方法如果取消注释会导致编译错误
        // public static void processData(List<Integer> integers) {
        //     System.out.println("处理整数列表: " + integers.size());
        // }

        // 解决方案：不同方法名
        public static void processStringData(List<String> strings) {
            System.out.println("处理字符串数据: " + strings.size());
        }

        public static void processIntegerData(List<Integer> integers) {
            System.out.println("处理整数数据: " + integers.size());
        }

        /**
         * 泛型数组问题演示
         * 反编译重点：泛型数组限制的字节码原因
         */
        public static <T> void demonstrateGenericArrayProblem() {
            // 编译错误：Cannot create a generic array of T
            // T[] array = new T[10];

            // 运行时类型不安全的"解决方案"
            @SuppressWarnings("unchecked")
            T[] unsafeArray = (T[]) new Object[10];

            // 类型安全的替代方案
            List<T> safeList = new java.util.ArrayList<>();

            System.out.println("泛型数组问题演示完成");
        }
    }

    /**
     * 运行时类型检查策略
     * 反编译重点：类型token模式的实现
     */
    public static abstract class TypeToken<T> {
        private final java.lang.reflect.Type type;

        protected TypeToken() {
            java.lang.reflect.Type superClass = getClass().getGenericSuperclass();
            if (superClass instanceof java.lang.reflect.ParameterizedType) {
                this.type = ((java.lang.reflect.ParameterizedType) superClass)
                        .getActualTypeArguments()[0];
            } else {
                throw new RuntimeException("Missing type parameter.");
            }
        }

        public java.lang.reflect.Type getType() {
            return type;
        }

        public Class<T> getRawType() {
            @SuppressWarnings("unchecked")
            Class<T> clazz = (Class<T>) ((java.lang.reflect.ParameterizedType) type).getRawType();
            return clazz;
        }

        /**
         * 类型安全的向下转型
         */
        public boolean isInstance(Object obj) {
            return getRawType().isInstance(obj);
        }

        @SuppressWarnings("unchecked")
        public T cast(Object obj) {
            if (isInstance(obj)) {
                return (T) obj;
            }
            throw new ClassCastException("Cannot cast " + obj.getClass() + " to " + type);
        }
    }

    /**
     * 演示方法
     */
    public static void main(String[] args) {
        System.out.println("=== Java跨语言互操作演示 ===");

        // 1. 桥方法演示
        JavaGenericImpl<String> impl = new JavaGenericImpl<>("test");
        String result = impl.process("input");
        System.out.println("泛型实现结果: " + result);

        // 2. 复杂泛型方法
        List<Optional<Double>> inputs = List.of(
                Optional.of(1.5), Optional.empty(), Optional.of(2.5)
        );
        Optional<List<Double>> output = complexGenericMethod(inputs);
        System.out.println("复杂泛型方法结果: " + output);

        // 3. 类型擦除边界测试
        TypeErasureBoundaryTest.processStringData(List.of("a", "b", "c"));
        TypeErasureBoundaryTest.processIntegerData(List.of(1, 2, 3));
        TypeErasureBoundaryTest.demonstrateGenericArrayProblem();

        // 4. TypeToken演示
        TypeToken<List<String>> token = new TypeToken<List<String>>() {
        };
        System.out.println("TypeToken类型: " + token.getType());

        // 5. 协变函数演示
        CovariantFunction<String, Integer> func =
                new CovariantFunction<>(String::length);
        Integer length = func.apply("Hello");
        System.out.println("协变函数结果: " + length);
    }
}