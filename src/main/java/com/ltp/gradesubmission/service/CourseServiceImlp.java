package com.ltp.gradesubmission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.repository.CourseRepository;

public class CourseServiceImlp implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Course getCourse(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Course saveCourse(Course course) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Course> getCourses() {
       
        return courseRepository.getCourses();
    }
    
}
