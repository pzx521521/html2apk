package para.html2apk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCmd {
    private static final Logger logger = LoggerFactory.getLogger(RunCmd.class);
    //执行cmd命令，获取返回结果
    public static String execCMD(String command) {
        StringBuilder sb =new StringBuilder();
        try {
            Process process=Runtime.getRuntime().exec(command);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                sb.append(line+"\n");
            }
        } catch (Exception e) {
            return e.toString();
        }
        return sb.toString();
    }
    public static String runCMD(String cmd) throws IOException, InterruptedException {
        final String METHOD_NAME = "runCMD";
        Process p = Runtime.getRuntime().exec(cmd);
        //p.waitFor();
        BufferedReader brRgiht = null;
        BufferedReader brError = null;
        BufferedReader br = null;
        try {
            brRgiht = new BufferedReader(new InputStreamReader(p.getInputStream()));
            brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            br = brRgiht;
            String readLine = br.readLine();
            if(readLine == null){
                br = brError;
                readLine = br.readLine();
            }
            StringBuilder builder = new StringBuilder();
            while (readLine != null) {
                readLine = br.readLine();
                builder.append(readLine);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
