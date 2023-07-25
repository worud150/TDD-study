package com.green.todotestapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@MockMvcConfig
@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService service;

    @Test
    @DisplayName("todo - insert")
    void postTodo() throws Exception {
        TodoInsDto dto = new TodoInsDto();
        dto.setItodo(3L);
        dto.setCtnt("test");
        dto.setPic("main.jpg");
        TodoRes res = new TodoRes(dto);
        given(service.insTodo(any())).willReturn(res);

        String originFileNm = "9ad908e2-13cb-4756-b42a-cc49d03d0aeb.png";
        String contentType = "png";
        String filePath = "D:/download/board3/user/9/" + originFileNm;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MockMultipartFile pic = new MockMultipartFile("pic", originFileNm, "jpg", fileInputStream);

        // 자바객체를 문자로 바꿔주고 그리고 제이슨으로 되어있는 문자를 자바 객체로 바꿔주는 것
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String resJson = om.writeValueAsString(res);

        mvc.perform(multipart ("/api/todo")
                        .file(pic)
                        .part(new MockPart("ctnt", "test".getBytes(StandardCharsets.UTF_8)))
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().string(resJson))
                    .andDo(print());

        verify(service).insTodo(any());
    }
}