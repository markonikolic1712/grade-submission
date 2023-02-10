package com.ltp.gradesubmission.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ltp.gradesubmission.entity.Course;

@Repository
public class CourseRepository {

    List<Course> courses = new ArrayList();
    
    public List<Course> getCourses(){
        return courses;
    }


}
