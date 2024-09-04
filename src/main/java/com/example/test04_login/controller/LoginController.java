package com.example.test04_login.controller;

import com.example.test04_login.dto.Login;
import com.example.test04_login.dto.Member;
import com.example.test04_login.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Controller
public class LoginController {
    @Autowired private MemberService service;

    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(Login login, HttpServletRequest req, HttpSession httpSession) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", login.getId());
        map.put("pwd", login.getPwd());
        Member m = service.isMember(map);
        if (m == null) { //회원이 아닌경우
            return "member/login";
        } else {
            //세션에 id저장
            HttpSession session = req.getSession(false); // 세션객체에 얻어오기
            session.setAttribute("id", m.getId());//세션에 아이디 저장하기
//            System.out.println("id==>" + session.getAttribute("id"));
            return "redirect:/";


        }
    }

    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
