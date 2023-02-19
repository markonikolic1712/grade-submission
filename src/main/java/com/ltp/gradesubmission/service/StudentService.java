package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Set;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;

public interface StudentService {
    
    Student getStudent(Long id) throws StudentNotFoundException;
    List<Student> getStudents() throws StudentNotFoundException;
    Student saveStudent(Student student) throws StudentNotFoundException;
    void deleteStudent(Long id) throws StudentNotFoundException;
    void updateStudent(Long id, Student newStudent) throws StudentNotFoundException;
    Set<Course> getEnrolledCourses(Long id) throws StudentNotFoundException;
}
