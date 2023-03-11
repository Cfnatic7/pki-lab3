package com.example.demo.controllers;

import com.example.demo.dtos.HelloWorldDto;
import com.example.demo.dtos.NameRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class HelloWorld {

    @PostMapping(value = "/hello-world", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public HelloWorldDto helloWorld(@RequestBody NameRequest nameRequest) {
        try {
            String retVal = nameRequest.getName() != null ? nameRequest.getName() : "World";
            return HelloWorldDto.builder().Hello(retVal).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
