package cn.edu.szu.Service;

import cn.edu.szu.domain.*;
import cn.edu.szu.service.Impl.ImageUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Slf4j
public class StableDiffusionTest {

    @Test
    public void testSdApi() throws IOException {
        TextToImgRequest body = getArtisticWordStableDiffusionTextToImg();
        final List<String> images = callSdApi(body);
        for (String image : images) {
            ImageUtil.convertBase64StrToImage(image, String.format("./src/main/resources/image/%s.png", UUID.randomUUID().toString().replaceAll("-", "")));
            //writeBase642ImageFile(image, String.format("./src/main/resources/image/%s.png", UUID.randomUUID().toString().replaceAll("-", "")));
        }
    }

    private TextToImgRequest getArtisticWordStableDiffusionTextToImg() throws IOException {
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

        String vae = "pastel-waifu-diffusion.vae.pt";
        TextToImgRequest body = TextToImgRequest.builder().sampler_name("")
                .prompt("masterpiece, best quality, very detailed, extremely detailed beautiful, super detailed, tousled hair, illustration, dynamic angles, girly, fashion clothing, standing, mannequin, looking at viewer, interview, beach, beautiful detailed eyes, exquisitely beautiful face, floating, high saturation, beautiful and detailed light and shadow")
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
                                .args(Lists.newArrayList(args1, args2))
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

    private List<String> callSdApi(TextToImgRequest body) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TextToImgRequest> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<JSONObject> entity = restTemplate.postForEntity("http://sd.fc-stable-diffusion-plus.1012799444647674.cn-shenzhen.fc.devsapp.net/sdapi/v1/txt2img", requestEntity, JSONObject.class);
        final TextToImgResponse textToImgResponse = handleResponse(entity);
        final List<String> images = textToImgResponse.getImages();

        if (CollectionUtils.isEmpty(images)) {
            log.info("empty images");
            return Lists.newArrayList();
        }

        return images;
    }

    private TextToImgResponse handleResponse(ResponseEntity<JSONObject> response) {
        if (Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful()) {
            log.warn("call stable diffusion api status code: {}", JSONObject.toJSONString(response));
        }

        final JSONObject body = response.getBody();
        if (Objects.isNull(body)) {
            log.error("send request failed. response body is empty");
        }
        return body.toJavaObject(TextToImgResponse.class);
    }


//    public static void writeBase642ImageFile(String image, String fileName) {
//        try (OutputStream outputStream = new FileOutputStream(fileName)) {
//            byte[] imageBytes = Base64.getDecoder().decode(image);
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
//
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//
//            log.info("图片写入成功！");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String convertImageToBase64(String imagePath) throws IOException {
//        File file = new File(imagePath);
//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] imageData = new byte[(int) file.length()];
//        fileInputStream.read(imageData);
//        fileInputStream.close();
//        return Base64.getEncoder().encodeToString(imageData);
//    }
}
