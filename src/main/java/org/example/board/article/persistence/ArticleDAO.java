package org.example.board.article.persistence;

import org.example.board.article.domain.ArticleVO;
import org.example.board.commons.paging.Criteria;

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

    // 페이징 처리
    List<ArticleVO> listPaging(int page) throws Exception;

    // 페이징 처리(매개변수를 Criteria 타입의 변수를 가진 게시글 페이징 목록 메서드를 인터페이스에 선언)
    List<ArticleVO> listCriteria(Criteria criteria) throws Exception;

    // 페이징 전체 게시글 갯수
    int countArticles(Criteria criteria) throws Exception;

}
