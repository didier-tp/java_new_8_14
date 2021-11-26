cd /d "%~dp0"
REM this script should be run after mvn package or ...

set JAVA_HOME=C:\Program Files\Java\jdk-17
REM NB: on windows10 , with jdk17 , wix tools set is a required dependency of jpackage
REM wix311-binaries.zip can be download from https://github.com/wixtoolset
set WIX_HOME=C:\Prog\wix311
set PATH=%PATH%;%JAVA_HOME%\bin;%WIX_HOME%
REM type=msi or exe for windows , deb or rpm for windows , dmg or pkg on mac
set TYPE=msi
set NAME=JPackageDemoApp
set MAIN_CLASS=tp.j15_16_17.MainClassForJPackageTestApp
set INPUT_JAR_DIR=../target
set MAIN_JAR=tp_java_8_14-0.0.1-SNAPSHOT.jar
REM set OPTIONS=--win-console --java-options '--enable-preview'
REM set OPTIONS=--java-options '--enable-preview'
set OPTIONS=

jpackage --input %INPUT_JAR_DIR%  --name %NAME% --main-jar %MAIN_JAR%  --main-class %MAIN_CLASS% --type %TYPE%  %OPTIONS%
REM fichier construit : JPackageDemoApp.msi

REM apres installation de JPackageDemoApp.msi
REM C:\Program Files\JPackageDemoApp/JPackageDemoApp.exe et runtime java

REM https://www.baeldung.com/java14-jpackage
REM https://www.devdungeon.com/content/use-jpackage-create-native-java-app-installers
