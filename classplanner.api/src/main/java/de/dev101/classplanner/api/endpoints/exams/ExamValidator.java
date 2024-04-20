package de.dev101.classplanner.api.endpoints.exams;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExamValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Exam.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object obj, @NonNull Errors errors) {
        Exam exam = (Exam) obj;

        var minLen = 2;
        var maxLen = 50;

        var name = exam.name();

        if (name == null) {
            errors.rejectValue("name", "name.null");
        } else {
            if (invalidString(name) || invalidStringLength(name, minLen, maxLen)) {
                errors.rejectValue("name", "name.invalid");
            }
        }

        var minMaxPoints = 0d;
        var maxMaxPoints = 1000000d;

        var maxPoints = exam.maxPoints();

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