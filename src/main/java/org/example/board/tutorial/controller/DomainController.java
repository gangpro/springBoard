package org.example.board.tutorial.controller;

import org.example.board.tutorial.domain.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// 만들어진 결과 데이터를 view 에 전달할 경우
@Controller
public class DomainController {

    private static final Logger logger = LoggerFactory.getLogger(DomainController.class);

    @RequestMapping("/doD")
    public String doD(Model model) {
        logger.info("/doD called...");

        ProductVO product = new ProductVO("desktop", 10000);
        model.addAttribute(product);

        return "/tutorial/product_detail";
    }

}