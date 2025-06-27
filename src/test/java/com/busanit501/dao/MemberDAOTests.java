package com.busanit501.dao;

import com.busanit501.hello_project._3jdbc.dao.MemberDAO;
import com.busanit501.hello_project._3jdbc.domain.MemberVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

@Log4j2
public class MemberDAOTests {
    // 1, MemberDAO 기능 사용하기, 가져오기.
    private MemberDAO memberDAO;

    // 각각 @Test 메서드가 실행 되기전에 실해되는 메서드.
    @BeforeEach
    public void ready() {
        memberDAO = new MemberDAO();
    }

    // 아이디, 패스워드, mid,mpw 를 이용해서, 한명 회원 조회 하기.
    @Test
    public void  getMembers() throws Exception {
        String mid = "lsy";
        String mpw = "1234";

        MemberVO memberVO = memberDAO.getMemberVO(mid, mpw);
        log.info("조회한 멤버 : " + memberVO);
    }

    // mid로 유저의 uuid 업데이트
    @Test
    public void testUpdateUuid() throws Exception {
        String mid = "lsy";
        String uuid = UUID.randomUUID().toString();
        memberDAO.updateUuid(mid, uuid);
    }

    // uuid 유저의 검색
    @Test
    public void testSearchWithUuid() throws Exception {
        // 실제 테이블에서, uuid 를 복사해서 사용하기.
        String uuid = "e7b93f15-0f9b-4d58-aa13-0318eaba991f";
        MemberVO memberVO = memberDAO.getMemberVOByUuid(uuid);
        log.info("DAO 테스트, uuid 로 검색한 유저 :  " + memberVO);

    }

}
