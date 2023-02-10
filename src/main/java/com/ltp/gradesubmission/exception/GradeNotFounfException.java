package com.ltp.gradesubmission.exception;

public class GradeNotFounfException extends RuntimeException {
    public GradeNotFounfException(String id) {
        super("Grade with id: '" + id + "' does not exists");
    }
}
