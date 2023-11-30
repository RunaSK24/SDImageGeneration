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
public class ImgToImgRequest implements Serializable {
    /**
     * Alwayson Scripts
     */
    private AlwaysonScripts alwayson_scripts;
    /**
     * Batch Size
     */
    private Integer batch_size;
    /**
     * Cfg Scale
     */
    private Double cfg_scale;
    /**
     * Height
     */
    private Integer height;
    /**
     * Include Init Images
     */
    private Boolean include_init_images;
    /**
     * Init Images
     */
    private List<String> init_images;

    /**
     * Negative Prompt
     */
    private String negative_prompt;
    /**
     * Override Settings
     */
    private OverrideSettings override_settings;
    /**
     * Prompt
     */
    private String prompt;

    /**
     * Restore Faces
     */
    private Boolean restore_faces;

    /**
     * Sampler Index
     */
    private String sampler_index;
    /**
     * Sampler Name
     */
    private String sampler_name;

    /**
     * Script Args
     */
    private List<Object> script_args;
    /**
     * Seed
     */
    private Integer seed;
    /**
     * Steps
     */
    private Integer steps;

    /**
     * Tiling
     */
    private Boolean tiling;
    /**
     * Width
     */
    private Integer width;
}
