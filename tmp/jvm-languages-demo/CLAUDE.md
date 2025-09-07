# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 提供在此代码库中工作的指导。

**重要提醒**: 本项目专为 JVM 类型系统研究设计，主要通过字节码反编译进行对比分析。开发时需关注类型系统实现差异而非常规业务逻辑。

## 项目概述

这是一个专注于类型系统研究的 JVM 多语言演示项目，专门用于研究泛型、协变/逆变、类型擦除、Monad 以及 Java、Scala 和 Kotlin 之间的跨语言互操作性。该项目为字节码分析和基于反编译的 JVM 类型系统探索提供了一个综合平台。

## 架构

### 多语言源码结构

项目按语言包组织，Maven 统一编译管理：

```
src/main/
├── java/com/example/           # Java 实现
│   ├── advanced/               # 高级类型系统特性  
│   ├── bridges/                # 桥接方法生成演示
│   ├── benchmark/              # 性能测试
│   └── interop/                # 跨语言互操作性
├── scala/com/example/          # Scala 实现
│   ├── advanced/               # Scala 3 高级类型，monads
│   └── freemonads/             # Free monad 实现
└── kotlin/com/example/         # Kotlin 实现
    ├── generics/               # Kotlin 特定的泛型特性
    └── interop/                # Kotlin 互操作示例
```

### 核心组件

- **泛型容器**: 三种语言中的核心泛型实现对比
- **协变/逆变演示**: 各语言间的协变/逆变实现
- **类型擦除补偿**: Jackson/Gson 风格的 TypeReference 模式
- **桥接方法分析**: 为泛型继承生成的合成方法
- **跨语言互操作**: 安全的类型转换和函数互操作性
- **Free Monads**: 高级函数式编程抽象 (Scala)
- **性能基准测试**: 基于 JMH 的类型系统性能分析

### 编译管道

Maven 协调特定的编译顺序以确保跨语言兼容性：
1. **Java** (JDK 21): 基础实现和接口
2. **Scala** (3.6.2): 高级类型特性，构建在 Java 基础之上
3. **Kotlin** (2.1.0): 与已编译的 Java 和 Scala 类互操作

## 命令

### 开发命令

```bash
# 构建整个多语言项目
mvn clean compile

# 运行主演示程序
mvn exec:java -Dexec.mainClass="com.example.Main"

# 运行测试 (JUnit 5 + Kotlin test)
mvn test

# 生成包含所有依赖的可执行 JAR
mvn package

# 运行性能基准测试 (如果可用)
java -cp target/classes com.example.benchmark.TypeSystemBenchmark
```

### 平台特定构建脚本

```bash
# Linux/macOS 全面构建和分析
./build.sh

# Windows 构建 (设置 Java 21 环境)  
build.bat

# 自动字节码分析
./analysis.sh

# 语言对比分析 (Windows)
compare-languages.bat
```

### 反编译和分析命令

```bash
# 查看带泛型签名的字节码
javap -v -p target/classes/com/example/GenericContainer.class

# 提取泛型签名信息
javap -v target/classes/com/example/advanced/TypeReferenceDemo.class | grep -A2 "Signature"

# 分析桥接方法
javap -v target/classes/com/example/bridges/BridgeMethodDemo\$StringProcessor.class | grep "bridge"

# CFR 反编译 (当 CFR 可用时)
java -jar cfr.jar target/classes/com/example/freemonads/FreeMonadDemo.class

# 使用 JVM 诊断进行性能分析
java -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining com.example.Main
```

## 开发工作流

### 研究驱动的开发流程

本项目遵循 **反编译优先的研究方法论**：

1. **实现特性**: 用 Java/Scala/Kotlin 编写等效功能
2. **编译为字节码**: 使用 Maven 多语言编译
3. **反编译分析**: 使用 javap、CFR 或其他工具分析字节码
4. **跨语言对比**: 比较各语言的实现策略
5. **性能基准测试**: 测量不同类型系统特性的开销
6. **文档化结果**: 更新分析结果和对比见解

### 关键分析点

分析生成的字节码时，重点关注：

- **签名属性**: 泛型信息如何被保留
- **桥接方法**: 编译器生成的合成方法
- **类型擦除**: 哪些信息丢失 vs 被补偿
- **协变/逆变注解**: 协变/逆变如何编码
- **跨语言桥接**: 字节码中的互操作机制

### 测试策略

- **编译测试**: 验证三种语言能够成功一起编译
- **运行时类型测试**: 验证跨语言边界的类型安全性  
- **性能测试**: JMH 基准测试用于类型系统开销分析
- **反编译验证**: 在可能的情况下进行自动化字节码分析

**注意**: 本项目重点在于类型系统演示，目前测试覆盖有限 (仅在 src/test/java 中有基础测试)。

## 技术配置

### Maven 多语言设置

本项目使用专门的 Maven 配置进行 JVM 多语言开发：

- **Java**: 标准编译，保留泛型签名
- **Scala**: scala-maven-plugin 与 Scala 3 兼容
- **Kotlin**: kotlin-maven-plugin 与 Java/Scala 互操作
- **构建顺序**: 通过 Maven 阶段绑定强制执行，确保正确的编译顺序

### 依赖和版本

- **JDK**: 21 (所有语言编译目标所需)
- **Scala**: 3.6.2 (具有高级类型特性的最新稳定版)
- **Kotlin**: 2.1.0 (具有协程和高级泛型)
- **测试**: JUnit 5 + Kotlin Test 集成
- **基准测试**: JMH 1.37 用于性能分析
- **类型工具**: Jackson + Gson 用于 TypeReference 演示

### 分析结果结构

项目在以下位置生成分析产物：
- `analysis_results/` - 自动化字节码分析输出
- `analysis-results/comparison/` - compare-languages.bat 的多语言对比结果
- `target/classes/` - 编译的字节码用于手动检查  
- `decompiled/` - 反编译的源码用于对比 (如适用)

## 研究扩展

### 当前高级特性 (2024)

- **TypeReference 模式**: 类型擦除补偿机制
- **Scala 3 联合/交集类型**: 高级类型代数
- **存在类型**: 深度通配符捕获场景  
- **Free Monads**: 范畴理论抽象的实践
- **跨语言单子操作**: 类型安全的转换

### 推荐研究路径

1. **初学者**: 从 Java 泛型和类型擦除基础开始
2. **中级**: 比较 Scala/Kotlin 协变/逆变机制  
3. **高级**: 分析跨语言互操作桥接生成
4. **专家**: 研究 Free Monad 字节码和性能影响

本项目专为对在字节码级别理解 JVM 类型系统感兴趣的开发者和研究者设计，提供了进行比较语言分析的结构化环境。