package para.html2apk.util;

import java.io.File;
import java.io.IOException;

public class GetPath {
    static String rootPath = GetPath.getRootDir();
    public static  String signShell = rootPath + "sign.bat";

    public static  String htmlPath = rootPath + "apk/app/assets/web/";
    public static  String androidManifestPath = rootPath + "apk/app/" + "AndroidManifest.xml";
    public static  String stringXmlPath = rootPath + "apk/app/res/values/" + "strings.xml";
    public static String smaliPath = rootPath + "apk/app/smali/" ;
    public static String mainActivePath = smaliPath + "app/para/webview/" + "MainActivity.smali";

    public static String appDir = rootPath + "apk/app";
    public static String appDir2 = rootPath + "apk/app2";
    public static String outApkName = "app.apk";
    public static String outApk = rootPath + "apk/" + outApkName;

    public static String temp = rootPath + "apk/temp/";
    public static String icoDir = rootPath + "apk/app/res/mipmap-hdpi/";
    public static String icoPath = icoDir + "ic_launcher.png"; //72
    public static String icoPath2 = icoDir + "ic_launcher_foreground.png"; //162
    public static String icoPath3 = icoDir + "ic_launcher_round.png"; //72


    public static String getRootDir(){
        File directory = new File("");// 参数为空
        String courseFile = null;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courseFile +  "/";
    }
}
