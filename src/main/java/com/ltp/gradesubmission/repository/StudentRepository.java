package com.ltp.gradesubmission.repository;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ltp.gradesubmission.entity.Student;


@Repository
public class StudentRepository {
    
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents(){
        return students;
    }

    public Student getStudent(int index){
        return students.get(index);
    }

    public Student getStudentById(int id){
        
        if(students.size() == 0) return new Student();

        List<Student> student = students
        .stream()
        .filter((Student s) -> {
            return s.getId() == id;
        })
        .collect(Collectors.toList());

        Optional<Student> optional = Optional.of(student.get(0));

        // proverava se da li je student uzet iz liste i da li postoji u optional. Ako postoji onda se on vraca se optional.get() a ako ne postoji onda se vraca prazan objekat Student.
        return optional.isPresent() ? optional.get() : new Student();
    }

    public void addStudent(Student student) {
        student.setId( (long) (students.size() + 1));
        students.add(student);
    }

    public void updateStudent(int index, Student student) {
        students.set(index, student);
    }

    public void deleteStudent(int index) {
        students.remove(index);
    }
}
