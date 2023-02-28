package com.ltp.gradesubmission.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.websocket.server.PathParam;

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
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.service.CourseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/course")
public class ApiCourseController {

    private CourseService courseService;

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getCourses(){
        System.out.println("----------- REST API - getCourses() -----------");
        List<Course> courses = courseService.getCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") Long id){
        System.out.println("----------- REST API - getCourse() -----------");
        Course course = courseService.getCourse(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Course> saveCourse(@Valid @RequestBody Course course){
        System.out.println("----------- REST API - saveCourse() -----------");
        Course newCourse = courseService.saveCourse(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        System.out.println("----------- REST API - deleteCourse() -----------");
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}/student/{studentId}")
    public ResponseEntity<Course> enrollStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        System.out.println("----------- REST API - enrollStudentToCourse() -----------");
        return new ResponseEntity<>(courseService.addStudentToCourse(studentId, courseId), HttpStatus.OK);
    }

    // metoda koja vraca stuidente koji su upisani na kurs
    @GetMapping("/{id}/students")
    public ResponseEntity<Set<Student>> getEnrolledStudents(@PathVariable Long id) {
        System.out.println("----------- REST API - getEnrolledStudents() -----------");
        return new ResponseEntity<>(courseService.getEnrolledStudents(id), HttpStatus.OK);
    }

    // PUT - http://localhost:8080/api/course/1    
    // u body-u se salje json objekat sa novim podacima
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCourse(@PathVariable("id") Long id, @RequestBody Course newCourse){
        courseService.updateCourse(id, newCourse);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
