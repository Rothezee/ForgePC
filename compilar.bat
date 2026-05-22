@echo off
setlocal
cd /d "%~dp0"
if not exist "target\classes" mkdir "target\classes"
"C:\Program Files\BellSoft\LibericaJDK-22\bin\javac.exe" --release 21 -d "target\classes" -sourcepath "src\main\java" "src\main\java\Interfaz\Main.java" "src\main\java\Interfaz\Menu.java"
if errorlevel 1 (
    echo Compilacion fallida.
    exit /b 1
)
echo Compilacion OK. Clases en target\classes
exit /b 0
