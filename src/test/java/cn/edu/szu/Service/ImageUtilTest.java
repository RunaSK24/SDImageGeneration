package cn.edu.szu.Service;

import cn.edu.szu.service.Impl.ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImageUtilTest {
    @Test
    public void testConvertBase64StrToImage() {
        System.out.println("----------------------------图片转Base64字符串---------------------------");
        //图片文件路径
        String imageFileName = "D:\\Code\\Java\\SDImageGeneration\\src\\main\\resources\\image\\1.jpg";
        //图片转Base64字符串
        String base64Str = ImageUtil.convertImageToBase64Str(imageFileName);
        System.out.println(base64Str);

        System.out.println("----------------------------Base64字符串转图片---------------------------");
        //新文件路径
        String newFileName = "D:\\Code\\Java\\SDImageGeneration\\src\\main\\resources\\image\\imageTest.jpg";
        //Base64字符串转图片
        ImageUtil.convertBase64StrToImage(base64Str, newFileName);
        System.out.println("生成的文件的路径是：" + newFileName);
    }
}
