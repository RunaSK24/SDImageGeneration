package cn.edu.szu.service;

import cn.edu.szu.domain.User;

public interface UserService {
    int save(User user);//注册
    int update(User user);//修改密码
    User selectById(Long id);//搜索用户
    int delete(Long id);//注销
    Long isValid(User user);//判断密码正确性
}
