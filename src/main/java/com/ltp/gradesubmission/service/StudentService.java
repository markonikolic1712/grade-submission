package com.ltp.gradesubmission.service;

import java.util.List;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;

public interface StudentService {
    
    Student getStudent(int id) throws StudentNotFoundException;
    List<Student> getStudents() throws StudentNotFoundException;
    void createStudent(Student student) throws StudentNotFoundException;
    void deleteStudent(int id) throws StudentNotFoundException;
}
