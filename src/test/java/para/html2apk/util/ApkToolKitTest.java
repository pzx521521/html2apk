package para.html2apk.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ApkToolKitTest {

    @Test
    void build() {
        ApkToolKit.Build();
        try {
            RunCmd.runCMD("jarsigner -verbose -keystore apk/apk.keystore apk/app.apk -storepass 123456789 apk.keystore");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void decode() {
        ApkToolKit.Decde();
    }
}