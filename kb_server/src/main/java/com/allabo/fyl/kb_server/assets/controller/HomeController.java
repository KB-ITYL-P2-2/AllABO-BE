package com.allabo.fyl.kb_server.assets.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("================> HomController /");
        return "index";		// View의 이름
    }

}
