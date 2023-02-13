package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.exception.CourseNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import lombok.*;

@AllArgsConstructor
@Service
public class CourseServiceImlp implements CourseService {

    CourseRepository courseRepository;

    @Override
    public Course getCourse(Long id) throws CourseNotFoundException {
        Optional<Course> course = courseRepository.findById((long) id);
        try {
            if(course.isPresent()) return course.get();
        } catch (Exception e) {
            new CourseNotFoundException(id);
        }
        return null; 
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

    @Override
    public void updateCourse(int index, Course newCourse) throws CourseNotFoundException {
        // TODO Auto-generated method stub
        
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
