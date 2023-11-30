package cn.edu.szu.service;

import cn.edu.szu.domain.Picture;

import java.util.List;

public interface PicService {
    int insertPic(Picture picture);
    int updatePic(Picture picture);
    List<Picture> selectByDia(Integer uid,Integer did);
    Picture selectByPic(Integer uid,Integer did,Integer pid);
    int deletePic(Integer uid,Integer did,Integer pid);
}
