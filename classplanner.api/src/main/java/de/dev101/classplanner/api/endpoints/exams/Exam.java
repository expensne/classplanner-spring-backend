package de.dev101.classplanner.api.endpoints.exams;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exams")
public record Exam(
                @Id String id,
                String name,
                double maxPoints,
                GradingScale gradingScale) {
}

record GradingScale(double A, double B, double C, double D, double E, double F) {
}