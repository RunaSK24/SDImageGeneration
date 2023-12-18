package cn.edu.szu.service.Impl;

import cn.edu.szu.domain.ImgToImgRequest;
import cn.edu.szu.domain.TextToImgRequest;
import cn.edu.szu.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public String textToImage(String prompt) {
        String fileName = String.format("%s.png", UUID.randomUUID().toString().replaceAll("-", ""));
        TextToImgRequest body = StableDiffusionApiUtil.getText2ImageRequestBody(prompt);
        final List<String> images = StableDiffusionApiUtil.callSdTextToImgApi(body);
        for (String image : images) {
            ImageUtil.convertBase64StrToImage(image, fileName);
        }

        return fileName;
    }

    @Override
    public String imageToImage(String imagePath) {
        String fileName = String.format("%s.png", UUID.randomUUID().toString().replaceAll("-", ""));
        ImgToImgRequest body = StableDiffusionApiUtil.getImg2ImageRequestBody(imagePath,"");
        final List<String> images = StableDiffusionApiUtil.callSdImgToImgApi(body);
        for (String image : images) {
            ImageUtil.convertBase64StrToImage(image,fileName);
        }

        return fileName;
    }
}
