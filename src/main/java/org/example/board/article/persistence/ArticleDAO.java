package org.example.board.article.persistence;

import org.example.board.article.domain.ArticleVO;

import java.util.List;

// ArticleDAO 인터페이스 생성 후 메서드 정의
public interface ArticleDAO {

    // 등록
    void create(ArticleVO articleVO) throws Exception;

    // 조회
    ArticleVO read(Integer articleNo) throws Exception;

    // 수정
    void update(ArticleVO articleVO) throws Exception;

    // 삭제
    void delete(Integer articleNo) throws Exception;

    // 조회
    List<ArticleVO> listAll() throws Exception;

}
