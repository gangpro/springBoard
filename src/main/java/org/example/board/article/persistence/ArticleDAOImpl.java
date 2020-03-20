package org.example.board.article.persistence;

import org.apache.ibatis.session.SqlSession;
import org.example.board.article.domain.ArticleVO;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

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
}
