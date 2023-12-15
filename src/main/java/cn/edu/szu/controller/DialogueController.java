package cn.edu.szu.controller;

import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.domain.Message;
import cn.edu.szu.service.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Dia")
public class DialogueController {
    @Autowired
    private DialogueService dialogueService;

    //根据userId和DialogueId查找唯一Dia
    @GetMapping("/{uid}/{did}")
    public Result getDiaByIds(@PathVariable Long uid, @PathVariable Long did) {
        Dialogue dialogue = dialogueService.selectByIds(uid, did);
        Integer code;
        String msg;
        if (dialogue == null) {
            code = Code.GET_ERR;
            msg = "不存在对话";
        } else {
            code = Code.GET_OK;
            msg = "成功";
        }
        return new Result(code, dialogue, msg);
    }

    //    获取用户对话列表
    @GetMapping("/{id}")
    public Result getUsersDialogue(@PathVariable Long id) {
        System.out.println(id + "  !!");
        List<Dialogue> dialogueList = dialogueService.selectByUserId(id);
        Integer code;
        String msg;
        if (dialogueList == null || dialogueList.isEmpty()) {
            code = Code.GET_ERR;
            msg = "不存在对话";
        } else {
            code = Code.GET_OK;
            msg = "成功";
        }
        return new Result(code, dialogueList, msg);
    }

    //读取文件(历史记录)
    @GetMapping("/fileRead/{src}")
    public Result ReadFile(@PathVariable String src) {
        src = "./src/main/resources/history/" + src;
        String res = dialogueService.ReadFile(src);
        Integer code;
        String msg;
        if (res.equals("") || res == null) {
            code = Code.GET_ERR;
            msg = "不存在对话";
        } else {
            code = Code.GET_OK;
            msg = "成功";
        }
        return new Result(code, res, msg);
    }

    @PostMapping
    public Result saveDia(@RequestBody Dialogue dialogue) {
        dialogue.setDid(System.currentTimeMillis());
        int len = dialogueService.insertDialogue(dialogue);
        int code;
        String msg;
        if (len > 0) {
            code = Code.SAVE_OK;
            msg = "Save Success";
        } else {
            code = Code.SAVE_ERR;
            msg = "Save ERROR";
        }
        return new Result(code, null, msg);
    }

    @PutMapping
    public Result updateDia(@RequestBody Dialogue dialogue) {
        int len = dialogueService.updateDialogue(dialogue);
        int code;
        String msg;
        if (len > 0) {
            code = Code.UPDATE_OK;
            msg = "Update Success";
        } else {
            code = Code.UPDATE_ERR;
            msg = "Update ERROR";
        }
        return new Result(code, null, msg);
    }

    @DeleteMapping("/{uid}/{did}")
    public Result deleteDia(@PathVariable Long uid, @PathVariable Long did) {
        int len = dialogueService.delete(uid, did);
        int code;
        String msg;
        if (len > 0) {
            code = Code.DELETE_OK;
            msg = "Delete Success";
        } else {
            code = Code.DELETE_ERR;
            msg = "Delete ERROR";
        }
        return new Result(code, null, msg);

    }

    @PostMapping("/storeTextMsg")//前端发送请求，将历史记录存储在本地
    public Result storeMsg(@RequestBody Message msg) {
        System.out.println("收到消息：" + msg);
        boolean check = dialogueService.loadLocal(msg);
        Integer code;
        String mes;
        if (check) {
            code = Code.HIS_LOAD_OK;
            mes = "LOAD_SUCCESS";
        } else {
            code = Code.HIS_LOAD_ERR;
            mes = "LOAD_ERR";
        }

        return new Result(code, null, mes);
    }

    @PostMapping("/storeImageMsg")//前端发送请求，将历史记录存储在本地
    public Result storeImage(@RequestBody Message msg) {
        //System.out.println("收到消息：" + msg);
        boolean check = true;
        Integer code;
        String mes;
        if (check) {
            code = Code.HIS_LOAD_OK;
            mes = "LOAD_SUCCESS";
        } else {
            code = Code.HIS_LOAD_ERR;
            mes = "LOAD_ERR";
        }

        return new Result(code, null, mes);
    }

    @GetMapping("/Image/{fileName}")
    public Result getImage(@PathVariable String fileName){
        String base64 = dialogueService.getImage(fileName);

        Integer code;
        String msg;
        if (base64 == null || base64.isEmpty()){
            code = Code.GET_ERR;
            msg = "获取图片失败，图片可能已丢失";
        }else {
            code = Code.GET_OK;
            msg = "获取成功";
        }

        return new Result(code, base64, msg);
    }
}
