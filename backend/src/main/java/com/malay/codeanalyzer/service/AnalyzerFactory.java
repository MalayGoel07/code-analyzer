package com.malay.codeanalyzer.service;

import org.springframework.stereotype.Service;

import com.malay.codeanalyzer.model.AnalysisResult;

@Service
public class AnalyzerFactory {

    private final JavaAnalyzer javaAnalyzer;
    private final PythonAnalyzerClient pythonAnalyzerClient;

    public AnalyzerFactory(JavaAnalyzer javaAnalyzer,PythonAnalyzerClient pythonAnalyzerClient) {
        this.javaAnalyzer = javaAnalyzer;
        this.pythonAnalyzerClient = pythonAnalyzerClient;
    }

    public AnalysisResult analyze(String language, String code) {
        return switch (language.toLowerCase()) {
            case "java" -> javaAnalyzer.analyze(code);
            case "python" -> pythonAnalyzerClient.analyze(code);
            default -> throw new IllegalArgumentException("Unsupported language");
        };
    }
}
