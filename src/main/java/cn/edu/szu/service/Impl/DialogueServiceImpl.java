package cn.edu.szu.service.Impl;

import cn.edu.szu.dao.DialogueDao;
import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.domain.ImgToImgRequest;
import cn.edu.szu.domain.Message;
import cn.edu.szu.domain.TextToImgRequest;
import cn.edu.szu.service.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class DialogueServiceImpl implements DialogueService {
    @Autowired
    DialogueDao dialogueDao;

    @Override
    public int insertDialogue(Dialogue dialogue) {
        String fileName = "file_" + System.currentTimeMillis() + ".txt";
        dialogue.setDialogueSource(fileName);
        loadLocalFile("让我们开始吧", fileName, "Bot");
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
        return dialogueDao.selectByIds(uid, did);
    }


    @Override
    public int delete(Long uid, Long did) {
        return dialogueDao.delete(uid, did);
    }

    /**
     * 文生图
     *
     * @param msg
     * @return
     */
    @Override
    public String textToImage(Message msg) {
        String result = "";
        //保存消息到历史记录
        Dialogue dialogue = dialogueDao.selectByIds(msg.getUid(), msg.getDid());
        String src = dialogue.getDialogueSource();
        boolean saveCheck = loadLocalFile(msg.getMessage(), src, "User");
        if (!saveCheck) return null;

        //调用api进行文生图
        String fileName = String.format("%s.png", UUID.randomUUID().toString().replaceAll("-", ""));
        TextToImgRequest body = StableDiffusionApiUtil.getText2ImageRequestBody(msg.getMessage());
        final List<String> images = StableDiffusionApiUtil.callSdTextToImgApi(body);
        for (String image : images) {
            result = image;
            ImageUtil.convertBase64StrToImage(image, fileName);
        }

        //保存生成的图片到消息记录
        saveCheck = loadLocalFile("imageSource:" + fileName, src, "Bot");
        if (!saveCheck) return null;

        return result;
    }

    @Override
    public String imageToImage(Message msg) {
        String result = "";
        //保存用户输入的图片
        String fileName = String.format("%s.png", UUID.randomUUID().toString().replaceAll("-", ""));
        ImageUtil.convertBase64StrToImage(msg.getImage(), fileName);
        //保存消息记录
        Dialogue dialogue = dialogueDao.selectByIds(msg.getUid(), msg.getDid());
        String src = dialogue.getDialogueSource();
        //图片消息
        boolean saveCheck = loadLocalFile("imageSource:" + fileName, src, "User");
        if (!saveCheck) return null;
        //机器人回复
        saveCheck = loadLocalFile("请继续输入图生图的提示词", src, "Bot");
        if (!saveCheck) return null;
        //文字消息
        saveCheck = loadLocalFile(msg.getMessage(), src, "User");
        if (!saveCheck) return null;

        //进行图生图
        fileName = String.format("%s.png", UUID.randomUUID().toString().replaceAll("-", ""));
        ImgToImgRequest body = StableDiffusionApiUtil.getImg2ImageRequestBody(msg.getImage(),msg.getMessage());
        final List<String> images = StableDiffusionApiUtil.callSdImgToImgApi(body);
        for (String image : images) {
            result = image;
            ImageUtil.convertBase64StrToImage(image,fileName);
        }

        //保存生成的图片到消息记录
        saveCheck = loadLocalFile("imageSource:" + fileName, src, "Bot");
        if (!saveCheck) return null;

        return result;
    }

    /**
     * 读历史记录
     *
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
                res.append(line + "\n");
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


    private boolean loadLocalFile(String msg, String dialogSrc, String actor) {
        try {
            FileWriter fileWriter =
                    new FileWriter("src/main/resources/history/" + dialogSrc, true);
            fileWriter.write("\n" + actor + "=" + msg);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getImage(String filename) {
        return ImageUtil.convertImageToBase64Str("src/main/resources/image/" + filename);
    }
}
