package com.ercall.server.controller;

import com.ercall.server.dto.ErTriageRequestDto;
import com.ercall.server.dto.ErTriageResponseDto;
import com.ercall.server.service.ErTriageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ErTriageController {
    private final ErTriageService erTriageService;

    @PostMapping("/ertriage")
    public Long save(@RequestBody final ErTriageRequestDto params){
        return erTriageService.save(params);
    }
    @GetMapping("/ertriage")
    public List<ErTriageResponseDto> findAll(){
        return erTriageService.findAll();
    }


}
