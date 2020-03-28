package org.example.board.upload.persistence;

import java.util.List;

// 첨부파일의 정보를 테이블에 저장하기 위해
public interface ArticleFileDAO {

    // 파일 추가
    void addFile(String fullName) throws Exception;

    // 파일 목록
    List<String> getArticleFiles(Integer articleNo) throws Exception;

    // 파일 전체 삭제
    void deleteFiles(Integer articleNo) throws Exception;

    // 파일 삭제
    void deleteFile(String fileName) throws Exception;

    // 파일 수정
    void replaceFile(String fileName, Integer articleNo) throws Exception;

    // 파일 개수 갱신
    void updateFileCnt(Integer articleNo) throws Exception;

}
