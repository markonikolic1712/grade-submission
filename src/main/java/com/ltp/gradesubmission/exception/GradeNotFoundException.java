package com.ltp.gradesubmission.exception;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException(Long id) {
        super("Grade with id: '" + String.valueOf(id) + "' does not exists");
    }
}
