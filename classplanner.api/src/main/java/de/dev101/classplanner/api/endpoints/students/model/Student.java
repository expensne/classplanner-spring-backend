package de.dev101.classplanner.api.endpoints.students.model;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends AbstractStudent {

    private final String firstName;
    private final String lastName;
    private Map<String, Score> scores;

    public Student(String id, String firstName, String lastName, Map<String, Score> scores) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.scores = scores;
    }
}
