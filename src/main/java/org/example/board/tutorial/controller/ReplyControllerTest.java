package org.example.board.tutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reply")
public class ReplyControllerTest {

    @RequestMapping("/test")
    public String replyAjaxTest() {

        return "/tutorial/reply_test";

    }
}
