package de.dev101.classplanner.api.endpoints.exams.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "exams")
public class EncryptedExam extends AbstractExam {

    private final byte[] name;
    private final byte[] maxPoints;
    private final byte[] gradingScale;

    private final byte[] nonce;

    public EncryptedExam(String id, byte[] name, byte[] maxPoints, byte[] gradingScale, byte[] nonce) {
        super(id);

        this.name = name;
        this.maxPoints = maxPoints;
        this.gradingScale = gradingScale;

        this.nonce = nonce;
    }
}
