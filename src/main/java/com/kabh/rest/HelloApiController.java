package com.kabh.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class HelloApiController {

    @GetMapping("/add")
    public int add(@RequestParam int num1, @RequestParam int num2) {
        return num1 + num2;
    }

}
