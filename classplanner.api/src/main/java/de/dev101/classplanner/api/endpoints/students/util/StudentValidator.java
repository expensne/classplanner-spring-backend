package de.dev101.classplanner.api.endpoints.students.util;

import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.dev101.classplanner.api.endpoints.students.model.Score;
import de.dev101.classplanner.api.endpoints.students.model.Student;

public class StudentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Student student = (Student) obj;

        var minLen = 2;
        var maxLen = 50;

        var firstName = student.getFirstName();

        if (firstName == null) {
            errors.rejectValue("firstName", "firstName.null");
        } else {
            if (invalidString(firstName) || invalidStringLength(firstName, minLen, maxLen)) {
                errors.rejectValue("firstName", "firstName.invalid");
            }
        }

        var lastName = student.getLastName();

        if (lastName == null) {
            errors.rejectValue("lastName", "lastName.null");
        } else {
            if (invalidString(lastName) || invalidStringLength(lastName, minLen, maxLen)) {
                errors.rejectValue("lastName", "lastName.invalid");
            }
        }

        var minScore = 0d;
        var maxScore = 1000000d;

        var scores = student.getScores();

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

    private boolean invalidScore(Map<String, Score> scores, double min, double max) {
        return scores.values().stream()
                .anyMatch(score -> score.getPointsScored() < min || score.getPointsScored() > max);
    }
}