package cn.edu.szu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private Long uid;
    private Long did;
    private String message;
}
