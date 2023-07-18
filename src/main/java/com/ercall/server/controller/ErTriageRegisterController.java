package com.ercall.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ertriage")
@Controller
public class ErTriageRegisterController {

    @GetMapping("/ertriageform")
    public String ertriageform() {
        System.out.println("중증도 분류 저장 페이지");
        return "ErTriageForm/erTriageForm";
    }
}