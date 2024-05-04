package de.dev101.classplanner.api.endpoints.students.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "students")
public class EncryptedStudent extends AbstractStudent {

    private final byte[] firstName;
    private final byte[] lastName;
    private final byte[] scores;

    private final byte[] nonce;

    public EncryptedStudent(String id, byte[] firstName, byte[] lastName, byte[] scores, byte[] nonce) {
        super(id);

        this.firstName = firstName;
        this.lastName = lastName;
        this.scores = scores;

        this.nonce = nonce;
    }
}
