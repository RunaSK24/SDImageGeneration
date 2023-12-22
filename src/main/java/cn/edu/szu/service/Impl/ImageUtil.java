package cn.edu.szu.service.Impl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageUtil {
    /**
     * 图片转Base64字符串
     *
     * @param imageFileName 文件地址
     * @return String Base64图片代码
     */
    public static String convertImageToBase64Str(String imageFileName) {
        ByteArrayOutputStream baos = null;
        try {
            //获取图片类型
            String suffix = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
            //构建文件
            File imageFile = new File(imageFileName);
            //通过ImageIO把文件读取成BufferedImage对象
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            //构建字节数组输出流
            baos = new ByteArrayOutputStream();
            //写入流
            ImageIO.write(bufferedImage, suffix, baos);
            //通过字节数组流获取字节数组
            byte[] bytes = baos.toByteArray();
            //获取JDK8里的编码器Base64.Encoder转为base64字符
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Base64字符串转图片
     *
     * @param base64String  Base64代码
     * @param imageFileName 文件储存地址
     */
    public static boolean convertBase64StrToImage(String base64String, String imageFileName) {
        String filePath = "./src/main/resources/image/" + imageFileName;
        System.out.println(filePath);
        ByteArrayInputStream bais = null;
        try {
            //获取图片类型
            String suffix = imageFileName.substring(imageFileName.lastIndexOf(".") + 1);
            //获取JDK8里的解码器Base64.Decoder,将base64字符串转为字节数组
            byte[] bytes = Base64.getDecoder().decode(base64String);
            //构建字节数组输入流
            bais = new ByteArrayInputStream(bytes);
            //通过ImageIO把字节数组输入流转为BufferedImage
            BufferedImage bufferedImage = ImageIO.read(bais);
            //构建文件
            File imageFile = new File(filePath);
            //写入生成文件
            ImageIO.write(bufferedImage, suffix, imageFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isImageFromBase64(String base64Str) {
        if (base64Str == null || base64Str.isEmpty()) {
            return false;
        }
        boolean flag = false;
        try {
            // 获取图片流
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64Str)));
            if (null == bufImg) {
                return false;
            }
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return flag;
    }
}