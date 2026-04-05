package com.malay.codeanalyzer.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.malay.codeanalyzer.model.AnalysisResult;
import org.springframework.stereotype.Service;

@Service
public class JavaAnalyzer {

    public AnalysisResult analyze(String code) {

        AnalysisResult result = new AnalysisResult();

        if (code == null || code.isBlank()) {return result;}
        result.setLines(code.split("\\R").length);
        result.setWords(code.trim().split("\\s+").length);
        
        try {
            if (!code.contains("class")) {code = "public class Temp { void tempMethod() { " + code + " } }";}

            CompilationUnit cu = StaticJavaParser.parse(code);
            int classCount = cu.findAll(ClassOrInterfaceDeclaration.class).size();
            int methodCount = cu.findAll(MethodDeclaration.class).size();
            int ifCount = cu.findAll(IfStmt.class).size();
            int loopCount = cu.findAll(ForStmt.class).size() + cu.findAll(WhileStmt.class).size() + cu.findAll(ForEachStmt.class).size();
            int switchCount = cu.findAll(SwitchStmt.class).size();
            int catchCount = cu.findAll(CatchClause.class).size();
            int cyclomaticComplexity = 1 + ifCount + loopCount + switchCount + catchCount;
            int maxDepth = getMaxLoopDepth(cu);

            String level;
            if (cyclomaticComplexity <= 5) {level = "Low";} 
            else if (cyclomaticComplexity <= 10) {level = "Moderate";} 
            else {level = "High";}

            String timeComplexity;
            if (maxDepth == 0) {timeComplexity = "O(1)";} 
            else if (maxDepth == 1) {timeComplexity = "O(n)";}
            else {timeComplexity = "O(n^" + maxDepth + ")";}

            int arrayCount = cu.findAll(ArrayCreationExpr.class).size();
            String spaceComplexity =arrayCount > 0 ? "O(n)" : "O(1)";

            result.setClassCount(classCount);
            result.setFunctionCount(methodCount);
            result.setIfCount(ifCount);
            result.setLoopCount(loopCount);
            result.setTimeComplexity(timeComplexity);
            result.setSpaceComplexity(spaceComplexity);
            result.setCyclomaticComplexity(level);
            
        } catch (Exception e) {
            result.setClassCount(0);
            result.setFunctionCount(0);
            result.setIfCount(0);
            result.setLoopCount(0);
            result.setCyclomaticComplexity("N/A");
            result.setTimeComplexity("N/A");
            result.setSpaceComplexity("N/A");
        }
        return result;
    }

    private int getMaxLoopDepth(CompilationUnit cu) {
        int maxDepth = 0;
        for (Statement stmt : cu.findAll(Statement.class)) {
            if (stmt instanceof ForStmt || stmt instanceof WhileStmt || stmt instanceof ForEachStmt) {
                int depth = 1;
                Node parent = stmt.getParentNode().orElse(null);
                while (parent != null) {
                    if (parent instanceof ForStmt || parent instanceof WhileStmt || parent instanceof ForEachStmt) {
                        depth++;}
                    parent = parent.getParentNode().orElse(null);
                }
                maxDepth = Math.max(maxDepth, depth);
            }
        }
        return maxDepth;
    }
}
