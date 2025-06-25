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

@WebServlet(name = "todoReadController2", value = "/todo/read2")
@Log4j2
public class TodoReadController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        log.info("하나 조회");
        Long tno = Long.parseLong(req.getParameter("tno2"));
        try {
            TodoDTO dto = TodoService.INSTANCE.getTodoByTno(tno);

            if (dto == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("dto2", dto);
        } catch (Exception e) {
            throw new ServletException (e);
        }
        req.getRequestDispatcher("/WEB-INF/todo/todoRead.jsp").forward(req,resp);
    }
}
