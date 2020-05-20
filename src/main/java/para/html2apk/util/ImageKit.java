package para.html2apk.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 图像裁剪工具类
 * @author Administrator
 *
 */
class ImageKit{

    /**
     * 根据尺寸将图像居中裁剪
     * @param src  源图像的存储路径
     * @param desc 处理后的图像准备存储的路径
     * @param w  裁剪要求的宽
     * @param h  裁剪要求的高
     * @throws FileNotFoundException
     */
    public static void cutImageByCenter(String src, String desc, int w, int h) throws Exception{

        //获得一个迭代器
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");

        //使用迭代器将图片读取到一个图片字符流对象中
        ImageReader reader = (ImageReader) iterator.next();

        //获取图片的输入字节流
        InputStream in = new FileInputStream(src);

        //ImageInputStream是供 ImageReader 使用的可查找输入流接口。
        ImageInputStream iis = ImageIO.createImageInputStream(in);

        //设置用于给定的ImageInputStream 或其他Object的输入源
        reader.setInput(iis,true);

        //返回一个可以使用一组默认设置控制解码过程的 ImageReadParam 对象
        ImageReadParam param = reader.getDefaultReadParam();

        //图像索引
        int imageIndex = 0;

        //设置一个矩形区域。
        Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-w)/2,(reader.getHeight(imageIndex)-w)/2,w,h);

        /*
         * 设置感兴趣的源区域。感兴趣的区域杯描述成一个矩形。
         * 源图像左上角为像素（0,0），该值向下向右递增。
         */
        param.setSourceRegion(rect);

        //将字符流图片加一个缓冲了流。
        BufferedImage bi = reader.read(0, param);

        //写进新文件中
        ImageIO.write(bi, "jpg", new File(desc));
    }


    /**
     * 将图片裁剪至二分之一
     * @param src  源图像路径
     * @param dest   裁剪后图像路径
     * @throws IOException
     */
    public static void cutHalfImage(String src,String dest) throws IOException{
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)iterator.next();
        InputStream in=new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        int width = reader.getWidth(imageIndex)/2;
        int height = reader.getHeight(imageIndex)/2;
        Rectangle rect = new Rectangle(width/2, height/2, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0,param);
        ImageIO.write(bi, "jpg", new File(dest));
    }

    /**
     * 图片裁剪通用接口
     * @param src  源图像路径
     * @param dest   裁剪后图像路径
     * @param x   裁剪的图像左上角横坐标
     * @param y 裁剪的图像左上角纵坐标
     * @param w 裁剪的图像的宽
     * @param h 裁剪的图像的高
     * @throws IOException
     **/
    public static void cutImage(String src,String dest,int x,int y,int w,int h) throws IOException{
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)iterator.next();
        InputStream in=new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w,h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0,param);
        ImageIO.write(bi, "jpg", new File(dest));
    }

    /**
     * 将图像缩放
     * @param src  源图像路径
     * @param dest   缩放后图像路径
     * @param w 缩放的图像的宽
     * @param h 缩放的图像的高
     * @throws Exception
     */
    public static void zoomImage(String src,String dest,int w,int h) throws Exception {
        double wr=0,hr=0;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);

        /*
         * w - 将图像缩放到的宽度。
         * h - 将图像缩放到的高度。
         * bufImg.SCALE_SMOOTH - 指示用于图像重新取样的算法类型的标志。选择图像平滑度比缩放速度具有更高优先级的图像缩放算法
         */
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);

        //获得缩放比例值
        wr=w*1.0/bufImg.getWidth();
        hr=h*1.0 / bufImg.getHeight();

        /*
         * 此类使用仿射转换来执行从源图像或 Raster 中 2D 坐标到目标图像或 Raster 中 2D 坐标的线性映射。
         * 参数：AffineTransform.getScaleInstance(wr, hr)表示缩放变换的变换矩阵，矩阵为：
         *    -----   [   sx   0  0   ]   -----
         * ----- [   0  sy   0   ]   -----
         * ----- [   0  0  1   ]   -----
         */
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);

        /*
         * 转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
         * 参数一：要转换的BufferedImage
         * 参数二：要在其中存储转换结果的BufferedImage。
         * 返回值：过滤后的BuffereedImage
         */
        Itemp = ato.filter(bufImg, null);

        try {
            //写入图片。
            ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
