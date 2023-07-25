package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoUpdDto;
import com.green.todotestapp.model.TodoVo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoMapperTest {

    @Autowired
    private TodoMapper MAPPER;

    @Test
    void insTodo() {
        TodoInsDto dto = new TodoInsDto();
        dto.setCtnt("testStory");
        dto.setPic("main.jpg");

        int result = MAPPER.insTodo(dto);
        assertEquals(1,result);
        assertEquals(5,dto.getItodo());

        TodoInsDto dto2 = new TodoInsDto();
        dto2.setCtnt("testStory22");
        dto2.setPic("sub.jpg");

        int result2 = MAPPER.insTodo(dto2);
        assertEquals(1,result2);
        assertEquals(6,dto2.getItodo());

        List<TodoVo> list = MAPPER.selTodo();

        assertEquals(6 ,list.size());

        TodoVo vo = list.get(4);
        assertEquals(5, vo.getItodo());
        assertEquals(dto.getCtnt(), vo.getCtnt());
        assertEquals(dto.getPic(), vo.getPic());

        TodoVo vo2 = list.get(5);
        assertEquals(6, vo2.getItodo());
        assertEquals(dto2.getCtnt(), vo2.getCtnt());
        assertEquals(dto2.getPic(), vo2.getPic());
    }

    @Test
    public void selTodo() {
        List<TodoVo> list = MAPPER.selTodo();


        assertEquals(4, list.size());

        TodoVo vo = list.get(0);
        assertEquals(1, vo.getItodo());
        assertEquals("차돌", vo.getCtnt());
        assertEquals("2023-06-13T16:51:11", vo.getCreatedAt().toString());
        assertEquals(null, vo.getPic());
        assertEquals(1, vo.getFinishYn());
        assertEquals("2023-06-13T16:51:23", vo.getFinishedAt().toString());

        TodoVo vo1 = list.get(1);
        assertEquals(2, vo1.getItodo());
        assertEquals("먹어", vo1.getCtnt());
        assertEquals("2023-06-13T16:51:23", vo1.getCreatedAt().toString());
        assertEquals(null, vo1.getPic());
        assertEquals(0, vo1.getFinishYn());
        assertEquals(null, vo1.getFinishedAt());
    }

    @Test
    public void updTodo () {
        TodoUpdDto dto = new TodoUpdDto();
        dto.setItodo(1);
        dto.setCtnt("111");
        dto.setPic("111.jpg");
        int result = MAPPER.updTodo(dto);
        assertEquals(1, result);

        List<TodoVo> list = MAPPER.selTodo();

        TodoVo vo = list.get(0);
        assertEquals("111", vo.getCtnt());
        assertEquals("111.jpg", vo.getPic());

        dto.setItodo(1);
        dto.setPic(null);
        int result1 = MAPPER.updTodo(dto);
        assertEquals(1, result1);

        TodoVo vo1 = list.get(0);
        assertEquals("111", vo1.getCtnt());
        assertEquals("111.jpg", vo1.getPic());
    }
}




























