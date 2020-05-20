package para.html2apk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import para.html2apk.bean.Info;
import para.html2apk.bean.ResposeInfo;
import para.html2apk.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
public class MainController
{
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    HttpServletRequest request;

    @RequestMapping("hello")
    private String Hello(){
        return "Hello";
    }

    @PostMapping("getApk")
    private ResposeInfo getApk(HttpServletRequest request){
        ResposeInfo resposeInfo = new ResposeInfo();
        String Result = "";
        MultipartHttpServletRequest params=((StandardMultipartHttpServletRequest) request);
        Info info = new Info();
        String type = params.getParameter("type");
        info.setType(type);
        logger.info("替换app名字...");
        info.setAppTitle(params.getParameter("appTitle"));
        ModifyAsTxt.ModifyStringXml(info.getAppTitle());
        Result = Result + "\n替换app名字成功";
        info.setPackageName(params.getParameter("packageName"));
        info.setVersion(params.getParameter("version"));
        MultipartFile imgSrc = params.getFile("imgSrc");
        //替换图标文件
        logger.info("替换图标文件...");
        Result = IcoReplace.SaveTransFile(imgSrc);

        //删除之前的文件
        logger.info("删除之前的文件...");
        File htmlPath = new File(GetPath.htmlPath);
        FileUtil.deleteDir(htmlPath);
        htmlPath.mkdir();
        Result = Result + "\n删除旧html文件成功";
        logger.info("替换url...");
        if(type.equals("url")){
            //替换url
            info.setUrl(params.getParameter("url"));
            ModifyAsTxt.ModifyHtml(info.getUrl());
            Result = Result + "\n替换html路径成功";
        }else{
            //替换url
            ModifyAsTxt.ModifyHtml("");
            Result = Result + "\n替换html路径成功";
            //复制文件
            if(type.equals("zip")){
                MultipartFile file = params.getFile("zip");
                ModifyHtml.CopyZip(file);
            }else if(type.equals("html")){
                MultipartFile file = params.getFile("html");
                ModifyHtml.CopyHtml(file);
            }else if(type.equals("dir")){
                List<MultipartFile> files = params.getFiles("file");
                ModifyHtml.CopyDir(files);
            }
            Result = Result + "\n复制新html文件成功";
        }
        //生成apk
        logger.info("生成apk...");
        if(ApkToolKit.Build()){
            Result = Result + "\n生成apk成功";
            resposeInfo.setResult(true);
        }else{
            Result = Result + "\n生成apk失败";
            resposeInfo.setResult(false);
        }
        //签名
        logger.info("签名...");
        try {
            Result = Result + "\n" + RunCmd.runCMD("jarsigner -verbose -keystore apk/apk.keystore apk/app.apk -storepass 123456789 apk.keystore");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        resposeInfo.setResultInfo(Result);
        return resposeInfo;
    }

    @RequestMapping(value="/download",method=RequestMethod.GET)
    public void apkDownload(HttpServletResponse response) throws IOException {
        File file = new File(GetPath.outApk);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + GetPath.outApkName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("success");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
