package com.example.ri_test.test_test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {


  @GetMapping("/")
  public String getHello() {
    return "Yes, it works!";
  }
}