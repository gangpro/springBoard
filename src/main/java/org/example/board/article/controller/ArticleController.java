package org.example.board.article.controller;

import org.example.board.article.domain.ArticleVO;
import org.example.board.article.service.ArticleService;
import org.example.board.commons.paging.Criteria;
import org.example.board.commons.paging.PageMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private ArticleService articleService;

    @Inject
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 등록 페이지 이동
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeGET() {

        logger.info("writeGET() called...");

        return "/article/write";
    }

    // 등록 처리
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writePOST(ArticleVO articleVO,
                            RedirectAttributes redirectAttributes) throws Exception {

        logger.info("/writePOST() called...");
        logger.info(articleVO.toString());

        articleService.create(articleVO);
        redirectAttributes.addFlashAttribute("msg", "regSuccess");

        return "redirect:/article/list";
    }

    // 목록 페이지 이동
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        logger.info("list() called ...");
        model.addAttribute("articles", articleService.listAll());

        return "/article/list";
    }

    // 조회 페이지 이동
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read(@RequestParam("articleNo") int articleNo,
                       Model model) throws Exception {

        logger.info("read() called...");
        model.addAttribute("article", articleService.read(articleNo));

        return "/article/read";
    }

    // 수정 페이지 이동
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modifyGET(@RequestParam("articleNo") int articleNo,
                            Model model) throws Exception {

        logger.info("modifyGet() called ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "/article/modify";
    }

    // 수정 처리
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(ArticleVO articleVO,
                             RedirectAttributes redirectAttributes) throws Exception {

        logger.info("modifyPOST() called ...");

        articleService.update(articleVO);
        redirectAttributes.addFlashAttribute("msg", "modSuccess");

        return "redirect:/article/list";
    }

    // 삭제 처리
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam("articleNo") int articleNo,
                         RedirectAttributes redirectAttributes) throws Exception {

        logger.info("remove() called ...");

        articleService.delete(articleNo);
        redirectAttributes.addFlashAttribute("msg", "delSuccess");

        return "redirect:/article/list";
    }

    // 페이징 처리
    @RequestMapping(value = "/listCriteria", method = RequestMethod.GET)
    public String listCriteria(Model model, Criteria criteria) throws Exception {

        logger.info("normal listCriteria() ...");

        model.addAttribute("articles", articleService.listCriteria(criteria));

        return "/article/list_criteria";
    }

    // 페이징 목록 처리
    @RequestMapping(value = "/listPaging", method = RequestMethod.GET)
    public String list(Model model, Criteria criteria) throws Exception {   // Criteria, Model 타입의 변수 criteria 와 model 을 파라미터로 상요한다.

        logger.info("paging list() called ...");

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(criteria);
//        pageMaker.setTotalCount(1000);    // 아직 영속계층에서 전체 게시글의 갯수를 구하는 로직을 구현하지 않았기 때문에 setTotalCount() 의 매개변수는 1000을 임시로 넣어줌.
        pageMaker.setTotalCount(articleService.countArticles(criteria));

        model.addAttribute("articles", articleService.listCriteria(criteria));
        model.addAttribute("pageMaker", pageMaker);

        return "/article/list_paging";
    }

}
