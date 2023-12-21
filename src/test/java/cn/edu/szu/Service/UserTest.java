package cn.edu.szu.Service;

import cn.edu.szu.dao.UserDao;
import cn.edu.szu.domain.User;
import cn.edu.szu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserTest {
    @Autowired
    UserService userService;
    @Test
    @Transactional
    public void saveTest(){
        User user = new User();
        user.setId(10L);
        user.setUserName("ZhangSansss");
        user.setPassWord("123456ddd");
        int a = userService.save(user);
        assertTrue(a > 0);
        assertEquals(user,userService.selectById(10L));
    }

    @Test
    @Transactional
    public void updateTest(){
        User user = new User();
        user.setId(3L);
        user.setUserName("ZhangSan");
        user.setPassWord("1234567");
        int a = userService.update(user);
        assertTrue(a > 0);
        assertEquals(user,userService.selectById(3L));
    }

    @Test
    public void selectByIdTest(){
        System.out.println(userService.selectById(1L));
        assertNotNull(userService.selectById(1L));
    }
    @Test
    @Transactional
    public void deleteTest(){
        assertTrue(userService.delete(2L) > 0);
    }

    @Test
    public void Vaild(){
        User user = new User();
        user.setId(3L);
        user.setUserName("ZhangSan");
        user.setPassWord("123456");
        assertNotEquals(userService.isValid(user),-1L);
    }
}
