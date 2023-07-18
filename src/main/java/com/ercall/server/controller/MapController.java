package com.ercall.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
    @GetMapping("/map")
    public String map(){
        System.out.println("map 페이지");
        return "manualSearching/map";
    }

}
