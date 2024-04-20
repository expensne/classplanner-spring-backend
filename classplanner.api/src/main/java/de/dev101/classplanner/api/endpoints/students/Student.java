package de.dev101.classplanner.api.endpoints.students;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public record Student(
        @Id String id,
        String firstName,
        String lastName,
        HashMap<String, Score> scores) {
}

record Score(double pointsScored, boolean isPostscript) {
}