package cn.edu.szu.service.Impl;

import cn.edu.szu.dao.DialogueDao;
import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.service.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class DialogueServiceImpl implements DialogueService {
    @Autowired
    DialogueDao dialogueDao;
    @Override
    public int insertDialogue(Dialogue dialogue) {
        String fileName = "file_" + System.currentTimeMillis() + ".txt";
        dialogue.setDialogueSource(fileName);
        return dialogueDao.insertDialogue(dialogue);
    }

    @Override
    public int updateDialogue(Dialogue dialogue) {
        return dialogueDao.updateDialogue(dialogue);
    }

    @Override
    public List<Dialogue> selectByUserId(Integer id) {
        return dialogueDao.selectByUserId(id);
    }

    @Override
    public Dialogue selectByIds(Integer uid, Integer did) {
        return dialogueDao.selectByIds(uid,did);
    }


    @Override
    public int delete(Integer uid,Integer did) {
        return dialogueDao.delete(uid, did);
    }

    @Override
    public boolean loadLocal(String msg,Integer uid,Integer did) {
        Dialogue dialogue = dialogueDao.selectByIds(uid,did);
        String src = dialogue.getDialogueSource();
        return loadLocalFile(msg,src);
    }
    private boolean loadLocalFile(String msg,String dialogSrc) {
        try {
            FileWriter fileWriter =
                    new FileWriter("src/main/resources/history/"+dialogSrc, true);
            fileWriter.write(msg+"\n");
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



}
