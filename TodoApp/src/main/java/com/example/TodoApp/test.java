package com.example.TodoApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    String test = "To-do Application";

    @GetMapping("/")
    public String helloWorld(){
        return  test;
    }



}
