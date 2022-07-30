package com.hitwh.face.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangrong
 * @date 2022/4/14 18:11
 */
@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
