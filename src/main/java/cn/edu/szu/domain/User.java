package cn.edu.szu.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;
    private String passWord;
}
