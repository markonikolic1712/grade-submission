package com.ltp.gradesubmission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.exception.GradeNotFounfException;
import com.ltp.gradesubmission.service.GradeServiceImpl;

@RestController
@RequestMapping("/api/grade")
public class ApiGradeController {
    
    @Autowired
    GradeServiceImpl gradeService;

    @GetMapping("/all")
    public ResponseEntity<List<Grade>> getGrades(){
        System.out.println("----------- REST API - getGrades() -----------");
        List<Grade> grades = gradeService.getGrades();
        // if(grades.size() == 0) {
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }
        
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Grade> saveGrade(@RequestBody Grade grade) {
        System.out.println("----------- REST API - saveGrade() -----------");
        gradeService.addGrade(grade);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable("id") String id) {
        System.out.println("----------- REST API - getGradeById() -----------");
        Grade grade = gradeService.getGradeById(id);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }
}
