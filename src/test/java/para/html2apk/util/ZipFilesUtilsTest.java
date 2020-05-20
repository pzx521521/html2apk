package para.html2apk.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class ZipFilesUtilsTest {

    @Test
    void unzip() throws IOException {
        ZipUtil.unzip("C:\\Users\\Administrator\\Desktop\\中文\\中文.zip", "C:\\Users\\Administrator\\Desktop\\中文");
    }
    @Test
    void dealIndex() {
        FileUtil.dealIndex();
    }

    @Test
    void deleteAll() {
        FileUtil.deleteDir(new File("C:\\Users\\Administrator\\Desktop\\中文"));
    }


}