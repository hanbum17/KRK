package com.spring5legacy.mypro00.common.paging;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;

import lombok.ToString;

@Getter
@ToString

public class MyBoardPagingDTO {

    private Integer pageNum;              //현재 페이지 번호
    private Integer rowAmountPerPage;    //페이지당 출력할 레코드 개수

    private String scope;
    private String keyword ;
    
    private String startDate;             // 시작 날짜
    private String endDate;  
    
    public MyBoardPagingDTO() {
    	this.pageNum = 1;
    	this.rowAmountPerPage = 10;
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
    		this.rowAmountPerPage = rowAmountPerPage;
    	}
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
    
	// 이 메서드는 Mybatis 엔진에 의해서 자동으로 호출되어 사용됨(SQL 매퍼 파일의 SELECT문에서 설명함)
	// [주의] 메서드 이름이 "Getter 의 이름 지정 방식"을 준수해야 함
	@SuppressWarnings ("unused")
	private String[] getScopeArray() {
		return scope == null? new String[] {} : scope.split("");
	}
    

	public String addPagingParamsToURI () {
	    UriComponentsBuilder uriBuilder =
	        UriComponentsBuilder.fromPath("")
	                              .queryParam("pageNum", this.pageNum)
	                              .queryParam("rowAmountPerPage", this.rowAmountPerPage)
	                              .queryParam("scope", this.scope)
	                              .queryParam("keyword", this.keyword) 
								  .queryParam("startDate", this.startDate)
							      .queryParam("endDate", this.endDate);
	    String uriString = uriBuilder.toUriString();
	    System.out.println("생성된 파라미터 추가 URI String: " + uriString);
	    return uriString;
	}



}

