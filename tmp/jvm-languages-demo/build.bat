@echo off
echo === JVM多语言项目构建开始 ===

:: 检查Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误: Java未安装
    pause
    exit /b 1
)

:: 检查Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo 错误: Maven未安装
    pause
    exit /b 1
)

:: 清理并构建
echo 开始构建多语言项目...
call mvn clean compile

if %errorlevel% equ 0 (
    echo ✅ 构建成功!
    echo.
    echo === 运行演示程序 ===
    call mvn exec:java -Dexec.mainClass="com.example.Main"
) else (
    echo ❌ 构建失败
    pause
    exit /b 1
)

echo.
echo === 反编译分析准备 ===
echo 已生成的类文件位于: target\classes\com\example\
echo.
echo 可用的分析命令:
echo 1. 查看字节码: javap -v -p target\classes\com\example\GenericContainer.class
echo 2. 运行测试: mvn test
pause