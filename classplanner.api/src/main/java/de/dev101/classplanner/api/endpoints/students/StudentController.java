package de.dev101.classplanner.api.endpoints.students;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentRepository studentRepository;

    StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{id}")
    Student getStudent(@PathVariable String id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + id));
    }

    @GetMapping
    List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    Student updateStudent(@PathVariable String id, @RequestBody Student student) {
        var studentId = student.id();
        return studentRepository.findById(studentId)
                .map(s -> studentRepository.save(student))
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + studentId));
    }

    @DeleteMapping("/{id}")
    void deleteStudent(@PathVariable String id) {
        studentRepository.deleteById(id);
    }
}
