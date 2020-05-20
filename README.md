# build Apk with html
[中文点这里](README_zh.md)
#### Start
+ build Apk with html, which can be local(index_html) or network(http)
+ Using HTML as UI, can be used as server or local 
+ Three ways to local_html: sinlge-file, zip or  folders(only support chrome)

#### require
+ java 1.8+

#### theory
+ Decompilation through apktool[^1]
+ Modify decompiled content
+ Recompile and sign
[^1]: 1 apktool.jar -> org.yaml.snakeyaml and springboot Version conflict (springboot snakeyaml-1.25, apktool snakeyaml-1.12) Must compile by yourself See [How to Build ApkToolJar](HowtoBuildApkToolJar.md)

#### todo
+ Support for multi-user simultaneous use
+ UI adjustment
+ Support package name modification
+ Support version modification
+ More about WebView features
  - Navigation bar
  - progress bar
  - etc..
  
#### How to use
+ Installing the Java environment 1.8 +
+ download releases 
+ java -jar html2apk-1.0.jar
+ defaul port 80, if u wana modify port:
  java -jar demo.jar --server.port=8080
+ you must have apk-template dir : /apk/*   
+ you must have ui dir : /html/*   

####  How to Build
+ ./gradlew for unix based systems or gradlew.bat for windows.
+ After build completes you should have a jar file at: build/libs/html2apk-xxx.jar
