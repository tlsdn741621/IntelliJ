package com.busanit501.hello_project._2todo.service;

import com.busanit501.hello_project._2todo.dto.TodoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum TodoService {
    // static final 암묵적으로 생략함.
    INSTANCE;
    // 등록 기능,
    public void register(TodoDTO todoDTO) {
        System.out.println("서비스에서 작성할 글의 입력 데이터 확인 : "
                +todoDTO);
    }

    // 전체 조회,
    public List<TodoDTO> getList() {

        // 전,
        List<TodoDTO> list = new ArrayList<>();
        for (int i =0 ; i <10 ; i++){
            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTno((long)i);
            todoDTO.setTitle("Todo.." +i);
            todoDTO.setDueDate(LocalDate.now());
        }

        // 후, 빌더 패턴,
        // 연속적인 행위,
//        IntStream.range(0,10)
//        .mapToObj(더미데이터 10개생성)
//        .collect(생성된 10개 데이터 리스트화)
        List<TodoDTO> todoDTOList2 = IntStream.range(0,10).mapToObj(i -> {
            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTno((long)i);
            todoDTO.setTitle("Todo.." +i);
            todoDTO.setDueDate(LocalDate.now());
            return todoDTO;
        }).collect(Collectors.toList());


//        return list;
        return todoDTOList2;
    }

    // 하나 조회, TodoDTO , tno 번호에 해당하는 값 조회
    // 현재는, 메모리 상에서 더미로 작업 중.
    // 내일은 마리아 디비 서버 설치하고, 디비 연결을 할 예정.
    public TodoDTO getTodoByTno(long tno) {
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTno(tno);
        todoDTO.setTitle("샘플 Todo ");
        todoDTO.setDueDate(LocalDate.now());
        todoDTO.setFinished(true);
        return todoDTO;
    }
}
