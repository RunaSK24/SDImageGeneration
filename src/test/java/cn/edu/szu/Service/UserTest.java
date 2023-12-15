package cn.edu.szu.Service;

import cn.edu.szu.dao.UserDao;
import cn.edu.szu.domain.User;
import cn.edu.szu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;
    @Test
    public void saveTest(){
        User user = new User();
        user.setId(3L);
        user.setUserName("ZhangSan");
        user.setPassWord("123456");
        int a = userService.save(user);

    }

    @Test
    public void updateTest(){
        User user = new User();
        user.setId(3L);
        user.setUserName("ZhangSan");
        user.setPassWord("123456");
        int a = userService.update(user);

    }

    @Test
    public void selectByIdTest(){
        System.out.println(userService.selectById(1));
    }
    @Test
    public void deleteTest(){
        userService.delete(3);
    }

    @Test
    public void Vaild(){
        User user = new User();
        user.setId(3L);
        user.setUserName("ZhangSan");
        user.setPassWord("123456");
        System.out.println(userService.isValid(user));
    }
}
