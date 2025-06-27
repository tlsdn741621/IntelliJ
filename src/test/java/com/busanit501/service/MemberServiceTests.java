package com.busanit501.service;

import com.busanit501.hello_project._3jdbc.dto.MemberDTO;
import com.busanit501.hello_project._3jdbc.service.MemberService;
import com.busanit501.hello_project._3jdbc.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Log4j2
public class MemberServiceTests {
    // 서비스의 기능을 불러오기.
    private MemberService memberService;

    // 각 메서드가 실행 되기전 , 먼저 실행하는 메서드.
    @BeforeEach
    public void ready() {
        memberService = MemberService.INSTANCE;
    }

    @Test
    public void testLogin() throws Exception {
        String mid = "lsy";
        String mpw = "1234";
        MemberDTO memberDTO = memberService.login(mid, mpw);
        log.info("멤버서비스 테스트 : 로그인 확인 된 유저 :"+memberDTO);
    }

    // 업데이트 uuid
    @Test
    public void updateUuid() throws  Exception {
        String mid = "lsy";
        String uuid = UUID.randomUUID().toString();
        memberService.updateUuid(mid,uuid);
    }

    // uuid 멤버 검색
    @Test
    public void testSearchWithUuid() throws Exception {
        // 실제 테이블에서, uuid 를 복사해서 사용하기.
        String uuid = "e7b93f15-0f9b-4d58-aa13-0318eaba991f";
        MemberDTO memberDTO = memberService.getMemberDTOByUuid(uuid);
        log.info("MemberService 테스트, uuid 로 검색한 유저 :  " + memberDTO);

    }

}
