package cn.edu.szu.controller;

import cn.edu.szu.domain.Dialogue;
import cn.edu.szu.domain.Message;
import cn.edu.szu.service.DialogueService;
import cn.edu.szu.service.Impl.ImageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DialogueController.class)
class DialogueControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DialogueService dialogueService;

    @Autowired
    private ObjectMapper objectMapper;

    private Dialogue createTestDialogue() {
        Dialogue mockDialogue = new Dialogue();
        mockDialogue.setUid(1L);
        mockDialogue.setDid(1L);
        mockDialogue.setDialogueSource("testFile.txt");
        return mockDialogue;
    }

    private List<Dialogue> createTestDialogueList() {
        Dialogue mockDialogue = createTestDialogue();
        List<Dialogue> mockDialogueList = new ArrayList<>();
        mockDialogueList.add(mockDialogue);
        return mockDialogueList;
    }

    @Test
    void testGetDiaByIds() throws Exception {
        //创建用于返回的Dialogue
        Dialogue mockDialogue = createTestDialogue();

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.selectByIds(anyLong(), anyLong())).thenReturn(mockDialogue);

        //判断请求返回结果
        mockMvc.perform(get("/Dia/{uid}/{did}", mockDialogue.getUid(), mockDialogue.getDid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20041))
                .andExpect(jsonPath("$.data").value(mockDialogue))
                .andExpect(jsonPath("$.msg").value("成功"));

        verify(dialogueService, times(1)).selectByIds(anyLong(), anyLong());
    }

    @Test
    void testGetDiaByIdsErr() throws Exception {
        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.selectByIds(anyLong(), anyLong())).thenReturn(null);

        //判断请求返回结果
        mockMvc.perform(get("/Dia/{uid}/{did}", anyLong(), anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20040))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("不存在对话"));

        //判断dialogueService是否被调用
        verify(dialogueService, times(1)).selectByIds(anyLong(), anyLong());
    }

    @Test
    void testGetUsersDialogue() throws Exception {
        //创建用于service层返回的对象
        List<Dialogue> mockDialogues = createTestDialogueList();

        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.selectByUserId(anyLong())).thenReturn(mockDialogues);

        //判断请求返回结果
        mockMvc.perform(get("/Dia/{id}", mockDialogues.get(0).getUid()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20041))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0]").value(mockDialogues.get(0)))
                .andExpect(jsonPath("$.msg").value("成功"));

        verify(dialogueService, times(1)).selectByUserId(anyLong());
    }

    @Test
    void testGetUsersDialogueErr() throws Exception {
        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.selectByUserId(anyLong())).thenReturn(null);

        //判断请求返回结果
        mockMvc.perform(get("/Dia/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20040))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("不存在对话"));

        verify(dialogueService, times(1)).selectByUserId(anyLong());
    }

    @Test
    void testReadFile() throws Exception {
        //创建用于service层返回的对象
        String mockString = "TestFileString";

        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.ReadFile(anyString())).thenReturn(mockString);

        //判断请求返回结果
        mockMvc.perform(get("/Dia/fileRead/{src}", anyString() + ".txt"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20041))
                .andExpect(jsonPath("$.data").value(mockString))
                .andExpect(jsonPath("$.msg").value("成功"));

        verify(dialogueService, times(1)).ReadFile(anyString());
    }

    @Test
    void testReadFileErr() throws Exception {
        //创建用于service层返回的对象
        String mockString = "TestFileString";

        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.ReadFile(anyString())).thenReturn("");

        //判断请求返回结果
        mockMvc.perform(get("/Dia/fileRead/{src}", anyString() + ".txt"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20040))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.msg").value("不存在对话"));

        verify(dialogueService, times(1)).ReadFile(anyString());
    }

    @Test
    void testSaveDia() throws Exception {
        //创建用于返回的Dialogue
        Dialogue mockDialogue = createTestDialogue();

        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.insertDialogue(any(Dialogue.class))).thenReturn(1);

        //判断请求返回结果
        mockMvc.perform(post("/Dia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDialogue)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20011))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("Save Success"));

        verify(dialogueService, times(1)).insertDialogue(any(Dialogue.class));
    }

    @Test
    void testSaveDiaErr() throws Exception {
        //创建用于返回的Dialogue
        Dialogue mockDialogue = createTestDialogue();

        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.insertDialogue(any(Dialogue.class))).thenReturn(0);

        //判断请求返回结果
        mockMvc.perform(post("/Dia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockDialogue)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20010))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("Save ERROR"));

        verify(dialogueService, times(1)).insertDialogue(any(Dialogue.class));
    }

    @Test
    void testDeleteDia() throws Exception {
        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.delete(anyLong(), anyLong())).thenReturn(1);

        //判断请求返回结果
        mockMvc.perform(delete("/Dia/{uid}/{did}", anyLong(), anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20021))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("Delete Success"));

        verify(dialogueService, times(1)).delete(anyLong(), anyLong());
    }

    @Test
    void testDeleteDiaErr() throws Exception {
        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.delete(anyLong(), anyLong())).thenReturn(0);

        //判断请求返回结果
        mockMvc.perform(delete("/Dia/{uid}/{did}", anyLong(), anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20020))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("Delete ERROR"));

        verify(dialogueService, times(1)).delete(anyLong(), anyLong());
    }

    @Test
    void testTextToImage() throws Exception {
        Message mockMessage = new Message(1L, 1L, null, "1girl");
        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.textToImage(any(Message.class))).thenReturn("testImageBase64");

        //判断请求返回结果
        mockMvc.perform(post("/Dia/imageGeneration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockMessage)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(22001))
                .andExpect(jsonPath("$.data").value("testImageBase64"))
                .andExpect(jsonPath("$.msg").value("LOAD_SUCCESS"));

        verify(dialogueService, times(1)).textToImage(any(Message.class));
    }

    @Test
    void testTextToImageErr() throws Exception {
        Message mockMessage = new Message(1L, 1L, null, "1girl");
        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.textToImage(any(Message.class))).thenReturn(null);

        //判断请求返回结果
        mockMvc.perform(post("/Dia/imageGeneration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockMessage)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(22000))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.msg").value("LOAD_ERR"));

        verify(dialogueService, times(1)).textToImage(any(Message.class));
    }

    @Test
    void testImageToImage() throws Exception {
        Message mockMessage = new Message(1L, 1L, "imageBase64", "1girl");
        mockMessage.setImage(ImageUtil.convertImageToBase64Str("src/main/resources/static/images/bot.png"));

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.imageToImage(any(Message.class))).thenReturn("testImageBase64");

        //判断请求返回结果
        mockMvc.perform(post("/Dia/imageGeneration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockMessage)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(22001))
                .andExpect(jsonPath("$.data").value("testImageBase64"))
                .andExpect(jsonPath("$.msg").value("LOAD_SUCCESS"));

        verify(dialogueService, times(1)).imageToImage(any(Message.class));
    }

    @Test
    void testImageToImageErr() throws Exception {
        Message mockMessage = new Message(1L, 1L, "imageBase64", "1girl");
        mockMessage.setImage(ImageUtil.convertImageToBase64Str("src/main/resources/static/images/bot.png"));

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(dialogueService.imageToImage(any(Message.class))).thenReturn(null);

        //判断请求返回结果
        mockMvc.perform(post("/Dia/imageGeneration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockMessage)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(22000))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.msg").value("LOAD_ERR"));

        verify(dialogueService, times(1)).imageToImage(any(Message.class));
    }

    @Test
    void testGetImage() throws Exception {
        //创建用于service层返回的对象
        String mockString = "TestImageBase64";

        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.getImage(anyString())).thenReturn(mockString);

        //判断请求返回结果
        mockMvc.perform(get("/Dia/Image/{fileName}", anyString() + ".png"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20041))
                .andExpect(jsonPath("$.data").value(mockString))
                .andExpect(jsonPath("$.msg").value("获取成功"));

        verify(dialogueService, times(1)).getImage(anyString());
    }

    @Test
    void testGetImageErr() throws Exception {
        //拦截controller发往业务层service的请求，返回自定义的对象
        when(dialogueService.getImage(anyString())).thenReturn(null);

        //判断请求返回结果
        mockMvc.perform(get("/Dia/Image/{fileName}", anyString() + ".png"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20040))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("获取图片失败，图片可能已丢失"));

        verify(dialogueService, times(1)).getImage(anyString());
    }
}