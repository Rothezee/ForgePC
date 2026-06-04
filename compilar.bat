@echo off
setlocal EnableDelayedExpansion
cd /d "%~dp0"

set "JAVA_HOME=C:\Program Files\BellSoft\LibericaJDK-22"
set "JAVAC=%JAVA_HOME%\bin\javac.exe"

if not exist "%JAVAC%" (
    echo No se encontro javac en %JAVAC%
    exit /b 1
)

set "ABS_JAR=%USERPROFILE%\.m2\repository\org\netbeans\external\AbsoluteLayout\RELEASE290\AbsoluteLayout-RELEASE290.jar"
if not exist "%ABS_JAR%" (
    echo Descargando dependencias Maven...
    where mvn >nul 2>&1
    if errorlevel 1 (
        echo Instale Maven o en NetBeans: clic derecho en el proyecto - Reload POM
        exit /b 1
    )
    call mvn -q dependency:resolve
    if errorlevel 1 exit /b 1
)

if not exist "target\classes" mkdir "target\classes"

set "SOURCES="
for /r "src\main\java" %%f in (*.java) do set "SOURCES=!SOURCES! "%%f""

"%JAVAC%" --release 21 -encoding UTF-8 -d "target\classes" -cp "target\classes;%ABS_JAR%" !SOURCES!
if errorlevel 1 (
    echo Compilacion fallida.
    exit /b 1
)

echo Compilacion OK.
exit /b 0
