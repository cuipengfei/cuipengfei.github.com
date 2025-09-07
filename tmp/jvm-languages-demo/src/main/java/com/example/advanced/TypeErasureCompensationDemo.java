package com.example.advanced;

import java.lang.reflect.*;
import java.util.*;

/**
 * 类型擦除补偿机制演示
 * 任务3.2：各语言如何补偿类型擦除
 * 
 * 反编译分析重点：
 * 1. TypeToken模式在字节码中的实现
 * 2. 反射API的类型信息获取机制
 * 3. 运行时类型检查的策略
 */
public class TypeErasureCompensationDemo {
    
    /**
     * 1. TypeToken模式实现
     * 反编译重点：ParameterizedType的获取机制
     */
    public abstract static class TypeToken<T> {
        private final Type type;
        
        protected TypeToken() {
            // 获取当前类的泛型超类信息
            Type superClass = getClass().getGenericSuperclass();
            if (superClass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) superClass;
                // 提取第一个类型参数
                this.type = parameterizedType.getActualTypeArguments()[0];
            } else {
                throw new RuntimeException("Missing type parameter");
            }
        }
        
        public Type getType() {
            return type;
        }
        
        /**
         * 获取原始类型
         */
        @SuppressWarnings("unchecked")
        public Class<T> getRawType() {
            if (type instanceof Class) {
                return (Class<T>) type;
            } else if (type instanceof ParameterizedType) {
                return (Class<T>) ((ParameterizedType) type).getRawType();
            }
            throw new RuntimeException("Cannot get raw type for: " + type);
        }
        
        /**
         * 类型安全的向下转型
         */
        @SuppressWarnings("unchecked")
        public T cast(Object obj) {
            if (obj == null) return null;
            return getRawType().cast(obj);
        }
    }
    
    /**
     * 2. 泛型数组的类型安全创建
     * 反编译重点：Array.newInstance的调用机制
     */
    public static class GenericArrayCreator {
        
        /**
         * 使用反射创建泛型数组
         * 反编译重点：类型擦除后的数组创建
         */
        @SuppressWarnings("unchecked")
        public static <T> T[] createGenericArray(Class<T> componentType, int length) {
            // 使用Array.newInstance创建类型安全的数组
            return (T[]) Array.newInstance(componentType, length);
        }
        
        /**
         * 集合到数组的转换
         * 反编译重点：类型转换的运行时检查
         */
        @SuppressWarnings("unchecked")
        public static <T> T[] toArray(List<T> list, Class<T> componentType) {
            T[] array = createGenericArray(componentType, list.size());
            return list.toArray(array);
        }
        
        /**
         * 演示类型擦除导致的数组问题
         * 反编译重点：编译器限制的字节码原因
         */
        public static <T> void demonstrateArrayProblem() {
            // 这行代码无法编译：Cannot create a generic array of T
            // T[] genericArray = new T[10];
            
            // 类型不安全的"解决方案"
            @SuppressWarnings("unchecked")
            T[] unsafeArray = (T[]) new Object[10];
            System.out.println("创建了类型不安全的数组: " + unsafeArray.getClass());
        }
    }
    
    /**
     * 3. 运行时类型检查
     * 反编译重点：类型检查的策略和限制
     */
    public static class RuntimeTypeChecker {
        
        /**
         * 检查List的元素类型
         * 反编译重点：运行时类型检查的局限性
         */
        public static <T> boolean isListOf(List<?> list, Class<T> elementType) {
            for (Object element : list) {
                if (!elementType.isInstance(element)) {
                    return false;
                }
            }
            return true;
        }
        
        /**
         * 类型安全的列表获取
         * 反编译重点：类型转换的运行时验证
         */
        @SuppressWarnings("unchecked")
        public static <T> List<T> castList(List<?> list, Class<T> elementType) {
            if (!isListOf(list, elementType)) {
                throw new ClassCastException("List contains elements of wrong type");
            }
            return (List<T>) list;
        }
        
        /**
         * 演示instanceof的限制
         * 反编译重点：类型擦除导致的检查失效
         */
        public static void demonstrateInstanceofLimitation() {
            List<String> stringList = Arrays.asList("a", "b", "c");
            
            // 编译错误：Cannot perform instanceof check against parameterized type List<String>
            // if (stringList instanceof List<String>) { }
            
            // 只能检查原始类型
            if (stringList instanceof List) {
                System.out.println("这是一个List，但类型参数被擦除了");
            }
            
            // TypeToken可以提供部分类型信息
            TypeToken<List<String>> token = new TypeToken<List<String>>() {};
            System.out.println("TypeToken提供的信息: " + token.getType());
        }
    }
    
    /**
     * 4. 反序列化中的类型恢复
     * 反编译重点：复杂类型的反序列化策略
     */
    public static class SerializationHelper {
        
        /**
         * 模拟JSON反序列化
         * 反编译重点：类型信息的保存和恢复
         */
        @SuppressWarnings("unchecked")
        public static <T> T fromJson(String json, TypeToken<T> typeToken) {
            // 这里模拟反序列化过程
            // 实际实现会使用Gson/Jackson等库
            
            Type type = typeToken.getType();
            System.out.println("反序列化类型: " + type);
            
            // 简单的模拟实现
            if (type == String.class) {
                return (T) json;
            } else if (type == Integer.class || type == int.class) {
                return (T) Integer.valueOf(json);
            }
            
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
        
        /**
         * 泛型集合的反序列化
         * 反编译重点：ParameterizedType的处理
         */
        @SuppressWarnings("unchecked")
        public static <T> List<T> fromJsonArray(String json, Class<T> elementType) {
            // 模拟数组反序列化 - 修复类型转换问题
            String[] items = json.split(",");
            List<T> result = new ArrayList<>();
            
            for (String item : items) {
                if (elementType == String.class) {
                    result.add((T) item.trim());
                } else if (elementType == Integer.class) {
                    result.add((T) Integer.valueOf(item.trim()));
                } else if (elementType == Double.class) {
                    result.add((T) Double.valueOf(item.trim()));
                } else {
                    throw new IllegalArgumentException("Unsupported element type: " + elementType);
                }
            }
            
            return result;
        }
    }
    
    /**
     * 5. 多重边界的类型信息保存
     * 反编译重点：复杂类型边界的字节码表示
     */
    public static class MultipleBoundsDemo {
        
        /**
         * 多重边界的方法
         * 反编译重点：类型签名中的边界信息
         */
        public static <T extends Number & Comparable<T>> void processBounded(T value) {
            System.out.println("处理多重边界值: " + value);
            System.out.println("数值操作: " + (value.doubleValue() * 2));
            System.out.println("比较操作: " + value.compareTo((T) Integer.valueOf(10)));
        }
        
        /**
         * 获取多重边界类型信息
         * 反编译重点：反射API对复杂类型的支持
         */
        public static void examineMultipleBounds() {
            try {
                // 获取方法的泛型签名
                Method method = MultipleBoundsDemo.class.getDeclaredMethod("processBounded", Comparable.class);
                Type[] parameterTypes = method.getGenericParameterTypes();
                
                if (parameterTypes[0] instanceof TypeVariable) {
                    TypeVariable<?> typeVar = (TypeVariable<?>) parameterTypes[0];
                    System.out.println("类型变量: " + typeVar.getName());
                    System.out.println("边界: " + Arrays.toString(typeVar.getBounds()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 演示方法
     */
    public static void main(String[] args) {
        System.out.println("=== 类型擦除补偿机制演示 ===");
        
        // 1. TypeToken演示
        System.out.println("\n1. TypeToken模式:");
        TypeToken<List<String>> stringListToken = new TypeToken<List<String>>() {};
        TypeToken<Map<String, Integer>> mapToken = new TypeToken<Map<String, Integer>>() {};
        
        System.out.println("String List类型: " + stringListToken.getType());
        System.out.println("Map类型: " + mapToken.getType());
        System.out.println("原始类型: " + stringListToken.getRawType());
        
        // 2. 泛型数组演示
        System.out.println("\n2. 泛型数组:");
        String[] stringArray = GenericArrayCreator.createGenericArray(String.class, 3);
        stringArray[0] = "Hello";
        stringArray[1] = "World";
        stringArray[2] = "Java";
        System.out.println("创建的字符串数组: " + Arrays.toString(stringArray));
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Integer[] numberArray = GenericArrayCreator.toArray(numbers, Integer.class);
        System.out.println("转换后的数组: " + Arrays.toString(numberArray));
        
        // 3. 运行时类型检查
        System.out.println("\n3. 运行时类型检查:");
        List<String> strings = Arrays.asList("a", "b", "c");
        List<Integer> integers = Arrays.asList(1, 2, 3);
        
        System.out.println("strings是String列表: " + RuntimeTypeChecker.isListOf(strings, String.class));
        System.out.println("integers是String列表: " + RuntimeTypeChecker.isListOf(integers, String.class));
        
        RuntimeTypeChecker.demonstrateInstanceofLimitation();
        
        // 4. 反序列化演示
        System.out.println("\n4. 反序列化演示:");
        String json = "42";
        Integer result = SerializationHelper.fromJson(json, new TypeToken<Integer>() {});
        System.out.println("反序列化结果: " + result);
        
        String arrayJson = "1,2,3,4,5";
        List<Integer> resultList = SerializationHelper.fromJsonArray(arrayJson, Integer.class);
        System.out.println("数组反序列化: " + resultList);
        
        // 5. 多重边界演示
        System.out.println("\n5. 多重边界:");
        MultipleBoundsDemo.processBounded(42);
        MultipleBoundsDemo.examineMultipleBounds();
        
        System.out.println("\n=== 反编译分析提示 ===");
        System.out.println("1. 使用 javap -v 查看TypeToken的字节码结构");
        System.out.println("2. 观察Signature属性中的泛型信息");
        System.out.println("3. 分析Array.newInstance调用的字节码");
        System.out.println("4. 比较类型擦除前后的方法签名差异");
    }
}