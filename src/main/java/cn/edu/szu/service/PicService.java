package cn.edu.szu.service;

import cn.edu.szu.domain.Picture;

import java.util.List;

public interface PicService {
    int insertPic(Picture picture);
    int updatePic(Picture picture);
    List<Picture> selectByDia(Long uid,Long did);
    Picture selectByPic(Long uid,Long did,Long pid);
    int deletePic(Long uid,Long did,Long pid);
}
