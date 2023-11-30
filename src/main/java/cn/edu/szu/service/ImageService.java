package cn.edu.szu.service;

public interface ImageService {
    String textToImage(String prompt);

    String imageToImage(String imagePath);
}
