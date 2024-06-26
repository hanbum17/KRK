package com.spring5legacy.mypro00.common.paging;

import java.util.List;

import com.spring5legacy.mypro00.domain.MyBoardVO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyBoardPagingCreatorDTO {

    private MyBoardPagingDTO myBoardPaging;  //페이지번호와 행 개수 저장 객체
    private Long startPagingNum;  	//화면에 표시되는 시작 페이징 번호
    private Long endPagingNum;    	//화면에 표시되는 끝 페이징 번호
    private boolean prev;        	//이전 버튼 표시 여부 결정 변수(true: 표시됨, false: 표시 안됨)
    private boolean next;        	//다음 버튼 표시 여부 결정 변수(true: 표시됨, false: 표시 안됨)
    private Long rowTotal; 	      //전체 행 개수
    private Integer pagingNumCnt;    	//화면 하단에 표시할 기본 페이지 번호 개수(10)
    private Long lastPageNum;     	//행 총수를 기준으로 한 실제 마지막 페이지 번호
    
    private List<MyBoardVO> myBoardList ; //데이터베이스에서 가져온 게시물 정보

    //생성자 코드: 객체가 생성될 때, 화면 페이징 데이터가 생성되어, 위의 필드에 대입되도록 구현함
    public MyBoardPagingCreatorDTO(Long rowTotal, MyBoardPagingDTO myBoardPaging, List<MyBoardVO> myBoardList ) {
        this.myBoardPaging = myBoardPaging ;
        this.rowTotal = rowTotal;
        this.myBoardList = myBoardList ;
        this.pagingNumCnt = 10;
        
        //계산된 끝 페이징 번호:
        this.endPagingNum = (long)( Math.ceil((myBoardPaging.getPageNum()* 1.0) / this.pagingNumCnt ) ) * this.pagingNumCnt ;
        //계산된 시작 페이징 번호:
        this.startPagingNum = this.endPagingNum - (this.pagingNumCnt -1);
        
        //행 총수를 기준으로 한 실제 마지막 페이지 번호 저장(총 페이지 수 = 맨 마지막 페이지의 페이징 번호)
        this.lastPageNum = (long)( Math.ceil( (rowTotal * 1.0) / myBoardPaging.getRowAmountPerPage() ) );
        
        //총 행수를 기준으로 계산된 마지막 페이지 번호(lastPageNum)가 공식에 의해 계산된 끝 페이징 번호(endPagingNum)
        //보다 작으면, lastPageNum 값을 endPagingNum 값에 대입
        if (lastPageNum < this.endPagingNum) {
            this.endPagingNum = lastPageNum ;
        }
        //이전/다음 버튼 표시(true) 여부
        this.prev = this.startPagingNum > 1 ;
        this.next = this.endPagingNum < this.lastPageNum ;
        
        System.out.println("전달된 페이징 기본데이터(myBoardPaging): " + this.myBoardPaging.toString());
        System.out.println("시작 페이징번호: " + this.startPagingNum);
        System.out.println("끝 페이징번호: " + this.endPagingNum);
        System.out.println("이전버튼 표시 여부: " + this.prev);
        System.out.println("다음버튼 표시 여부: " + this.next);
        System.out.println("마지막 페이지 번호: " + this.lastPageNum);
    }

}
