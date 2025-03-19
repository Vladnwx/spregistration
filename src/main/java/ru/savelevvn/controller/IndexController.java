package ru.savelevvn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/index")
    public String index() {
        log.info("Slf4j: " + this.getClass().getName() + " Method " + Thread.currentThread().getStackTrace()[1].getMethodName() + " executed");
        return "index";
    }

}
