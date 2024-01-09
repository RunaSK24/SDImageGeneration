package cn.edu.szu.dao;


import cn.edu.szu.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Insert("insert into user (id, userName, passWord) " +
            "values (#{id},#{userName},#{passWord})")
    int save(User user);

    @Update("UPDATE user SET userName = #{userName}, passWord = #{passWord} " +
            "WHERE id = #{id}")
    int update(User user);

    @Select("select * from user where id = #{id}")
    User selectById(Long id);

    @Delete("DELETE from user where id = #{id}")
    int deleteById(Long id);

    @Select("SELECT * from user where userName = #{userName}")
    User selectByName(String name);
}
