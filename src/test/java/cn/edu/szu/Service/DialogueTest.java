package cn.edu.szu.Service;

import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.domain.Message;
import cn.edu.szu.service.DialogueService;
import cn.edu.szu.service.Impl.ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DialogueTest {
    @Autowired
    DialogueService dialogueService;

    @Test
    @Transactional
    public void insertDialogue(){
        Dialogue dialogue = new Dialogue();
        dialogue.setUid(3L);
        dialogue.setDid(10L);
        dialogue.setDialogueSource("dialogue");
        assertTrue(dialogueService.insertDialogue(dialogue) > 0);
    }
    @Test
    @Transactional
    public void updateDialogue(){
        Dialogue dialogue = new Dialogue();
        dialogue.setUid(1L);
        dialogue.setDid(1L);
        dialogue.setDialogueSource("update source");
        assertTrue(dialogueService.updateDialogue(dialogue) > 0);
        assertEquals(dialogue,dialogueService.selectByIds(1L,1L));
    }
    @Test
    public void selectByUserId(){
        assertNotNull(dialogueService.selectByUserId(3L));
        System.out.println(dialogueService.selectByUserId(3L));
    }

    @Test
    public void selectByIds(){
        assertNotNull(dialogueService.selectByIds(3L,1702654410968L));
        System.out.println(dialogueService.selectByIds(3L,1702654410968L));
    }
    @Test
    @Transactional
    public void delete(){
        assertTrue(dialogueService.delete(1L,1L) > 0);
    }

    /**
     * 读文件测试
     */
    @Test
    @Transactional
    public void ReadFileTest(){
        String expectedContent = "1234456\n" + "555\n";
        String actualContent = dialogueService.ReadFile("src/main/resources/history/ddd.txt");

        assertNotNull(actualContent);
        System.out.println(actualContent);
        assertEquals(expectedContent, actualContent);
    }
    @Test
    @Transactional//图生文测试
    public void textToImage(){
        // 准备测试数据
        Long uid = 1L;
        Long did = 1L;
        String message = "Hello, World!";
        // 创建Message对象
        Message msg = new Message(uid, did, null, message);
        // 模拟DialogueDao的行为，使其返回预期的Dialogue对象
        Dialogue dialogue = new Dialogue();
        // 调用方法进行测试
        String result = dialogueService.textToImage(msg);
        // 断言结果是否符合预期
        assertNotNull(result);
    }

    @Test
    @Transactional//图生图测试
    public void imgToImg(){
        // 准备测试数据
        Long uid = 1L;
        Long did = 1L;
        String message = "Hello, World!";
        String imageFileName = "src/main/resources/static/images/bot.png";
        //图片转Base64字符串
        String base64Str = ImageUtil.convertImageToBase64Str(imageFileName);
        // 创建Message对象
        Message msg = new Message(uid, did, base64Str, message);
        // 模拟DialogueDao的行为，使其返回预期的Dialogue对象
        Dialogue dialogue = new Dialogue();
        // 调用方法进行测试
        String result = dialogueService.imageToImage(msg);
        // 断言结果是否符合预期
        assertNotNull(result);
    }

    /**
     * 保存历史记录测试
     */
    @Test
    @Transactional
    public void loadFileTest(){
        boolean check = dialogueService.loadLocalFile("hello","ddd.txt","bot");
        assertTrue(check);
    }

    /**
     * 从服务器取出图片
     */
    @Test
    public void getImageTest(){
        String base64 = dialogueService.getImage("0cd579e19ba24acf83320751c6d74c5f.png");
        assertNotNull(base64);
    }

}
