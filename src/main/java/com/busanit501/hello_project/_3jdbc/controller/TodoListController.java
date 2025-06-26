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
import java.util.List;

@WebServlet(name = "todoListController2", value = "/todo/list2")
@Log4j2
// 전달 개요 : 화면 -> 컨트롤러(C):현위치 -> 서비스 (S) - > DAO() -> DB
public class TodoListController extends HttpServlet {
    // 서비스의 도움을 받기, 서비스 기능 가져오기. 준비하기.
    private TodoService todoService = TodoService.INSTANCE;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("TodoListController doGet ,전체 목록 조회 확인");

        // 서비스 도움받기.
        // 디비에서 조회한 데이터 -> TodoVO 클래스 변환 하고, -> TodoDTO 변환하고,
        // 리스트에 담아서 전달 받기.
        // 컨트롤러에서는, 서비스에서 받은 List<TodoDTO> 받아서, 화면에 탑재 할 예정.
        try {
            List<TodoDTO> dtoList = todoService.listAll();
            // 화면에 탑재 해보기.
            req.setAttribute("dtoList", dtoList);
            // 화면에 전달
            req.getRequestDispatcher("/WEB-INF/todo/todoList.jsp").forward(req, resp);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }
}
