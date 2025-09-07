package com.example;

import java.util.Optional;
import java.util.function.Function;

/**
 * Java泛型容器示例 - 展示泛型、协变逆变、类型擦除
 */
public class GenericContainer<T extends Number> {
    private T value;

    public GenericContainer(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    /**
     * 泛型方法示例 - 展示类型参数在方法中的使用
     */
    public <U extends Comparable<U>> Optional<U> transform(Function<T, U> mapper) {
        return Optional.ofNullable(mapper.apply(value));
    }

    /**
     * 协变示例 - 使用通配符上界
     */
    public void processCovariant(GenericContainer<? extends Number> container) {
        Number num = container.getValue(); // 安全：读取操作
        System.out.println("Processing covariant: " + num);
    }

    /**
     * 逆变示例 - 使用通配符下界
     */
    public void processContravariant(GenericContainer<? super Integer> container) {
        // 安全：写入操作
        container.setValue(42);
        System.out.println("Set value via contravariant");
    }

    /**
     * PECS原则示例 (Producer Extends, Consumer Super)
     */
    public static <U extends Number> void copy(
            GenericContainer<? extends U> source,
            GenericContainer<? super U> destination) {
        destination.setValue(source.getValue());
    }
}