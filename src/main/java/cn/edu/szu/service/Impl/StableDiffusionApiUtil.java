package cn.edu.szu.service.Impl;

import cn.edu.szu.domain.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StableDiffusionApiUtil {
    /**
     * 拼接TextToImg请求体
     *
     * @param prompt 引导词
     * @return TextToImg请求体
     */
    public static TextToImgRequest getText2ImageRequestBody(String prompt) {
        final String base64SrcImg = "base64SrcImg"; //convertImageToBase64("src/main/resources/image/1.jpg");

        Args args1 = Args.builder()
                .enabled(true)
                .control_mode(0)
                .guidance_start(0)
                .guidance_end(0.5)
                .weight(0.3)
                .pixel_perfect(true)
                .resize_mode(1)
                .model("control_v11p_sd15_softedge [a8575a2a]")
                .module("softedge_pidinet")
                .input_image(base64SrcImg)
                .lowvram(false)
                .processor_res(0)
                .threshold_a(0)
                .threshold_b(0)
                .build();

        Args args2 = Args.builder()
                .enabled(true)
                .control_mode(0)
                .guidance_start(0)
                .guidance_end(0.5)
                .weight(0.75)
                .pixel_perfect(true)
                .resize_mode(1)
                .model("control_v11f1p_sd15_depth [cfd03158]")
                .module("depth_midas")
                .input_image(base64SrcImg)
                .lowvram(false)
                .processor_res(0)
                .threshold_a(0)
                .threshold_b(0)
                .build();

        List<Args> argsList = new ArrayList<Args>();
        argsList.add(args1);
        argsList.add(args2);

        String vae = "pastel-waifu-diffusion.vae.pt";
        TextToImgRequest body = TextToImgRequest.builder().sampler_name("")
                .prompt(prompt)
                .negative_prompt("loli,nsfw,logo,text,badhandv4,EasyNegative,ng_deepnegative_v1_75t,rev2-badprompt,verybadimagenegative_v1.3,negative_hand-neg,mutated hands and fingers,poorly drawn face,extra limb,missing limb,disconnected limbs,malformed hands,ugly")
                .sampler_index("DPM++ SDE Karras")
                .seed(-1)
                .width(512)
                .height(512)
                .restore_faces(false)
                .tiling(false)
                .clip_skip(2)
                .batch_size(1)
                .script_args(new ArrayList<>())
                .alwayson_scripts(AlwaysonScripts.builder()
                        .controlnet(ControlNet.builder()
                                .args(argsList)
                                .build())
                        .build())
                .steps(28)
                .override_settings(OverrideSettings.builder()
                        .sd_model_checkpoint("chosenMix_chosenMix.ckpt [dd0aacadb6]")
                        .sd_vae(vae)
                        .build())
                .cfg_scale(7.0)
                .build();
        return body;
    }

    /**
     * 拼接ImgToImg请求体
     *
     * @param image base64图片数据
     * @return ImgToImg请求体
     */
    public static ImgToImgRequest getImg2ImageRequestBody(String image, String prompt) {
        final String base64SrcImg = "base64SrcImg"; //convertImageToBase64("src/main/resources/image/1.jpg");

        Args args1 = Args.builder()
                .enabled(true)
                .control_mode(0)
                .guidance_start(0)
                .guidance_end(0.5)
                .weight(0.3)
                .pixel_perfect(true)
                .resize_mode(1)
                .model("control_v11p_sd15_softedge [a8575a2a]")
                .module("softedge_pidinet")
                .input_image(base64SrcImg)
                .lowvram(false)
                .processor_res(0)
                .threshold_a(0)
                .threshold_b(0)
                .build();

        Args args2 = Args.builder()
                .enabled(true)
                .control_mode(0)
                .guidance_start(0)
                .guidance_end(0.5)
                .weight(0.75)
                .pixel_perfect(true)
                .resize_mode(1)
                .model("control_v11f1p_sd15_depth [cfd03158]")
                .module("depth_midas")
                .input_image(base64SrcImg)
                .lowvram(false)
                .processor_res(0)
                .threshold_a(0)
                .threshold_b(0)
                .build();

        List<Args> argsList = new ArrayList<>();
        argsList.add(args1);
        argsList.add(args2);
        String vae = "pastel-waifu-diffusion.vae.pt";
        List<String> images = new ArrayList<>();
        images.add(image);
        ImgToImgRequest body = ImgToImgRequest.builder().sampler_name("")
                .prompt(prompt)
                .negative_prompt("nsfw,logo,text,badhandv4,EasyNegative,ng_deepnegative_v1_75t,rev2-badprompt,verybadimagenegative_v1.3,negative_hand-neg,mutated hands and fingers,poorly drawn face,extra limb,missing limb,disconnected limbs,malformed hands,ugly")
                .sampler_index("DPM++ SDE Karras")
                .seed(-1)
                .width(512)
                .height(512)
                .restore_faces(false)
                .tiling(false)
                .batch_size(1)
                .script_args(new ArrayList<>())
                .alwayson_scripts(AlwaysonScripts.builder()
                        .controlnet(ControlNet.builder()
                                .args(argsList)
                                .build())
                        .build())
                .steps(28)
                .override_settings(OverrideSettings.builder()
                        .sd_model_checkpoint("chosenMix_chosenMix.ckpt [dd0aacadb6]")
                        .sd_vae(vae)
                        .build())
                .cfg_scale(7.0)
                .include_init_images(true)
                .init_images(images)
                .build();
        return body;
    }

    /**
     * 调用SD的文生图api
     *
     * @param body TextToImg请求体
     * @return List<String>生成的图片集合（base64格式）
     */
    public static List<String> callSdTextToImgApi(TextToImgRequest body) {
        RestTemplate restTemplate = new RestTemplate();
        //定义HTTP请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //组装请求体
        HttpEntity<TextToImgRequest> requestEntity = new HttpEntity<>(body, headers);

        //发送请求
        ResponseEntity<JSONObject> entity =
                restTemplate.postForEntity("http://sd.fc-stable-diffusion-plus.1012799444647674.cn-shenzhen.fc.devsapp.net/sdapi/v1/txt2img", requestEntity, JSONObject.class);

        //处理返回消息
        final ToImgResponse toImgResponse = handleResponse(entity);

        //拿出image的base64数据
        final List<String> images = toImgResponse.getImages();

        if (CollectionUtils.isEmpty(images)) {
            System.out.println("empty images");
            return new ArrayList<>();
        }

        return images;
    }

    /**
     * 调用SD的图生图api
     *
     * @param body ImgToImg请求体
     * @return List<String>生成的图片集合（base64格式）
     */
    public static List<String> callSdImgToImgApi(ImgToImgRequest body) {
        RestTemplate restTemplate = new RestTemplate();
        //定义HTTP请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //组装请求体
        HttpEntity<ImgToImgRequest> requestEntity = new HttpEntity<>(body, headers);

        //发送请求
        ResponseEntity<JSONObject> entity =
                restTemplate.postForEntity("http://sd.fc-stable-diffusion-plus.1012799444647674.cn-shenzhen.fc.devsapp.net/sdapi/v1/img2img", requestEntity, JSONObject.class);

        //处理返回消息
        final ToImgResponse toImgResponse = handleResponse(entity);

        //拿出image的base64数据
        final List<String> images = toImgResponse.getImages();

        if (CollectionUtils.isEmpty(images)) {
            System.out.println("empty images");
            return new ArrayList<>();
        }

        return images;
    }

    /**
     * 处理返回值，封装成ToImgResponse对象
     *
     * @param response 返回的json数据
     * @return 按照格式封装后的json数据
     */
    private static ToImgResponse handleResponse(ResponseEntity<JSONObject> response) {
        //api调用失败
        if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
            System.out.println("call stable diffusion api status code: " + JSONObject.toJSONString(response));
            //可能需要抛出异常
        }
        final JSONObject body = response.getBody();
        //返回为空
        if (Objects.isNull(body)) {
            System.out.println("send request failed. response body is empty");
            //可能需要抛出异常
        }

        //封装数据
        return body.toJavaObject(ToImgResponse.class);
    }
}
