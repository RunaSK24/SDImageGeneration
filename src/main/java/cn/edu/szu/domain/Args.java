package cn.edu.szu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Args {
    private boolean enabled;
    /**
     * PreProcessor 例如："module": "lineart_coarse"
     */
    private String module;
    private String model;
    private double weight = 1.0;
    private String input_image;
    private String mask;
    private int control_mode = 0;
    private int resize_mode;

    /**
     * enable pixel-perfect preprocessor. defaults to false
     */
    private boolean pixel_perfect;

    /**
     * whether to compensate low GPU memory with processing time. defaults to false
     */
    private boolean lowvram;
    private int processor_res;
    private int threshold_a;
    private int threshold_b;
    private double guidance_start;
    private double guidance_end = 1.0;
}
