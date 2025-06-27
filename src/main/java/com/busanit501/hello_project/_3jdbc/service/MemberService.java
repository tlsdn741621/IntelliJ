package com.busanit501.hello_project._3jdbc.service;

import com.busanit501.hello_project._3jdbc.dao.MemberDAO;
import com.busanit501.hello_project._3jdbc.domain.MemberVO;
import com.busanit501.hello_project._3jdbc.dto.MemberDTO;
import com.busanit501.hello_project._3jdbc.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {
    INSTANCE;
    // 외주, 의존하기, 가져오기.
    private MemberDAO memberDAO;
    private ModelMapper modelMapper;

    MemberService() {
        memberDAO = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    // 로그인 기능.
    public MemberDTO login(String mid, String mpw) throws Exception {
        MemberVO memberVO = memberDAO.getMemberVO(mid, mpw);
        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);
        return memberDTO;
    }

    // uuid 업데이트 기능.
    public void updateUuid(String mid, String uuid) throws Exception {
        memberDAO.updateUuid(mid, uuid);
    }

    // uuid 로 유저 검색.
    public MemberDTO getMemberDTOByUuid(String uuid) throws Exception {
        MemberVO memberVO = memberDAO.getMemberVOByUuid(uuid);
        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);
        return memberDTO;
    }
}
