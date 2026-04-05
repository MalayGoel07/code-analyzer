package com.malay.codeanalyzer.service;

import com.malay.codeanalyzer.model.AnalysisResult;

public class PythonAnalyzer {

    public AnalysisResult analyze(String code) {
        AnalysisResult result = new AnalysisResult();

        if (code == null || code.isBlank()) {return result;}
        result.setLanguage("Python");
        result.setLines(code.split("\n").length);
        result.setWords(code.trim().split("\\s+").length);

        //basic open
        int ifCount = countKeyword(code, "if");
        int loopCount = countKeyword(code, "for") + countKeyword(code, "while");
        int functionCount = countKeyword(code, "def");
        int classCount = countKeyword(code, "class");
        result.setIfCount(ifCount);
        result.setLoopCount(loopCount);
        result.setFunctionCount(functionCount);
        result.setClassCount(classCount);
        //basic close

        //complexity
        int complexity = 1 + ifCount + loopCount;
        int maxDepth = getMaxLoopDepth(code);
        String timeComplexity;
        String level;
        //complexity

        //cases open
        if (complexity <= 5) {level = "Low";}
        else if (complexity <= 10) {level = "Moderate";}
        else {level = "High";}
        result.setCyclomaticComplexity(level);
        if (maxDepth == 0){timeComplexity = "O(1)";}
        else if (maxDepth == 1) {timeComplexity = "O(n)";} 
        else {timeComplexity = "O(n^" + maxDepth + ")";}
        //cases close

        result.setTimeComplexity(timeComplexity);

        //list and dict
        int listCount = code.length() - code.replace("[", "").length();
        int dictCount = code.length() - code.replace("{", "").length();
        if (listCount > 0 || dictCount > 0) {result.setSpaceComplexity("O(n)");}
        else {result.setSpaceComplexity("O(1)");}
        return result;
        //list and dict
    }
    private int countKeyword(String code, String keyword) {
        return code.split("\\b" + keyword + "\\b").length - 1;
    }
    private int getMaxLoopDepth(String code) {
        String[] lines = code.split("\n");
        int maxDepth = 0;
        int currentDepth = 0;
        int preindex = -1;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("for ") || trimmed.startsWith("while ")) {
                int index = line.indexOf(trimmed);
                if (index > preindex)
                    {currentDepth++;} 
                else if (index == preindex)
                    {} 
                else 
                    {currentDepth = 1;}
                maxDepth = Math.max(maxDepth, currentDepth);
                preindex =index;
            }
        }
        return maxDepth;
    }
}
