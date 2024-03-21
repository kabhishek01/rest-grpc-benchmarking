package com.kabh.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApiController {

    @PostMapping("/hello")
    public String hello(@RequestBody String name) {
        return "Hello, " + name + "!";
    }


}
