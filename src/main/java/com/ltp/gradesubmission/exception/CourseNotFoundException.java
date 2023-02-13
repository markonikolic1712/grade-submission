package com.ltp.gradesubmission.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Course with id: '" + String.valueOf(id) + "' does not exists");
    }
}
