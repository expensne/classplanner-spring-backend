package de.dev101.classplanner.api.endpoints.exams.util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.dev101.classplanner.api.endpoints.exams.model.Exam;

public class ExamValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Exam.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Exam exam = (Exam) obj;

        var minLen = 2;
        var maxLen = 50;

        var name = exam.getName();

        if (name == null) {
            errors.rejectValue("name", "name.null");
        } else {
            if (invalidString(name) || invalidStringLength(name, minLen, maxLen)) {
                errors.rejectValue("name", "name.invalid");
            }
        }

        var minMaxPoints = 0d;
        var maxMaxPoints = 1000000d;

        var maxPoints = exam.getMaxPoints();

        if (invalidPoints(maxPoints, minMaxPoints, maxMaxPoints)) {
            errors.rejectValue("maxPoints", "maxPoints.invalid");
        }
    }

    private boolean invalidString(String input) {
        return input.trim().isEmpty() || input.trim().isBlank();
    }

    private boolean invalidStringLength(String input, int min, int max) {
        return input.trim().length() < min || input.trim().length() > max;
    }

    private boolean invalidPoints(double maxPoints, double min, double max) {
        return maxPoints < min || maxPoints > max;
    }
}