package com.green.todotestapp.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// 클라이언트가 받는 부분
@Data
public class TodoInsParam {
    private String ctnt;
    private MultipartFile pic;
}
