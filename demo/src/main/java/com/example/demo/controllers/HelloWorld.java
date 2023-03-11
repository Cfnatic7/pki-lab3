package com.example.demo.controllers;

import com.example.demo.dtos.HelloWorldDto;
import com.example.demo.dtos.NameRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class HelloWorld {

    private static int wins = 0;

    private static int loses = 0;

    private static int ties = 0;

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

    @PostMapping("/wins")
    public int wins() {
        return wins++;
    }

    @PostMapping("/loses")
    public int loses() {
        return loses++;
    }

    @PostMapping("/ties")
    public int ties() {
        return ties++;
    }

    @GetMapping("/score")
    public String score() {
        String pattern =
                "{ \"wins\":\"%s\", \"losses\":\"%s\", \"ties\": \"%s\"}";
        return String.format(pattern, wins, loses, ties);
    }

    @PutMapping("/update")
    public String update(@RequestParam("wins") int wins,
                         @RequestParam("loses") int loses,
                         @RequestParam("ties") int ties) {
        HelloWorld.wins = wins;
        HelloWorld.loses = loses;
        HelloWorld.ties = ties;

        String pattern =
                "{ \"wins\":\"%s\", \"losses\":\"%s\", \"ties\": \"%s\"}";
        return String.format(pattern, HelloWorld.wins, HelloWorld.loses, HelloWorld.ties);
    }
}
