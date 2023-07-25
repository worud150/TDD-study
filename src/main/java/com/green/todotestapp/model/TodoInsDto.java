package com.green.todotestapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// 디비의 내용을 받는 친구
public class TodoInsDto {
    private String ctnt;
    private String pic;
    private Long itodo;
}
