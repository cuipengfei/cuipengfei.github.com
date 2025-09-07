package com.example.advanced;

import java.util.*;

/**
 * Java高级泛型演示 - 类型擦除边界案例
 */
public class AdvancedGenericsDemo {

    /**
     * 任务3.1：类型擦除的边界案例
     * 演示类型擦除导致的各种问题
     */

    // 1. 方法重载冲突 - 编译错误示例
    static class OverloadConflict {
        // 这会导致编译错误：'process(List<String>)' clashes with 'process(List<Integer>)'
        // public void process(List<String> strings) {}
        // public void process(List<Integer> integers) {}

        // 解决方案：使用不同的方法名
        public void processStrings(List<String> strings) {
        }

        public void processIntegers(List<Integer> integers) {
        }
    }

    // 2. 泛型数组创建限制
    static class GenericArrayProblem {
        // 编译错误：Cannot create a generic array of T
        // public <T> T[] createGenericArray() {
        //     return new T[10];
        // }

        // 解决方案1：使用Array.newInstance
        public <T> T[] createArrayWithReflection(Class<T> clazz, int size) {
            @SuppressWarnings("unchecked")
            T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
            return array;
        }

        // 解决方案2：使用List作为替代
        public <T> List<T> createListAlternative() {
            return new ArrayList<>();
        }
    }

    // 3. instanceof检查的失效
    static class InstanceofProblem {
        public void demonstrateInstanceof(List<?> list) {
            // 编译错误：Cannot perform instanceof check against parameterized type List<String>
            // if (list instanceof List<String>) { }

            // 只能检查原始类型
            if (list instanceof List) {
                System.out.println("This is a List, but type parameter is erased");
            }
        }
    }

    // 4. 泛型异常限制
    static class GenericException {
        // 编译错误：Generic class may not extend java.lang.Throwable
        // static class MyGenericException<T> extends Exception {}
    }

    /**
     * 任务1.2：高级泛型特性
     * 演示Java的泛型边界和通配符使用
     */

    // 1. 多重边界
    static class MultipleBounds {
        public static <T extends Number & Comparable<T>> void processBoundedValue(T value) {
            System.out.println("Value: " + value + ", can compare and perform numeric operations");
        }
    }

    // 2. 通配符捕获
    static class WildcardCapture {
        public void swapFirst(List<?> list1, List<?> list2) {
            // 编译错误：capture#1-of ? cannot be converted to capture#2-of ?
            // Collections.swap(list1, list2, 0);

            // 解决方案：不能在两个不同的通配符类型间操作
            // swapFirstHelper(list1, list2);  // 这会导致编译错误

            System.out.println("无法在两个不同的通配符类型间交换元素");
            System.out.println("这正好演示了通配符捕获的限制");
        }

        private <T> void swapFirstHelper(List<T> list1, List<T> list2) {
            T temp = list1.get(0);
            list1.set(0, list2.get(0));
            list2.set(0, temp);
        }

        // 正确的使用方式：使用相同类型参数
        public <T> void swapFirstCorrect(List<T> list1, List<T> list2) {
            swapFirstHelper(list1, list2);
        }
    }

    // 3. 泛型单例模式
    static class GenericSingleton<T> {
        private static final Map<Class<?>, Object> instances = new HashMap<>();

        @SuppressWarnings("unchecked")
        public static <T> T getInstance(Class<T> clazz) {
            return (T) instances.computeIfAbsent(clazz, k -> {
                try {
                    return k.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * 任务1.3：泛型与反射
     * 演示运行时类型信息的获取
     */

    static class TypeTokenPattern {
        abstract static class TypeToken<T> {
            private final java.lang.reflect.Type type;

            protected TypeToken() {
                this.type = ((java.lang.reflect.ParameterizedType)
                        getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            }

            public java.lang.reflect.Type getType() {
                return type;
            }
        }

        public static void demonstrateTypeToken() {
            TypeToken<String> stringToken = new TypeToken<String>() {
            };
            TypeToken<Integer> intToken = new TypeToken<Integer>() {
            };

            System.out.println("String token type: " + stringToken.getType());
            System.out.println("Integer token type: " + intToken.getType());
        }
    }

    /**
     * 任务4.3：Monad实现分析
     * Java的Optional作为Monad的演示
     */

    static class OptionalMonadDemo {
        public static void demonstrateMonadLaws() {
            // 左恒等律：unit(a) flatMap f ≡ f(a)
            Optional<Integer> leftIdentity = Optional.of(5)
                    .flatMap(x -> Optional.of(x * 2));
            Optional<Integer> direct = Optional.of(5 * 2);

            System.out.println("Left identity: " + leftIdentity.equals(direct));

            // 右恒等律：m flatMap unit ≡ m
            Optional<String> rightIdentity = Optional.of("hello")
                    .flatMap(Optional::of);
            Optional<String> original = Optional.of("hello");

            System.out.println("Right identity: " + rightIdentity.equals(original));

            // 结合律验证
            Optional<Integer> chained = Optional.of(5)
                    .flatMap(x -> Optional.of(x * 2))
                    .flatMap(x -> Optional.of(x + 3));

            Optional<Integer> composed = Optional.of(5)
                    .flatMap(x -> Optional.of(x * 2)
                            .flatMap(y -> Optional.of(y + 3)));

            System.out.println("Associativity: " + chained.equals(composed));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Java高级泛型和Monad演示 ===");

        // 演示类型擦除边界案例
        OverloadConflict conflict = new OverloadConflict();
        conflict.processStrings(Arrays.asList("a", "b"));
        conflict.processIntegers(Arrays.asList(1, 2));

        // 演示泛型数组问题
        GenericArrayProblem arrayProblem = new GenericArrayProblem();
        String[] stringArray = arrayProblem.createArrayWithReflection(String.class, 5);
        System.out.println("Created generic array: " + stringArray.getClass());

        // 演示TypeToken模式
        TypeTokenPattern.demonstrateTypeToken();

        // 演示Monad定律
        OptionalMonadDemo.demonstrateMonadLaws();
    }
}