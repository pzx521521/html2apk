#### springboot 下 java 调用 apktool
+ 从官网下apktool.jar
+ 把依赖加入gradle 
dependencies 下添加
```
compile files('tool/apktool.jar')
```
但是apktool和springboot 里面的依赖冲突了 报 **java.lang.NoSuchMethodError**
从报错种找到相关包:

+ org.yaml.snakeyaml
springboot 下是1.25
apktool 下是 1.12
好吧
+ 要么编译springboot 要么编译 apktool 
+ 本着新版本应该会向老版本兼容的原则 尝试把 apktool 的 snakeyaml 从1.12提升到1.25
#### 编译 apktool 
+ github 下载源码
参考编译说明:
https://ibotpeaches.github.io/Apktool/build/
+ 修改 build.gradle  Line:122 切换版本
```
snakeyaml: 'org.yaml:snakeyaml:1.12:android',
修改为
snakeyaml: 'org.yaml:snakeyaml:1.25:android',
```
+ gradlew.bat 秒闪后退出 看不清报错信息
修改gradlew.bat  在 :execute 后加 pause 看报错信息
```
:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%
pause
```
+ 看到原因 Unsupported class file major version 57
  好吧 jdk13 不支持 换jdk 8 
  重新编译成功

+ 文件在: **brut.apktool/apktool-cli**, 没看到这句话 纠结了1kb好久
```
After build completes you should have a jar file at: ./brut.apktool/apktool-cli/build/libs/apktool-xxxxx.jar
```

```
import brut.androlib.Androlib;
import brut.androlib.AndrolibException;
import brut.androlib.ApkDecoder;
import brut.common.BrutException;
import brut.directory.DirectoryException;

import java.io.File;
import java.io.IOException;

public class ApkToolKit {
    static String rootPath = GetPath.getRootDir();
    static String appDir = rootPath + "apk/app";
    static String appDir2 = rootPath + "apk/app2";
    static String outApk = rootPath + "apk/app.apk";
    public static void Build(){
        Androlib instance = new Androlib();
        try {
            instance.build(new File(appDir) , new File(outApk));
        } catch (BrutException e) {
            e.printStackTrace();
        }
        System.out.println("buildApk done");
    }

    public static void Decde(){
        try {
            File inFile = new File(outApk);
            ApkDecoder decoder = new ApkDecoder();
            decoder.setOutDir(new File(appDir2));
            decoder.setApkFile(inFile);
            decoder.decode();
        } catch (IOException|AndrolibException | DirectoryException e) {
            e.printStackTrace();
        }
    }
}
```
