package de.dev101.classplanner.api.endpoints.exams.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
abstract class AbstractExam {

    @Id
    private final String id;
}
