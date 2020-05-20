package para.html2apk.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ReNamePackageName {
    static String rootPath = GetPath.getRootDir();


    public static void saveXml(Document doc, String sPath){
        DOMSource ds = new DOMSource(doc);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer tm = null;
        try {
            tm = tf.newTransformer();
            StreamResult sr = new StreamResult(new File(sPath));
            tm.transform(ds, sr);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    public static void modifyAndroidManifest(String packageName, String version){
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(GetPath.androidManifestPath);

            NodeList sList = doc.getElementsByTagName("application");
            if(sList.getLength()>0){
                Element element = (Element) sList.item(0);
                element.setAttribute("android:versionName", version);
            }
            sList = doc.getElementsByTagName("manifest");
            if(sList.getLength()>0){
                Element element = (Element) sList.item(0);
                element.setAttribute("package", packageName);
            }
            sList = doc.getElementsByTagName("activity");
            if(sList.getLength()>0){
                Element element = (Element) sList.item(0);
                element.setAttribute("android:name", packageName + ".MainActivity");
            }
            saveXml(doc, GetPath.androidManifestPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean ReNameDir(File file, String newName){
        return file.renameTo(new File(file.getParentFile().getAbsolutePath() + "\\" + newName));
    }
    public static Boolean modifyDir(String packageName){
        String [] strArr= packageName.split("\\.");
        if(strArr.length != 3){
            return false;
        }
        File[] files = new File(GetPath.smaliPath).listFiles();
        for(int i=0;i < files.length;i++) {
            String tmp = files[i].getName();
            if(files[i].isDirectory() && !tmp.equals("android")){
                //app.para.webview->app
                File[] files2 = files[i].listFiles();
                for(int j=0;j < files2.length;j++) {
                    if(files2[j].isDirectory()){
                        //app.para.webview->app.para
                        File[] files3 = files2[j].listFiles();
                        for(int k=0;k < files3.length;k++) {
                            if(files3[k].isDirectory()){
                                //app.para.webview->app.para.webview
                                ReNameDir(files3[k], strArr[2]);
                            }
                        }
                        ReNameDir(files2[j], strArr[1]);
                    }
                }
                ReNameDir(files[i], strArr[0]);
            }
        }
        return true;
    }
}
