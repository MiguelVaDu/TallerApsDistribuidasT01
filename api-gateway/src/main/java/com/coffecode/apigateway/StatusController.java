package com.coffecode.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/")
    public Map<String, String> estado() {
        return Map.of(
                "estado", "✅ API Gateway activo",
                "fecha", LocalDateTime.now().toString()
        );
    }
}
