package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService service;

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("list 요청");

        model.addAttribute("result", service.getList(requestDto));
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam Long bno, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read or modify 요청");

        model.addAttribute("dto", service.getRow(bno));
    }

    // input hidden으로 담아와서 한 번더 검증
    @PreAuthorize("authentication.name == #dto.writerEmail")
    @PostMapping("/modify")
    public String postModify(BoardDto dto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("modify 요청", dto);

        service.modify(dto);

        rttr.addAttribute("bno", dto.getBno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/board/read";
    }

    @PreAuthorize("authentication.name == #writerEmail")
    @PostMapping("/remove")
    public String postRemove(Long bno, String writerEmail, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("remove 요청", bno);

        service.removeWithReplies(bno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/board/list";
    }

    // 로그인한 (허가받은) 사용자만 접근 가능하게 지정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public void getCreate(BoardDto dto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("create 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid BoardDto dto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("create 요청", dto);

        if (result.hasErrors()) {
            return "/board/create";
        }

        service.create(dto);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/board/list";
    }

}
