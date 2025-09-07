package com.example.advanced;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TypeReference模式演示 - 任务3.2的实际库应用
 * Jackson/Gson风格的类型擦除补偿机制
 * <p>
 * 反编译分析重点：
 * 1. TypeReference的超类型捕获机制
 * 2. ParameterizedType的运行时获取
 * 3. 复杂泛型类型的反序列化支持
 * 4. 类型信息缓存机制
 * <p>
 * 分析指令：
 * javac TypeReferenceDemo.java
 * javap -v -p TypeReferenceDemo.class
 * javap -v -p TypeReferenceDemo\$TypeReference.class
 */
public class TypeReferenceDemo {

    /**
     * 通用TypeReference实现 - 模拟Jackson的TypeReference
     * 反编译重点：匿名内部类的泛型信息捕获
     */
    public abstract static class TypeReference<T> implements Type {
        private final Type type;
        private static final Map<Type, TypeReference<?>> cache = new ConcurrentHashMap<>();

        /**
         * 构造器 - 通过反射捕获泛型类型信息
         * 反编译重点：getGenericSuperclass的调用机制
         */
        protected TypeReference() {
            Type superClass = getClass().getGenericSuperclass();
            if (superClass instanceof Class<?>) {
                throw new IllegalArgumentException("缺少类型参数信息");
            }

            ParameterizedType parameterized = (ParameterizedType) superClass;
            this.type = parameterized.getActualTypeArguments()[0];
        }

        /**
         * 直接构造（用于工具方法）
         * 反编译重点：类型信息的直接传递
         */
        private TypeReference(Type type) {
            this.type = type;
        }

        /**
         * 获取捕获的类型
         */
        public Type getType() {
            return type;
        }

        /**
         * 获取原始类型
         * 反编译重点：类型转换的安全性检查
         */
        @SuppressWarnings("unchecked")
        public Class<T> getRawClass() {
            if (type instanceof Class<?>) {
                return (Class<T>) type;
            } else if (type instanceof ParameterizedType) {
                return (Class<T>) ((ParameterizedType) type).getRawType();
            } else if (type instanceof GenericArrayType) {
                GenericArrayType gat = (GenericArrayType) type;
                Class<?> componentClass = TypeReference.of(gat.getGenericComponentType()).getRawClass();
                return (Class<T>) Array.newInstance(componentClass, 0).getClass();
            }
            throw new IllegalArgumentException("无法获取原始类型: " + type);
        }

        /**
         * 工厂方法 - 从Class创建TypeReference
         * 反编译重点：匿名类的动态生成
         */
        public static <T> TypeReference<T> of(Type type) {
            @SuppressWarnings("unchecked")
            TypeReference<T> cached = (TypeReference<T>) cache.get(type);
            if (cached != null) {
                return cached;
            }

            TypeReference<T> newRef = new TypeReference<T>(type) {
            };
            cache.put(type, newRef);
            return newRef;
        }

        /**
         * 工厂方法 - 从Class创建
         */
        public static <T> TypeReference<T> of(Class<T> clazz) {
            return of((Type) clazz);
        }

        @Override
        public String toString() {
            return "TypeReference{" + type + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TypeReference)) return false;
            TypeReference<?> that = (TypeReference<?>) o;
            return Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }

        // 实现Type接口的方法（简化实现）
        @Override
        public String getTypeName() {
            return type.getTypeName();
        }
    }

    /**
     * 模拟JSON反序列化器
     * 反编译重点：泛型类型信息的实际应用
     */
    public static class JsonDeserializer {

        /**
         * 使用TypeReference的反序列化
         * 反编译重点：类型信息驱动的对象创建
         */
        @SuppressWarnings("unchecked")
        public static <T> T fromJson(String json, TypeReference<T> typeRef) {
            Type type = typeRef.getType();

            // 简化的JSON解析实现
            if (type == String.class) {
                return (T) json;
            } else if (type == Integer.class) {
                return (T) Integer.valueOf(json);
            } else if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) type;
                if (pType.getRawType() == List.class) {
                    return (T) parseJsonArray(json, pType.getActualTypeArguments()[0]);
                } else if (pType.getRawType() == Map.class) {
                    return (T) parseJsonMap(json, pType.getActualTypeArguments()[1]);
                }
            }

            throw new IllegalArgumentException("不支持的类型: " + type);
        }

        /**
         * 解析JSON数组
         * 反编译重点：元素类型的递归解析
         */
        private static List<?> parseJsonArray(String json, Type elementType) {
            // 简化实现：按逗号分割
            String[] elements = json.split(",");
            List<Object> result = new ArrayList<>();

            for (String element : elements) {
                if (elementType == String.class) {
                    result.add(element.trim());
                } else if (elementType == Integer.class) {
                    result.add(Integer.parseInt(element.trim()));
                } else if (elementType == Double.class) {
                    result.add(Double.parseDouble(element.trim()));
                }
            }

            return result;
        }

        /**
         * 解析JSON对象到Map
         * 反编译重点：值类型的动态解析
         */
        private static Map<String, ?> parseJsonMap(String json, Type valueType) {
            // 极简实现
            Map<String, Object> result = new HashMap<>();
            result.put("example", "这是模拟的Map解析");
            return result;
        }
    }

    /**
     * 复杂泛型类型的TypeReference使用
     * 反编译重点：嵌套泛型的类型捕获
     */
    public static class ComplexTypeExamples {

        /**
         * List<String>的TypeReference
         */
        public static void demonstrateListTypeReference() {
            TypeReference<List<String>> listType = new TypeReference<List<String>>() {
            };

            String json = "hello,world,java";
            List<String> result = JsonDeserializer.fromJson(json, listType);

            System.out.println("List<String>反序列化: " + result);
            System.out.println("捕获的类型: " + listType.getType());
            System.out.println("原始类型: " + listType.getRawClass());
        }

        /**
         * Map<String, Integer>的TypeReference
         */
        public static void demonstrateMapTypeReference() {
            TypeReference<Map<String, Integer>> mapType = new TypeReference<Map<String, Integer>>() {
            };

            String json = "{}"; // 简化的JSON
            Map<String, Integer> result = JsonDeserializer.fromJson(json, mapType);

            System.out.println("Map<String, Integer>反序列化: " + result);
            System.out.println("捕获的类型: " + mapType.getType());
        }

        /**
         * 嵌套复杂类型 List<Map<String, List<Integer>>>
         */
        public static void demonstrateNestedTypeReference() {
            TypeReference<List<Map<String, List<Integer>>>> complexType =
                    new TypeReference<List<Map<String, List<Integer>>>>() {
                    };

            System.out.println("复杂嵌套类型: " + complexType.getType());

            // 分析类型结构
            analyzeTypeStructure(complexType.getType());
        }

        /**
         * 数组类型的TypeReference
         */
        public static void demonstrateArrayTypeReference() {
            TypeReference<String[]> arrayType = new TypeReference<String[]>() {
            };
            TypeReference<List<String>[]> complexArrayType = new TypeReference<List<String>[]>() {
            };

            System.out.println("数组类型: " + arrayType.getType());
            System.out.println("复杂数组类型: " + complexArrayType.getType());
            System.out.println("数组原始类型: " + arrayType.getRawClass());
        }
    }

    /**
     * 类型结构分析工具
     * 反编译重点：反射API的深度使用
     */
    public static void analyzeTypeStructure(Type type) {
        System.out.println("=== 类型结构分析 ===");
        System.out.println("类型: " + type);
        System.out.println("类型名: " + type.getTypeName());
        System.out.println("类型类别: " + type.getClass().getSimpleName());

        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            System.out.println("原始类型: " + pType.getRawType());
            System.out.println("类型参数:");
            for (int i = 0; i < pType.getActualTypeArguments().length; i++) {
                System.out.println("  [" + i + "]: " + pType.getActualTypeArguments()[i]);
            }

            if (pType.getOwnerType() != null) {
                System.out.println("拥有者类型: " + pType.getOwnerType());
            }
        } else if (type instanceof GenericArrayType) {
            GenericArrayType gType = (GenericArrayType) type;
            System.out.println("组件类型: " + gType.getGenericComponentType());
        } else if (type instanceof WildcardType) {
            WildcardType wType = (WildcardType) type;
            System.out.println("上界: " + Arrays.toString(wType.getUpperBounds()));
            System.out.println("下界: " + Arrays.toString(wType.getLowerBounds()));
        }

        System.out.println("==================");
    }

    /**
     * 性能测试 - TypeReference vs 反射
     * 反编译重点：缓存机制的性能影响
     */
    public static class PerformanceTest {

        public static void comparePerformance() {
            int iterations = 100000;

            // 预热
            for (int i = 0; i < 10000; i++) {
                new TypeReference<List<String>>() {
                };
                TypeReference.of(List.class);
            }

            // 测试TypeReference创建
            long start1 = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                new TypeReference<List<String>>() {
                };
            }
            long time1 = System.nanoTime() - start1;

            // 测试工厂方法
            long start2 = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                TypeReference.of(List.class);
            }
            long time2 = System.nanoTime() - start2;

            System.out.println("性能对比 (" + iterations + " 次迭代):");
            System.out.println("匿名类创建: " + (time1 / 1_000_000) + "ms");
            System.out.println("工厂方法(缓存): " + (time2 / 1_000_000) + "ms");
            System.out.println("性能比: " + (double) time1 / time2);
        }
    }

    /**
     * 实际使用场景演示
     */
    public static void demonstrateRealWorldUsage() {
        System.out.println("=== 实际使用场景演示 ===");

        // 场景1: Web API响应解析
        System.out.println("\n1. Web API响应解析:");
        TypeReference<List<String>> apiResponseType = new TypeReference<List<String>>() {
        };
        List<String> apiData = JsonDeserializer.fromJson("用户1,用户2,用户3", apiResponseType);
        System.out.println("API响应数据: " + apiData);

        // 场景2: 配置文件解析
        System.out.println("\n2. 配置文件解析:");
        TypeReference<Map<String, Integer>> configType = new TypeReference<Map<String, Integer>>() {
        };
        Map<String, Integer> config = JsonDeserializer.fromJson("{}", configType);
        System.out.println("配置数据: " + config);

        // 场景3: 缓存系统
        System.out.println("\n3. 类型安全的缓存:");
        TypeReference<String> stringType = new TypeReference<String>() {
        };
        String cachedValue = JsonDeserializer.fromJson("缓存的字符串", stringType);
        System.out.println("缓存值: " + cachedValue);

        System.out.println("\n=== TypeReference的优势 ===");
        System.out.println("1. 编译时类型安全");
        System.out.println("2. 运行时类型信息保留");
        System.out.println("3. 泛型擦除的优雅解决方案");
        System.out.println("4. 库生态系统的标准模式");
    }

    /**
     * 演示入口
     */
    public static void main(String[] args) {
        System.out.println("=== TypeReference模式演示 ===");
        System.out.println("模拟Jackson/Gson的类型擦除补偿机制");

        // 基础类型引用演示
        ComplexTypeExamples.demonstrateListTypeReference();
        ComplexTypeExamples.demonstrateMapTypeReference();

        System.out.println();
        ComplexTypeExamples.demonstrateNestedTypeReference();

        System.out.println();
        ComplexTypeExamples.demonstrateArrayTypeReference();

        System.out.println();
        demonstrateRealWorldUsage();

        System.out.println();
        PerformanceTest.comparePerformance();

        System.out.println("\n=== 反编译分析重点 ===");
        System.out.println("1. 观察匿名内部类的字节码结构");
        System.out.println("2. 分析getGenericSuperclass的调用");
        System.out.println("3. 查看ParameterizedType的运行时表示");
        System.out.println("4. 研究类型信息缓存的实现机制");
        System.out.println("5. 对比不同TypeReference创建方式的性能");
    }
}