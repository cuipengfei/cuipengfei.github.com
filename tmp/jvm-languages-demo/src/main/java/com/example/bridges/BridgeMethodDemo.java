package com.example.bridges;

/**
 * 桥方法生成演示
 * 目标：系统性展示各种会导致桥方法生成的场景
 * <p>
 * 反编译分析重点：
 * 1. 使用javap -v观察synthetic bridge方法的生成
 * 2. 理解类型擦除后的方法签名冲突如何通过桥方法解决
 * 3. 分析继承、泛型、协变逆变组合产生的桥方法
 */
public class BridgeMethodDemo {

    /**
     * 场景1：泛型接口实现
     * 桥方法产生原因：类型擦除后需要Object参数的方法来匹配接口
     */
    public interface GenericProcessor<T> {
        T process(T input);

        boolean validate(T input);
    }

    /**
     * String实现 - 会生成桥方法
     * 期望的桥方法：
     * - public Object process(Object input) -> 调用 process(String)
     * - public boolean validate(Object input) -> 调用 validate(String)
     */
    public static class StringProcessor implements GenericProcessor<String> {
        @Override
        public String process(String input) {
            System.out.println("处理字符串: " + input);
            return input.toUpperCase();
        }

        @Override
        public boolean validate(String input) {
            return input != null && !input.isEmpty();
        }
    }

    /**
     * 场景2：泛型类继承
     * 桥方法产生原因：子类重写泛型父类方法时的类型具体化
     */
    public static abstract class GenericTransformer<T, R> {
        public abstract R transform(T input);

        public abstract java.util.List<R> batchTransform(java.util.List<T> inputs);
    }

    /**
     * 具体实现 - 会生成桥方法
     * 期望的桥方法：
     * - public Object transform(Object input)
     * - public List batchTransform(List inputs)
     */
    public static class StringToIntegerTransformer extends GenericTransformer<String, Integer> {
        @Override
        public Integer transform(String input) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        @Override
        public java.util.List<Integer> batchTransform(java.util.List<String> inputs) {
            return inputs.stream().map(this::transform).collect(java.util.stream.Collectors.toList());
        }
    }

    /**
     * 场景3：协变返回类型 + 泛型
     * 桥方法产生原因：协变返回类型在泛型上下文中的类型擦除
     */
    public static class GenericBuilder<T> {
        protected T value;

        public GenericBuilder(T initialValue) {
            this.value = initialValue;
        }

        public GenericBuilder<T> setValue(T newValue) {
            this.value = newValue;
            return this;
        }

        public T build() {
            return value;
        }
    }

    /**
     * 子类实现协变返回类型 - 会生成桥方法
     * 期望的桥方法：
     * - public GenericBuilder setValue(Object) -> 调用 setValue(String)
     */
    public static class StringBuilder extends GenericBuilder<String> {
        public StringBuilder(String initialValue) {
            super(initialValue);
        }

        @Override
        public StringBuilder setValue(String newValue) {  // 协变返回类型
            super.setValue(newValue);
            return this;
        }

        // 添加特定于String的方法
        public StringBuilder append(String suffix) {
            this.value = this.value + suffix;
            return this;
        }
    }

    /**
     * 场景4：多层泛型继承
     * 桥方法产生原因：多层继承中的类型参数传递
     */
    public interface Repository<T, ID> {
        T findById(ID id);

        java.util.List<T> findAll();

        void save(T entity);
    }

    public static abstract class AbstractRepository<T, ID> implements Repository<T, ID> {
        protected java.util.Map<ID, T> storage = new java.util.HashMap<>();

        @Override
        public T findById(ID id) {
            return storage.get(id);
        }

        @Override
        public java.util.List<T> findAll() {
            return new java.util.ArrayList<>(storage.values());
        }

        @Override
        public void save(T entity) {
            ID id = extractId(entity);
            storage.put(id, entity);
        }

        protected abstract ID extractId(T entity);
    }

    /**
     * 具体实现 - 会生成多个桥方法
     * 期望的桥方法：
     * - public Object findById(Object) -> 调用 findById(String)
     * - public void save(Object) -> 调用 save(User)
     * - protected Object extractId(Object) -> 调用 extractId(User)
     */
    public static class UserRepository extends AbstractRepository<User, String> {
        @Override
        protected String extractId(User entity) {
            return entity.getId();
        }

        // 添加特定查询方法
        public java.util.List<User> findByName(String name) {
            return findAll().stream()
                    .filter(user -> name.equals(user.getName()))
                    .collect(java.util.stream.Collectors.toList());
        }
    }

    /**
     * 用户实体类
     */
    public static class User {
        private String id;
        private String name;
        private int age;

        public User(String id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        // Getters
        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return String.format("User{id='%s', name='%s', age=%d}", id, name, age);
        }
    }

    /**
     * 场景5：泛型方法重写
     * 桥方法产生原因：泛型方法在继承时的特化
     */
    public static class GenericComparator<T extends Comparable<T>> {
        public int compare(T a, T b) {
            return a.compareTo(b);
        }

        public T max(T a, T b) {
            return compare(a, b) > 0 ? a : b;
        }

        public java.util.List<T> sort(java.util.List<T> items) {
            java.util.List<T> sorted = new java.util.ArrayList<>(items);
            sorted.sort(this::compare);
            return sorted;
        }
    }

    /**
     * 字符串特化比较器 - 会生成桥方法
     * 期望的桥方法：
     * - public int compare(Object, Object) -> 调用 compare(String, String)
     * - public Object max(Object, Object) -> 调用 max(String, String)
     * - public List sort(List) -> 调用 sort(List<String>)
     */
    public static class StringComparator extends GenericComparator<String> {
        @Override
        public int compare(String a, String b) {
            System.out.println("比较字符串: " + a + " vs " + b);
            return a.compareToIgnoreCase(b);  // 大小写不敏感比较
        }

        // 添加特定于字符串的方法
        public String concatenate(String a, String b) {
            return a + " + " + b;
        }
    }

    /**
     * 场景6：函数式接口的泛型实现
     * 桥方法产生原因：SAM类型的类型擦除处理
     */
    @FunctionalInterface
    public interface GenericFunction<T, R> {
        R apply(T input);

        // 默认方法不会产生桥方法
        default <V> GenericFunction<T, V> andThen(GenericFunction<R, V> after) {
            return (T t) -> after.apply(apply(t));
        }
    }

    /**
     * 函数接口的具体实现 - 会生成桥方法
     * 期望的桥方法：
     * - public Object apply(Object) -> 调用 apply(String)
     */
    public static class StringLengthFunction implements GenericFunction<String, Integer> {
        @Override
        public Integer apply(String input) {
            System.out.println("计算字符串长度: " + input);
            return input == null ? 0 : input.length();
        }
    }

    /**
     * 演示和验证方法
     */
    public static void demonstrateBridgeMethods() {
        System.out.println("=== 桥方法生成演示 ===");
        System.out.println("请使用 javap -v BridgeMethodDemo 查看生成的桥方法");
        System.out.println();

        // 1. 泛型接口实现测试
        StringProcessor processor = new StringProcessor();
        GenericProcessor<String> genericProcessor = processor;  // 向上转型
        String result1 = genericProcessor.process("hello");
        System.out.println("泛型接口调用结果: " + result1);

        // 2. 泛型继承测试
        StringToIntegerTransformer transformer = new StringToIntegerTransformer();
        GenericTransformer<String, Integer> genericTransformer = transformer;
        Integer result2 = genericTransformer.transform("123");
        System.out.println("泛型继承调用结果: " + result2);

        // 3. 协变返回类型测试
        StringBuilder builder = new StringBuilder("hello");
        GenericBuilder<String> genericBuilder = builder;  // 向上转型
        GenericBuilder<String> result3 = genericBuilder.setValue("world");
        System.out.println("协变返回类型结果: " + result3.build());

        // 4. Repository测试
        UserRepository userRepo = new UserRepository();
        Repository<User, String> genericRepo = userRepo;
        User user = new User("1", "Alice", 25);
        genericRepo.save(user);
        User found = genericRepo.findById("1");
        System.out.println("Repository调用结果: " + found);

        // 5. 泛型比较器测试
        StringComparator stringComp = new StringComparator();
        GenericComparator<String> genericComp = stringComp;
        String maxResult = genericComp.max("apple", "banana");
        System.out.println("泛型比较器结果: " + maxResult);

        // 6. 函数接口测试
        StringLengthFunction lengthFunc = new StringLengthFunction();
        GenericFunction<String, Integer> genericFunc = lengthFunc;
        Integer length = genericFunc.apply("bridge methods");
        System.out.println("函数接口调用结果: " + length);

        System.out.println();
        System.out.println("=== 桥方法分析提示 ===");
        System.out.println("1. 使用 javap -v -p BridgeMethodDemo\\$StringProcessor 查看桥方法");
        System.out.println("2. 查找标记为 'synthetic bridge' 的方法");
        System.out.println("3. 对比桥方法的签名与实际方法签名的差异");
        System.out.println("4. 观察桥方法如何调用实际的类型化方法");
    }

    /**
     * 主方法
     */
    public static void main(String[] args) {
        demonstrateBridgeMethods();
    }
}