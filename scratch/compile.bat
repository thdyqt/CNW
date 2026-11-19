@echo off
set "JAVA_HOME=C:\Program Files (x86)\BellSoft\LibericaJDK-21-Full"
set "JAVAC=%JAVA_HOME%\bin\javac.exe"

set "CP=c:\xampp\tomcat\lib\*;WEB-INF\lib\*;src"

if not exist "WEB-INF\classes" mkdir "WEB-INF\classes"

echo Finding Java files...
dir /s /b src\*.java > scratch\sources.txt

echo Compiling Java files...
"%JAVAC%" -encoding UTF-8 -cp "%CP%" -d "WEB-INF\classes" @scratch\sources.txt

if %ERRORLEVEL% equ 0 (
    echo Compilation successful!
) else (
    echo Compilation failed with error %ERRORLEVEL%
)
