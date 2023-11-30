package cn.edu.szu.Service;

import cn.edu.szu.domain.Dialogue;
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
        dialogue.setUid(3);
        dialogue.setDid(3);
        dialogue.setDialogueSource("dialogue");
        dialogueService.insertDialogue(dialogue);

    }
    @Test
    public void updateDialogue(){
        Dialogue dialogue = new Dialogue();
        dialogue.setUid(3);
        dialogue.setDid(2);
        dialogue.setDialogueSource("update source");
        dialogueService.updateDialogue(dialogue);
    }
    @Test
    public void selectByUserId(){
        System.out.println(dialogueService.selectByUserId(3));
    }

    @Test
    public void selectByIds(){
        System.out.println(dialogueService.selectByIds(3,1));
    }
    @Test
    public void delete(){
        System.out.println(dialogueService.delete(3,1));
    }
    @Test
    public void load(){
        for (int i = 0 ;i < 10;i++){
            boolean check = dialogueService.loadLocal("777",3,3);
        }


    }

}
