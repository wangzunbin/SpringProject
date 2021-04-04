package com.wangzunbin.uaa.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ClassName:SecuredRestAPIIntTests
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/4 16:34
 */

@SpringBootTest
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
}
