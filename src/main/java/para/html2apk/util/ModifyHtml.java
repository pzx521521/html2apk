package para.html2apk.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ModifyHtml {
    public static void CopyZip(MultipartFile file) {
        //放到临时文件中
        String path = FileUtil.SaveFile(file, "", "");
        //解压文件-> 不支持中文!
        try {
            ZipFilesUtils.decompress(path, GetPath.htmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //防止用户多打包一层文件夹
        FileUtil.dealIndex();
    }

    public static void CopyHtml(MultipartFile file){
        FileUtil.SaveFile(file, GetPath.htmlPath, "index.html");
    }

    public static void CopyDir(List<MultipartFile> files){
        for(MultipartFile f:files){
            if (f instanceof CommonsMultipartFile) {
                //转换成这个对象，然后我们需要通过里面的FileItem来获得相对路径
                CommonsMultipartFile f2 = (CommonsMultipartFile) f;
                String name = f2.getName();
                System.out.println(name + "---------相对路径");
                File file1 = new File(GetPath.htmlPath + name);
                file1.mkdirs();
                try {
                    file1.createNewFile();
                    f.transferTo(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
