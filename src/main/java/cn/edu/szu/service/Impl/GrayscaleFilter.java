package cn.edu.szu.service.Impl;

import jakarta.xml.bind.DatatypeConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GrayscaleFilter implements BufferedImageOp {
    //转换为base64
    public String toBase64(BufferedImage img){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "png", byteArrayOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = byteArrayOutputStream.toByteArray();

// 对字节数组Base64编码
        String base64Image = DatatypeConverter.printBase64Binary(bytes);
        return base64Image;
    }

    /**
     * 灰度处理
     * @param img The {@code BufferedImage} to be filtered
     * @param dest The {@code BufferedImage} in which to store the results$
     *
     * @return
     */
    public BufferedImage filter(BufferedImage img, BufferedImage dest) {
        if (dest == null) {
            dest = createCompatibleDestImage(img, null);
        }

        int width = img.getWidth();
        int height = img.getHeight();

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                int p = img.getRGB(i, j);

                int a = (p>>24) & 0xff;
                int r = (p>>16) & 0xff;
                int g = (p>>8) & 0xff;
                int b = p & 0xff;

                int avg = (r+g+b)/3;

                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                dest.setRGB(i, j, p);
            }
        }
        return dest;
    }


    /**
     * 继承了接口重写的方法
     * @param src The {@code BufferedImage} to be filtered
     *
     * @return
     */
    @Override
    public Rectangle2D getBounds2D(BufferedImage src) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
        if (destCM == null) {
            destCM = src.getColorModel();
        }
        return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), destCM.isAlphaPremultiplied(), null);
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Double();
        }
        dstPt.setLocation(srcPt.getX(), srcPt.getY());
        return dstPt;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }
}
