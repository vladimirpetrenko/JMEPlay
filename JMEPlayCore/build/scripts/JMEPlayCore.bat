@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  JMEPlayCore startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and JME_PLAY_CORE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\JMEPlayCore-1.0.jar;%APP_HOME%\lib\JMEPlayAssets-1.0.jar;%APP_HOME%\lib\JMEPlayImageView-1.0.jar;%APP_HOME%\lib\JMEPlaySound-1.0.jar;%APP_HOME%\lib\JMEPlayConsole-1.0.jar;%APP_HOME%\lib\JMEPlayDefinition-1.0.jar;%APP_HOME%\lib\spring-boot-starter-1.5.2.RELEASE.jar;%APP_HOME%\lib\jme3-lwjgl3-3.1.0-stable.jar;%APP_HOME%\lib\jme3-jogl-3.1.0-stable.jar;%APP_HOME%\lib\jme3-desktop-3.1.0-stable.jar;%APP_HOME%\lib\jme3-jogg-3.1.0-stable.jar;%APP_HOME%\lib\jme3-core-3.1.0-stable.jar;%APP_HOME%\lib\spring-boot-autoconfigure-1.5.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-1.5.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-logging-1.5.2.RELEASE.jar;%APP_HOME%\lib\spring-context-4.3.7.RELEASE.jar;%APP_HOME%\lib\spring-aop-4.3.7.RELEASE.jar;%APP_HOME%\lib\spring-beans-4.3.7.RELEASE.jar;%APP_HOME%\lib\spring-expression-4.3.7.RELEASE.jar;%APP_HOME%\lib\spring-core-4.3.7.RELEASE.jar;%APP_HOME%\lib\snakeyaml-1.17.jar;%APP_HOME%\lib\lwjgl-platform-3.0.0b-natives-windows.jar;%APP_HOME%\lib\lwjgl-platform-3.0.0b-natives-linux.jar;%APP_HOME%\lib\lwjgl-platform-3.0.0b-natives-osx.jar;%APP_HOME%\lib\lwjgl-glfw-3.1.2.jar;%APP_HOME%\lib\lwjgl-glfw-3.1.2-natives-windows.jar;%APP_HOME%\lib\lwjgl-glfw-3.1.2-natives-linux.jar;%APP_HOME%\lib\lwjgl-glfw-3.1.2-natives-macos.jar;%APP_HOME%\lib\lwjgl-jemalloc-3.1.2.jar;%APP_HOME%\lib\lwjgl-jemalloc-3.1.2-natives-windows.jar;%APP_HOME%\lib\lwjgl-jemalloc-3.1.2-natives-linux.jar;%APP_HOME%\lib\lwjgl-jemalloc-3.1.2-natives-macos.jar;%APP_HOME%\lib\lwjgl-openal-3.1.2.jar;%APP_HOME%\lib\lwjgl-openal-3.1.2-natives-windows.jar;%APP_HOME%\lib\lwjgl-openal-3.1.2-natives-linux.jar;%APP_HOME%\lib\lwjgl-openal-3.1.2-natives-macos.jar;%APP_HOME%\lib\lwjgl-opencl-3.1.2.jar;%APP_HOME%\lib\lwjgl-opengl-3.1.2.jar;%APP_HOME%\lib\lwjgl-opengl-3.1.2-natives-windows.jar;%APP_HOME%\lib\lwjgl-opengl-3.1.2-natives-linux.jar;%APP_HOME%\lib\lwjgl-opengl-3.1.2-natives-macos.jar;%APP_HOME%\lib\lwjgl-stb-3.1.2.jar;%APP_HOME%\lib\lwjgl-stb-3.1.2-natives-windows.jar;%APP_HOME%\lib\lwjgl-stb-3.1.2-natives-linux.jar;%APP_HOME%\lib\lwjgl-stb-3.1.2-natives-macos.jar;%APP_HOME%\lib\lwjgl-3.1.2.jar;%APP_HOME%\lib\lwjgl-3.1.2-natives-windows.jar;%APP_HOME%\lib\lwjgl-3.1.2-natives-linux.jar;%APP_HOME%\lib\lwjgl-3.1.2-natives-macos.jar;%APP_HOME%\lib\jogl-all-main-2.3.2.jar;%APP_HOME%\lib\joal-main-2.3.2.jar;%APP_HOME%\lib\gluegen-rt-main-2.3.2.jar;%APP_HOME%\lib\javafxsvg-1.2.1.jar;%APP_HOME%\lib\richtextfx-0.7-M5.jar;%APP_HOME%\lib\logback-classic-1.1.11.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.24.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.24.jar;%APP_HOME%\lib\log4j-over-slf4j-1.7.24.jar;%APP_HOME%\lib\xmlgraphics-commons-2.1.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\jogl-all-2.3.2.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-android-aarch64.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-android-armv6.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-linux-amd64.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-linux-armv6.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-linux-armv6hf.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-linux-i586.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-macosx-universal.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-solaris-amd64.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-solaris-i586.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-windows-amd64.jar;%APP_HOME%\lib\jogl-all-2.3.2-natives-windows-i586.jar;%APP_HOME%\lib\joal-2.3.2.jar;%APP_HOME%\lib\joal-2.3.2-natives-android-aarch64.jar;%APP_HOME%\lib\joal-2.3.2-natives-android-armv6.jar;%APP_HOME%\lib\joal-2.3.2-natives-linux-amd64.jar;%APP_HOME%\lib\joal-2.3.2-natives-linux-armv6.jar;%APP_HOME%\lib\joal-2.3.2-natives-linux-armv6hf.jar;%APP_HOME%\lib\joal-2.3.2-natives-linux-i586.jar;%APP_HOME%\lib\joal-2.3.2-natives-macosx-universal.jar;%APP_HOME%\lib\joal-2.3.2-natives-solaris-amd64.jar;%APP_HOME%\lib\joal-2.3.2-natives-solaris-i586.jar;%APP_HOME%\lib\joal-2.3.2-natives-windows-amd64.jar;%APP_HOME%\lib\joal-2.3.2-natives-windows-i586.jar;%APP_HOME%\lib\gluegen-rt-2.3.2.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-android-aarch64.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-android-armv6.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-linux-amd64.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-linux-armv6.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-linux-armv6hf.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-linux-i586.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-macosx-universal.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-solaris-amd64.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-solaris-i586.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-windows-amd64.jar;%APP_HOME%\lib\gluegen-rt-2.3.2-natives-windows-i586.jar;%APP_HOME%\lib\batik-transcoder-1.8.jar;%APP_HOME%\lib\j-ogg-all-1.0.0.jar;%APP_HOME%\lib\wellbehavedfx-0.3.jar;%APP_HOME%\lib\undofx-1.2.2.jar;%APP_HOME%\lib\flowless-0.5.2.jar;%APP_HOME%\lib\reactfx-2.0-M5.jar;%APP_HOME%\lib\logback-core-1.1.11.jar;%APP_HOME%\lib\slf4j-api-1.7.24.jar;%APP_HOME%\lib\batik-bridge-1.8.jar;%APP_HOME%\lib\batik-script-1.8.jar;%APP_HOME%\lib\batik-anim-1.8.jar;%APP_HOME%\lib\batik-gvt-1.8.jar;%APP_HOME%\lib\batik-svggen-1.8.jar;%APP_HOME%\lib\batik-svg-dom-1.8.jar;%APP_HOME%\lib\batik-parser-1.8.jar;%APP_HOME%\lib\batik-awt-util-1.8.jar;%APP_HOME%\lib\batik-dom-1.8.jar;%APP_HOME%\lib\batik-xml-1.8.jar;%APP_HOME%\lib\batik-css-1.8.jar;%APP_HOME%\lib\batik-util-1.8.jar;%APP_HOME%\lib\batik-ext-1.8.jar;%APP_HOME%\lib\xalan-2.7.0.jar;%APP_HOME%\lib\xml-apis-ext-1.3.04.jar;%APP_HOME%\lib\commons-io-1.3.1.jar

@rem Execute JMEPlayCore
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %JME_PLAY_CORE_OPTS%  -classpath "%CLASSPATH%" com.jmeplay.core.gui.JMEPlay %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable JME_PLAY_CORE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%JME_PLAY_CORE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
