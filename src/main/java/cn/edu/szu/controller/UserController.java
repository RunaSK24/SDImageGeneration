package cn.edu.szu.controller;

import cn.edu.szu.domain.User;
import cn.edu.szu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
//    获取User
    @GetMapping("/{id}")
    public Result getUser(@PathVariable Long id){
        User user = userService.selectById(id);
        Integer code = user != null? Code.GET_OK : Code.GET_ERR;
        String msg = user != null ? null : "数据查询失败，请重试";
        return new Result(code,user,msg);
    }
    @PostMapping
    public Result saveUser(@RequestBody User user){
        System.out.println(user);
        Long id = System.currentTimeMillis();
        user.setId(id);
        int check = userService.save(user);
        boolean res = check > 0;
        Integer code = check > 0?Code.SAVE_OK : Code.SAVE_ERR;
        String msg = check >0 ? null : "保存失败，请重试";
        return new Result(code,res,msg);
    }
    @PostMapping("/validate")
    public Result validateUser(@RequestBody User user) {
        System.out.println(user);
        Long check = userService.isValid(user);
        System.out.println(check);
        StringBuilder msg = new StringBuilder();
        Integer code;
        if (check != -1){
            msg.append("找到用户,登录成功");
            code = Code.USER_CHECK_OK;
        }else {
            msg.append("密码错误，重试");
            code = Code.USER_CHECK_ERR;
        }
        return new Result(code,check,msg.toString());
    }

}
