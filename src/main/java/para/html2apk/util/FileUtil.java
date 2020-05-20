package para.html2apk.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class FileUtil {
    public static String SaveFile(MultipartFile file, String path, String name){
        String filePath = path;
        String fileName = name;
        if (file.isEmpty()) {
            return "";
        }
        if(filePath == null || filePath.equals("")){
            filePath = GetPath.temp;
        }
        if(fileName == null || fileName.equals("")){
            fileName = file.getOriginalFilename();
        }
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return filePath + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean copyAllToAnotherDirectory(File srcDir, File desDir)
    {
        if (srcDir == null | desDir == null)
            throw new NullPointerException("The parameter srcDir or desDisr is null.");
        // 源文件不存在时抛出非法参数异常
        if (!srcDir.exists())
            throw new IllegalArgumentException("复制源不存在!");
        // 系统中不存在目标目录，则创建一个desDir这样的目录。
        if (!desDir.exists() || !desDir.isDirectory())
            desDir.mkdirs();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File desChildFile = null; // 当前级别的目标子文件或子目录对象
        byte[] bs = new byte[1024 * 1024]; // 保存读取或写入流数据的字节数组
        int len=0;  //读到的字节长度
        try
        {
            // 源目录是一个普通文件时
            if (srcDir.isFile())
            {
                fis = new FileInputStream(srcDir); // 初始化源文件输入流
                desChildFile = new File(desDir, srcDir.getName()); // 初始化一个目标文件对象
                // 在磁盘上创建一个desChildFile对象对应的目标文件
                if (!desChildFile.exists())
                    desChildFile.createNewFile();
                fos = new FileOutputStream(desChildFile); // 初始化目标文件的输出流
                while ((len=fis.read(bs) )!= -1) // 读数据
                {
                    fos.write(bs,0,len); // 写数据
                }

                // 源目录是一个正常目录时
            }
            else if (srcDir.isDirectory())
            {

                // 罗列出当前源目录下所有的下一级文件、下一级目录，保存在File数据中
                File[] childFiles = srcDir.listFiles();

                /*
                 * 遍历当前级源目录的子文件、子目录，
                 * 并分别针对其下的文件、目录 进行不同的处理，进入不同分支。
                 */
                for (File childFile : childFiles)
                {

                    // 下级元素是一个普通文件时
                    if (childFile.isFile())
                    {
                        fis = new FileInputStream(childFile); // 初始化源文件输入流
                        // 初始化一个在当前级目标目录下，和当前级源目录子文件同名的文件对象
                        desChildFile = new File(desDir, childFile.getName());
                        // 在磁盘上创建一个desChildFile对象对应的文件
                        if (!desChildFile.exists())
                            desChildFile.createNewFile();
                        fos = new FileOutputStream(desChildFile); // 初始化目标文件输出流
                        while ((len=fis.read(bs) )!= -1) // 读入数据
                        {
                            fos.write(bs,0,len); // 向desChildFile对象对应的文件上写入数据
                        }
                        fis.close();
                        fos.close();
                        desChildFile=null;  //desChildFile赋空，方便垃圾回收器收回内存
                    }
                    // 下级元素是一个目录时
                    else if (childFile.isDirectory())
                    {
                        // 初始化一个在当前级目标目录下，和当前级源目录子目录同名的目录对象
                        desChildFile = new File(desDir.getAbsoluteFile() + "/" + childFile.getName());
                        // 在磁盘上创建一个desChildFile对象对应的目录
                        if (!desChildFile.exists())
                            desChildFile.mkdirs();
                        copyAllToAnotherDirectory(childFile, desChildFile); // 递归调用方法本身
                    }
                }
            }
            fis.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void deleteDir(File file){
        File[] fileArrays = file.listFiles();//列出源文件下所有文件，包括子目录
        if(fileArrays == null) return;
        for (File f: fileArrays){//将源文件下的所有文件逐个删除
            if(f.isDirectory()){
                deleteDir(f);
            }else{
                f.delete();
            }
        }
        file.delete();
    }

    /**
     * 防止用户多套了一层文件夹
     */
    public static void dealIndex(){
        File file = new File(GetPath.htmlPath);
        File[] files = file.listFiles();
        //只有一个文件夹的情况下提升到父文件夹
        if(files.length == 1){
            if(files[0].isDirectory()){
                copyAllToAnotherDirectory(files[0], file);
                deleteDir(files[0]);
            }
        }

    }
}
