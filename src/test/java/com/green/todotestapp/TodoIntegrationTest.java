package com.green.todotestapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoRes;
import com.green.todotestapp.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class TodoIntegrationTest extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Value("${file.dir}")
    private String fileDir;

    @Test
    @Rollback(value = false)
    public void postTodo() throws Exception{

        String originFileNm = "9ad908e2-13cb-4756-b42a-cc49d03d0aeb.png";
        String contentType = "png";
        String filePath = "D:/download/board3/user/9/" + originFileNm;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MockMultipartFile pic = new MockMultipartFile("pic", originFileNm, contentType, fileInputStream);

        MvcResult mr = mvc.perform(multipart ("/api/todo")
                        .file(pic)
                        .part(new MockPart("ctnt", "test3".getBytes(StandardCharsets.UTF_8)))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = mr.getResponse().getContentAsString();
        TodoRes todoRes = om.readValue(content, TodoRes.class);
        log.info("todoRes : {} ", todoRes);

        String dicPath = MyFileUtils.getAbsolutePath(fileDir + "/todo/" + todoRes.getItodo());
        File dicFile = new File(dicPath);

        assertEquals(true, dicFile.exists(), "1번 폴더가 없음");

        File picFile = new File(dicPath, todoRes.getPic());
        assertEquals(true, picFile.exists(), "1번 이미지가 없음");
        assertEquals("test3", todoRes.getCtnt());

    }

}
