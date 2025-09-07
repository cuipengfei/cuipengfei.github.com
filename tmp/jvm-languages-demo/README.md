# JVM多语言类型系统演示项目

这是一个展示如何在同一个Maven项目中同时使用Java、Scala和Kotlin的演示项目，专门用于JVM类型系统（泛型、协变逆变、类型擦除、Monad）的研究和学习。

## 项目特色

✅ **三语言共存**: 同时支持Java、Scala、Kotlin  
✅ **类型系统演示**: 展示各语言的泛型、协变逆变实现  
✅ **反编译友好**: 便于字节码分析和对比研究  
✅ **现代版本**: Java 17, Scala 3.3, Kotlin 1.9  

## 目录结构

```
jvm-languages-demo/
├── pom.xml                       # Maven配置文件
├── src/main/
│   ├── java/com/example/         # Java代码
│   │   ├── GenericContainer.java # Java泛型演示
│   │   └── Main.java            # 主程序
│   ├── scala/com/example/        # Scala代码
│   │   └── ScalaVarianceDemo.scala # Scala协变逆变
│   └── kotlin/com/example/       # Kotlin代码
│       └── KotlinGenericsDemo.kt # Kotlin泛型投影
├── build.bat                     # Windows构建脚本
└── analysis.bat                  # 分析脚本
```

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.8+

### 构建项目
```bash
# Windows
build.bat

# 或者手动
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.Main"
```

### 运行分析
```bash
# 查看生成的字节码
javap -v -p target/classes/com/example/GenericContainer.class

# 运行测试
mvn test
```

## 类型系统演示内容

### Java部分
- 泛型容器和泛型方法
- 协变逆变（通配符上界/下界）
- PECS原则（Producer Extends, Consumer Super）
- 类型擦除的影响

### Scala部分
- 声明处协变逆变（`+T`, `-T`）
- 高阶类型和类型构造函数
- 类型lambda表达式
- 变性在继承中的应用

### Kotlin部分
- 声明处和使用处变性
- 具体化类型参数（`reified`）
- 类型投影（`out T`, `in T`）
- 密封类与泛型结合

## 研究用途

这个项目特别适合：
- **类型系统研究**: 对比不同语言的类型系统实现
- **字节码分析**: 反编译研究语言特性的底层实现
- **教学演示**: 展示JVM生态的多样性
- **最佳实践**: 学习多语言互操作的技巧

## 扩展建议

1. **添加Groovy支持**: 展示动态语言的类型系统
2. **性能基准测试**: 对比不同语言实现的性能差异
3. **反编译工具集成**: 自动化的字节码分析流程
4. **更多Monad示例**: 展示函数式编程模式

## 技术细节

### Maven多语言配置要点
1. **编译顺序**: Java -> Scala -> Kotlin
2. **依赖管理**: 各语言标准库的协调
3. **源码路径**: 多源码目录的统一管理
4. **字节码兼容**: 统一的Java 17目标版本

### 反编译分析建议
1. **对比分析**: 同一功能的不同语言实现
2. **字节码结构**: Signature属性的差异
3. **桥方法**: 协变逆变的编译器生成代码
4. **类型信息**: 运行时类型保留情况

## 贡献指南

欢迎提交Issue和Pull Request来改进这个项目。建议的贡献方向：
- 添加更多类型系统演示案例
- 完善文档和注释
- 优化构建配置
- 添加更多JVM语言支持

## 许可证

MIT License - 自由使用和学习参考

---

**注意**: 这个项目主要用于学习和研究目的，展示了JVM类型系统的复杂性。在实际项目中，建议根据团队技能选择合适的单一语言。但理解多语言的类型系统差异对于架构设计和性能优化非常有价值。这个项目的配置可以作为你反编译驱动研究的起点！你可以基于这个框架进行更深入的字节码分析。参考你在Desugar Scala系列中的成功经验，建议从Java泛型的反编译开始，然后逐步探索Scala和Kotlin的差异。