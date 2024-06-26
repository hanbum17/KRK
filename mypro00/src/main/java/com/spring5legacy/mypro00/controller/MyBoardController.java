package com.spring5legacy.mypro00.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring5legacy.mypro00.common.paging.MyBoardPagingDTO;
import com.spring5legacy.mypro00.domain.MyBoardVO;
import com.spring5legacy.mypro00.service.MyBoardService;

@Controller
@RequestMapping(value = {"/myboard/*"})
public class MyBoardController {
	
    private MyBoardService myBoardService ;
    
//    //Setter를 이용한 의존성 주입(기본생성자를 사용)
//    public MyBoardController() {
//        System.out.println("MyBoardController의 기본 생성자====");
//    }
// 
//    @Autowired
//    public void setMyBoardService(MyBoardService myBoardService) {
//        this.myBoardService = myBoardService;
//        System.out.println("의존성 주입 Setter 실행====");       
//    }
    
    //(생성자를 이용한 의존성 주입: 생성자가 하나인 경우,@Autowired를 명시하지 않아도 됨)
    //@Autowired
	public MyBoardController(MyBoardService myBoardService) {
		this.myBoardService = myBoardService ;
		//System.out.println("MyBoardController의 모든 필드 초기화 생성자====");
	}
    	
    	
    //게시물 목록 조회 서비스1
    @GetMapping("/list")
//    public void showBoardList(Model model, Integer pageNum, Integer rowAmountPerPage) {
    public void showBoardList(Model model, @ModelAttribute("myBoardPaging") MyBoardPagingDTO myBoardPaging) {
    	//System.out.println(myBoardPaging);
    	//MyBoardPagingCreatorDTO pagingCreator = myBoardService.getBoardList(myBoardPaging) ;
    	//model.addAttribute("pagingCreator", pagingCreator);
    	//MyBoardPagingDTO myBoardPaging = new MyBoardPagingDTO(pageNum, rowAmountPerPage) ;
    	model.addAttribute("pagingCreator", myBoardService.getBoardList(myBoardPaging));
    	
    	System.out.println("컨트롤러: JSP에 전달할 model: " + model);
    }
    
    
    //게시물 등록 페이지 호출
    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public void showBoardRegisterPage() {
    	
    }
    
    //게시물 등록
    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String registerBoard(MyBoardVO myBoard, RedirectAttributes redirectAttr) {
    	System.out.println("controller: 전달된 정보 board: " + myBoard);
    	Long bno = myBoardService.registerBoard(myBoard) ;
    	
    	//redirectAttr.addAttribute("result", bno) ;
    	redirectAttr.addFlashAttribute("result", bno) ;
    	
    	return "redirect:/myboard/list" ;
    }
    
    //게시물 조회 페이지 호출(조회수 증가 고려)
    @GetMapping("/detail")
//  @PreAuthorize("isAuthenticated()")
    @PostAuthorize("permitAll")
    public void showBoard(Long bno, String from, Model model, @ModelAttribute("myBoardPaging") MyBoardPagingDTO myBoardPaging) {
    	
    	if(from == null) {
    		model.addAttribute("myBoard", myBoardService.getBoard(bno)) ;
    	} else {
    		model.addAttribute("myBoard", myBoardService.getBoardToAfterModify(bno)) ;
    	}
    	
    	//model.addAttribute("myBoardPaging", myBoardPaging) ;
    	
    	
        System.out.println("컨트롤러: model: " + model);
    	
    	
    }
    
    
    //게시물 수정/삭제 페이지 호출(조회수 증가 되면 않됨)
    @GetMapping("/modify")
    public void showModBoard(Long bno, Model model, @ModelAttribute("myBoardPaging") MyBoardPagingDTO myBoardPaging) {
    	MyBoardVO myBoard = myBoardService.getBoardToAfterModify(bno) ;
    	model.addAttribute("myBoard", myBoard) ;
    		
    }
    
    //게시물 수정
    @PostMapping("/modify")
    public String modifyBoard(MyBoardVO board, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPaging) {
    	
    	if(myBoardService.modifyBoard(board)) {
    		redirectAttr.addFlashAttribute("result", "successModify") ;
    		
    	} 
    	
    	redirectAttr.addAttribute("bno", board.getBno()) ;
    	redirectAttr.addAttribute("pageNum", myBoardPaging.getPageNum()) ;
    	redirectAttr.addAttribute("rowAmountPerPage", myBoardPaging.getRowAmountPerPage()) ;
    	redirectAttr.addAttribute("scope", myBoardPaging.getScope()) ;
    	redirectAttr.addAttribute("keyword", myBoardPaging.getKeyword()) ;
    	redirectAttr.addAttribute("beginDate", myBoardPaging.getBeginDate()) ;
    	redirectAttr.addAttribute("endDate", myBoardPaging.getEndDate()) ;
    	redirectAttr.addAttribute("from", "m");
    	
    	//return "redirect:/myboard/detail" + myBoardPaging.addPagingParamsToURI();
    	return "redirect:/myboard/detail";
    }
    
    //게시물 수정
    @PostMapping("/modify2")
    public String modifyBoard2(Long bno, String btitle, String bcontent, RedirectAttributes redirectAttr) {
    	
    	if(myBoardService.modifyBoard(bno, btitle, bcontent)) {
    		redirectAttr.addFlashAttribute("result", "successModify") ;
    	} 
    	
    	return "redirect:/myboard/detail?bno=" + bno + "&from=m" ;
    }
    
    
    //게시물 삭제 의뢰(블라인드 처리)
    @PostMapping("/delete")
    public String deleteBoard(Long bno, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPaging) {
    	
    	myBoardService.setBoardDeleted(bno) ;
    	redirectAttr.addFlashAttribute("result", "successDelete") ;
    	
    	redirectAttr.addAttribute("pageNum", myBoardPaging.getPageNum()) ;
    	redirectAttr.addAttribute("rowAmountPerPage", myBoardPaging.getRowAmountPerPage()) ;
    	redirectAttr.addAttribute("scope", myBoardPaging.getScope()) ;
    	redirectAttr.addAttribute("keyword", myBoardPaging.getKeyword()) ;
    	redirectAttr.addAttribute("beginDate", myBoardPaging.getBeginDate()) ;
    	redirectAttr.addAttribute("endDate", myBoardPaging.getEndDate()) ;
    	return "redirect:/myboard/list" ;
    }
    
//    //게시물 삭제 실제 삭제(첨부파일 포함)
//    @PostMapping("/remove")
//    public String removeBoard(Long bno, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPaging) {
//    	if (myBoardService.removeBoard(bno)) {
//    		redirectAttr.addFlashAttribute("result", "successRemove") ;
//    	}
//    	
//    	redirectAttr.addAttribute("pageNum", myBoardPaging.getPageNum()) ;
//    	redirectAttr.addAttribute("rowAmountPerPage", myBoardPaging.getRowAmountPerPage()) ;
//    	redirectAttr.addAttribute("scope", myBoardPaging.getScope()) ;
//    	redirectAttr.addAttribute("keyword", myBoardPaging.getKeyword()) ;
//    	redirectAttr.addAttribute("beginDate", myBoardPaging.getBeginDate()) ;
//    	redirectAttr.addAttribute("endDate", myBoardPaging.getEndDate()) ;
//    	
//    	return "redirect:/myboard/list" ;
//    }
    
 
    //게시물 삭제 실제 삭제(첨부파일 및 자식 댓글-답글 삭제 시)
    @PostMapping("/remove")
    public String removeBoard(MyBoardVO myBoard, RedirectAttributes redirectAttr, MyBoardPagingDTO myBoardPaging) {
    	if ((myBoard = myBoardService.removeBoard(myBoard)) != null) {
    		System.out.println("controller::service로부터 전달된 myBoard: " + myBoard);
    		redirectAttr.addFlashAttribute("result", "successRemove") ;
            redirectAttr.addFlashAttribute("deletedAttachFileCnt", String.valueOf(myBoard.getDeletedAttachFileCnt())  ) ;
            redirectAttr.addFlashAttribute("deletedReplyCnt", String.valueOf(myBoard.getDeletedReplyCnt())) ;
    	}
    	
    	redirectAttr.addAttribute("pageNum", myBoardPaging.getPageNum()) ;
    	redirectAttr.addAttribute("rowAmountPerPage", myBoardPaging.getRowAmountPerPage()) ;
    	redirectAttr.addAttribute("scope", myBoardPaging.getScope()) ;
    	redirectAttr.addAttribute("keyword", myBoardPaging.getKeyword()) ;
    	redirectAttr.addAttribute("beginDate", myBoardPaging.getBeginDate()) ;
    	redirectAttr.addAttribute("endDate", myBoardPaging.getEndDate()) ;
    	
    	return "redirect:/myboard/list" ;
    }
	
	
	
	

}
