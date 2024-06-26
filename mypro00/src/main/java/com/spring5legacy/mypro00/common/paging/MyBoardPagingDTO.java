package com.spring5legacy.mypro00.common.paging;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyBoardPagingDTO {
	
	private Integer pageNum ;
	private Integer rowAmountPerPage ;
	
	private String scope ;   //검색범위(scope - T:btitle, C:bcontent, W:bwriter)
	private String keyword ;
	
	
//	@DateTimeFormat(pattern = "yyyy-MM-dd")  //java.util.Date
//	private Date beginDate ;
//		
//	@DateTimeFormat(pattern = "yyyy-MM-dd")  //java.util.Date
//	private Date endDate ;
	
	private String beginDate ;
	private String endDate ;
	
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public MyBoardPagingDTO() {
		this.pageNum = 1 ;
		this.rowAmountPerPage = 10 ;
	}
	
    public void setPageNum(Integer pageNum) {
        if(pageNum == null || pageNum <= 0) {
            this.pageNum = 1 ;
        } else {
            this.pageNum = pageNum ;
		}
	}

	public void setRowAmountPerPage(Integer rowAmountPerPage) {
        if(rowAmountPerPage == null || rowAmountPerPage <= 0) {
            this.rowAmountPerPage = 10 ;
            
        } else {
            this.rowAmountPerPage = rowAmountPerPage ;
		}
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	

	//검색범위 값 처리를 위한 메소드: 화면에서 선택된 "TC" 값을 ["T", "C"] 배열로 반환 
	//이 메서드는 Mybatis 엔진에 의해서 자동으로 호출되어 사용됨(SQL 매퍼 파일의 SELECT문에서 설명함)
	//[주의] 메서드 이름이 "Getter 의 이름 지정 방식"을 준수해야 함
	@SuppressWarnings("unused")
	private String[] getScopeArray() {
		return scope == null ? new String[] {} : scope.split("") ; 
	}

	
	public String addPagingParamsToURI () {
	    UriComponentsBuilder uriBuilder =
	        UriComponentsBuilder.fromPath("")
	                              .queryParam("pageNum", this.pageNum)
	                              .queryParam("rowAmountPerPage", this.rowAmountPerPage)
	                              .queryParam("scope", this.scope)
	                              .queryParam("keyword", this.keyword) 
	                              .queryParam("begindDate", this.beginDate)
	                              .queryParam("endDate", this.endDate)
	                              ; 
	    String uriString = uriBuilder.toUriString();
	    System.out.println("생성된 파라미터 추가 URI String: " + uriString);
	    return uriString;
	}






	
	
	
//	//생성자를 통해 표시할 페이지번호와 페이지당 출력할 레코드 개수를 컨트롤러로 전달
//    public MyBoardPagingDTO(Integer pageNum, Integer rowAmountPerPage) {
//        if(pageNum == null || pageNum <= 0) {
//            this.pageNum = 1 ;
//        } else {
//            this.pageNum = pageNum ;
//		}
//		
//		if(rowAmountPerPage == null || rowAmountPerPage <= 0) {
//			this.rowAmountPerPage = 10 ;
//		} else {
//			this.rowAmountPerPage = rowAmountPerPage ;
//		}
//		
//		System.out.println("모든 필드 초기화 생성자");
//		
//	}
	

}
