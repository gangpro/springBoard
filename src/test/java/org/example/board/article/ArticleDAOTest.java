package org.example.board.article;

import org.example.board.article.persistence.ArticleDAO;
import org.example.board.article.domain.ArticleVO;
import org.example.board.commons.paging.Criteria;
import org.example.board.commons.paging.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml"})
public class ArticleDAOTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDAOTest.class);

    @Inject
    private ArticleDAO articleDAO;

//    // 게시글 1개 등록 테스트
//    @Test
//    public void testCreate() throws Exception {
//        ArticleVO articleVO = new ArticleVO();
//
//        articleVO.setTitle("제목 신규 테스트");
//        articleVO.setContent("내용 신규 테스트");
//        articleVO.setWriter("작성자 신규 테스트");
//        articleDAO.create(articleVO);
//    }

    // 게시글 1000개 생성 테스트
    @Test
    public void testCreate() throws Exception {

        for(int i = 1; i <= 1000; i++) {
            ArticleVO articleVO = new ArticleVO();
            articleVO.setTitle("제목" + i);
            articleVO.setContent("내용" + i);
            articleVO.setWriter("작성자" + (i%10));

            articleDAO.create(articleVO);
        }
    }

    // 게시글 조회
    @Test
    public void testRead() throws Exception {
        logger.info(articleDAO.read(3).toString());
    }

    // 게시글 업데이트 테스트
    @Test
    public void testUpdate() throws Exception {
        ArticleVO articleVO = new ArticleVO();
        articleVO.setArticleNo(3);
        articleVO.setTitle("제목 수정 테스트3");
        articleVO.setContent("내용 수정 테스트3");
        articleDAO.update(articleVO);
    }

    // 게시글 1개 삭제 테스트
    @Test
    public void testDelete() throws Exception {
        articleDAO.delete(2);
    }

    // 페이징 처리 SQL 테스트
    @Test
    public void testListPaging() throws Exception {

        int page = 3;

        List<ArticleVO> articles = articleDAO.listPaging(page);

        for(ArticleVO article : articles) {
            logger.info(article.getArticleNo() + ":" + article.getTitle());
        }

    }

    // 페이징 처리 + Criteria 구현 SQL 테스트
    @Test
    public void testListCriteria() throws Exception {
        Criteria criteria = new Criteria();

        criteria.setPage(3);
        criteria.setPerPageNum(20);

        List<ArticleVO> articles = articleDAO.listCriteria(criteria);

        for(ArticleVO article : articles) {
            logger.info(article.getArticleNo() + " : " + article.getTitle());
        }
    }

    // 페이징 처리 개선 URI test1
    @Test
    public void testURI() throws Exception {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/article/read")
                .queryParam("articleNo", 12)
                .queryParam("perPageNum", 20)
                .build();

        logger.info("/article/read?articleNo=12&perPageNum=20");
        logger.info(uriComponents.toString());
    }
    // 테스트 실행 전
    //= INFO : org.example.board.article.ArticleDAOTest - /article/read?articleNo=12
    // 테스트 실행 후
    //= INFO : org.example.board.article.ArticleDAOTest - /article/read?articleNo=12&perPageNum=20
    // UriComponents 클래스를 통해 path 나 query 에 해당하는
    // 문자열을 추가해서 원하는 URI 를 생성할 수가 있다.ㅇㄹㅇㄹㅇ

    // 페이징 처리 개선 URI test2
    @Test
    public void testURI2() throws Exception {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/{module}/{page}")
                .queryParam("articleNo", 12)
                .queryParam("perPageNum", 20)
                .build()
                .expand("article", "read")
                .encode();

        logger.info("/article/read?articleNo=12&perPageNum=20");
        logger.info(uriComponents.toString());

        System.out.println(uriComponents);
    }
    // 테스트 실행 후 testURI1 과 동일한 값을 얻을 수 있다.
    //= INFO : org.example.board.article.ArticleDAOTest - /article/read?articleNo=12&perPageNum=20
    //= INFO : org.example.board.article.ArticleDAOTest - /article/read?articleNo=12&perPageNum=20

    // 검색 관련 동적 SQL 테스트
    @Test
    public void testDynamic1() throws Exception {

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setKeyword("999");
        searchCriteria.setSearchType("t");

        logger.info("======================");

        List<ArticleVO> articles = articleDAO.listSearch(searchCriteria);

        for (ArticleVO article : articles) {
            logger.info(article.getArticleNo() + " : " + article.getTitle());
        }

        logger.info("======================");

        logger.info("searched articles count : " + articleDAO.countSearchedArticles(searchCriteria));
    }

}
