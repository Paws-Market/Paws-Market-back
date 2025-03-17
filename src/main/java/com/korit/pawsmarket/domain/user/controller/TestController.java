package com.korit.pawsmarket.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    // 백 -> 프론트
    @GetMapping("/test")
    public String test() {
        return "백도리";
    }

    // 프론트 -> 백
    @PostMapping("/test/name")
    public void updateName(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        System.out.println("***" + name + "***");
    }
}