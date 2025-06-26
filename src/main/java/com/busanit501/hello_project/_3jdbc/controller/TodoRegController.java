package com.busanit501.hello_project._3jdbc.controller;

import com.busanit501.hello_project._3jdbc.dto.TodoDTO;
import com.busanit501.hello_project._3jdbc.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "todoRegController2", value = "/todo/register2")
@Log4j2
public class TodoRegController extends HttpServlet {
    // 등록 1) 등록 화면 get , 2)등록 처리 post
    // 외주 맡기기, 등록을 구현 할수 있는  , TodoService 외주 요청. 준비.
    private TodoService todoService = TodoService.INSTANCE;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("TodoRegController : 등록 화면 제공 , doGet 작업");

        //=======================================================================
        // 추가 작업, 세션을 이용한 , 시스템이 제공한 세션 확인.
        HttpSession session = req.getSession();

        // 기존에 JSESSIONID 가 없는 새로운 사용자 확인. -> 최초 웹서버에 접근 한 상태.
        if(session.isNew()) {
            log.info("JSESSIONID 시스템 쿠키가 새로 만들어진 사용자");
            // 미구현
            resp.sendRedirect("/login");
            return;

        }
        // JSSESIONID 는 있지만, 해당 세션 컨텍스트에 loginInfo 이름으로 저장된 객체가 없는 경우
        if(session.getAttribute("loginInfo") == null) {
            log.info("로그인 정보가 없는 사용자");
            resp.sendRedirect("/login");
            return;
        }

        // 정상적인 상태, 1) JSSESIONID 존재, 2) 세션이라는 서버의 임시 메모리 공간에 키: loginInfo 있다면,
        // 정상인 경우만, 글쓰기 폼 화면으로 안내 하겠다.
        req.getRequestDispatcher("/WEB-INF/todo/todoReg.jsp").forward(req, resp);
        //=======================================================================

        // 화면 전달 먼저 하기.
        req.getRequestDispatcher("/WEB-INF/todo/todoReg.jsp").forward(req, resp);
    }

    // 등록 처리 기능
    // 화면으로부터 전달 받은 데이터를 , 모델 클래스 TodoDTO에 담아서 전달 하기.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 화면으로 부터 전달받은 데이터 인코딩 타입 UTF-8,
        // 서버에서 받아서 처리 할 때도 같은 인코딩 타입으로 처리.
        // 현재 방법1, 코드로 처리 했고,
        // 방법2) 뒤에서 처리하기.
        // web.xml, 서버 시작 할 때, 항상 들어오는 데이터 타입을  UTF-8 기본설정.
//        req.setCharacterEncoding("UTF-8");
        TodoDTO todoDTO = TodoDTO.builder()
                .title(req.getParameter("title"))
                .dueDate(LocalDate.parse(req.getParameter("dueDate"), formatter))
                .build();
        log.info("TodoRegController  작업중, doPost 처리중./todo/register2");
        log.info("전달 받은 데이터 변환 확인 : " + todoDTO);

        // 서비스에 외주 주기, 데이터를 전달만 해주면 됨.
        try{
            todoService.register(todoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 데이터를 전달 후, 다시, PRG 패턴, post 처리후,  다시 화면으로 전환해주기.
        resp.sendRedirect("/todo/list2");
    }
}
