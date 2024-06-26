package com.spring5legacy.mypro00.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring5legacy.mypro00.common.paging.MyBoardPagingDTO;
import com.spring5legacy.mypro00.domain.MyBoardVO;
import com.spring5legacy.mypro00.service.MyBoardService;

@Controller
@RequestMapping(value = {"/myboard/*"})
public class MyBoardController {

    private MyBoardService myBoardService;

    // 생성자를 이용한 의존성 주입
    public MyBoardController(MyBoardService myBoardService) {
        this.myBoardService = myBoardService;
    }

    // 게시물 목록 조회 서비스
    @GetMapping("/list")
    public void showBoardList(Model model, @ModelAttribute("myBoardPaging") MyBoardPagingDTO myBoardPaging) {
        model.addAttribute("pagingCreator", myBoardService.getBoardList(myBoardPaging));
        System.out.println("컨트롤러: JSP에 전달할 model: " + model);
    }

    // 게시물 등록 페이지 호출
    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public void showBoardRegisterPage() {
    }

    // 게시물 등록
    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String registerBoard(MyBoardVO board, RedirectAttributes redirectAttr) {
        Long bno = myBoardService.registerBoard(board);
        redirectAttr.addFlashAttribute("bno", bno);
        return "redirect:/myboard/list";
    }

    // 게시물 조회 페이지 호출 (조회수 증가 고려)
    @GetMapping({"/detail"})
    @PreAuthorize("permitAll")
//    @PreAuthorize("isAuthenticated()")
//    @PreAuthorize("isAnonynous()")
    public void showBoardDetail(Long bno, String from, Model model, @ModelAttribute("myBoardPaging") MyBoardPagingDTO myBoardPaging) {
        if (from == null) {
            model.addAttribute("myboard", myBoardService.getBoard(bno));
        } else {
            model.addAttribute("myboard", myBoardService.getBoardToAfterModify(bno));
        }
        System.out.println("컨트롤러: model: " + model);
    }

    // 게시물 수정/삭제 페이지 호출 (조회수 증가 되면 않됨)
    @GetMapping("/modify")
    public void showModifyPage(@RequestParam("bno") long bno, Model model, @ModelAttribute("myBoardPaging") MyBoardPagingDTO myBoardPaging) {
        model.addAttribute("myboard", myBoardService.getBoardToAfterModify(bno));
    }

    // 게시물 수정
    @PostMapping("/modify")
    public String modifyBoard(MyBoardVO myBoard, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPaging) {
        if (myBoardService.modifyBoard(myBoard)) {
            redirectAttr.addFlashAttribute("result", "successModify");
        }

        redirectAttr.addAttribute("bno", myBoard.getBno());
        redirectAttr.addAttribute("pageNum", myBoardPaging.getPageNum());
        redirectAttr.addAttribute("rowAmountPerPage", myBoardPaging.getRowAmountPerPage());
        redirectAttr.addAttribute("scope", myBoardPaging.getScope());
        redirectAttr.addAttribute("keyword", myBoardPaging.getKeyword());
        redirectAttr.addAttribute("beginDate", myBoardPaging.getStartDate());
        redirectAttr.addAttribute("endDate", myBoardPaging.getEndDate());
        redirectAttr.addAttribute("from", "m");

        return "redirect:/myboard/detail";
    }

    // 게시물 삭제 의뢰 (블라인드 처리)
    @PostMapping("/deleteFlag")
    public String deleteBoard(@RequestParam("bno") long bno, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPaging) {
        if (myBoardService.setBoardDeleted(bno)) {
            redirectAttr.addFlashAttribute("result", "successDeleted");
        }
        return "redirect:/myboard/list" + myBoardPaging.addPagingParamsToURI();
    }

    // 게시물 삭제 의뢰
    @PostMapping("/remove")
    public String removeBoard(@RequestParam("bno") long bno, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPaging) {
        if (myBoardService.removeBoardAndReplies(bno)) {
            redirectAttr.addFlashAttribute("result", "successRemoved");
            redirectAttr.addAttribute("pageNum", myBoardPaging.getPageNum());
            redirectAttr.addAttribute("rowAmountPerPage", myBoardPaging.getRowAmountPerPage());
            redirectAttr.addAttribute("scope", myBoardPaging.getScope());
            redirectAttr.addAttribute("keyword", myBoardPaging.getKeyword());
            redirectAttr.addAttribute("startDate", myBoardPaging.getStartDate());
            redirectAttr.addAttribute("endDate", myBoardPaging.getEndDate());
        }
        return "redirect:/myboard/list";
    }
}
