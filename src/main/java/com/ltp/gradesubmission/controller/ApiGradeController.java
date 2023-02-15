package com.ltp.gradesubmission.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.service.CourseService;
import com.ltp.gradesubmission.service.GradeServiceImpl;
import com.ltp.gradesubmission.service.StudentService;


@RestController
@RequestMapping("/api/grade")
public class ApiGradeController {
    
    @Autowired
    private GradeServiceImpl gradeService;

    @GetMapping("/all")
    public ResponseEntity<List<Grade>> getGrades(){
        System.out.println("----------- REST API - getGrades() -----------");
        List<Grade> grades = gradeService.getAllGrades();
        // if(grades.size() == 0) {
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }
        
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    // uzimanje ocene po studentId- u i courseId-u
    // GET - http://localhost:8080/api/grade/student/{studentId}/course/{courseId}
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> getGrade(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId){
        return new ResponseEntity<>(gradeService.getGrade(studentId, courseId), HttpStatus.OK);
    }

    // kada se kreira grade/ocena u body-u se salje json grade a kao path variable se salje studentId i courseId
    // studentId i courseId su strani kljucevi u grade tabeli
    // POST - http://localhost:8080/api/grade/student/3/course/2
    // POST - /grade/student/{studentId}/course/{courseId}
    @PostMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> saveGrade(@RequestBody Grade grade, @PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        System.out.println("----------- REST API - saveGrade() -----------");
        
        return new ResponseEntity<>(gradeService.saveGrade(grade, studentId, courseId), HttpStatus.CREATED);
    }

    // radi se update grade record-a u bazi - @RequestBody nosi novi podatak score. U servisu se prvo pronalazi red po studentId i courseId a zatim se tom redu upisuje novi score
    // PUT - http://localhost:8080/api/grade/student/3/course/2
    // PUT - /grade/student/{studentId}/course/{courseId}
    @PutMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> updateGrade(@Valid @RequestBody Grade grade, @PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        System.out.println("----------- REST API - saveGrade() -----------");
        
        return new ResponseEntity<>(gradeService.updateGrade(grade.getScore(), studentId, courseId), HttpStatus.OK);
    }

     // radi se delete grade record-a u bazi - @RequestBody nosi novi podatak score. U servisu se prvo pronalazi red po studentId i courseId a zatim se tom redu upisuje novi score
    // DELETE - http://localhost:8080/api/grade/student/3/course/2
    // DELETE - /grade/student/{studentId}/course/{courseId}
    @DeleteMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<Grade> deleteGrade(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        System.out.println("----------- REST API - deleteGrade() -----------");
        gradeService.deleteGrade(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } 
    
    // uzimanje svih ocena za nekog studenta
    // GET - http://localhost:8080/api/grade/student/{studentId}
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getStudentGrades(@PathVariable("studentId") Long studentId){
        return new ResponseEntity<>(gradeService.getStudentGrades(studentId), HttpStatus.OK);
    }


    // uzimanje svih ocena za neki kurs
    // GET - http://localhost:8080/api/grade/course/{courseId}
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Grade>> getCourseGrades(@PathVariable("courseId") Long courseId){
        return new ResponseEntity<>(gradeService.getCourseGrades(courseId), HttpStatus.OK);
    }



    // @GetMapping("/{id}")
    // public ResponseEntity<Grade> getGradeById(@PathVariable("id") Long id) {
    //     System.out.println("----------- REST API - getGradeById() -----------");
    //     Grade grade = gradeService.getGradeById(id);
    //     return new ResponseEntity<>(grade, HttpStatus.OK);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Grade> deleteGrade(@PathVariable("id") Long id) {
    //     System.out.println("----------- REST API - deleteGrade() -----------");
    //     gradeService.deleteGrade(id);
    //     return new ResponseEntity<>(HttpStatus.OK);
    // }

}
