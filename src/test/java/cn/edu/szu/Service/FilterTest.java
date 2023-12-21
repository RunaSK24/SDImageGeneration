package cn.edu.szu.Service;

import cn.edu.szu.service.Impl.GrayscaleFilter;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@SpringBootTest
public class FilterTest {
    @Test
    public void test() throws IOException {
        BufferedImage img = ImageIO.read(new File("src/main/resources/image/0cd579e19ba24acf83320751c6d74c5f.png"));
//        GrayscaleFilter filter = new GrayscaleFilter();
//        img = filter.filter(img, null);
//        ImageIO.write(img, "png", new File("D:\\test.png"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

// 对字节数组Base64编码
        String base64Image = DatatypeConverter.printBase64Binary(bytes);
        System.out.println(base64Image);
    }

    public static void main(String[] args) throws IOException {
        BufferedImage img = ImageIO.read(new File("src/main/resources/image/0cd579e19ba24acf83320751c6d74c5f.png"));

        System.out.println(new GrayscaleFilter().toBase64(img));
    }
}
