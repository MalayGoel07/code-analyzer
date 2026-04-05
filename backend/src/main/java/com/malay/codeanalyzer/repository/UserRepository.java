package com.malay.codeanalyzer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.malay.codeanalyzer.model.SignupForm;

@Repository
public interface UserRepository extends MongoRepository<SignupForm, String> {
    boolean existsByEmail(String email);
    SignupForm findByEmail(String email);
}