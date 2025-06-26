package com.busanit501.hello_project._3jdbc.controller;

import com.busanit501.hello_project._3jdbc.dto.TodoDTO;
import com.busanit501.hello_project._3jdbc.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            // 화면 전달.
            req.getRequestDispatcher("/WEB-INF/todo/todoRead2.jsp").forward(req,resp);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
