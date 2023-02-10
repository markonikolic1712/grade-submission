package com.ltp.gradesubmission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class ApiStudentController {
    
    @Autowired
    StudentService studentService;
    
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getStudents() {
        System.out.println("----------- REST API - getStudents() -----------");
        List<Student> students = studentService.getStudents();
        return new ResponseEntity<>(students, HttpStatus.OK); 
    }

    @PostMapping("")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        System.out.println("----------- REST API - saveStudent() -----------");
        studentService.createStudent(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        System.out.println("----------- REST API - getStudentById() -----------");
        Student student = studentService.getStudent(id);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    } 
}
