package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.CourseNotFoundException;
import com.ltp.gradesubmission.exception.EntityNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.StudentRepository;

import lombok.*;

@AllArgsConstructor
@Service
public class CourseServiceImlp implements CourseService {

    CourseRepository courseRepository;
    StudentRepository studentRepository;
    

    @Override
    public Course getCourse(Long id) {
        Optional<Course> course = courseRepository.findById((long) id);

        return unwrapCourse(course, id);
    }

    @Override
    public Course saveCourse(Course course) throws CourseNotFoundException {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) throws CourseNotFoundException {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getCourses() throws CourseNotFoundException {
       return (List<Course>) courseRepository.findAll();
    }

    // PUT - http://localhost:8080/api/course/1    
    // u body-u se salje json objekat sa novim podacima
    @Override
    public void updateCourse(Long id, Course newCourse) {
        // 1. uzima se kurs po id-u
        // 2. radi se update property-a
        // 3. course koji je uzet po id-u se snima u bazu. Posto je to isti objekat on ce biti snimljen preko starog
        Course course = unwrapCourse(courseRepository.findById(id), id);
        course.setCode(newCourse.getCode());
        course.setDescription(newCourse.getDescription());
        course.setSubject(newCourse.getSubject());
        course.setGrades(newCourse.getGrades());
        course.setStudents(newCourse.getStudents());
        courseRepository.save(course);
    }

    static Course unwrapCourse(Optional<Course> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Course.class);
    }

    @Override
    public Course addStudentToCourse(Long studentId, Long courseId) {
        Course course = getCourse(courseId);
        Optional<Student> student = studentRepository.findById(studentId);
        Student unwrappedStudent = StudentServiceImpl.unwrapStudent(student, studentId);
        course.getStudents().add(unwrappedStudent); // uzima se property students iz course entiteta i u kolekciju se dodaje novi student
        return courseRepository.save(course);
    }

    @Override
    public Set<Student> getEnrolledStudents(Long id) {
        Course course = getCourse(id);
        return course.getStudents();
    }

    /*
     @Override
    public List<Course> getCourses() {
        return courseRepository.getCourses();
    }

    @Override
    public Course getCourse(Long id) {
        int index = getCourseIndex(id);
        return courseRepository.getCourse(index);
    }

    @Override
    public Course saveCourse(Course course) {
        courseRepository.addCourse(course);
        return course;
    }

    @Override
    public void deleteCourse(Long id) {
        int index = getCourseIndex(id);
        courseRepository.deleteCourse(index);
    }

    @Override
    public void updateCourse(int index, Course newCourse) throws CourseNotFoundException {
        courseRepository.updateCourse(index, newCourse);
    }
    
    public int getCourseIndex(Long id) {
        return IntStream.range(0, courseRepository.getCourses().size())
                .filter(index -> courseRepository.getCourse(index).getId() == id)
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException(id));
    }
     */
}
