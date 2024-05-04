package de.dev101.classplanner.api.endpoints.exams;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.dev101.classplanner.api.endpoints.exams.model.EncryptedExam;

public interface EncryptedExamRepository extends MongoRepository<EncryptedExam, String> {
}
