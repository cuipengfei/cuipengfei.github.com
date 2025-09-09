# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

这是一个 **JVM 多语言类型系统演示项目**，专门用于研究和学习 Java、Scala、Kotlin 等 JVM 语言的类型系统（泛型、协变逆变、类型擦除、Monad、跨语言互操作）。项目采用反编译驱动的研究方法，通过字节码分析深入理解各语言的类型系统实现机制。

当前实现主要专注于 **Java 类型擦除深度分析**，是整个多语言类型系统研究的第一阶段。

## 核心研究内容

### 当前阶段：Java 类型擦除分析
- **RuntimeTypeBypassDemo.java**: 演示通过反射绕过编译时类型检查的机制
- **GenericClassDemo.java**: 泛型类在字节码中的表示和签名保留机制
- **GenericReflectionTest.java**: 反射 API 获取泛型信息的能力边界测试
- **GenericsBenchmark.java**: JMH 性能基准测试，量化泛型抽象的运行时开销

### 字节码分析工件
- **bytecode-analysis-*.txt**: javap 完整反编译输出，保存所有字节码细节
- **runtime-output-*.txt**: 程序实际运行输出，用于验证字节码分析
- **类型擦除深度分析.md**: 自包含的完整技术分析文档，包含源码、字节码和结论

## 开发命令

### 构建与编译
```bash
# 清理并编译所有源代码（Java、Scala、Kotlin）
mvn clean compile

# 编译并运行主要演示
mvn exec:java -Dexec.mainClass="com.example.RuntimeTypeBypassDemo"
mvn exec:java -Dexec.mainClass="com.example.GenericReflectionTest"

# 运行性能基准测试
mvn test-compile exec:java -Dexec.mainClass="com.example.benchmark.GenericsBenchmark"
```

### 字节码分析命令
```bash
# 一次性生成所有字节码分析文件（推荐工作流程）
javap -v target/classes/com/example/RuntimeTypeBypassDemo.class > bytecode-analysis-RuntimeTypeBypassDemo.txt
javap -v target/classes/com/example/GenericClassDemo.class > bytecode-analysis-GenericClassDemo.txt  
javap -v target/classes/com/example/GenericReflectionTest.class > bytecode-analysis-GenericReflectionTest.txt

# 保存程序运行输出用于分析
java -cp target/classes com.example.RuntimeTypeBypassDemo > runtime-output-RuntimeTypeBypassDemo.txt 2>&1
java -cp target/classes com.example.GenericReflectionTest > runtime-output-GenericReflectionTest.txt 2>&1
```

### 高级字节码分析
```bash
# 查看特定的类型擦除模式
grep -A5 -B5 "invokeinterface.*List.add" bytecode-analysis-RuntimeTypeBypassDemo.txt

# 查看 Signature 属性中的泛型信息
grep -A2 -B2 "Signature:" bytecode-analysis-GenericClassDemo.txt

# 查看编译器插入的类型转换
grep -A3 -B3 "checkcast" bytecode-analysis-RuntimeTypeBypassDemo.txt

# 查看本地变量类型表（调试信息）
grep -A10 -B5 "LocalVariableTypeTable" bytecode-analysis-*.txt
```

## 项目架构

### 多语言编译体系
- **编译顺序**: Java → Scala → Kotlin (Maven 插件协调)
- **目标版本**: 统一编译到 Java 21 字节码
- **包管理**: Maven 统一管理多语言依赖

### 当前源码结构
```
src/main/java/com/example/
├── RuntimeTypeBypassDemo.java      # 类型擦除漏洞演示 
├── GenericClassDemo.java           # 泛型类字节码分析
└── GenericReflectionTest.java      # 反射 API 能力测试

src/test/java/com/example/
└── benchmark/
    └── GenericsBenchmark.java      # JMH 性能基准测试
```

### 规划中的扩展结构 (基于 README.md)
```
src/main/
├── java/com/example/               # Java 实现
├── scala/com/example/              # Scala 实现 (待添加)
├── kotlin/com/example/             # Kotlin 实现 (待添加)
└── groovy/com/example/             # Groovy 实现 (待添加)
```

## 核心研究方法

### 反编译驱动的研究流程
1. **编写目标特性代码** - 实现要研究的语言特性
2. **编译到字节码** - 使用各语言编译器生成 .class 文件
3. **反编译字节码分析** - javap 详细分析字节码实现
4. **横向对比研究** - 对比不同语言的实现差异
5. **性能量化测试** - JMH 基准测试量化性能影响
6. **总结实现规律** - 从字节码层面理解语言设计决策

### 分析工具链
- **javap**: 字节码反编译，分析方法签名和 Signature 属性
- **JMH**: 微基准测试框架，量化抽象开销
- **反射 API**: 运行时获取泛型信息，测试补偿机制边界

## 关键技术洞察

### 类型擦除机制核心发现
- **运行时擦除**: 泛型参数在字节码中被原始类型替代 (`List<String>` → `List`)
- **编译器补偿**: 自动插入 `checkcast` 指令在类型转换点进行运行时检查
- **延迟错误**: 类型错误延迟到 `checkcast` 执行时，而非添加时

### 补偿机制分析
- **Signature 属性**: 始终保留完整泛型签名，支持反射获取声明时类型信息
- **LocalVariableTypeTable**: 调试信息，仅在 `javac -g` 时存在
- **反射限制**: 可获取声明信息，无法获取运行时实例的具体类型参数

### 安全性影响
- **反射绕过**: 可通过反射调用 `List.add(Object)` 绕过编译时类型检查
- **运行时检查**: 错误在类型转换时抛出 `ClassCastException`，而非操作时
- **直接访问安全**: 获取 `Object` 类型不触发类型检查

## 开发最佳实践

### 研究代码规范
- **单一特性原则**: 每个演示类专注一个具体的类型系统特性
- **自包含设计**: 演示代码应可独立运行，包含完整的测试用例
- **充分注释**: 标明字节码分析的关键观察点和预期行为
- **对比友好**: 相同功能的多语言实现便于横向对比

### 分析文档要求
- **完整性**: 包含源码、字节码片段、运行结果的完整分析链
- **可重现性**: 提供完整的命令序列，他人可重现分析过程
- **工作流优化**: 一次性保存所有分析文件，避免重复运行 javap

### 性能测试指南
- **JMH 基准**: 使用 JMH 框架量化类型抽象的运行时开销
- **多维对比**: 原始类型 vs 泛型、不同抽象级别的性能差异
- **JIT 影响**: 考虑长时间运行后的编译器优化效果

## 扩展开发指南

### 添加新语言支持
1. 在 `pom.xml` 中添加相应语言的编译插件和依赖
2. 创建对应的源码目录结构 (`src/main/{language}`)
3. 实现与 Java 版本功能等价的演示代码
4. 生成字节码分析文件进行对比研究

### 添加新特性研究
1. 在 Java 中实现该特性的演示代码
2. 使用标准字节码分析流程生成分析文件
3. 更新技术文档记录关键发现
4. 如适用，添加 JMH 性能基准测试

## 环境配置

### 必需工具
- **JDK 21**: 编译和运行环境
- **Maven 3.8+**: 构建工具和依赖管理
- **javap**: 字节码分析 (JDK 自带)

### 可选工具
- **CFR/JD-Core**: 第三方反编译器，提供更清晰的反编译输出
- **JProfiler/JConsole**: JVM 性能分析工具