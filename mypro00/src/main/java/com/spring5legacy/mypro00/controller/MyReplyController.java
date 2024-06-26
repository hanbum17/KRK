package com.spring5legacy.mypro00.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.domain.MyReplyVO;
import com.spring5legacy.mypro00.service.MyReplyService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/myreplies", "/replies"})
public class MyReplyController {
	
	public MyReplyService myReplyService ;
	
    //http://localhost:8080/mypro00/replies/list/229742/1
    //게시물의 댓글/답글 페이징 목록 처리
    @GetMapping(value = "/list/{bno}/{pageNum}", produces = "application/json;charset=utf-8" )
    public ResponseEntity<MyReplyPagingCreatorDTO> showReplyList(
            @PathVariable("bno") Long bno,
            @PathVariable("pageNum") Integer pageNum) {
        //DB목록 가져와서 브라우저로 리턴
        MyReplyPagingCreatorDTO myReplyPagingCreator = myReplyService.getReplyList(new MyReplyPagingDTO(bno, pageNum));
        return new ResponseEntity<MyReplyPagingCreatorDTO>(myReplyPagingCreator, HttpStatus.OK)   ;
    }

    //댓글/답글 등록(rno 반환) POST 	/replies/{bno}/new
  	@PostMapping(value = "/{bno}/new" , 
  				 consumes = {"application/json;charset=utf-8"} ,//consumes:브라우저--> 메서드로 전송한 데이터 유형
  				 produces = {"text/plain; charset=utf-8"} )		//produces:메서드--> 브라우저로 보내는 데이터 유형
  	@PreAuthorize("isAuthenticated()")
  	public ResponseEntity<String> registerReply(@PathVariable("bno") Long bno ,
                                                @RequestBody MyReplyVO myreply) {
        Long registeredRno = myReplyService.registerReply(myreply);
        //System.out.println("controller:: 서비스로부터 전달받은 등록된 rno: " + registeredRno);
        String _registeredRno = null ;
        if (registeredRno != null) {
            _registeredRno = String.valueOf(registeredRno) ;
        }
  	    return registeredRno != null ? new ResponseEntity<String>(_registeredRno, HttpStatus.OK) 
                                     : new ResponseEntity<String>(_registeredRno, HttpStatus.OK); //HttpStatus.INTERNAL_SERVER_ERROR 대신 OK 전송
    }
  	
    //특정 댓글/답글 수정: PUT, /replies/{bno}/{rno}
    //Ajax에서의 요청 URI: /mypro00/replies/229402/102
    @PutMapping(value="/{bno}/{rno}" , 
                consumes = "application/json;charset=utf-8" ,
                produces = "text/plain;charset=utf-8") 
    @PreAuthorize("isAuthenticated() && principal.username == #myreply.rwriter")
    public ResponseEntity<String> modifyReply(@PathVariable("bno") Long bno ,
                                              @PathVariable("rno") Long rno ,
                                              @RequestBody MyReplyVO myreply){
        System.out.println("컨트롤러에 전달된 myreply: " + myreply);
        
        return myReplyService.modifyReply(myreply) 
        		? new ResponseEntity<String>("modifySuccess", HttpStatus.OK) 
                : new ResponseEntity<String>("modifyFail", HttpStatus.OK); //HttpStatus.INTERNAL_SERVER_ERROR 대신 OK 전송
    }

    


//특정 게시물에 대한 특정 댓글/답글 블라인드:  Patch, /replies/{bno}/{rno}
@PatchMapping(value = "/{bno}/{rno}" 
//			   ,consumes = "application/json; charset=utf-8"
			   ,produces = "text/plain;charset=utf-8")
//@PreAuthorize("isAuthenticated() && principal.username == #yourReply.rwriter")
public ResponseEntity<String> blindReply(@PathVariable("bno") Long bno, 
										 @PathVariable("rno") Long rno
										  //,@RequestBody MyReplyVO yourReply
										  ) {
	System.out.println("controller:::bno: " + bno);
	System.out.println("controller:::rno: " + rno);
	return myReplyService.blindReply(bno, rno)  
		   ? new ResponseEntity<String>("blindSuccess", HttpStatus.OK)
		   : new ResponseEntity<String>("blindFail", HttpStatus.INTERNAL_SERVER_ERROR) ;
}

//특정 게시물에 대한 특정 댓글/답글 및 모든 자식답글 삭제:  DELETE, /replies/{bno}/{rno}
@DeleteMapping(value = "/{bno}/{rno}", produces = "text/plain;charset=utf-8")
public ResponseEntity<String> removeReply(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno ) {
    return new ResponseEntity<String>(myReplyService.removeReply(bno, rno), HttpStatus.OK) ;
}


/*	
//특정 게시물에 대한 모든 댓글 삭제: 삭제 행수가 반환됨
	@DeleteMapping(value = "/{bno}" , produces = "text/plain;charset=utf-8")
	public ResponseEntity<String> removeAllReply(@PathVariable("bno") Long bno){
		
		int deleteRows = myReplyService.removeAllMyReply(bno) ;
		
		return new ResponseEntity<String>(String.valueOf(deleteRows), HttpStatus.OK) ;
	} 
*/
 

    
}
