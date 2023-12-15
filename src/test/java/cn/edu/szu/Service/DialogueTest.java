package cn.edu.szu.Service;

import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.domain.Message;
import cn.edu.szu.service.DialogueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DialogueTest {
    @Autowired
    DialogueService dialogueService;
    @Test
    public void insertDialogue(){
        Dialogue dialogue = new Dialogue();
        dialogue.setUid(3L);
        dialogue.setDid(5L);
        dialogue.setDialogueSource("dialogue");
        dialogueService.insertDialogue(dialogue);

    }
    @Test
    public void updateDialogue(){
        Dialogue dialogue = new Dialogue();
        dialogue.setUid(3L);
        dialogue.setDid(2L);
        dialogue.setDialogueSource("update source");
        dialogueService.updateDialogue(dialogue);
    }
    @Test
    public void selectByUserId(){
        System.out.println(dialogueService.selectByUserId(3L));
        System.out.println(dialogueService.ReadFile("./src/main/resources/history/file_1701328704148.txt"));

    }

    @Test
    public void selectByIds(){
        System.out.println(dialogueService.selectByIds(3L,1702629678137L));
    }
    @Test
    public void delete(){
        System.out.println(dialogueService.delete(3L,1L));
    }
    @Test
    public void load(){
        for (int i = 0 ;i < 10;i++){
            String base64 = dialogueService.textToImage(new Message(3L,3L,"777"));
        }


    }

}
