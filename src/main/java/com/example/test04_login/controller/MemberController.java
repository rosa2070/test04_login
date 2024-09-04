package com.example.test04_login.controller;

import com.example.test04_login.dto.Member;
import com.example.test04_login.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @Autowired private MemberService service;

    @GetMapping("/member/join")
    public String join(@ModelAttribute Member member) {
        return "member/join";
    }

    @PostMapping("/member/join")
    public String insert(@ModelAttribute Member member, BindingResult bindingResult, Model model) {
        if(!StringUtils.hasText(member.getId())) { // id 텍스트가 비어있으면
            //bindingResult에 에러담기 new FieldError("객체명", "필드명", "에러메시지")
            bindingResult.addError(new FieldError("member", "id", "아이디를 입력하세요"));
        }
        //비밀번호
        if(!StringUtils.hasText(member.getPwd())) {
            bindingResult.addError(new FieldError("member", "pwd", "비밀번호를 입력하세요"));
        }
        //나이 0~150살까지
        if(member.getAge()>150 || member.getAge() < 0) {
            bindingResult.addError(new FieldError("member", "age", "나이를 잘못 입력했습니다."));
        }

        if (bindingResult.hasErrors()) { //에러가 존재하면 가입페이지로 이동하기
            return "member/join";
        }

        try {
            service.insert(member);
            model.addAttribute("result", "회원가입성공!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("result", "회원가입실패");
        }
        return "member/result";
    }
}
