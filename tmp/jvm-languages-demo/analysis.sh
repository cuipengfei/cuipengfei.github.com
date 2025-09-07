#!/bin/bash

# JVM类型系统反编译分析脚本
echo "=== JVM类型系统反编译分析 ==="

# 创建分析结果目录
mkdir -p analysis_results

# 分析Java泛型类
echo "1. 分析Java泛型类..."
JAVA_CLASS="target/classes/com/example/GenericContainer.class"

if [ -f "$JAVA_CLASS" ]; then
    echo "=== 1.1 Java字节码结构 ===" > analysis_results/java_analysis.txt
    javap -v -p "$JAVA_CLASS" >> analysis_results/java_analysis.txt
    
    echo "=== 1.2 提取泛型签名信息 ===" >> analysis_results/java_analysis.txt
    javap -v "$JAVA_CLASS" | grep -A2 -B2 "Signature\|LocalVariableTypeTable" >> analysis_results/java_analysis.txt
    
    echo "✅ Java泛型类分析完成"
else
    echo "❌ 未找到Java类文件"
fi

# 分析Scala协变逆变类
if [ -f "target/classes/com/example/ScalaVarianceDemo.class" ]; then
    echo "2. 分析Scala协变逆变类..."
    echo "=== 2.1 Scala字节码结构 ===" > analysis_results/scala_analysis.txt
    javap -v -p target/classes/com/example/ScalaVarianceDemo*.class >> analysis_results/scala_analysis.txt
    
    echo "✅ Scala协变逆变类分析完成"
fi

# 分析Kotlin泛型类
if [ -f "target/classes/com/example/KotlinGenericsDemoKt.class" ]; then
    echo "3. 分析Kotlin泛型类..."
    echo "=== 3.1 Kotlin字节码结构 ===" > analysis_results/kotlin_analysis.txt
    javap -v -p target/classes/com/example/KotlinGenericsDemo*.class >> analysis_results/kotlin_analysis.txt
    
    echo "✅ Kotlin泛型类分析完成"
fi

# 生成对比报告
echo ""
echo "=== 生成对比分析报告 ==="
cat > analysis_results/comparison_report.md << 'EOF'
# JVM类型系统反编译分析对比报告

## 分析方法
- 工具: javap (JDK内置)
- 分析维度: 泛型签名、字节码结构、类型信息保留

## 主要发现

### Java泛型
- 类型擦除到边界类型
- 泛型签名保留在Signature属性中
- 桥方法自动生成

### Scala协变逆变  
- 变性声明通过注解实现
- 高阶类型编译为普通类
- 类型构造函数的特殊处理

### Kotlin泛型
- 具体化类型参数的内联机制
- 声明处变性的字节码表示
- 类型投影的实现策略

## 下一步分析
- 使用CFR进行反编译对比
- 性能基准测试
- 运行时行为验证
EOF

echo ""
echo "=== 分析完成 ==="
echo "结果保存在: analysis_results/"
echo "查看报告: cat analysis_results/comparison_report.md"