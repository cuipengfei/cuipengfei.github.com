package com.example.benchmark;

/**
 * JVM语言类型系统性能基准测试
 * 目标：对比不同语言特性的性能开销，理解字节码优化
 * 
 * 反编译分析重点：
 * 1. 泛型类型擦除vs具体类型的性能差异
 * 2. 装箱拆箱操作的字节码成本
 * 3. 虚方法调用vs静态分发的差异
 * 4. 内联优化的效果观察
 * 5. JIT编译器的优化策略
 * 
 * 使用说明：
 * 1. javac编译后运行多次预热JIT
 * 2. 使用-XX:+PrintCompilation观察JIT编译
 * 3. 使用JProfiler等工具分析热点
 * 4. 对比反编译的字节码理解性能差异原因
 */
public class TypeSystemBenchmark {
    
    // 测试数据大小
    private static final int DATA_SIZE = 1_000_000;
    private static final int WARMUP_ITERATIONS = 10;
    private static final int BENCHMARK_ITERATIONS = 100;
    
    /**
     * 场景1：泛型vs原始类型性能对比
     * 目标：理解类型擦除对性能的实际影响
     */
    public static class GenericVsRawBenchmark {
        
        // 泛型容器
        private static class GenericContainer<T> {
            private Object[] array;
            private int size;
            
            public GenericContainer(int capacity) {
                array = new Object[capacity];
                size = 0;
            }
            
            public void add(T item) {
                if (size < array.length) {
                    array[size++] = item;
                }
            }
            
            @SuppressWarnings("unchecked")
            public T get(int index) {
                return (T) array[index];
            }
            
            public int size() {
                return size;
            }
        }
        
        // 原始类型容器
        private static class RawContainer {
            private Object[] array;
            private int size;
            
            public RawContainer(int capacity) {
                array = new Object[capacity];
                size = 0;
            }
            
            public void add(Object item) {
                if (size < array.length) {
                    array[size++] = item;
                }
            }
            
            public Object get(int index) {
                return array[index];
            }
            
            public int size() {
                return size;
            }
        }
        
        // 特化容器（避免泛型）
        private static class IntContainer {
            private int[] array;
            private int size;
            
            public IntContainer(int capacity) {
                array = new int[capacity];
                size = 0;
            }
            
            public void add(int item) {
                if (size < array.length) {
                    array[size++] = item;
                }
            }
            
            public int get(int index) {
                return array[index];
            }
            
            public int size() {
                return size;
            }
        }
        
        public static void runBenchmark() {
            System.out.println("=== 泛型vs原始类型性能对比 ===");
            
            // 预热JIT编译器
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                benchmarkGeneric();
                benchmarkRaw();
                benchmarkSpecialized();
            }
            
            // 正式基准测试
            long genericTime = 0, rawTime = 0, specializedTime = 0;
            
            for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
                genericTime += benchmarkGeneric();
                rawTime += benchmarkRaw();
                specializedTime += benchmarkSpecialized();
            }
            
            System.out.printf("泛型容器平均时间: %.2f ms%n", 
                genericTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
            System.out.printf("原始类型容器平均时间: %.2f ms%n", 
                rawTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
            System.out.printf("特化容器平均时间: %.2f ms%n", 
                specializedTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
            
            double genericOverhead = ((genericTime - specializedTime) / (double) specializedTime) * 100;
            double rawOverhead = ((rawTime - specializedTime) / (double) specializedTime) * 100;
            
            System.out.printf("泛型开销: %.2f%%%n", genericOverhead);
            System.out.printf("原始类型开销: %.2f%%%n", rawOverhead);
        }
        
        private static long benchmarkGeneric() {
            GenericContainer<Integer> container = new GenericContainer<>(DATA_SIZE);
            long start = System.nanoTime();
            
            // 填充数据 - 会有装箱开销
            for (int i = 0; i < DATA_SIZE; i++) {
                container.add(i);
            }
            
            // 读取数据 - 会有拆箱开销
            long sum = 0;
            for (int i = 0; i < container.size(); i++) {
                sum += container.get(i);  // 自动拆箱
            }
            
            long end = System.nanoTime();
            blackhole(sum);  // 防止优化掉计算
            return end - start;
        }
        
        private static long benchmarkRaw() {
            RawContainer container = new RawContainer(DATA_SIZE);
            long start = System.nanoTime();
            
            // 填充数据 - 会有装箱开销
            for (int i = 0; i < DATA_SIZE; i++) {
                container.add(i);
            }
            
            // 读取数据 - 会有拆箱开销
            long sum = 0;
            for (int i = 0; i < container.size(); i++) {
                sum += (Integer) container.get(i);  // 显式转型+拆箱
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
        
        private static long benchmarkSpecialized() {
            IntContainer container = new IntContainer(DATA_SIZE);
            long start = System.nanoTime();
            
            // 填充数据 - 无装箱开销
            for (int i = 0; i < DATA_SIZE; i++) {
                container.add(i);
            }
            
            // 读取数据 - 无拆箱开销
            long sum = 0;
            for (int i = 0; i < container.size(); i++) {
                sum += container.get(i);
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
    }
    
    /**
     * 场景2：多态调用vs静态调用性能对比
     * 目标：理解虚方法表查找的开销
     */
    public static class PolymorphismBenchmark {
        
        interface Processor {
            int process(int value);
        }
        
        static class AddProcessor implements Processor {
            private final int increment;
            
            public AddProcessor(int increment) {
                this.increment = increment;
            }
            
            @Override
            public int process(int value) {
                return value + increment;
            }
        }
        
        static class MultiplyProcessor implements Processor {
            private final int factor;
            
            public MultiplyProcessor(int factor) {
                this.factor = factor;
            }
            
            @Override
            public int process(int value) {
                return value * factor;
            }
        }
        
        static class StaticProcessor {
            public static int processAdd(int value, int increment) {
                return value + increment;
            }
            
            public static int processMultiply(int value, int factor) {
                return value * factor;
            }
        }
        
        public static void runBenchmark() {
            System.out.println("=== 多态调用vs静态调用性能对比 ===");
            
            // 预热
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                benchmarkPolymorphic();
                benchmarkMonomorphic();
                benchmarkStatic();
            }
            
            long polymorphicTime = 0, monomorphicTime = 0, staticTime = 0;
            
            for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
                polymorphicTime += benchmarkPolymorphic();
                monomorphicTime += benchmarkMonomorphic();
                staticTime += benchmarkStatic();
            }
            
            System.out.printf("多态调用平均时间: %.2f ms%n", 
                polymorphicTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
            System.out.printf("单态调用平均时间: %.2f ms%n", 
                monomorphicTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
            System.out.printf("静态调用平均时间: %.2f ms%n", 
                staticTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
                
            double polymorphicOverhead = ((polymorphicTime - staticTime) / (double) staticTime) * 100;
            double monomorphicOverhead = ((monomorphicTime - staticTime) / (double) staticTime) * 100;
            
            System.out.printf("多态调用开销: %.2f%%%n", polymorphicOverhead);
            System.out.printf("单态调用开销: %.2f%%%n", monomorphicOverhead);
        }
        
        // 多态调用：运行时类型不确定，需要虚方法表查找
        private static long benchmarkPolymorphic() {
            Processor[] processors = {
                new AddProcessor(1),
                new MultiplyProcessor(2),
                new AddProcessor(3),
                new MultiplyProcessor(4)
            };
            
            long start = System.nanoTime();
            long sum = 0;
            
            for (int i = 0; i < DATA_SIZE; i++) {
                Processor p = processors[i % processors.length];
                sum += p.process(i);  // 多态调用
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
        
        // 单态调用：运行时类型确定，可以内联优化
        private static long benchmarkMonomorphic() {
            AddProcessor processor = new AddProcessor(5);
            
            long start = System.nanoTime();
            long sum = 0;
            
            for (int i = 0; i < DATA_SIZE; i++) {
                sum += processor.process(i);  // 单态调用
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
        
        // 静态调用：编译时确定，最优性能
        private static long benchmarkStatic() {
            long start = System.nanoTime();
            long sum = 0;
            
            for (int i = 0; i < DATA_SIZE; i++) {
                sum += StaticProcessor.processAdd(i, 5);  // 静态调用
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
    }
    
    /**
     * 场景3：装箱拆箱性能分析
     * 目标：量化自动装箱拆箱的性能开销
     */
    public static class BoxingBenchmark {
        
        public static void runBenchmark() {
            System.out.println("=== 装箱拆箱性能分析 ===");
            
            // 预热
            for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                benchmarkPrimitive();
                benchmarkBoxed();
                benchmarkMixed();
            }
            
            long primitiveTime = 0, boxedTime = 0, mixedTime = 0;
            
            for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
                primitiveTime += benchmarkPrimitive();
                boxedTime += benchmarkBoxed();
                mixedTime += benchmarkMixed();
            }
            
            System.out.printf("基本类型计算平均时间: %.2f ms%n", 
                primitiveTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
            System.out.printf("装箱类型计算平均时间: %.2f ms%n", 
                boxedTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
            System.out.printf("混合计算平均时间: %.2f ms%n", 
                mixedTime / (double) BENCHMARK_ITERATIONS / 1_000_000);
                
            double boxedOverhead = ((boxedTime - primitiveTime) / (double) primitiveTime) * 100;
            double mixedOverhead = ((mixedTime - primitiveTime) / (double) primitiveTime) * 100;
            
            System.out.printf("装箱开销: %.2f%%%n", boxedOverhead);
            System.out.printf("混合开销: %.2f%%%n", mixedOverhead);
        }
        
        private static long benchmarkPrimitive() {
            long start = System.nanoTime();
            
            long sum = 0;
            for (int i = 0; i < DATA_SIZE; i++) {
                int a = i;
                int b = i + 1;
                int c = a + b;  // 纯基本类型运算
                sum += c;
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
        
        private static long benchmarkBoxed() {
            long start = System.nanoTime();
            
            long sum = 0;
            for (int i = 0; i < DATA_SIZE; i++) {
                Integer a = i;          // 装箱
                Integer b = i + 1;      // 装箱
                Integer c = a + b;      // 拆箱 + 计算 + 装箱
                sum += c;               // 拆箱
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
        
        private static long benchmarkMixed() {
            long start = System.nanoTime();
            
            long sum = 0;
            for (int i = 0; i < DATA_SIZE; i++) {
                Integer a = i;          // 装箱
                int b = i + 1;          // 基本类型
                int c = a + b;          // 拆箱 + 计算
                sum += c;
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
    }
    
    /**
     * 场景4：泛型集合性能对比
     * 目标：对比不同集合实现的泛型开销
     */
    public static class CollectionsBenchmark {
        
        public static void runBenchmark() {
            System.out.println("=== 泛型集合性能对比 ===");
            
            // 预热
            for (int i = 0; i < WARMUP_ITERATIONS / 2; i++) {
                benchmarkArrayList();
                benchmarkLinkedList();
                benchmarkArrayDeque();
            }
            
            long arrayListTime = 0, linkedListTime = 0, arrayDequeTime = 0;
            int iterations = BENCHMARK_ITERATIONS / 10;  // 减少迭代次数，集合操作较慢
            
            for (int i = 0; i < iterations; i++) {
                arrayListTime += benchmarkArrayList();
                linkedListTime += benchmarkLinkedList();
                arrayDequeTime += benchmarkArrayDeque();
            }
            
            System.out.printf("ArrayList平均时间: %.2f ms%n", 
                arrayListTime / (double) iterations / 1_000_000);
            System.out.printf("LinkedList平均时间: %.2f ms%n", 
                linkedListTime / (double) iterations / 1_000_000);
            System.out.printf("ArrayDeque平均时间: %.2f ms%n", 
                arrayDequeTime / (double) iterations / 1_000_000);
        }
        
        private static long benchmarkArrayList() {
            long start = System.nanoTime();
            
            java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
            for (int i = 0; i < DATA_SIZE / 100; i++) {
                list.add(i);  // 装箱
            }
            
            long sum = 0;
            for (Integer value : list) {
                sum += value;  // 拆箱
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
        
        private static long benchmarkLinkedList() {
            long start = System.nanoTime();
            
            java.util.LinkedList<Integer> list = new java.util.LinkedList<>();
            for (int i = 0; i < DATA_SIZE / 100; i++) {
                list.add(i);  // 装箱
            }
            
            long sum = 0;
            for (Integer value : list) {
                sum += value;  // 拆箱
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
        
        private static long benchmarkArrayDeque() {
            long start = System.nanoTime();
            
            java.util.ArrayDeque<Integer> deque = new java.util.ArrayDeque<>();
            for (int i = 0; i < DATA_SIZE / 100; i++) {
                deque.add(i);  // 装箱
            }
            
            long sum = 0;
            for (Integer value : deque) {
                sum += value;  // 拆箱
            }
            
            long end = System.nanoTime();
            blackhole(sum);
            return end - start;
        }
    }
    
    /**
     * 防止编译器优化掉"无用"计算的黑洞方法
     */
    private static void blackhole(long value) {
        if (value == Long.MIN_VALUE) {
            System.out.println("Impossible value");
        }
    }
    
    /**
     * JIT编译状态监控
     */
    private static void printJITInfo() {
        System.out.println("=== JIT编译信息 ===");
        System.out.println("建议使用以下JVM参数观察优化：");
        System.out.println("-XX:+PrintCompilation");
        System.out.println("-XX:+UnlockDiagnosticVMOptions");
        System.out.println("-XX:+PrintInlining");
        System.out.println("-XX:+PrintAssembly  (需要hsdis库)");
        System.out.println();
        
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        System.out.printf("内存使用: %.2f MB / %.2f MB%n", 
            usedMemory / 1024.0 / 1024.0, totalMemory / 1024.0 / 1024.0);
    }
    
    /**
     * 主方法 - 运行所有基准测试
     */
    public static void main(String[] args) {
        System.out.println("JVM类型系统性能基准测试");
        System.out.println("数据大小: " + DATA_SIZE);
        System.out.println("预热迭代: " + WARMUP_ITERATIONS);
        System.out.println("基准迭代: " + BENCHMARK_ITERATIONS);
        System.out.println();
        
        printJITInfo();
        
        // 运行各项基准测试
        GenericVsRawBenchmark.runBenchmark();
        System.out.println();
        
        PolymorphismBenchmark.runBenchmark();
        System.out.println();
        
        BoxingBenchmark.runBenchmark();
        System.out.println();
        
        CollectionsBenchmark.runBenchmark();
        System.out.println();
        
        System.gc();  // 建议垃圾收集
        System.out.println("基准测试完成");
    }
}