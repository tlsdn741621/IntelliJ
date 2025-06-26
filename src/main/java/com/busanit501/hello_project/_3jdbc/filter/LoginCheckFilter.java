package com.busanit501.hello_project._3jdbc.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// /todo/* 로 요청되는 request 모두 검사 대상자
@WebFilter(urlPatterns = "/todo/*")
@Log4j2
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       log.info("LoginCheckFilter 에서, 작업중. /todo/* , 접근시 검사 대상입니다.");
       // 로그인 체크 규칙,
        // ServletRequest -> HttpServletRequest, 다운 캐스팅
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 세션 이용하는 도구 가져오기.
        HttpSession session = req.getSession();

        // 세션에 키 : loginInfo , 값 : mid+mpw, 존재하면, 로그인 된 상태이고,
        // 세션에 키 : loginInfo 없으면, 로그인 안되었다.
        if (session.getAttribute("loginInfo") == null) {
            log.info("LoginCheckFilter 에서, 작업중, loginInfo 비어 있음");
            resp.sendRedirect("/login");
            return;
        }

        // 위의 검사 규칙이 끝나고, 이어서 진행하겠다. 의미라고 보면 됨
        chain.doFilter(request, response);
    }
}
