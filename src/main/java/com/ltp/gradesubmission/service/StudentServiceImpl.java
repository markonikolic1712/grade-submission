package com.ltp.gradesubmission.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.*;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    StudentRepository studentRepository;
    CourseRepository courseRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);        
    }

    @Override
    public Student getStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        // proverava se da li postoji value u optional objektu - ako postoji onda se sa get() uzima i vraca a ako ne postoji onda se baca izuzetak. Ovo se obavlja u metodi unwrapStudent()
        return unwrapStudent(student, id);
    }

    @Override
    public void deleteStudent(Long id) {        
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    // PUT - http://localhost:8080/api/student/1    
    // u body-u se salje json objekat sa novim podacima
    @Override
    public void updateStudent(Long id, Student newStudent) throws StudentNotFoundException {

        // uzima se student po id-u
        Optional<Student> optional = studentRepository.findById(id);
        Student student = unwrapStudent(optional, id);

        // objektu koji je uzet se setuju novi podaci u property-e
        student.setName(newStudent.getName());
        student.setBirthDate(newStudent.getBirthDate());
        student.setGrades(newStudent.getGrades());
        student.setCourses(newStudent.getCourses());

        // objekat koji je uzet se snima u bazu
        studentRepository.save(student);  
    }   

    @Override
    public Set<Course> getEnrolledCourses(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isEmpty()) throw new StudentNotFoundException(studentId);
        
        // 
        // Set<Course> newCourseSet = new HashSet<>();
        // for(Course course : (Set<Course>) student.get().getCourses()) {
        //     Course c = new Course(course.getSubject(), course.getCode(), course.getDescription());
        //     newCourseSet.add(c);
        // }
        // return newCourseSet;

        return student.get().getCourses();
    }

    // proverava se da li postoji value u optional objektu - ako postoji onda se sa get() uzima i vraca a ako ne postoji onda se baca izuzetak. Ovo se obavlja u metodi unwrapStudent()
    static Student unwrapStudent(Optional<Student> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new StudentNotFoundException(id);
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
