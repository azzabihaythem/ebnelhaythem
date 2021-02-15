package com.medical.ebnelhaythem.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MainController {

    @GetMapping(value = "/custom")
    public String custom() {
        return "custom";
    }
}
