package com.busanit501.hello_project._3jdbc.filter;

import com.busanit501.hello_project._3jdbc.dto.MemberDTO;
import com.busanit501.hello_project._3jdbc.service.MemberService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

// /todo/* 로 요청되는 request 모두 검사 대상자.
@WebFilter(urlPatterns = "/todo/*")
@Log4j2
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LoginCheckFilter 에서, 작업중. /todo/* , 접근시 검사 대상입니다.");
        // 로그인 체크 규칙 ,
        // ServletRequest -> HttpServletRequest , 다운캐스팅,
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // 세션 이용하는 도구 가져오기.
        HttpSession session = req.getSession();

        // 세션에 키 : loginInfo , 값: mid+mpw , 존재하면, 로그인 된 상태이고,
        // 세션에 키 : loginInfo  없으면, 로그인 안되었다.

        // 전
//        if (session.getAttribute("loginInfo") == null) {
//            log.info("LoginCheckFilter 에서, 작업중, loginInfo 비어 있음");
//            resp.sendRedirect("/login");
//            return;
//        }
        //=============================================================
        // 변경 후,
        if (session.getAttribute("loginInfo") != null) {
            // loginInfo 정보가 있다면, 계속 진행하기.(리스트로 가려다가, 필터에
            // 걸려서 검사 당했음. 다시 리스트로 가기. )
            filterChain.doFilter(servletRequest, servletResponse);
        }
        // loginInfo가 없다면, if 아래 코드 실행.
        // 쿠키를 체크. 쿠키 찾기.
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // 세션(loginInfo)에도 없고, 쿠기(remember-me)도 없으면, 바로 로그인 화면으로
        if (cookie == null) {
            resp.sendRedirect("/login");
            return;
        }

        // 쿠키가 존재하는 상황,. 쿠키에서 uuid 가져오기.
        String uuid = cookie.getValue();

        // 데이터베이스에서 , uuid 로 멤버 검색 하는 기능이용하고, 회원 정보 가져오기.
        try {
            MemberDTO memberDTO = MemberService.INSTANCE.getMemberDTOByUuid(uuid);
            log.info("로그인 필터에서, 쿠키값 uuid 로 조회한 회원 정보 : memberDTO : " + memberDTO);
            if (memberDTO == null) {
                // 조회시, 회원 정보가 없다면, 강제로 예외를 발생시키기.
                throw new Exception("쿠키 값의 uuid , 디비의 uuid 달라서, 유요하지 않습니다.");
            }
            // 정상인 경우.
            // 세션에 회원 정보를 추가.
            session.setAttribute("loginInfo", memberDTO);
            // 원래 진행 하던 일을 계속하기. 리스트를 가면, 리스트로 가기.
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/login");
        }


        //=============================================================

        // 위의 검사 규칙이 끝나고, 이어서 진행하겠다. 의미라고 보면 됨.
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // todoReadController 에 있는데, 통으로 복사했음. , 나중에 많이 사용시.
    // 파일 분리해서 사용해도 됩니다.
    private Cookie findCookie(Cookie[] cookies, String findCookieName) {
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        // 전체 쿠키의 목록이 존재한다면,
        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(findCookieName))
                .findFirst();
        return result.isPresent() ? result.get() : null;
    }
}
