set JAVA_HOME=C:\Prog\java\eclipse-jee-2023-09\eclipse\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.8.v20230831-1047\jre
set PATH=%JAVA_HOME%/bin;%PATH%
set MYCLASSPATH=../../target/myProcessApp.jar
cd /d "%~dp0"
java -cp %MYCLASSPATH% org.mycontrib.tp.ProcessApiApp
pause