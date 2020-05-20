package para.html2apk.util;

import java.io.*;
import java.util.ArrayList;

public class ModifyAsTxt {
    static final String indexhtml = "file:///android_asset/web/index.html";
    public static void ModifyHtml(String Url){
        try {
            int modifyline = 99;//要修改的行
            ArrayList<String> hostsArrayList= new ArrayList<String>();
            BufferedReader in=new BufferedReader(new FileReader(GetPath.mainActivePath));
            String line;
            int count=1;
            while((line=in.readLine())!=null){
                if(count==modifyline){
                    if(Url == null || Url.equals("")){

                        hostsArrayList.add(line.replaceFirst("\".*?\"", "\""+ indexhtml + "\""));
                    }else{
                         //替换abc成def
                        hostsArrayList.add(line.replaceFirst("\".*?\"", "\""+ Url + "\""));
                    }
                }else{
                    hostsArrayList.add(line);
                }
                count++;
            }
            in.close();

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(GetPath.mainActivePath)));
            for(int i=0;i<hostsArrayList.size();i++){
                String currentHostsArrayListTextLine =(String)hostsArrayList.get(i);
                out.println(currentHostsArrayListTextLine);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void ModifyStringXml(String appTitle){
        try {
            int modifyline = 34;//要修改的行
            ArrayList<String> hostsArrayList= new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(GetPath.stringXmlPath), "UTF-8"));
            String line;
            int count=1;
            while((line=in.readLine())!=null){
                if(count==modifyline){
                    hostsArrayList.add(line.replaceFirst("\"app_name\">.*?<", "\"app_name\">"+ appTitle + "<"));
                }else{
                    hostsArrayList.add(line);
                }
                count++;
            }
            in.close();

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(GetPath.stringXmlPath)));
            for(int i=0;i<hostsArrayList.size();i++){
                String currentHostsArrayListTextLine =(String)hostsArrayList.get(i);
                out.println(currentHostsArrayListTextLine);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
