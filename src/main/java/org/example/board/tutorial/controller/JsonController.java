package org.example.board.tutorial.controller;

import org.example.board.tutorial.domain.ProductVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// JSON 데이터를 생성하는 경우
@Controller
public class JsonController {

    @RequestMapping("/doJson")
    @ResponseBody
    public ProductVO doJson() {

        ProductVO productVO = new ProductVO("laptop", 3000000);

        return productVO;
    }

}