package cn.edu.szu.dao;

import cn.edu.szu.domain.Dialogue;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DialogueDao {
    @Insert("INSERT INTO dialogue (uid, did, dialogueSource) " +
            "VALUES (#{uid}, #{did}, #{dialogueSource})")
    int insertDialogue(Dialogue dialogue);

    @Update("UPDATE dialogue SET dialogueSource = #{dialogueSource} " +
            "WHERE uid = #{uid} AND did = #{did}")
    int updateDialogue(Dialogue dialogue);

    @Select("select * from dialogue where uid = #{id}")
    List<Dialogue> selectByUserId(Long id);

    @Select("select * from dialogue where uid = #{uid} and did = #{did}")
    Dialogue selectByIds(Long uid, Long did);

    @Delete("delete from dialogue where uid = #{uid} and did = #{did}")
    int delete(Long uid, Long did);
}
