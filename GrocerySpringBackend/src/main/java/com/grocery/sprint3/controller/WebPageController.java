package com.grocery.sprint3.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/form")  // Access via localhost:8080/form
public class WebPageController {

  @GetMapping(path="/add")  // Access via localhost:8080/form/add
  public String addForm(Model model) {
    return "form";
  }
}
