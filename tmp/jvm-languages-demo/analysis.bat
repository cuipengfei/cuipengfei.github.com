@echo off
REM JVM类型系统反编译分析脚本
REM 用于自动化分析Java/Scala/Kotlin/Groovy的字节码差异

echo === JVM类型系统深度分析工具 ===
echo.

REM 检查是否提供了类名参数
if "%1"=="" (
    echo 使用方法: analysis.bat [类名]
    echo 示例: analysis.bat GenericContainer
    echo.
    echo 支持的类名:
    echo   - GenericContainer (Java)
    echo   - ScalaVarianceDemo (Scala)
    echo   - KotlinGenericsDemo (Kotlin)
    echo   - GroovyGenericsDemo (Groovy)
    echo   - AdvancedGenericsDemo (Java高级)
    echo   - ScalaAdvancedFeatures (Scala高级)
    goto :eof
)

set CLASS_NAME=%1
set CLASS_PATH=target/classes/com/example

REM 创建分析结果目录
if not exist analysis-results mkdir analysis-results
echo 分析结果将保存在: analysis-results/%CLASS_NAME%-analysis.txt

echo === 开始分析 %CLASS_NAME% === > analysis-results/%CLASS_NAME%-analysis.txt 2>&1

REM 检查类文件是否存在
if not exist "%CLASS_PATH%/%CLASS_NAME%.class" (
    echo 错误: 类文件 %CLASS_PATH%/%CLASS_NAME%.class 不存在
    echo 请先运行: mvn clean compile
    goto :eof
)

echo === 1. 基本字节码信息 === >> analysis-results/%CLASS_NAME%-analysis.txt
echo 时间: %date% %time% >> analysis-results/%CLASS_NAME%-analysis.txt
echo 类文件: %CLASS_PATH%/%CLASS_NAME%.class >> analysis-results/%CLASS_NAME%-analysis.txt
echo. >> analysis-results/%CLASS_NAME%-analysis.txt

REM 使用javap进行详细分析
javap -v -p "%CLASS_PATH%/%CLASS_NAME%.class" >> analysis-results/%CLASS_NAME%-analysis.txt 2>&1

REM 提取关键信息
echo. >> analysis-results/%CLASS_NAME%-analysis.txt
echo === 2. 关键信息提取 === >> analysis-results/%CLASS_NAME%-analysis.txt
echo 泛型签名信息: >> analysis-results/%CLASS_NAME%-analysis.txt
javap -v "%CLASS_PATH%/%CLASS_NAME%.class" | findstr /C:"Signature" >> analysis-results/%CLASS_NAME%-analysis.txt

echo. >> analysis-results/%CLASS_NAME%-analysis.txt
echo 桥方法信息: >> analysis-results/%CLASS_NAME%-analysis.txt
javap -v "%CLASS_PATH%/%CLASS_NAME%.class" | findstr /C:"bridge" >> analysis-results/%CLASS_NAME%-analysis.txt

echo. >> analysis-results/%CLASS_NAME%-analysis.txt
echo 类型擦除信息: >> analysis-results/%CLASS_NAME%-analysis.txt
javap -v "%CLASS_PATH%/%CLASS_NAME%.class" | findstr /C:"descriptor" >> analysis-results/%CLASS_NAME%-analysis.txt

echo. >> analysis-results/%CLASS_NAME%-analysis.txt
echo === 3. 字节码结构分析 === >> analysis-results/%CLASS_NAME%-analysis.txt
javap -v "%CLASS_PATH%/%CLASS_NAME%.class" | findstr /C:"Code:" /A:3 >> analysis-results/%CLASS_NAME%-analysis.txt

echo.
echo 分析完成！结果保存在: analysis-results/%CLASS_NAME%-analysis.txt