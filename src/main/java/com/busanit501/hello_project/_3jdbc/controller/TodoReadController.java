package com.busanit501.hello_project._3jdbc.controller;

import com.busanit501.hello_project._3jdbc.dto.TodoDTO;
import com.busanit501.hello_project._3jdbc.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;

@WebServlet(name = "todoReadController2", urlPatterns = "/todo/read2")
@Log4j2
public class TodoReadController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 화면에서 쿼리 스트링 형식으로 전달
            // 예시) /todo/read2?tno=2, 무조건 문자열로 넘어옴.
            // 화면으로부터 전달 받은 tno 번호를 이용해서,
            // req.getParameter("tno") -> "2" 라는 결과값 타입은 문자열이다.
            // Long.parseLong(문자열) -> Long 타입으로 변환한다.
            Long tno = Long.parseLong(req.getParameter("tno"));

            // 서비스에 하나 조회를 요청 하기.
            TodoDTO todoDTO = todoService.getByTno(tno);

            // 데이터를 화면에 탑재.
            req.setAttribute("dto",todoDTO);

            //=====================================================================
            // 서버 -> 웹 브라우저에게, 쿠키에 이렇게 저장 해주세요, 응답.
            // 사용자가, 하나 조회를 한 번호를 쿠키에 담을 예정.
            // 작업 개요.
            // 1. 쿠키 박스의 이름 임의로 지정 : viewTodos
            // 2. 쿠기 박스에, viewTodos 없으면 새로 만들고, 있으면 기록하기.
            // 3. 쿠키 박스의 존재 여부를 확인 할 기능이 필요함. -> findCookie 이름으로 기능 추가.

            // 쿠키 찾기 , req.getCookies() : 전체 쿠키 목록
            // 있으면, 있는 쿠키 사용, 없으면, 새로 생성 해서 사용.
            Cookie viewTodoCookie = findCookie(req.getCookies(),"viewTodos");

            // 쿠키 박스에 저장된 : 사용자 조회한 todo 의 번호를 문자열 형태로 저장 할 예정.
            // 저장이 되어 있다면, 불러오기. 예) 3-4-,
            String todoListStr = viewTodoCookie.getValue();
            //상태 변수, exist
            boolean exist = false;

            // 유효성 체크. todoListStr , 문자열이 널인지 여부, tno(숫자)+ "-", 3-4- ,이런형식 검사
            // todoListStr = "1-2-7-", 조회한 , todo 번호를 - 구분자로해서, 포맷으로 저장.
            // todoListStr = ""
            if(todoListStr!=null && todoListStr.indexOf(tno+"-") >= 0){
                exist = true;
            }
            log.info("쿠키 박스가 있고, 안에 조회한 내용이 있다면: exitst : " + exist);

            // 쿠키 박스 안에, 조회한 내용이 없는 경우, -> 기록을 하자.
            if(!exist){
                // 문자열 안에 조회한 번호를 기록
                todoListStr += tno+"-";
                // 쿠키에 설정하는 옵션.
                viewTodoCookie.setValue(todoListStr);
                // 쿠키를 적용할 범위 , 전체 범위.
                viewTodoCookie.setPath("/");
                // 쿠키의 생존 시간, 5분
                viewTodoCookie.setMaxAge(60 * 5);
                // 서버 -> 웹브라우저로 전달
                resp.addCookie(viewTodoCookie);
            }
            //=====================================================================

            // 화면 전달.
            req.getRequestDispatcher("/WEB-INF/todo/todoRead2.jsp").forward(req,resp);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    // 쿠키 찾기 기능
    // 매개변수 1) 쿠키 상자들이 들어 있는 전체 목록
    // 매개변수 2) 쿠키 상자의 이름 : viewTodos
    private Cookie findCookie(Cookie[] cookies, String findCookieName) {
        // 1, 찾을 쿠키를 담아둘 임시 쿠키 정의
        Cookie targetCookie = null;

        //2, 전체 목록에서, 반복문으로 쿠키 박스의 이름이 : viewTodos 해당 하는게 있는지 조사.
        // 전체 목록이 있는지 확인, 유효성 체크
        if(cookies != null && cookies.length > 0){
            for (Cookie ck : cookies) {
                if(ck.getName().equals(findCookieName)) {
                    targetCookie = ck;
                    break;
                }
            }
        } // end if

        // 3, 찾고자 하는 쿠키 박스가 없다면, 생성 해주기.
        if(targetCookie == null){
            targetCookie = new Cookie(findCookieName,"");
            // 쿠키의 적용 범위.
            targetCookie.setPath("/");
            // 쿠키의 유효 시간, 하루, -> test 목적으로 5분,
            targetCookie.setMaxAge(60 * 5);
        }
        return targetCookie;
    }

}
