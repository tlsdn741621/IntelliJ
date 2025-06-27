package com.busanit501.hello_project._3jdbc.dao;

import com.busanit501.hello_project._3jdbc.domain.MemberVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {
    // mid, mpw 를 이용해서, 한 명 회원 조회.
    public MemberVO getMemberVO(String mid, String mpw) throws Exception {
        String query= "select mid,mpw,mname from tbl_member where mid=? and mpw=?";
        // 디비에서 조회한 회원 정보를 담을 임시 , 객체 생성.
        MemberVO memberVO = null;

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, mid);
        pstmt.setString(2, mpw);
        @Cleanup ResultSet rs = pstmt.executeQuery();

        rs.next();
        // 회원 정보가 있다면, 모델 클래스 담기.
        // 디비의 내용을 담아야 하는데, 화면에서 받아온 정보를 또 담고 있음. 여기가 틀림.
        memberVO = MemberVO.builder()
                .mid(rs.getString("mid"))
                .mpw(rs.getString("mpw"))
                .mname(rs.getString("mname"))
                .build();

        return memberVO;
    }

    // 자동로그인 기능 구현 , 도구 , uuid 업데이트
    public void  updateUuid(String mid, String uuid) throws Exception {
        String query= "update tbl_member set uuid=? where mid=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, uuid);
        pstmt.setString(2, mid);
        pstmt.executeUpdate();
    }

    //  uuid 를 이용해서, 멤버를 조회 하는 기능 필요함.
    public MemberVO getMemberVOByUuid(String uuid) throws Exception {
        String query = "select mid,mpw,mname,uuid from tbl_member where uuid=?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, uuid);
        @Cleanup ResultSet rs = pstmt.executeQuery();
        rs.next();

        MemberVO memberVO = MemberVO.builder()
                .mid(rs.getString("mid"))
                .mpw(rs.getString("mpw"))
                .mname(rs.getString("mname"))
                .uuid(rs.getString("uuid"))
                .build();

        return memberVO;
    }
}
