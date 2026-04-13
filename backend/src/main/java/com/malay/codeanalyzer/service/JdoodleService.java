package com.malay.codeanalyzer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class JdoodleService {
    @Value("${JDOODLE_CLIENT_ID:}")
    private String clientId;

    @Value("${JDOODLE_CLIENT_SECRET:}")
    private String clientSecret;

    public String runCode(String code, String language) {
        if (clientId == null || clientId.isBlank() || clientSecret == null || clientSecret.isBlank()) {
            return "JDoodle credentials are not configured.";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.jdoodle.com/v1/execute";

        Map<String, String> request = new HashMap<>();
        
        request.put("clientId", clientId);
        request.put("clientSecret", clientSecret);
        request.put("script", code);

        if (language.equals("java")) {
            request.put("language", "java");
            request.put("versionIndex", "4");
        } else {
            request.put("language", "python3");
            request.put("versionIndex", "3");
        }

        try {
            Map response = restTemplate.postForObject(url, request, Map.class);
            if (response == null || response.get("output") == null) {
                return "JDoodle did not return any output.";
            }
            return response.get("output").toString();
        } catch (Exception ex) {
            return "Unable to run code right now. Please try again later.";
        }
    }
}
