package de.dev101.classplanner.api.endpoints.students;

import java.util.HashMap;

import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class StudentValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object obj, @NonNull Errors errors) {
        Student student = (Student) obj;

        var minLen = 2;
        var maxLen = 50;

        var firstName = student.firstName();

        if (firstName == null) {
            errors.rejectValue("firstName", "firstName.null");
        } else {
            if (invalidString(firstName) || invalidStringLength(firstName, minLen, maxLen)) {
                errors.rejectValue("firstName", "firstName.invalid");
            }
        }

        var lastName = student.lastName();

        if (lastName == null) {
            errors.rejectValue("lastName", "lastName.null");
        } else {
            if (invalidString(lastName) || invalidStringLength(lastName, minLen, maxLen)) {
                errors.rejectValue("lastName", "lastName.invalid");
            }
        }

        var minScore = 0d;
        var maxScore = 1000000d;

        var scores = student.scores();

        if (scores == null) {
            errors.rejectValue("scores", "scores.null");
        } else {
            if (invalidScore(scores, minScore, maxScore)) {
                errors.rejectValue("scores", "scores.invalid");
            }
        }
    }

    private boolean invalidString(String input) {
        return input.trim().isEmpty() || input.trim().isBlank();
    }

    private boolean invalidStringLength(String input, int min, int max) {
        return input.trim().length() < min || input.trim().length() > max;
    }

    private boolean invalidScore(HashMap<String, Score> scores, double min, double max) {
        return scores.values().stream().anyMatch(score -> score.pointsScored() < min || score.pointsScored() > max);
    }
}