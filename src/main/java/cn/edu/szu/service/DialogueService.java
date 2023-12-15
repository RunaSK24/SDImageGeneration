package cn.edu.szu.service;

import cn.edu.szu.domain.Dialogue;

import java.util.List;

public interface DialogueService {
    int insertDialogue(Dialogue dialogue);
    int updateDialogue(Dialogue dialogue);
    List<Dialogue> selectByUserId(Long id);
    Dialogue selectByIds(Long uid,Long did);
    int delete(Long uid,Long did);
    boolean loadLocal(String msg,Long uid,Long did,String actor);

    String ReadFile(String source);

}
