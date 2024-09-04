package com.example.test04_login.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {
    String[] whitelist = {"/", "/member/login", "/member/join", "/member/logout", "/css"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();// 요청 uri값 얻어오기
        HttpSession session = req.getSession(false);
        if(isLoginCheck(uri)) { //로그인한 사용자만 볼 수 있는 페이지
            if(session==null || session.getAttribute("id")==null) {
                resp.sendRedirect("/member/login");
                return;
            }

        }
        chain.doFilter(request, response);
    }

    private  boolean isLoginCheck(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
