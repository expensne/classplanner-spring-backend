package de.dev101.classplanner.api.endpoints.students;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.dev101.classplanner.api.endpoints.students.model.EncryptedStudent;

public interface EncryptedStudentRepository extends MongoRepository<EncryptedStudent, String> {
}
