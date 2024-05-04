package de.dev101.classplanner.api.endpoints.students.model;

import lombok.Data;

// Note: making this a record does not work with Jackson because java records are implicitly final
// and the Score is stored inside a Map which then causes an error when trying to deserialize
// => dont store final stuff in a map when using Jackson

@Data
public class Score {

    private final double pointsScored;
    private final boolean isPostscript;

    public Score(double pointsScored, boolean isPostscript) {
        this.pointsScored = pointsScored;
        this.isPostscript = isPostscript;
    }
}
