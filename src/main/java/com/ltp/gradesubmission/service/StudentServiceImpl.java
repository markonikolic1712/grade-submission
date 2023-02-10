package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student getStudent(int id) {
        // return studentRepository.getStudent(id);
        return studentRepository.getStudentById(id);
    }

    @Override
    public List<Student> getStudents() {
        
        return studentRepository.getStudents();
    }

    @Override
    public void createStudent(Student student) {
        studentRepository.addStudent(student);        
    }

    @Override
    public void deleteStudent(int id) {
        int index = getStudentIndex(id);
        studentRepository.deleteStudent(index);
    }
    

    public int getStudentIndex(int id) {
        return IntStream.range(0, studentRepository.getStudents().size())
            .filter(index -> studentRepository.getStudents().get(index).getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new StudentNotFoundException(id));
    }
}
