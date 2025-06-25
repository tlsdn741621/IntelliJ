package com.busanit501.service;


import com.busanit501.hello_project._3jdbc.dto.TodoDTO;
import com.busanit501.hello_project._3jdbc.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Log4j2
public class TodoServiceTests {
    // 서비스의 기능을 불러오기.
    private TodoService todoService;

    // 각 메서드가 실행 되기전 , 먼저 실행하는 메서드.
    @BeforeEach
    public void ready() {
        todoService = TodoService.INSTANCE;
    }

    // 등록 기능 서비스
    @Test
    public void testRegister() throws Exception{
        // 화면 -> 컨트롤러 부터 , DTO를 받아왔다고 가정,
        // 더미 DTO 를 생성하기.
        TodoDTO todoDTO = TodoDTO.builder()
                .title("서비스, 단위테스트 진행")
                .dueDate(LocalDate.now())
                .build();
        log.info("TodoService 테스트: ");
        log.info("TodoService 테스트에서 todoDTO:"+todoDTO);
        // 서비스에서 기능 테스트.
        todoService.register(todoDTO);
    }
}
