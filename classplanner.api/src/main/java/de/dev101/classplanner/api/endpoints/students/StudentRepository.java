package de.dev101.classplanner.api.endpoints.students;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public interface StudentRepository extends MongoRepository<Student, String> {
}
