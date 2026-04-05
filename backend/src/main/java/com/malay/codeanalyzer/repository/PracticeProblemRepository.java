package com.malay.codeanalyzer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.malay.codeanalyzer.model.PracticeProblem;

@Repository
public interface PracticeProblemRepository extends MongoRepository<PracticeProblem, String> {
    List<PracticeProblem> findAllByOrderByCreatedAtDesc();
}
