package com.malay.codeanalyzer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.malay.codeanalyzer.model.AnalysisResult;
import com.malay.codeanalyzer.model.CodeRequest;

@Service
public class PythonAnalyzerClient {

    private final WebClient webClient;

    public PythonAnalyzerClient() {
        this.webClient = WebClient.create("http://localhost:8000");
    }

    public AnalysisResult analyze(String code) {
        try {
            AnalysisResult result = webClient.post()
                    .uri("/analyze")
                    .bodyValue(new CodeRequest(code))
                    .retrieve()
                    .bodyToMono(AnalysisResult.class)
                    .block();

            return result != null ? result : unavailableResult(code);
        } catch (Exception ex) {
            return unavailableResult(code);
        }
    }

    private AnalysisResult unavailableResult(String code) {
        AnalysisResult result = new AnalysisResult();
        if (code != null && !code.isBlank()) {
            result.setLines(code.split("\\R").length);
            result.setWords(code.trim().split("\\s+").length);
        }
        result.setLanguage("Python");
        result.setCyclomaticComplexity("Python service unavailable");
        result.setTimeComplexity("N/A");
        result.setSpaceComplexity("N/A");
        return result;
    }
}
