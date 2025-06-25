package com.busanit501.hello_project._2todo.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoRegController" , urlPatterns = "/todo/register")
public class TodoRegController extends HttpServlet {
    // 등록 목록 화면 제공.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("todoRegController.doGet 호출, 입력 화면제공. ");
        // 빌드 패턴으로 해당 객체에서, 사용하는 메서드를 연속적으로 사용하는 디자인 패턴형식
        req.getRequestDispatcher("/WEB-INF/todo/todoReg.jsp").forward(req, resp);
        // 아직 화면은 미구현.
    }
    // 등록 로직 처리,
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("todoRegController.doPost 호출, 로직 처리 ");

        // 단순 화면 전환, 리다이렉트
        resp.sendRedirect("/todo/list");
    }
}
