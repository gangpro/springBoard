package org.example.board.article.controller;

import org.example.board.article.domain.ArticleVO;
import org.example.board.article.service.ArticleService;
import org.example.board.commons.paging.PageMaker;
import org.example.board.commons.paging.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

// 프로젝트 구조 변경
// 기본적인 CRUD + 페이징 처리 + 검색 처리 구현
@Controller
@RequestMapping("/article/paging/search")
public class ArticlePagingSearchController {

    private static final Logger logger = LoggerFactory.getLogger(ArticlePagingSearchController.class);

    private final ArticleService articleService;

    @Inject
    public ArticlePagingSearchController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 등록 페이지 이동
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeGET() {

        logger.info("search writeGET() called...");

        return "/article/search/write";
    }

    // 등록 처리
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writePOST(ArticleVO articleVO,
                            RedirectAttributes redirectAttributes) throws Exception {

        logger.info("search writePOST() called...");
        logger.info(articleVO.toString());

        articleService.create(articleVO);
        redirectAttributes.addFlashAttribute("msg", "regSuccess");

        return "redirect:/article/paging/search/list";
    }

    // 목록
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                       Model model) throws Exception {

        logger.info(("search list() called..."));

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(searchCriteria);
//        pageMaker.setTotalCount(articleService.countArticles(searchCriteria));  // 검색 처리
        pageMaker.setTotalCount(articleService.countSearchedArticles(searchCriteria));    // 검색 처리, 동적 SQL

//        model.addAttribute("articles", articleService.listCriteria(searchCriteria));    // 검색 처리
        model.addAttribute("articles", articleService.listSearch(searchCriteria));    // 검색 처리, 동적 SQL
        model.addAttribute("pageMaker", pageMaker);

        return "article/search/list";
    }

    // 조회 페이지
    // 게시글의 검색정보가 유지되도록 read() 메서드의 매개변수 타입을 SearchCriteria 로 변경
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read(@RequestParam("articleNo") int articleNo,
                       @ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                       Model model) throws Exception {

        logger.info("search read() called ...");

        model.addAttribute("article", articleService.read(articleNo));

        return "article/search/read";
    }

    // 수정 페이지
    // 게시글의 검색정보가 유지되도록 modifyGET() 메서드의 매개변수 타입을 SearchCriteria 로 변경한다.
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modifyGET(@RequestParam("articleNo") int articleNo,
                            @ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                            Model model) throws Exception {

        logger.info("search modifyGet() called ...");
        logger.info(searchCriteria.toString());

        model.addAttribute("article", articleService.read(articleNo));

        return "article/search/modify";
    }

    // 수정 처리
    // 수정 처리가 완료되고, 목록 페이지로 리다이렉트될 때 검색 정보가 유지될 수 있도록 아래와 같이 검색조건과 검색 키워드를 redirectAttributes.addAttribute() 에 저장한다.
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(ArticleVO articleVO,
                             SearchCriteria searchCriteria ,
                             RedirectAttributes redirectAttributes) throws Exception {

        logger.info("search modifyPOST() called ...");

        articleService.update(articleVO);
        redirectAttributes.addAttribute("page", searchCriteria.getPage());
        redirectAttributes.addAttribute("perPageNum", searchCriteria.getPerPageNum());
        redirectAttributes.addAttribute("searchType", searchCriteria.getSearchType());
        redirectAttributes.addAttribute("keyword", searchCriteria.getKeyword());
        redirectAttributes.addFlashAttribute("msg", "modSuccess");

        return "redirect:/article/paging/search/list";
    }

    // 삭제 처리
    // 삭제처리가 완료되고, 목록페이지로 리다이렉트 될 때 검색 정보가 유지될 수 있도록 아래과 같이 검색조건과 검색 키워드를 redirectAttributes.addAttribute() 에 저장한다.
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam("articleNo") int articleNo,
                         SearchCriteria searchCriteria ,
                         RedirectAttributes redirectAttributes) throws Exception {

        logger.info("search remove() called ...");
        articleService.delete(articleNo);
        redirectAttributes.addAttribute("page", searchCriteria.getPage());
        redirectAttributes.addAttribute("perPageNum", searchCriteria.getPerPageNum());
        redirectAttributes.addAttribute("searchType", searchCriteria.getSearchType());
        redirectAttributes.addAttribute("keyword", searchCriteria.getKeyword());
        redirectAttributes.addFlashAttribute("msg", "delSuccess");

        return "redirect:/article/paging/search/list";
    }


}
