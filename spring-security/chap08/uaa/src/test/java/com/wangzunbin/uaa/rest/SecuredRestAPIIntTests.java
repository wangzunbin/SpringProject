package com.wangzunbin.uaa.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ClassName:SecuredRestAPIIntTests
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 16:34
 */

@SpringBootTest
@Slf4j
public class SecuredRestAPIIntTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
//                应用SpringSecurity安全检查
                .apply(springSecurity())
                .build();
    }

    //  通过WithMockUser可以方便的模拟一个授权用户的访问
    @WithMockUser(username = "user")
    @Test
    public void givenUserDto_thenRegisterSuccess() throws Exception {
        mockMvc.perform(get("/api/greeting")).andExpect(status().isOk());
    }

    // 默认是USER角色
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    public void givenRoleUserOrAdmin_thenAccessSuccess() throws Exception {
        String contentAsString = mockMvc.perform(get("/api/users/{username}", "user"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        log.debug("打印内容: {}", contentAsString);
    }



    @WithMockUser
    @Test
    public void givenUserRole_whenQueryUserByEmail_shouldSuccess() throws Exception {
        mockMvc.perform(get("/api/users/by-email/{email}", "user@local.dev"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * 测试 @PostAuthorize("returnObject.username == authentication.name")
     * 查询自己的信息应该成功
     *
     * @throws Exception 异常
     */

    @WithMockUser(username = "wangzunbin1")
    @Test
    public void givenUserRole_whenQueryUserByEmail_shouldSuccessByUser() throws Exception {
        mockMvc.perform(get("/api/users/by-email/{email}", "wangzunbin1@local.dev"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    public void givenUserRole_whenAcessManagerResourceSuccess() throws Exception {
        mockMvc.perform(get("/api/users/manager"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
