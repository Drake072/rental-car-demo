package com.example.demorentalcar.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "v1")
@RestController
public class UserController {

    @GetMapping
    public String hello() {
        return "Hello";
    }

}
