package com.ltp.gradesubmission.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.service.StudentService;
import com.ltp.gradesubmission.service.StudentServiceImpl;

@RestController
@RequestMapping("/api/student")
public class ApiStudentController {
    
    @Autowired
    StudentServiceImpl studentService;
    
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getStudents() {
        System.out.println("----------- REST API - getStudents() -----------");
        List<Student> students = studentService.getStudents();
        return new ResponseEntity<>(students, HttpStatus.OK); 
    }

    // 1. klijent posalje post request u body-u salje JSOM objekat sa podacima za studenta. 
    // 2. @RequestBody uzima taj json i deserijalizuje ga u Student objekat. 
    // 3. Zatim se taj objketa prosledjuje servisu koji poziva repository-u i u metodu save(student) prosledjuje objekat 
    // 4. repository koristi CRUD metode koje je nasledio od CrudRepository-a i student entity snima u bazu uz pomoc ORM-a
    // 5. ORM layer mapira property-e objekta na kolone i kreira sql statement za insert
    @PostMapping
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        System.out.println("----------- REST API - saveStudent() -----------");
        studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        System.out.println("----------- REST API - getStudentById() -----------");
        Student student = studentService.getStudent(id);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") Long id){
        System.out.println("----------- REST API - deleteStudent() -----------");
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody Student student) {
        System.out.println("----------- REST API - updateStudent() -----------");
        studentService.updateStudent(id, student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
