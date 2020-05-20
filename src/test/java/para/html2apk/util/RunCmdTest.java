package para.html2apk.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RunCmdTest {

    @Test
    void runCMD() throws IOException, InterruptedException {
        System.out.println(RunCmd.runCMD("jarsigner -verbose -keystore apk/apk.keystore apk/app.apk -storepass 123456789 apk.keystore"));
//        System.out.println(RunCmd.runCMD("java -version"));
//        System.out.println(RunCmd.runCMD("ipconfig /all"));
    }
}