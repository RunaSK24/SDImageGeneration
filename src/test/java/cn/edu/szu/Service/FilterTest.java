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
        BufferedImage img = ImageIO.read(new File("src/main/resources/image/bot.png"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();

        // 对字节数组Base64编码
        String base64Image = DatatypeConverter.printBase64Binary(bytes);
        System.out.println(base64Image);
    }
}
