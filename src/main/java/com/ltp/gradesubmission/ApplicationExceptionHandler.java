package com.ltp.gradesubmission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.ltp.gradesubmission.exception.ErrorResponse;
import com.ltp.gradesubmission.exception.GradeNotFounfException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleGradeNotFoundException(GradeNotFounfException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
