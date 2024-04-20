package de.dev101.classplanner.api.endpoints.exams;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamRepository extends MongoRepository<Exam, String> {
}
