package com.ltp.gradesubmission.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(int id) {
        super("Grade with id: '" + id + "' does not exists");
    }
}
