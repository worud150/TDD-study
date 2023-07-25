package com.green.todotestapp;

import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoInsParam;
import com.green.todotestapp.model.TodoRes;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {

    private final TodoService SERVICE;

    @PostMapping (consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public TodoRes postTodo(@RequestPart String ctnt, @RequestPart MultipartFile pic){
        TodoInsParam param = new TodoInsParam();
        param.setCtnt(ctnt);
        param.setPic(pic);
        return SERVICE.insTodo(param);
    }
}
