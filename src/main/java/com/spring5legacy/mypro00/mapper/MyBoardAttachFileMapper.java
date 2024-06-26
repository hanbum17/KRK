package com.spring5legacy.mypro00.mapper;

import java.util.List;
import com.spring5legacy.mypro00.domain.MyBoardAttachFileVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MyBoardAttachFileMapper {

    // 10번 게시물의 모든 첨부파일 정보 조회
    List<MyBoardAttachFileVO> selectAttachFiles(Long bno);

    // 첨부파일 정보 입력
    void insertAttachFile(MyBoardAttachFileVO attachFile);

    // 10번 게시물의 모든 첨부파일 삭제
    int deleteAttachFiles(Long bno);

    // 10번 게시물의 모든 첨부파일 블라인드처리
    int updateSetFileDelFlag(Long bno);

    // 특정 첨부파일 한 개 정보 삭제
    void deleteAttachFile(String uuid);
}
