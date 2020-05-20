package para.html2apk.util;

import brut.androlib.Androlib;
import brut.androlib.AndrolibException;
import brut.androlib.ApkDecoder;
import brut.common.BrutException;
import brut.directory.DirectoryException;

import java.io.File;
import java.io.IOException;

public class ApkToolKit {


    public static Boolean Build(){
        Androlib instance = new Androlib();
        try {
            instance.build(new File(GetPath.appDir) , new File(GetPath.outApk));
            return true;
        } catch (BrutException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void Decde(){
        try {
            File inFile = new File(GetPath.outApk);
            ApkDecoder decoder = new ApkDecoder();
            decoder.setOutDir(new File(GetPath.appDir2));
            decoder.setApkFile(inFile);
            decoder.decode();
        } catch (IOException|AndrolibException | DirectoryException e) {
            e.printStackTrace();
        }
    }
}
