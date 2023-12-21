package cn.edu.szu.controller;

import cn.edu.szu.domain.User;
import cn.edu.szu.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User createUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUserName("username");
        mockUser.setPassWord("password");
        return mockUser;
    }

    @Test
    void testGetUser() throws Exception {
        //创建用于返回的Dialogue
        User mockUser = createUser();

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(userService.selectById(anyLong())).thenReturn(mockUser);

        //判断请求返回结果
        mockMvc.perform(get("/users/{id}", mockUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20041))
                .andExpect(jsonPath("$.data").value(mockUser))
                .andExpect(jsonPath("$.msg").doesNotExist());

        verify(userService, times(1)).selectById(anyLong());
    }

    @Test
    void testGetUserErr() throws Exception {
        //拦截controller发往业务层service的请求，返回mockDialogue
        when(userService.selectById(anyLong())).thenReturn(null);

        //判断请求返回结果
        mockMvc.perform(get("/users/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20040))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.msg").value("数据查询失败，请重试"));

        verify(userService, times(1)).selectById(anyLong());
    }

    @Test
    void testSaveUser() throws Exception {
        //创建用于返回的Dialogue
        User mockUser = createUser();

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(userService.save(any(User.class))).thenReturn(1);

        //判断请求返回结果
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20011))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.msg").doesNotExist());

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testSaveUserErr() throws Exception {
        //创建用于返回的Dialogue
        User mockUser = createUser();

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(userService.save(any(User.class))).thenReturn(0);

        //判断请求返回结果
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(20010))
                .andExpect(jsonPath("$.data").value(false))
                .andExpect(jsonPath("$.msg").value("保存失败，请重试"));

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testValidateUser() throws Exception {
        //创建用于返回的Dialogue
        User mockUser = createUser();

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(userService.isValid(any(User.class))).thenReturn(1L);

        //判断请求返回结果
        mockMvc.perform(post("/users/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(21001))
                .andExpect(jsonPath("$.data").value(1L))
                .andExpect(jsonPath("$.msg").value("找到用户,登录成功"));

        verify(userService, times(1)).isValid(any(User.class));
    }

    @Test
    void testValidateUserErr() throws Exception {
        //创建用于返回的Dialogue
        User mockUser = createUser();

        //拦截controller发往业务层service的请求，返回mockDialogue
        when(userService.isValid(any(User.class))).thenReturn(-1L);

        //判断请求返回结果
        mockMvc.perform(post("/users/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(21000))
                .andExpect(jsonPath("$.data").value(-1L))
                .andExpect(jsonPath("$.msg").value("密码错误，重试"));

        verify(userService, times(1)).isValid(any(User.class));
    }
}