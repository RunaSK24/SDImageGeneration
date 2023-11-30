package cn.edu.szu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToImgResponse implements Serializable {
    /**
     * 生成的图片结果 base64
     */
    private List<String> images;

    /**
     * 入参和默认值
     */
    private TextToImgRequest parameters;

    /**
     * 参数的组合字符串
     */
    private String info;
}