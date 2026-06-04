@echo off
cd /d "%~dp0"
call compilar.bat
if errorlevel 1 exit /b 1

set "ABS_JAR=%USERPROFILE%\.m2\repository\org\netbeans\external\AbsoluteLayout\RELEASE290\AbsoluteLayout-RELEASE290.jar"
"C:\Program Files\BellSoft\LibericaJDK-22\bin\java.exe" -cp "target\classes;%ABS_JAR%" Interfaz.Main
