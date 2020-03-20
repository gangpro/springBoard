package org.example.board.article.service;

import org.example.board.article.domain.ArticleVO;
import org.example.board.article.persistence.ArticleDAO;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

// 클래스 생성하고, 메서드를 오버라이딩
@Repository
public class ArticleServiceImpl implements ArticleService {

    private ArticleDAO articleDAO;

    @Inject
    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    // 등록
    @Override
    public void create(ArticleVO articleVO) throws Exception {
        articleDAO.create(articleVO);
    }

    // 조회
    @Override
    public ArticleVO read(Integer articleNo) throws Exception {
        return articleDAO.read(articleNo);
    }

    // 수정
    @Override
    public void update(ArticleVO articleVO) throws Exception {
        articleDAO.update(articleVO);
    }

    // 삭제
    @Override
    public void delete(Integer articleNo) throws Exception {
        articleDAO.delete(articleNo);
    }

    // 조회
    @Override
    public List<ArticleVO> listAll() throws Exception {
        return articleDAO.listAll();
    }
}
