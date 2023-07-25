package com.green.todotestapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Data
// entity에 가까운 res
// 응답받는 부분
public class TodoRes {
    private Long itodo;
    private String ctnt;
    private String pic;
    private LocalDateTime createdAt;
    private int finishYn;

    public TodoRes(TodoInsDto dto) {
        this.itodo = dto.getItodo();
        this.ctnt = dto.getCtnt();
        this.pic = dto.getPic();
        this.createdAt = LocalDateTime.now();
        this.finishYn = 0;
    }
}

