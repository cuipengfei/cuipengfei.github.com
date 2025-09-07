#!/bin/bash

# JVM多语言项目构建脚本
echo "=== JVM多语言项目构建开始 ==="

# 检查工具安装
echo "检查开发环境..."

# 检查Java
if ! command -v java &> /dev/null; then
    echo "错误: Java未安装"
    exit 1
fi

# 检查Maven
if ! command -v mvn &> /dev/null; then
    echo "错误: Maven未安装"
    exit 1
fi

# 检查Scala
if ! command -v scala &> /dev/null; then
    echo "警告: Scala未安装，某些功能可能受限"
fi

# 检查Kotlin
if ! command -v kotlinc &> /dev/null; then
    echo "警告: Kotlin未安装，某些功能可能受限"
fi

# 清理并构建
echo "开始构建多语言项目..."
mvn clean compile

if [ $? -eq 0 ]; then
    echo "✅ 构建成功!"
    echo ""
    echo "=== 运行演示程序 ==="
    mvn exec:java -Dexec.mainClass="com.example.Main"
else
    echo "❌ 构建失败"
    exit 1
fi

echo ""
echo "=== 反编译分析准备 ==="
echo "已生成的类文件位于: target/classes/com/example/"
echo ""
echo "可用的分析命令:"
echo "1. 查看字节码: javap -v -p target/classes/com/example/GenericContainer.class"
echo "2. 反编译分析: java -jar cfr.jar target/classes/com/example/GenericContainer.class"
echo "3. 运行测试: mvn test"