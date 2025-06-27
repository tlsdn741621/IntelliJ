package com.busanit501.hello_project._3jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String mid;
    private String mpw;
    private String mname;
    // 추가, 자동로그인, uuid
    private String uuid;
}
