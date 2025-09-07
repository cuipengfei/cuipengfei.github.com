package com.example.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * JVM类型系统性能基准测试
 * 测试不同语言实现的泛型、Monad等抽象的运行时开销
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
public class GenericsBenchmark {

    @Param({"100", "1000", "10000"})
    private int size;

    private List<Integer> numbers;
    private Optional<Integer> optionalValue;
    private List<String> strings;

    @Setup
    public void setup() {
        numbers = new ArrayList<>();
        strings = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
            numbers.add(i);
            strings.add("string-" + i);
        }
        
        optionalValue = Optional.of(42);
    }

    /**
     * 测试Java泛型容器的性能
     */
    @Benchmark
    public void javaGenericContainer(Blackhole blackhole) {
        GenericContainer<Integer> container = new GenericContainer<>(42);
        blackhole.consume(container.getValue());
    }

    /**
     * 测试Java Optional的Monad操作性能
     */
    @Benchmark
    public void javaOptionalMonad(Blackhole blackhole) {
        Optional<String> result = optionalValue
            .flatMap(x -> Optional.of(x * 2))
            .flatMap(x -> Optional.of(x.toString()));
        blackhole.consume(result.orElse(""));
    }

    /**
     * 测试原始类型与泛型的性能差异
     */
    @Benchmark
    public void rawTypeList(Blackhole blackhole) {
        List rawList = numbers;
        for (int i = 0; i < rawList.size(); i++) {
            blackhole.consume(rawList.get(i));
        }
    }

    @Benchmark
    public void genericList(Blackhole blackhole) {
        for (Integer num : numbers) {
            blackhole.consume(num);
        }
    }

    /**
     * 测试泛型方法调用的开销
     */
    @Benchmark
    public void genericMethodCall(Blackhole blackhole) {
        String result = transformGeneric("test");
        blackhole.consume(result);
    }

    private <T> T transformGeneric(T input) {
        return input;
    }

    /**
     * 测试协变逆变的性能影响
     */
    @Benchmark
    public void covarianceCast(Blackhole blackhole) {
        List<? extends Number> covariant = numbers;
        for (Number num : covariant) {
            blackhole.consume(num.intValue());
        }
    }

    @Benchmark
    public void normalList(Blackhole blackhole) {
        for (int i = 0; i < numbers.size(); i++) {
            blackhole.consume(numbers.get(i));
        }
    }

    /**
     * 测试类型擦除的影响
     */
    @Benchmark
    public void typeErasureImpact(Blackhole blackhole) {
        // 模拟类型擦除后的操作
        Object obj = numbers;
        List<?> wildcardList = (List<?>) obj;
        blackhole.consume(wildcardList.size());
    }

    /**
     * 测试泛型数组创建的性能
     */
    @Benchmark
    public void genericArrayCreation(Blackhole blackhole) {
        Integer[] array = createGenericArray(Integer.class, size);
        blackhole.consume(array.length);
    }

    private <T> T[] createGenericArray(Class<T> clazz, int size) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
        return array;
    }

    /**
     * 简单的泛型容器用于基准测试
     */
    public static class GenericContainer<T> {
        private final T value;
        
        public GenericContainer(T value) {
            this.value = value;
        }
        
        public T getValue() {
            return value;
        }
    }
}