package org.example.board.article.service;

import org.example.board.article.domain.ArticleVO;
import org.example.board.article.persistence.ArticleDAO;
import org.example.board.commons.paging.Criteria;
import org.example.board.commons.paging.SearchCriteria;
import org.example.board.upload.persistence.ArticleFileDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

// 클래스 생성하고, 메서드를 오버라이딩
@Repository
public class ArticleServiceImpl implements ArticleService {

    private ArticleDAO articleDAO;
    private final ArticleFileDAO articleFileDAO;

    @Inject
    public ArticleServiceImpl(ArticleDAO articleDAO, ArticleFileDAO articleFileDAO) {
        this.articleDAO = articleDAO;
        this.articleFileDAO = articleFileDAO;
    }

    // 등록
    @Transactional  // 게시글 입력처리와 게시글 첨부파일 입력처리가 동시에 이루어지기 때문에 트랜잭션 처리를 반드시 해줘야 함.
    @Override
    public void create(ArticleVO articleVO) throws Exception {

        // 게시글 입력처리
        articleDAO.create(articleVO);
        String[] files = articleVO.getFiles();

        if (files == null)
            return;
        // 게시글 첨부파일 입력처리
        for (String fileName : files)
            articleFileDAO.addFile(fileName);

    }

    // 조회
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public ArticleVO read(Integer articleNo) throws Exception {
        articleDAO.updateViewCnt(articleNo);
        return articleDAO.read(articleNo);
    }

    // 수정
    @Transactional
    @Override
    public void update(ArticleVO articleVO) throws Exception {
        Integer articleNo = articleVO.getArticleNo();
        String[] files = articleVO.getFiles();

        articleDAO.update(articleVO);           // 게시글 수정처리
        articleFileDAO.deleteFiles(articleNo);  // 기존의 첨부파일 정보는 전체 삭제처리

        if (files == null)
            return;
        for (String fileName : files)
            articleFileDAO.replaceFile(fileName, articleNo);    // 첨부파일 정보를 새로 테이블에 입력처리
    }

    // 삭제
    @Transactional  // 게시글이 삭제 처리되기 전에 해당 게시글의 첨부파일들을 테이블에서 먼저 삭제처리 하도록 아래와 같이 코드를 작성
    @Override
    public void delete(Integer articleNo) throws Exception {
        articleFileDAO.deleteFiles(articleNo);
        articleDAO.delete(articleNo);
    }

    // 조회
    @Override
    public List<ArticleVO> listAll() throws Exception {
        return articleDAO.listAll();
    }

    // 페이징 목록 메서드 구현
    @Override
    public List<ArticleVO> listCriteria(Criteria criteria) throws Exception {
        return articleDAO.listCriteria(criteria);
    }

    // 페이징 전체 게시글 갯수 처리
    @Override
    public int countArticles(Criteria criteria) throws Exception {
        return articleDAO.countArticles(criteria);
    }

    // 검색된 목록
    @Override
    public List<ArticleVO> listSearch(SearchCriteria searchCriteria) throws Exception {
        return articleDAO.listCriteria(searchCriteria);
    }

    // 검색된 게시글의 갯수
    @Override
    public int countSearchedArticles(SearchCriteria searchCriteria) throws Exception {
        return articleDAO.countSearchedArticles(searchCriteria);
    }

}
