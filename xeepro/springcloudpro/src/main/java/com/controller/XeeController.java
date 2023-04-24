package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by swh on 2023/4/24.
 */
@RestController
@RequestMapping("/xee")
public class XeeController {
    // 访问路径： http://localhost:8080/xee/test
    @GetMapping("/test")
    public String test(){
        return "xee";
    }

    // 访问路径： http://localhost:8080/xee
    @GetMapping()
    public String test2(){
        return "xee";
    }

}
