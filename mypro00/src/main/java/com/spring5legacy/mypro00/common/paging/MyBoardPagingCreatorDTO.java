package com.spring5legacy.mypro00.common.paging;

import java.util.List;

import com.spring5legacy.mypro00.domain.MyBoardVO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyBoardPagingCreatorDTO {
	
	private MyBoardPagingDTO myBoardPaging ;
	
	private Long endPagingNum ;
	private Long startPagingNum ;
	
	
	private Boolean prev ;
	private Boolean next ;
	
	private Long rowTotal ;
	
	private Integer pagingNumCnt ;
	private Long lastPageNum ;
	
	private List<MyBoardVO> myBoardList ;
	
	
	public MyBoardPagingCreatorDTO(MyBoardPagingDTO myBoardPaging, 
			                       Long rowTotal,
			                       List<MyBoardVO> myBoardList) {
		this.myBoardPaging = myBoardPaging;
		this.rowTotal = rowTotal;
		this.pagingNumCnt = 10;
		this.myBoardList = myBoardList ;
		
		this.endPagingNum = (long) Math.ceil(1.0 * myBoardPaging.getPageNum() / this.pagingNumCnt) * this.pagingNumCnt ;
		this.startPagingNum = this.endPagingNum - (this.pagingNumCnt - 1) ;
		
		//행 총수를 기준으로 한, 실제 마지막 페이지 번호 저장(총 페이지 수 = 맨 마지막 페이지의 페이징 번호)
		this.lastPageNum =  (long)  Math.ceil(1.0 * this.rowTotal / myBoardPaging.getRowAmountPerPage() );
		
		//lastPageNum의 값이 endPagingNum(공식에 의해 계산된 끝 페이징 번호)보다 작으면, 
		//lastPageNum 값을 endPagingNum 값에 대입
		if (this.lastPageNum < this.endPagingNum) {
			this.endPagingNum = this.lastPageNum ;
		}
			
		
		this.prev = this.startPagingNum > 1 ;
		this.next = this.endPagingNum < this.lastPageNum;
		

        System.out.println("전달된 페이징 기본데이터(myBoardPaging): " + this.myBoardPaging.toString());
        System.out.println("시작 페이징번호: " + this.startPagingNum);
        System.out.println("끝 페이징번호: " + this.endPagingNum);
        System.out.println("이전버튼 표시 여부: " + this.prev);
        System.out.println("다음버튼 표시 여부: " + this.next);
        System.out.println("마지막 페이지 번호: " + this.lastPageNum);
		
	}
	
	
	

}
