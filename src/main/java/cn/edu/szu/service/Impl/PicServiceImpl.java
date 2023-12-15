package cn.edu.szu.service.Impl;

import cn.edu.szu.dao.PictureDao;
import cn.edu.szu.domain.Picture;
import cn.edu.szu.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicServiceImpl implements PicService {
    @Autowired
    PictureDao pictureDao;
    @Override
    public int insertPic(Picture picture) {
        return pictureDao.insertPic(picture);
    }

    @Override
    public int updatePic(Picture picture) {
        return pictureDao.updatePic(picture);
    }

    @Override
    public List<Picture> selectByDia(Long uid, Long did) {
        return pictureDao.selectByDialog(uid,did);
    }

    @Override
    public Picture selectByPic(Long uid, Long did, Long pid) {
        return pictureDao.selectByPic(uid,did,pid);
    }

    @Override
    public int deletePic(Long uid, Long did, Long pid) {
        return pictureDao.deletePic(uid,did,pid);
    }
}
