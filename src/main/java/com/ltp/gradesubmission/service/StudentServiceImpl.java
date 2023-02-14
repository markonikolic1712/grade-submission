package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.*;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);        
    }

    @Override
    public Student getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        // proverava se da li postoji value u optional objektu - ako postoji onda se sa get() uzima i vraca a ako ne postoji onda se baca izuzetak
        if(student.isPresent()) {
            return student.get();
        } else {
            throw new StudentNotFoundException(id);
        }
    }

    @Override
    public void deleteStudent(Long id) {        
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public void updateStudent(Long id, Student newStudent) throws StudentNotFoundException {
        // TODO Auto-generated method stub
        
    }   


    // @Override
    // public Student getStudent(Long id) {
    //     return studentRepository.findById(id);
    // }

    // @Override
    // public List<Student> getStudents() {
        
    //     return studentRepository.getStudents();
    // }
 

    // @Override
    // public void deleteStudent(Long id) {
    //     int index = getStudentIndex(id);
    //     studentRepository.deleteStudent(index);
    // }
    
    // @Override
    // public void updateStudent(Long id, Student newStudent) throws StudentNotFoundException {
    //     int index = getStudentIndex(id);
    //     studentRepository.updateStudent(index, newStudent);
    // }

    // public int getStudentIndex(Long id) {
    //     return IntStream.range(0, studentRepository.getStudents().size())
    //         .filter(index -> studentRepository.getStudents().get(index).getId() == id)
    //         .findFirst()
    //         .orElseThrow(() -> new StudentNotFoundException(id));
    // }
}
