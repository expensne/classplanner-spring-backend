package de.dev101.classplanner.api.endpoints.students.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
abstract class AbstractStudent {

    @Id
    private final String id;
}
