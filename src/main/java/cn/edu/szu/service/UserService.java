package cn.edu.szu.service;

import cn.edu.szu.domain.User;

public interface UserService {
    int save(User user);
    int update(User user);
    User selectById(Integer id);
    int delete(Integer id);
    boolean isValid(User user);

}
