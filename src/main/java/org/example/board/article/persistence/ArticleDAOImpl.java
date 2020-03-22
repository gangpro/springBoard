package org.example.board.article.persistence;

import org.apache.ibatis.session.SqlSession;
import org.example.board.article.domain.ArticleVO;
import org.example.board.commons.paging.Criteria;
import org.example.board.commons.paging.SearchCriteria;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 클래스 생성하고, 메서드를 오버라이딩
@Repository
public class ArticleDAOImpl implements ArticleDAO {

    // 변수 NAMESPACE 는 articleMapper.xml 의 <mapper> 태그의 namespace 속성과 일치해야 한다.
    private static final String NAMESPACE = "org.example.board.mappers.article.ArticleMapper";

    private final SqlSession sqlSession;

    @Inject
    public ArticleDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // 등록
    @Override
    public void create(ArticleVO articleVO) {
        sqlSession.insert(NAMESPACE + ".create", articleVO);
    }

    // 조회
    @Override
    public ArticleVO read(Integer articleNo) {
        return sqlSession.selectOne(NAMESPACE + ".read", articleNo);
    }

    // 수정
    @Override
    public void update(ArticleVO articleVO) {
        sqlSession.update(NAMESPACE + ".update", articleVO);
    }

    // 삭제
    @Override
    public void delete(Integer articleNo) {
        sqlSession.delete(NAMESPACE + ".delete", articleNo);
    }

    // 조회
    @Override
    public List<ArticleVO> listAll() {
        return sqlSession.selectList(NAMESPACE + ".listAll");
    }

    // 페이징 처리 구현 클래스
    @Override
    public List<ArticleVO> listPaging(int page) {

        if (page <= 0) {
            page = 1;
        }

        page = (page - 1) * 10;

        return sqlSession.selectList(NAMESPACE + ".listPaging", page);
    }

    // 페이징 처리 + Criteria 타입의 변수 구현 클래스
    @Override
    public List<ArticleVO> listCriteria(Criteria criteria) {
        return sqlSession.selectList(NAMESPACE + ".listCriteria", criteria);
    }

    // 페이징 전체 게시글 갯수 처리
    @Override
    public int countArticles(Criteria criteria) {
        return sqlSession.selectOne(NAMESPACE + ".countArticles", criteria);
    }

    // 검색된 목록 추상 메서드
    @Override
    public List<ArticleVO> listSearch(SearchCriteria searchCriteria) {
        return sqlSession.selectList(NAMESPACE + ".listSearch", searchCriteria);
    }

    // 검색된 게시글의 갯수를 리턴하는 추상 메서드 선언
    @Override
    public int countSearchedArticles(SearchCriteria searchCriteria) {
        return sqlSession.selectOne(NAMESPACE + ".countSearchedArticles", searchCriteria);
    }

    // 댓글 갱신 메서드 구현
    @Override
    public void updateReplyCnt(Integer articleNo, int amount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("articleNo", articleNo);
        paramMap.put("amount", amount);

        sqlSession.update(NAMESPACE + ".updateReplyCnt", paramMap);
    }

    // 게시글 조회수 증가 메서드 구현
    @Override
    public void updateViewCnt(Integer articleNo) {
        sqlSession.update(NAMESPACE + ".updateViewCnt", articleNo);
    }

}
