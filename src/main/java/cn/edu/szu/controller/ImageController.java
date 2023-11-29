package cn.edu.szu.controller;

import cn.edu.szu.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/{prompt}")
    private String textToImage(@PathVariable String prompt){
        System.out.println("prompt: " + prompt);
        return imageService.textToImage(prompt);
    }
}
