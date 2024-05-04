package de.dev101.classplanner.api.endpoints.students;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dev101.classplanner.api.endpoints.encryption.EncryptionService;
import de.dev101.classplanner.api.endpoints.students.model.EncryptedStudent;
import de.dev101.classplanner.api.endpoints.students.model.Student;

@CrossOrigin
@RestController
@RequestMapping("students")
public class StudentController {

    private final EncryptedStudentRepository studentRepository;
    private final EncryptionService encryptionService;

    private final ObjectMapper jsonMapper;

    StudentController(EncryptedStudentRepository studentRepository, EncryptionService encryptionService) {
        this.studentRepository = studentRepository;
        this.encryptionService = encryptionService;

        jsonMapper = new ObjectMapper();
    }

    @GetMapping("/{id}")
    Student getStudent(@PathVariable String id) {
        var student = studentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + id));
        return tryDecryptStudent(student);
    }

    @GetMapping
    List<Student> getAllStudents() {
        var encryptedStudents = studentRepository.findAll();
        var students = encryptedStudents.stream()
                .map(this::tryDecryptStudent)
                .toList();
        return students;
    }

    @PostMapping
    Student createStudent(@RequestBody Student student) {
        var savedStudent = studentRepository.save(tryEncryptStudent(student));
        return tryDecryptStudent(savedStudent);
    }

    @PutMapping("/{id}")
    Student updateStudent(@PathVariable String id, @RequestBody Student student) {
        var studentId = student.getId();
        var updatedStudent = studentRepository.findById(studentId)
                .map(s -> studentRepository.save(tryEncryptStudent(student)))
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + studentId));
        return tryDecryptStudent(updatedStudent);
    }

    @DeleteMapping("/{id}")
    void deleteStudent(@PathVariable String id) {
        studentRepository.deleteById(id);
    }

    private EncryptedStudent tryEncryptStudent(Student student) {
        try {
            return encryptStudent(student);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to encrypt student", e);
        }
    }

    private EncryptedStudent encryptStudent(Student student) throws JsonProcessingException {
        // objects
        var id = student.getId();
        var firstName = student.getFirstName();
        var lastName = student.getLastName();
        var scores = student.getScores();

        var nonce = encryptionService.generateNonce();

        // encoded bytes
        var encodedFirstName = firstName.getBytes();
        var encodedLastName = lastName.getBytes();
        var encodedScores = jsonMapper.writeValueAsBytes(scores);

        // encrypted bytes
        var encryptedFirstName = encryptionService.encrypt(encodedFirstName, nonce);
        var encryptedLastName = encryptionService.encrypt(encodedLastName, nonce);
        var encryptedScores = encryptionService.encrypt(encodedScores, nonce);

        return new EncryptedStudent(
                id,
                encryptedFirstName,
                encryptedLastName,
                encryptedScores,
                nonce);
    }

    private Student tryDecryptStudent(EncryptedStudent student) {
        try {
            return decryptStudent(student);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to decrypt student", e);
        }
    }

    private Student decryptStudent(EncryptedStudent student)
            throws StreamReadException, DatabindException, IOException {
        // encrypted bytes
        var id = student.getId();
        var encryptedFirstName = student.getFirstName();
        var encryptedLastName = student.getLastName();
        var encryptedScores = student.getScores();

        var nonce = student.getNonce();

        // encoded bytes
        var encodedFirstName = encryptionService.decrypt(encryptedFirstName, nonce);
        var encodedLastName = encryptionService.decrypt(encryptedLastName, nonce);
        var encodedScores = encryptionService.decrypt(encryptedScores, nonce);

        // objects
        var firstName = new String(encodedFirstName);
        var lastName = new String(encodedLastName);
        var scores = jsonMapper.readValue(encodedScores, Map.class);

        return new Student(
                id,
                firstName,
                lastName,
                scores);
    }
}
