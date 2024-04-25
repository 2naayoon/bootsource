package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequestMapping("/movie")
@Controller
public class MovieController {
    @GetMapping("/list")
    public String getList() {
        log.info("list 요청");
        return "/movie/list";
    }

}
