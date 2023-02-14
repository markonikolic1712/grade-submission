package com.ltp.gradesubmission.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Grade with id: '" + String.valueOf(id) + "' does not exists in our records");
    }
}
