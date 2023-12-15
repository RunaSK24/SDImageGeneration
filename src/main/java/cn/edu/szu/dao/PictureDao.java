package cn.edu.szu.dao;

import cn.edu.szu.domain.Picture;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PictureDao {

    @Insert("INSERT INTO picture (uid, did, pid, pictureSource) " +
            "VALUES (#{uid},#{did},#{pid},#{pictureSource})")
    int insertPic(Picture picture);

    @Update("UPDATE picture set pictureSource = #{pictureSource} " +
            "where pid = #{pid} and uid = #{uid} and did = #{did}")
    int updatePic(Picture picture);

    @Select("SELECT * from picture where uid = #{uid} and did = #{did}")
    List<Picture> selectByDialog(Long uid,Long did);
    @Select("SELECT * from picture where uid = #{uid} and did = #{did} and pid = #{pid}")
    Picture selectByPic(Long uid,Long did,Long pid);
    @Delete("delete from picture where  uid = #{uid} and did = #{did} and pid = #{pid} ")
    int deletePic(Long uid,Long did,Long pid);

}
