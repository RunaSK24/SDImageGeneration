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
public class TextToImgRequest implements Serializable {
    /**
     * 默认 {}
     */
    private AlwaysonScripts alwayson_scripts;

    /**
     * 批次数 default: 1
     */
    private Integer batch_size;

    /**
     * 提示词引导系数, 默认7
     */
    private Double cfg_scale;

    /**
     * 高度
     */
    private Integer height;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 正向提示词
     */
    private String prompt;

    /**
     * 反向提示词
     */
    private String negative_prompt;

    /**
     * 拓展设置
     */
    private OverrideSettings override_settings;

    private Integer clip_skip;

    /**
     * 面部修复, 默认 false
     */
    private Boolean restore_faces;

    /**
     * 采样方法 (Sampler) 下标
     */
    private String sampler_index;

    /**
     * 采样方法 (Sampler), 默认 null
     */
    private String sampler_name;

    private List<Object> script_args;

    /**
     * 随机数种子 (Seed)
     */
    private Integer seed;

    /**
     * 迭代步数 (Steps), 默认 50
     */
    private Integer steps;

    /**
     * 平铺图
     */
    private Boolean tiling;
}
