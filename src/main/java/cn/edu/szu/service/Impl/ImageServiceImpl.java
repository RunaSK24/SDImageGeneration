package cn.edu.szu.service.Impl;

import cn.edu.szu.domain.TextToImgRequest;
import cn.edu.szu.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {


    @Override
    public String textToImage(String prompt) {
        String path = "";
        TextToImgRequest body = StableDiffusionApiUtil.getText2ImageRequestBody(prompt);
        final List<String> images = StableDiffusionApiUtil.callSdApi(body);
        for (String image : images) {
            path = ImageUtil.convertBase64StrToImage(image, String.format("./src/main/resources/image/%s.png", UUID.randomUUID().toString().replaceAll("-", "")));
        }

        return path;
    }
}
