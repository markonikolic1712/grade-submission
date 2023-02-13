package com.ltp.gradesubmission.service;

import java.util.List;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.exception.CourseNotFoundException;

public interface CourseService {
    Course getCourse(Long id) throws CourseNotFoundException;
    Course saveCourse(Course course) throws CourseNotFoundException;
    void deleteCourse(Long id) throws CourseNotFoundException;
    List<Course> getCourses() throws CourseNotFoundException;
    void updateCourse(int index, Course newCourse) throws CourseNotFoundException;
}
