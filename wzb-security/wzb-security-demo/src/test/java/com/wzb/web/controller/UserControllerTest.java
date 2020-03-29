package com.wzb.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ClassName:UserControllerTest  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 21:00   <br/>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username", "wzb")
                .param("ageStart", BigInteger.ONE.toString())
                .param("ageEnd", BigInteger.TEN.toString())
                .param("xxxx", "yyy")
//                .param("size", "15")
//                .param("page", "3")
//                .param("sort", "age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                // $.length()具体操作 https://github.com/json-path/JsonPath
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);

    }

    @Test
    public void whenGenInfoSuccess() throws Exception {
       String contentAsString =  mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("wzb")).andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    public void whenGetInfoFail() throws Exception{
        // 主要是测试path变量不能为字符串
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenCreateSuccess() throws Exception{
        Date date = new Date();
        System.out.println(date);
        String content = "{\"username\":\"wzb\",\"password\":null,\"birthDay\":" + date.getTime() + "}";
        String contentAsString = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1")).andReturn().getResponse().getContentAsString();

        System.out.println(contentAsString);
    }


    @Test
    public void whenUpdateSuccess() throws Exception {

        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(date);
        String content = "{\"username\":\"wzb\",\"password\":null,\"birthDay\":" + date.getTime() + "}";
        String contentAsString = mockMvc.perform(put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1")).andReturn().getResponse().getContentAsString();

        System.out.println(contentAsString);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception{
        String result = mockMvc.perform(fileUpload("/file")
                .file(new MockMultipartFile("file", "test.text",
                        String.valueOf(MediaType.MULTIPART_FORM_DATA), "hello upload".getBytes("UTF-8"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);

    }

}
