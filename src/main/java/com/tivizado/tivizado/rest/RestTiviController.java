package com.tivizado.tivizado.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lista")
public class RestTiviController {
    
    @GetMapping
    public String list(){
        return "hola";
    }
}
