package cn.edu.szu.controller;

import cn.edu.szu.dao.PictureDao;
import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.domain.Picture;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pic")
public class PictureController {
    @Autowired
    private PictureDao picDao;

    @GetMapping("/{uid}/{did}")//获取对话所有图片
    public Result selectByDialog(@PathVariable Long uid,@PathVariable Long did){
        List<Picture>picList = picDao.selectByDialog(uid, did);
        Integer code;
        String msg;
        if(picList.isEmpty() || picList == null){
            code = Code.GET_ERR;
            msg = "GET ERROR";
        }else {
            code = Code.GET_OK;
            msg = "GET OK";
        }
        return new Result(code,picList,msg);
    }

    @GetMapping("/{uid}/{did}/{pid}")//获取指定的唯一图片
    public Result selectByPic(@PathVariable Long uid,@PathVariable Long did,@PathVariable Long pid){
        Picture pic = picDao.selectByPic(uid, did,pid);
        Integer code;
        String msg;
        if(pic == null){
            code = Code.GET_ERR;
            msg = "GET ERROR";
        }else {
            code = Code.GET_OK;
            msg = "GET OK";
        }
        return new Result(code,pic,msg);
    }

    @PostMapping//保存图片
    public Result insertPic(@RequestBody Picture picture){
        int len = picDao.insertPic(picture);
        int code;
        String msg;
        if(len > 0){
            code = Code.SAVE_OK;
            msg = "Save Success";
        }else {
            code = Code.SAVE_ERR;
            msg = "Save ERROR";
        }
        return new Result(code,null,msg);
    }

    @PutMapping//更新图片
    public Result updatePic(@RequestBody Picture picture){
        int len = picDao.updatePic(picture);
        int code;
        String msg;
        if(len > 0){
            code = Code.UPDATE_OK;
            msg = "Save Success";
        }else {
            code = Code.UPDATE_ERR;
            msg = "Save ERROR";
        }
        return new Result(code,null,msg);
    }

    @DeleteMapping("/{uid}/{did}/{pid}")
    public Result deletePic(@PathVariable Long uid,@PathVariable Long did,@PathVariable Long pid){
        int len = picDao.deletePic(uid, did, pid);
        int code;
        String msg;
        if(len > 0){
            code = Code.DELETE_OK;
            msg = "Update Success";
        }else {
            code = Code.DELETE_ERR;
            msg = "Update ERROR";
        }
        return new Result(code,null,msg);

    }


}
