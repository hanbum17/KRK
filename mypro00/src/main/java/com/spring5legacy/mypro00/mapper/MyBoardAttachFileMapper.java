package com.spring5legacy.mypro00.mapper;

import java.util.List;

import com.spring5legacy.mypro00.domain.MyBoardAttachFileVO;

public interface MyBoardAttachFileMapper {
    //기본 CRUD 에 해당하는 메서드 정의
	public List<MyBoardAttachFileVO> selectAttachFiles(Long bno) ;
	
	public void insertAttachFile(MyBoardAttachFileVO attachFile) ;
	
	public int deleteAttachFiles(Long bno) ;
	
	public int updateSetFileDelFlag(Long bno) ;
	
	public void deleteAttachFile(String uuid) ;
	
}
