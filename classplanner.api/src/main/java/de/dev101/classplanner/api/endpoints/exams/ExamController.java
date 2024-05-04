package de.dev101.classplanner.api.endpoints.exams;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.dev101.classplanner.api.endpoints.encryption.EncryptionService;
import de.dev101.classplanner.api.endpoints.exams.model.EncryptedExam;
import de.dev101.classplanner.api.endpoints.exams.model.Exam;
import de.dev101.classplanner.api.endpoints.exams.model.GradingScale;

@CrossOrigin
@RestController
@RequestMapping("exams")
public class ExamController {

    private final EncryptedExamRepository examRepository;
    private final EncryptionService encryptionService;

    private final ObjectMapper jsonMapper;

    ExamController(EncryptedExamRepository examRepository, EncryptionService encryptionService) {
        this.examRepository = examRepository;
        this.encryptionService = encryptionService;

        jsonMapper = new ObjectMapper();
    }

    @GetMapping("/{id}")
    Exam getExam(@PathVariable String id) {
        var exam = examRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with id " + id));
        return tryDecryptExam(exam);
    }

    @GetMapping
    List<Exam> getAllExams() {
        var encryptedExam = examRepository.findAll();
        var exams = encryptedExam.stream()
                .map(this::tryDecryptExam)
                .toList();
        return exams;
    }

    @PostMapping
    Exam createExam(@RequestBody Exam exam) {
        var savedExam = examRepository.save(tryEncryptExam(exam));
        return tryDecryptExam(savedExam);
    }

    @PutMapping("/{id}")
    Exam updateExam(@PathVariable String id, @RequestBody Exam exam) {
        var examId = exam.getId();
        var updatedExam = examRepository.findById(examId)
                .map(s -> examRepository.save(tryEncryptExam(exam)))
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with id " + examId));
        return tryDecryptExam(updatedExam);
    }

    @DeleteMapping("/{id}")
    void deleteExam(@PathVariable String id) {
        examRepository.deleteById(id);
    }

    private EncryptedExam tryEncryptExam(Exam exam) {
        try {
            return encryptExam(exam);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to encrypt exam", e);
        }
    }

    private EncryptedExam encryptExam(Exam exam) throws JsonProcessingException {
        // objects
        var id = exam.getId();
        var name = exam.getName();
        var maxPoints = exam.getMaxPoints();
        var gradingScale = exam.getGradingScale();

        var nonce = encryptionService.generateNonce();

        // encoded bytes
        var encodedName = name.getBytes();
        var encodedMaxPoints = jsonMapper.writeValueAsBytes(maxPoints);
        var encodedGradingScale = jsonMapper.writeValueAsBytes(gradingScale);

        // encrypted bytes
        var encryptedName = encryptionService.encrypt(encodedName, nonce);
        var encryptedMaxPoints = encryptionService.encrypt(encodedMaxPoints, nonce);
        var encryptedGradingScale = encryptionService.encrypt(encodedGradingScale, nonce);

        return new EncryptedExam(
                id,
                encryptedName,
                encryptedMaxPoints,
                encryptedGradingScale,
                nonce);
    }

    private Exam tryDecryptExam(EncryptedExam exam) {
        try {
            return decryptExam(exam);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to decrypt exam", e);
        }
    }

    private Exam decryptExam(EncryptedExam exam) throws StreamReadException, DatabindException, IOException {
        // encrypted bytes
        var id = exam.getId();
        var encryptedName = exam.getName();
        var encryptedMaxPoints = exam.getMaxPoints();
        var encryptedGradingScale = exam.getGradingScale();

        var nonce = exam.getNonce();

        // encoded bytes
        var encodedName = encryptionService.decrypt(encryptedName, nonce);
        var encodedMaxPoints = encryptionService.decrypt(encryptedMaxPoints, nonce);
        var encodedGradingScale = encryptionService.decrypt(encryptedGradingScale, nonce);

        // objects
        var name = new String(encodedName);
        var maxPoints = jsonMapper.readValue(encodedMaxPoints, Double.class);
        var gradingScale = jsonMapper.readValue(encodedGradingScale, GradingScale.class);

        return new Exam(
                id,
                name,
                maxPoints,
                gradingScale);
    }
}
