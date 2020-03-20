package org.example.board.tutorial.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

// String 리턴 타입인 경우
// http://localhost:8081/doC?msg=world
@Controller
public class StringController {

    private static final Logger logger = LoggerFactory.getLogger(StringController.class);

    @RequestMapping("/doC")
    public String doC(@ModelAttribute("msg") String msg) {

        logger.info("/doC called...");

        return "tutorial/result";
    }

}
