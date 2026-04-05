package com.malay.codeanalyzer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.malay.codeanalyzer.model.FeedbackForm;

@Repository
public interface FeedbackRepository extends MongoRepository<FeedbackForm, String> {
}
