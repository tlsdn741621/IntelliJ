package com.busanit501.hello_project._3jdbc.dao;

import com.busanit501.hello_project._3jdbc.domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    // 1, 테스트용으로 현재 시간 가져오는 메서드 , 디비서버에서 받아오기.
    public String getTime() {
        // 디비로 부터 전달받은 시간을 담아둘 임시 변수.
        String now = null;
        // 디비 연결 , sql 전달, 값 가져오기. 자원 반납(자동으로 하기 위해서 try~resource).
        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("select now()");
            ResultSet rs = pstmt.executeQuery();){
        // rs, 가상 테이블, 디비의 결과 내용을 테이블 형식으로 가지고 있다.
            rs.next();
            now = rs.getString(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;

    } //getTime

    //2. 똑같은 기능, 시간 가져오기,
    // 자원 반납을 다르게 표현.
    // Lombok 기능 중에서,  @Cleanup 을 이용해서, 자동반납 하기.
    public String getTime2() throws Exception {
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement("select now()");
        @Cleanup ResultSet rs = pstmt.executeQuery();

        rs.next(); // 가상 테이블에서 데이터 가져오고,
        String now = rs.getString(1);
        return now;
    } //getTime2

    // 등록 기능. 화면에서 todo의 내용을 전달 받아와서, -> 디비에 넣을 예정.
    // 화면에서 데이터를 각각 받는 것보다, -> 모델 클래스 담아서 전달.
    // 전달 개요 : 화면 -> 컨트롤러(C) -> 서비스 (S) - > DAO(현위치) -> DB
    public void insert(TodoVO todoVO) throws Exception{
        // 등록, insert sql, 문장 작성,
        String sql ="insert into tbl_todo (title, dueDate, finished) values (?,?,?)";

        // 디비에 연결할 객체 작성, 자원 반납 방법) @Cleanup 사용하기.
        // 연결 객체, sql 문장 담는 객체 , 반복.
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, todoVO.getTitle());
        // 요구사항 Date 타입을 원하는데, LocalDate 타입을 형변환하기.
        pstmt.setDate(2, Date.valueOf(todoVO.getDueDate()));
        pstmt.setBoolean(3, todoVO.isFinished());

        // 실제 디비서버에 반영하기. 쓰기 작업 진행.
        pstmt.executeUpdate();

    } // insert

    // 전체 조회 기능.
    public List<TodoVO> selectAll() throws Exception{
        String sql ="select * from tbl_todo";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        // 조회, ResultSet 필요함.
        @Cleanup ResultSet rs = pstmt.executeQuery();

        // 디비로 부터 전달 받은 내용을 담아 둘 임시 리스트
        List<TodoVO> list = new ArrayList<>();

        // 검색 결과를, -> 모델 담고 -> 리스트에 담기 , 반복 하기.
        while(rs.next()){
            // 디비로 부터 받은 하나의 행의 값을 저장할 임시 모델 객체
            TodoVO vo = TodoVO.builder()
                    .tno(rs.getLong("tno"))
                    .title(rs.getString("title"))
                    .dueDate(rs.getDate("dueDate").toLocalDate())
                    .finished(rs.getBoolean("finished"))
                    .build();
            // 모델 -> 리스트 담기.
            list.add(vo);
        }
        return list;
    }

    // 하나 조회,
    public TodoVO selectOne(Long tno) throws Exception{
        String sql = "select * from tbl_todo where tno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setLong(1, tno);
        // 조회, ResultSet 필요함.
        @Cleanup ResultSet rs = pstmt.executeQuery();

        rs.next();
        TodoVO vo = TodoVO.builder()
                .tno(rs.getLong("tno"))
                .title(rs.getString("title"))
                .dueDate(rs.getDate("dueDate").toLocalDate())
                .build();

        return vo;
    }

    // 삭제 기능
    public void deleteOne(Long tno) throws Exception{
        String sql = "delete from tbl_todo where tno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, tno);
        pstmt.executeUpdate();
    }

    //수정 기능.
    // TodoVO todoVO -> 화면에서, 변경할 데이터를 담아둔 모델 클래스
    public void updateOne(TodoVO todoVO) throws Exception{
        String sql = "update tbl_todo set title=?, dueDate=?, finished=? where tno=?";
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, todoVO.getTitle());
        pstmt.setDate(2, Date.valueOf(todoVO.getDueDate()));
        pstmt.setBoolean(3, todoVO.isFinished());
        pstmt.setLong(4, todoVO.getTno());
        pstmt.executeUpdate();
    }


}
