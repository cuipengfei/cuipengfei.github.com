#!/bin/bash

# JVM类型系统反编译分析脚本
# 基于 cuipengfei 的 desugar scala 系列方法论

set -e

EXPERIMENT_DIR="jvm-type-experiments"
RESULTS_DIR="analysis-results"
TOOLS_DIR="decompile-tools"

echo "=== JVM类型系统反编译分析工具 ==="
echo "基于反编译驱动的语言特性探索方法论"
echo

# 创建结果目录
mkdir -p $RESULTS_DIR
mkdir -p $TOOLS_DIR

# 分析单个class文件
analyze_class() {
    local class_file=$1
    local output_dir=$2
    local base_name=$(basename "$class_file" .class)
    
    echo "--- 分析 $class_file ---"
    
    # 1. Javap详细字节码分析
    echo "1. 字节码指令分析..."
    javap -v -p "$class_file" > "$output_dir/${base_name}_javap.txt"
    
    # 2. 提取类型签名信息
    echo "2. 提取类型签名..."
    javap -v "$class_file" | grep -E "(Signature|LocalVariableTypeTable|generic)" > "$output_dir/${base_name}_signatures.txt" || true
    
    # 3. CFR反编译 (如果存在)
    if command -v java >/dev/null && [ -f "$TOOLS_DIR/cfr.jar" ]; then
        echo "3. CFR反编译..."
        java -jar "$TOOLS_DIR/cfr.jar" "$class_file" > "$output_dir/${base_name}_cfr.java" 2>/dev/null || true
    fi
    
    # 4. 方法和字段统计
    echo "4. 结构统计..."
    {
        echo "=== 类结构统计 ==="
        echo "方法数量: $(javap -p "$class_file" | grep -c "public\|private\|protected" || echo "0")"
        echo "字段数量: $(javap -p "$class_file" | grep -E "^\s+(private|public|protected).*;" | wc -l)"
        echo
        echo "=== 桥方法检测 ==="
        javap -p "$class_file" | grep -E "bridge|synthetic" || echo "未发现桥方法"
        echo
        echo "=== 内部类检测 ==="
        javap -v "$class_file" | grep -E "InnerClasses:|EnclosingMethod:" || echo "未发现内部类"
    } > "$output_dir/${base_name}_stats.txt"
    
    echo "   完成分析: $output_dir/${base_name}_*"
}

# 编译和分析Java代码
analyze_java() {
    local java_file=$1
    echo "=== Java泛型分析 ==="
    
    # 编译
    javac -cp . "$java_file"
    
    # 找到所有生成的class文件
    local base_name=$(basename "$java_file" .java)
    local java_results="$RESULTS_DIR/java-$base_name"
    mkdir -p "$java_results"
    
    # 分析主类和内部类
    find . -name "${base_name}*.class" | while read class_file; do
        analyze_class "$class_file" "$java_results"
    done
    
    echo "Java分析完成: $java_results"
    echo
}

# 编译和分析Scala代码
analyze_scala() {
    local scala_file=$1
    echo "=== Scala泛型分析 ==="
    
    # 检查scalac是否存在
    if ! command -v scalac >/dev/null; then
        echo "错误: 未找到scalac编译器"
        return 1
    fi
    
    # 编译
    scalac "$scala_file"
    
    # 找到所有生成的class文件
    local base_name=$(basename "$scala_file" .scala)
    local scala_results="$RESULTS_DIR/scala-$base_name"
    mkdir -p "$scala_results"
    
    # 分析主类和内部类
    find . -name "${base_name}*.class" | while read class_file; do
        analyze_class "$class_file" "$scala_results"
    done
    
    echo "Scala分析完成: $scala_results"
    echo
}

# 编译和分析Kotlin代码
analyze_kotlin() {
    local kotlin_file=$1
    echo "=== Kotlin泛型分析 ==="
    
    # 检查kotlinc是否存在
    if ! command -v kotlinc >/dev/null; then
        echo "错误: 未找到kotlinc编译器"
        return 1
    fi
    
    # 编译
    kotlinc "$kotlin_file"
    
    # 找到所有生成的class文件
    local base_name=$(basename "$kotlin_file" .kt)
    local kotlin_results="$RESULTS_DIR/kotlin-$base_name"
    mkdir -p "$kotlin_results"
    
    # 分析主类和内部类
    find . -name "${base_name}*.class" | while read class_file; do
        analyze_class "$class_file" "$kotlin_results"
    done
    
    echo "Kotlin分析完成: $kotlin_results"
    echo
}

# 生成对比报告
generate_comparison_report() {
    echo "=== 生成跨语言对比报告 ==="
    
    local report_file="$RESULTS_DIR/cross_language_comparison.md"
    
    {
        echo "# JVM类型系统跨语言对比分析报告"
        echo
        echo "生成时间: $(date)"
        echo "分析工具: javap, CFR反编译器"
        echo
        
        echo "## 泛型实现对比"
        echo
        
        # 对比类签名
        echo "### 类签名对比"
        echo "**Java:**"
        echo '```'
        find "$RESULTS_DIR" -name "java-*_signatures.txt" -exec head -5 {} \; | head -10
        echo '```'
        echo
        
        echo "**Scala:**"
        echo '```'
        find "$RESULTS_DIR" -name "scala-*_signatures.txt" -exec head -5 {} \; | head -10
        echo '```'
        echo
        
        echo "**Kotlin:**"
        echo '```'
        find "$RESULTS_DIR" -name "kotlin-*_signatures.txt" -exec head -5 {} \; | head -10
        echo '```'
        echo
        
        # 对比桥方法
        echo "### 桥方法生成对比"
        for lang in java scala kotlin; do
            echo "**$lang:**"
            find "$RESULTS_DIR" -name "$lang-*_stats.txt" -exec grep -A 3 "桥方法检测" {} \; | head -5
            echo
        done
        
        echo "## 分析总结"
        echo
        echo "1. **类型擦除影响**: 所有JVM语言都受到类型擦除的影响"
        echo "2. **签名保留**: 泛型信息在Signature属性中保留"
        echo "3. **桥方法**: 编译器自动生成桥方法保持二进制兼容性"
        echo "4. **语言差异**: 不同语言在泛型实现上的策略差异"
        echo
        echo "## 详细分析"
        echo "请查看各语言的详细分析文件："
        ls -la "$RESULTS_DIR"/ | grep -E "(java|scala|kotlin)"
        
    } > "$report_file"
    
    echo "对比报告生成完成: $report_file"
}

# 主函数
main() {
    echo "当前目录: $(pwd)"
    echo "开始JVM类型系统反编译分析..."
    echo
    
    # 进入实验目录
    if [ -d "$EXPERIMENT_DIR" ]; then
        cd "$EXPERIMENT_DIR"
        echo "进入实验目录: $EXPERIMENT_DIR"
    fi
    
    # 分析所有语言版本
    if [ -f "BasicGenericsExploration.java" ]; then
        analyze_java "BasicGenericsExploration.java"
    fi
    
    if [ -f "BasicGenericsExploration.scala" ]; then
        analyze_scala "BasicGenericsExploration.scala"
    fi
    
    if [ -f "BasicGenericsExploration.kt" ]; then
        analyze_kotlin "BasicGenericsExploration.kt"
    fi
    
    # 生成对比报告
    generate_comparison_report
    
    echo "=== 分析完成 ==="
    echo "结果保存在: $RESULTS_DIR/"
    echo "建议下一步："
    echo "1. 查看生成的反编译结果"
    echo "2. 对比不同语言的字节码差异"
    echo "3. 分析泛型实现机制的差异"
    echo "4. 继续下一个实验：协变逆变机制分析"
}

# 执行主函数
if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
    main "$@"
fi