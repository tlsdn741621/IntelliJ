package com.busanit501.hello_project._3jdbc.domain;

import lombok.*;

import java.time.LocalDate;

// VO:Value Object
// 기능 : DB에서 받은 데이터를 담아둘 클래스 객체.
// DB에 데이터를 영향을 주는 역할(쓰기, 수정, 삭제 등)

// DTO(Data Transfer Object)
// 화면에서 사용자들로부터 받은 데이터를 담아둘 클래스 객체

// 진행,
// 화면 -> 데이터 전달 -> DTO 담기 -> 서비스에서
// -> VO 옮겨 담기 -> VO 를 디비에 쓰기, 수정, 삭제 작업하기.

// 소스코드 생성이 아니라.
// 롬복 이용해서, 사용해보기.
@Getter
//@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoVO {
    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
