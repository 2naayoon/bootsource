package com.example.book.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.book.dto.PageRequestDto;

@Log4j2
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(@ModelAttribute("requestDto") PageRequestDto requesDto) {
        log.info("home 요청");
        return "home";
    }
}
