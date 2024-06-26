package com.spring5legacy.mypro00.mapper;


import java.util.List;


import com.spring5legacy.mypro00.domain.MyBoardAttachFileVO;


public interface MyScheduledMapper {
	
	public List<MyBoardAttachFileVO> selectAttachFilesDuringBeforeOneDay(); 
	

    
}
