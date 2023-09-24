package com.test.sjna.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    @GetMapping(value = "/view/login")
    public String test(HttpSession session, HttpServletRequest request) {
        System.out.println("test 입니다.");
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        String token = csrfToken.getToken();
        System.out.println("token = " + token);
        return "login";
    }

}
