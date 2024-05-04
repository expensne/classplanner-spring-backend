package de.dev101.classplanner.api.endpoints.exams.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Exam extends AbstractExam {

    private final String name;
    private final double maxPoints;
    private final GradingScale gradingScale;

    public Exam(String id, String name, double maxPoints, GradingScale gradingScale) {
        super(id);
        this.name = name;
        this.maxPoints = maxPoints;
        this.gradingScale = gradingScale;
    }
}

