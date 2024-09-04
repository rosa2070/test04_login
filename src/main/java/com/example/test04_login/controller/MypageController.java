package com.example.test04_login.controller;

import com.example.test04_login.dto.Member;
import com.example.test04_login.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MypageController {
    @Autowired private MemberService service;

    @GetMapping("/member/mypage")
    public String mypage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if(session != null) {
            String id = (String) session.getAttribute("id");
            if (id != null) {
                Member m = service.select(id);
                model.addAttribute("member", m);
                return "member/mypage";

            }
        }
        return "redirect:/member/login";

    }

}
