package cn.edu.szu.Service;

import cn.edu.szu.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImageServiceTest {
    @Autowired
    private ImageService imageService;

    @Test
    public void testTextToImage() {
        String filename = imageService.textToImage("1girl, animal_ears, bangs, beach, black_hair, blue_sky, cat_ears, caustics, chain-link_fence, choker, cloud, cloudy_sky, collarbone, day, dress, fence, flower, hair_ornament, hairclip, hibiscus, horizon, long_hair, looking_at_viewer, ocean, one-piece_swimsuit, outdoors, pool, pool_ladder, poolside, purple_eyes, red_flower, sailor_collar, see-through, shallow_water, sky, sleeveless, soaking_feet, solo, swimsuit, very_long_hair, wading, water, wet, wet_clothes");
        System.out.println(filename);
    }

    @Test
    public void testImageToImage() {
        String filename = imageService.imageToImage("D:\\Code\\Java\\SDImageGeneration\\src\\main\\resources\\image\\2.png");
        System.out.println(filename);
    }
}
