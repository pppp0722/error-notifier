package com.ilhwanlee.mockup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MockupController {

    @PostMapping("/chat.postMessage")
    public ResponseEntity<Void> mock(@RequestBody String body) {
        System.out.println(body);
        return ResponseEntity.ok().build();
    }
}
