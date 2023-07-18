package com.ercall.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home";
    }

//    @GetMapping("/ertriageform")
//    public String ertriageform(){
//        System.out.println("중증도 분류 저장 페이지");
//        return "ErTriageForm/erTriageForm";
//    }
}
