package cn.edu.szu.service.Impl;

import cn.edu.szu.dao.UserDao;
import cn.edu.szu.domain.User;
import cn.edu.szu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public int save(User user) {
        return userDao.save(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public User selectById(Integer id) {
        return userDao.selectById(id);
    }

    @Override
    public int delete(Integer id) {
        return userDao.deleteById(id);
    }
    @Override
    public Long isValid(User user){
        String name = user.getUserName();
        User user1 = userDao.selectByName(name);
        if (user1 == null){
            return -1L;
        }
        String pass1 = user.getPassWord();
        String pass2 = user1.getPassWord();
        if(pass2.equals(pass1)) {
            return user1.getId();
        }
        return -1L;
    }
}
