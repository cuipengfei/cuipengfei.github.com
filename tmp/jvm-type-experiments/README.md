# JVM 类型系统实验说明

这个目录包含了 JVM 类型系统深度研究的第一阶段实验：**基础泛型反编译分析**。

## 实验目标

通过反编译分析理解 Java、Scala、Kotlin 三种语言的泛型实现机制差异。

## 实验文件

### 源代码文件

- `BasicGenericsExploration.java` - Java 版本的泛型探索
- `BasicGenericsExploration.scala` - Scala 版本的泛型探索
- `BasicGenericsExploration.kt` - Kotlin 版本的泛型探索

### 分析工具

- `analyze_bytecode.sh` - 自动化反编译分析脚本

## 实验内容对比

| 特性     | Java                        | Scala                  | Kotlin                        |
| -------- | --------------------------- | ---------------------- | ----------------------------- |
| 基础泛型 | `<T extends Comparable<T>>` | `[T <: Comparable[T]]` | `<T> where T : Comparable<T>` |
| 协变     | `? extends T`               | 声明处 `+T`            | 声明处 `out T`                |
| 逆变     | `? super T`                 | 声明处 `-T`            | 声明处 `in T`                 |
| 高级特性 | 有限的通配符                | 高阶类型、类型类       | 具体化类型参数                |

## 使用方法

1. **编译和分析**:

```bash
chmod +x analyze_bytecode.sh
./analyze_bytecode.sh
```

2. **查看结果**:

```bash
ls -la analysis-results/
```

## 预期发现

### 共同特征

- 类型擦除：所有泛型参数在运行时被擦除
- 签名保留：Signature 属性保留编译期类型信息
- 桥方法：编译器生成桥方法保持二进制兼容性

### 语言差异

- **Java**: 简洁的通配符实现，重点在使用处变性
- **Scala**: 丰富的类型系统特性，编译生成更多辅助类
- **Kotlin**: 平衡实用性和表达力，inline reified 特性独特

## 后续实验

完成这个实验后，继续进行：

1. 协变逆变机制深度分析
2. 类型擦除补偿机制探索
3. Monad 实现模式对比

## 分析维度

每个实验都从以下维度进行分析：

- **字节码结构**: 类、方法、字段的字节码表示
- **类型信息**: 签名表中的泛型信息保留
- **性能影响**: 抽象层次对运行时的影响
- **互操作性**: 跨语言调用的类型安全性

基于 @cuipengfei 的 desugar Scala 系列方法论设计。
