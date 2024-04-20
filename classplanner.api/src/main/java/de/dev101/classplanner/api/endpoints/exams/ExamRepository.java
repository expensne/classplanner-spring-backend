package de.dev101.classplanner.api.endpoints.exams;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface ExamRepository extends MongoRepository<Exam, String> {
}
