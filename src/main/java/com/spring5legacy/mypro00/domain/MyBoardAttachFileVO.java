package com.spring5legacy.mypro00.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyBoardAttachFileVO {
    private String uuid;
    private String uploadPath;
    private String fileName;
    private String fileType;
    private Long bno;
    private int fileDelFlag;
    private String repoPath= "C:/myupload";  //서버의 첨부파일 기본 저장경로, 데이터베이스에는 저장되지 않음.
}
