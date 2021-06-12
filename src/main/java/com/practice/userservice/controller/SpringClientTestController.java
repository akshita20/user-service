package com.practice.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
public class SpringClientTestController {

    @Value("${msg:Hello from user-service}")
    private String msg;

    private String bookUrl = "http://book-service/client-config";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/client-config")
    public String getBooks() {
        return msg;
    }

    @GetMapping("/connect-book-service")
    public String getDataFromBookService(){
        return restTemplate.getForObject(bookUrl,String.class);
    }


}
