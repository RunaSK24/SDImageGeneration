package cn.edu.szu.Service;

import cn.edu.szu.domain.ImgToImgRequest;
import cn.edu.szu.domain.TextToImgRequest;
import cn.edu.szu.service.Impl.ImageUtil;
import cn.edu.szu.service.Impl.StableDiffusionApiUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class StableDiffusionTest {
    @Test
    public void testTextToImg() {
        String prompt = "1girl, animal_ears, bangs, beach, black_hair, blue_sky, cat_ears, caustics, chain-link_fence, choker, cloud, cloudy_sky, collarbone, day, dress, fence, flower, hair_ornament, hairclip, hibiscus, horizon, long_hair, looking_at_viewer, ocean, one-piece_swimsuit, outdoors, pool, pool_ladder, poolside, purple_eyes, red_flower, sailor_collar, see-through, shallow_water, sky, sleeveless, soaking_feet, solo, swimsuit, very_long_hair, wading, water, wet, wet_clothes";
        TextToImgRequest body = StableDiffusionApiUtil.getText2ImageRequestBody(prompt);
        final List<String> images = StableDiffusionApiUtil.callSdTextToImgApi(body);
        for (String image : images) {
            System.out.println(ImageUtil.convertBase64StrToImage(image, String.format("%s.png", UUID.randomUUID().toString().replaceAll("-", ""))));
        }
    }

    @Test
    public void testImgToImg(){
        ImgToImgRequest body = StableDiffusionApiUtil.getImg2ImageRequestBody("D:\\Code\\Java\\SDImageGeneration\\src\\main\\resources\\image\\4.jpg");
        final List<String> images = StableDiffusionApiUtil.callSdImgToImgApi(body);
        for (String image : images) {
            System.out.println(ImageUtil.convertBase64StrToImage(image, String.format("%s.png", UUID.randomUUID().toString().replaceAll("-", ""))));
        }
    }
}
