package com.malay.codeanalyzer.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompAnalyzer {

    //inputer
    public String analyzeText(String code1, String code2) {
        List<String> lines1 = readText(code1);
        List<String> lines2 = readText(code2);
        double similarity = calculateSimilarity(lines1, lines2);
        String complexity1 = estimateComplexity(lines1);
        String complexity2 = estimateComplexity(lines2);
        long comments1 = countComments(lines1);
        long comments2 = countComments(lines2);
        int commonFunctions = countCommonFunctions(lines1, lines2);
        String conclusion = generateConclusion(complexity1, complexity2);
        return "Similarity: " + (int) similarity + "%\n\n" +
                "Complexity:\nCode1: " + complexity1 + "\nCode2: " + complexity2 + "\n\n" +
                "Functions:\nCommon: " + commonFunctions + "\n\n" +
                "Comments:\nCode1: " + comments1 + "\nCode2: " + comments2 + "\n\n" +
                "Conclusion:\n" + conclusion;
    }
    public String analyzeFiles(MultipartFile file1, MultipartFile file2) throws Exception {

        List<String> lines1 = readFile(file1);
        List<String> lines2 = readFile(file2);
        double similarity = calculateSimilarity(lines1, lines2);
        String complexity1 = estimateComplexity(lines1);
        String complexity2 = estimateComplexity(lines2);
        long comments1 = countComments(lines1);
        long comments2 = countComments(lines2);
        int commonFunctions = countCommonFunctions(lines1, lines2);
        String conclusion = generateConclusion(complexity1, complexity2);
        return "Similarity: " + (int) similarity + "%\n\n" +
                "Complexity:\nFile1: " + complexity1 + "\nFile2: " + complexity2 + "\n\n" +
                "Functions:\nCommon: " + commonFunctions + "\n\n" +
                "Comments:\nFile1: " + comments1 + "\nFile2: " + comments2 + "\n\n" +
                "Conclusion:\n" + conclusion;
    }
    //inputer

    //reader
    private List<String> readFile(MultipartFile file) throws Exception {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {lines.add(line);}
        reader.close();
        return lines;
    }
    private List<String> readText(String code) {
        List<String> lines = new ArrayList<>();
        if (code == null || code.isBlank()) {return lines;}
        for (String line : code.split("\\R")) {lines.add(line);}
        return lines;
    }
    //reader

    //normalizer
    private String normalize(String line) {
        return line.trim().replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]", "");
    }

    //calculateSimilarity
    private double calculateSimilarity(List<String> lines1, List<String> lines2) {
        int commonLines = 0;
        for (String line1 : lines1) {
            for (String line2 : lines2) {
                if (normalize(line1).equals(normalize(line2))) {
                    commonLines++;
                    break;
                }}}
        int totalLines = Math.max(lines1.size(), lines2.size());
        if (totalLines == 0) {return 0;}
        return ((double)commonLines/totalLines)*100;
    }

    private String estimateComplexity(List<String> lines) {
        int loopCount = 0;
        int maxDepth = 0;
        boolean hasMidpointCalculation = false;
        boolean hasShrinkingBounds = false;
        boolean hasRecursiveBinarySearchCall = false;
        String functionName = null;

        for (String raw : lines) {
            String line = raw.trim();
            String normalizedLine = line.toLowerCase();

            if (line.startsWith("def ")) {functionName = line.split("\\(")[0].replace("def", "").trim();}
            if (line.startsWith("for ") || line.startsWith("while ") || line.contains("for(") || line.contains("while(")) {loopCount++;}
            if (line.matches(".*\\b(max|min|sum|filter|map|sorted)\\s*\\(.*")) {maxDepth = Math.max(maxDepth, 1);}
            if (functionName != null && line.contains(functionName + "(")) {maxDepth = Math.max(maxDepth, 1);}
            hasMidpointCalculation = hasMidpointCalculation || hasMidpointCalculation(normalizedLine);
            hasShrinkingBounds = hasShrinkingBounds || hasShrinkingSearchBounds(normalizedLine);
            hasRecursiveBinarySearchCall = hasRecursiveBinarySearchCall|| hasRecursiveBinarySearchCall(line, normalizedLine);
        }

        boolean hasBinarySearchPattern = (hasMidpointCalculation && hasShrinkingBounds)|| hasRecursiveBinarySearchCall;

        if (hasBinarySearchPattern && (loopCount == 1 || maxDepth == 1)) {return "O(log n)";}
        if (loopCount >= 2) {return "O(n^" + loopCount + ")";}
        if (loopCount == 1 || maxDepth == 1) {return "O(n)";}
        return "O(1)";
    }

    private boolean hasMidpointCalculation(String normalizedLine) {
        return normalizedLine.contains("mid")
            || normalizedLine.contains("(left+right)/2")
            || normalizedLine.contains("(low+high)/2")
            || normalizedLine.contains("(start+end)/2")
            || normalizedLine.contains("left+(right-left)/2")
            || normalizedLine.contains("low+(high-low)/2")
            || normalizedLine.contains("start+(end-start)/2")
            || normalizedLine.contains("left+((right-left)/2)")
            || normalizedLine.contains("low+((high-low)/2)")
            || normalizedLine.contains("start+((end-start)/2)");
    }

    private boolean hasShrinkingSearchBounds(String normalizedLine) {
        return normalizedLine.matches(".*\\b(left|low|start)\\s*=\\s*(mid\\s*\\+\\s*1|mid).*")|| normalizedLine.matches(".*\\b(right|high|end)\\s*=\\s*(mid\\s*-\\s*1|mid).*");
    }

    private boolean hasRecursiveBinarySearchCall(String line, String normalizedLine) {
        return line.contains("(") && normalizedLine.contains("mid")&& (normalizedLine.contains("left") || normalizedLine.contains("right")|| normalizedLine.contains("low") || normalizedLine.contains("high")|| normalizedLine.contains("start") || normalizedLine.contains("end"));
    }

    private long countComments(List<String> lines) {
        int count = 0;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("//") ||trimmed.startsWith("/*") ||trimmed.startsWith("*") ||trimmed.startsWith("#")) {count++;}}
        return count;
    }
    private int countCommonFunctions(List<String> lines1, List<String> lines2) {
        List<String> functions1 = extractFunctions(lines1);
        List<String> functions2 = extractFunctions(lines2);
        int common = 0;
        for (String func1 : functions1) {
            for (String func2 : functions2) {
                if (func1.equals(func2)) {common++;}
            }}
        return common;
    }

    private List<String> extractFunctions(List<String> lines) {
        List<String> functions = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().startsWith("def ") ||line.trim().startsWith("async def ") ||line.matches(".*\\w+\\s+\\w+\\(.*\\)\\s*\\{.*")) {functions.add(line.trim());}}
        return functions;
    }

    private String generateConclusion(String complexity1, String complexity2) {
        int rank1 = getComplexityRank(complexity1);
        int rank2 = getComplexityRank(complexity2);
        if (rank1 == rank2) {return "Both files have similar performance.";}
        if (rank1 < rank2) {return "File1 is more efficient.";}
        return "File2 is more efficient.";
    }

    private int getComplexityRank(String complexity) {
        if (complexity == null || complexity.isBlank()) {return Integer.MAX_VALUE;}
        String normalized = complexity.trim().toLowerCase();
        if ("o(1)".equals(normalized)) {return 1;}
        if ("o(log n)".equals(normalized)) {return 2;}
        if ("o(n)".equals(normalized)) {return 3;}
        if (normalized.startsWith("o(n^") && normalized.endsWith(")")) {
            String exponent = normalized.substring(4, normalized.length() - 1);
            try {return 3 + Integer.parseInt(exponent);}
            catch (NumberFormatException ignored) {return Integer.MAX_VALUE - 1;}
        }
        return Integer.MAX_VALUE;
    }
}
