package com.mango.lolhero.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class SearchController {

    @PostMapping("/searchOne/user")
    public String searchOne(String username){
        System.out.println(username);

        return "selectPage";
    }

}
