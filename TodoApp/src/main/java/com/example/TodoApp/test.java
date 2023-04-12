package com.example.TodoApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/")
    public String helloWorld(){
        return "To-do Application!";
    }

}
