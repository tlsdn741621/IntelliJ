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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "todoUpdateController2", urlPatterns = "/todo/update2")
@Log4j2
public class TodoUpdateController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 수정폼 ,doGet,
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            // 수정할 tno 번호를 이용해서, 수정폼 화면으로, 하나의 정보를 조회해서, 화면에 보내기
            Long tno = Long.parseLong(request.getParameter("tno"));
            // 하나 정보 조회. tno 번호 이용해서.
            TodoDTO todoDTO = todoService.getByTno(tno);
            // 수정폼에, 하나 조회한 정보를 전달, 화면에 데이터 전달.
            request.setAttribute("dto", todoDTO);
            // 화면 전달.
            request.getRequestDispatcher("/WEB-INF/todo/todoUpdate.jsp").forward(request, response);

            
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("수정폼 조회 에러");
        }
    }


    // 로직처리, doPost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 처리, 임시 방편 작업함.
//        request.setCharacterEncoding("UTF-8");
        // 체크박스 처리 여부. finishedStr, true 이면 "on" 전달 받음.
        String finishedStr = request.getParameter("finished");

        // 수정 처리를 하겠다. 화면에서, 수정할 내용을 전달 받음, 실제 수정 작업.
        // 먼저, 전달 받은 데이터 확인.
        Long getTno = Long.parseLong(request.getParameter("tno"));
        String getTitle = request.getParameter("title");
        LocalDate getDueDate = LocalDate.parse(request.getParameter("dueDate"),formatter);
        boolean getFinished = finishedStr != null && finishedStr.equals("on");

        // 모델 클래스 , TodoDTO 에 담아서, 서비스에 전달.
        TodoDTO todoDTO = TodoDTO.builder()
                .tno(getTno)
                .title(getTitle)
                .dueDate(getDueDate)
                .finished(getFinished)
                .build();

        log.info("TodoUpdateController 에서 doPost 작업중. ");
        log.info("화면에서 전달 받은 데이터를 모델 TodoDTO에 담기 확인: " + todoDTO);

        // 서비스에 전달.
        try {
            todoService.modify(todoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 리다이렉트
        response.sendRedirect("/todo/list2");
    }
}
