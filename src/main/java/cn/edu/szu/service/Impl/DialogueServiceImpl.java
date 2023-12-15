package cn.edu.szu.service.Impl;

import cn.edu.szu.dao.DialogueDao;
import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.service.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DialogueServiceImpl implements DialogueService {
    @Autowired
    DialogueDao dialogueDao;
    @Override
    public int insertDialogue(Dialogue dialogue) {
        String fileName = "file_" + System.currentTimeMillis() + ".txt";
        dialogue.setDialogueSource(fileName);
        loadLocalFile("让我们开始吧",fileName,"Bot");
        return dialogueDao.insertDialogue(dialogue);
    }

    @Override
    public int updateDialogue(Dialogue dialogue) {
        return dialogueDao.updateDialogue(dialogue);
    }

    @Override
    public List<Dialogue> selectByUserId(Long id) {
        return dialogueDao.selectByUserId(id);
    }

    @Override
    public Dialogue selectByIds(Long uid, Long did) {
        return dialogueDao.selectByIds(uid,did);
    }


    @Override
    public int delete(Long uid,Long did) {
        return dialogueDao.delete(uid, did);
    }

    /**
     * 存储历史记录
     * @param msg
     * @param uid
     * @param did
     * @return
     */
    @Override
    public boolean loadLocal(String msg,Long uid,Long did,String actor) {
        Dialogue dialogue = dialogueDao.selectByIds(uid,did);
        String src = dialogue.getDialogueSource();
        return loadLocalFile(msg,src,actor);
    }

    /**
     * 读历史记录
     * @param source
     * @return
     */
    @Override
    public String ReadFile(String source) {
        StringBuilder res = new StringBuilder();
        String filePath = source; // 替换为你的文件路径
        try {
            List<String> lines = readLinesFromFile(filePath);
            for (String line : lines) {
//                System.out.println(line);
                res.append(line+"\n");
                // 在这里进行对每一行的处理
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return res.toString();
    }
    public List<String> readLinesFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }


    private boolean loadLocalFile(String msg,String dialogSrc,String actor) {
        try {
            FileWriter fileWriter =
                    new FileWriter("src/main/resources/history/"+dialogSrc, true);
            fileWriter.write("\n"+actor+"="+msg);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



}
