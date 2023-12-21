package cn.edu.szu.Service;

import cn.edu.szu.service.Impl.ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageUtilTest {
    @Test
    public void testConvertBase64StrToImage() {
        System.out.println("----------------------------图片转Base64字符串---------------------------");
        //图片文件路径
        String imageFileName = "src/main/resources/static/images/bot.png";
        //图片转Base64字符串
        String base64Str = ImageUtil.convertImageToBase64Str(imageFileName);
//        System.out.println(base64Str);
        assertNotNull(base64Str, "Base64字符串不能为空");

        System.out.println("----------------------------Base64字符串转图片---------------------------");
        //新文件路径
        String newFileName = "testIMG.png";
        //Base64字符串转图片
        boolean success = ImageUtil.convertBase64StrToImage(base64Str, newFileName);
        assertTrue(success,"转换Base64字符串为图片失败");
        System.out.println("生成的文件的路径是：" + newFileName);
    }
}
