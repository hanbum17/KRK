package com.spring5legacy.mypro00.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyBoardVO {
	
    private Long bno ;
    private String btitle ;
    private String bcontent ;
    private String bwriter ;
    private Date bregDate ;
    private Timestamp bmodDate ;
    private Integer bviewsCnt ;
    private Integer breplyCnt ;
    private Integer bdelFlag ; //1: 삭제 요청됨, 0: 유지
    
    private List<MyBoardAttachFileVO> attachFileList ;
    
    private Integer deletedReplyCnt ;        	//<-추가
    private Integer deletedAttachFileCnt ;   	//<-추가

	

}
