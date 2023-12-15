package cn.edu.szu.service;

import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.domain.Message;

import java.util.List;

public interface DialogueService {
    int insertDialogue(Dialogue dialogue);

    int updateDialogue(Dialogue dialogue);

    List<Dialogue> selectByUserId(Long id);

    Dialogue selectByIds(Long uid, Long did);

    int delete(Long uid, Long did);

    String textToImage(Message msg);

    String imageToImage(Message msg);

    String ReadFile(String source);

    String getImage(String filename);
}
