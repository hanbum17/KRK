package com.spring5legacy.mypro00.common.paging;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class MyReplyPagingDTO {

    private Long bno ;
	private Integer pageNum ;
    private Integer rowAmountPerPage ;
    
	public MyReplyPagingDTO(Long bno, Integer pageNum) {
		this.bno = bno;
		this.pageNum = pageNum;
		this.rowAmountPerPage = 5 ;
	}
    
    


}

