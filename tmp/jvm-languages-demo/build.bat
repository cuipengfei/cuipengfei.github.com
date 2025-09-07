@echo off
echo Setting Java 21 environment...
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.7.6-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Java version:
java -version

echo Maven version:
mvn -version

echo Starting compilation...
mvn clean compile
pause
