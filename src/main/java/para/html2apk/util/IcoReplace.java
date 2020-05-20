package para.html2apk.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

public class IcoReplace {
   //162 72
    public static String SaveTransFile(MultipartFile imgSrc){
        if(imgSrc == null) return "无需图标替换";
        String sPath = FileUtil.SaveFile(imgSrc, "", "");
        if (GeneralICO(sPath)) return "替换图标成功";
        return "替换图标失败";
    }
    public static Boolean GeneralICO(String picPath){
        try {
            Files.delete(new File(GetPath.icoPath).toPath());
            Files.delete(new File(GetPath.icoPath2).toPath());
            Files.delete(new File(GetPath.icoPath3).toPath());
            ImageKit.zoomImage(picPath,GetPath.icoPath,72, 72);
            ImageKit.zoomImage(picPath,GetPath.icoPath2,162, 162);
            Files.copy(new File(GetPath.icoPath).toPath(), new File(GetPath.icoPath3).toPath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
