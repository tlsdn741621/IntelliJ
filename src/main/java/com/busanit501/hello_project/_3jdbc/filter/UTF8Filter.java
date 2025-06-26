package com.busanit501.hello_project._3jdbc.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*") // 모든 경로에 대해서, 검사 대상자.
@Log4j2
public class UTF8Filter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("UTF8Filter 작업중, : 검사 대상은 모든 경로 : /*");
        HttpServletRequest req = (HttpServletRequest)request;
        req.setCharacterEncoding("UTF-8");

        // 위의 규칙 검사 후, 다시 계속 진행함.
        chain.doFilter(request, response);

    }
}
