package cn.edu.szu.service;

import cn.edu.szu.domain.Dialogue;

import java.util.List;

public interface DialogueService {
    int insertDialogue(Dialogue dialogue);
    int updateDialogue(Dialogue dialogue);
    List<Dialogue> selectByUserId(Integer id);
    Dialogue selectByIds(Integer uid,Integer did);
    int delete(Integer uid,Integer did);
    boolean loadLocal(String msg,Integer uid,Integer did);

}
