package com.malay.codeanalyzer.model;

public class AnalysisResult {

    private String language;
    private int lines;
    private int words;
    private int ifCount;
    private int loopCount;
    private int functionCount;
    private int classCount;
    private String timeComplexity;
    private String spaceComplexity;
    private String cyclomaticComplexity;
    private int maxNestingDepth;

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLines() {
        return lines;
    }
    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getIfCount() {
        return ifCount;
    }
    public void setIfCount(int ifCount) {
        this.ifCount = ifCount;
    }

    public int getLoopCount() {
        return loopCount;
    }
    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public int getFunctionCount() {
        return functionCount;
    }
    public void setFunctionCount(int functionCount) {
        this.functionCount = functionCount;
    }

    public int getClassCount() {
        return classCount;
    }
    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }

    public int getMaxNestingDepth() {
        return maxNestingDepth;
    }
    public void setMaxNestingDepth(int maxNestingDepth) {
        this.maxNestingDepth = maxNestingDepth;
    }

    public int getWords() {
        return words;
    }
    public void setWords(int words) {
        this.words = words;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public void setTimeComplexity(String timeComplexity) {
        this.timeComplexity = timeComplexity;
    }

    public String getSpaceComplexity() {
        return spaceComplexity;
    }

    public void setSpaceComplexity(String spaceComplexity) {
        this.spaceComplexity = spaceComplexity;
    }

    public String getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(String cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }
}
