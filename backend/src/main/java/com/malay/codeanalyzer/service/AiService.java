package com.malay.codeanalyzer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String analyze(String code) {
        String url = "http://localhost:8000/ai/generate";
        Map<String, String> request = new HashMap<>();
        request.put("task", code);
        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
        return Objects.toString(response != null ? response.get("response") : null, "");
    }
}
