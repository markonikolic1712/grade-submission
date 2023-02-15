package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Set;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.exception.CourseNotFoundException;
import com.ltp.gradesubmission.entity.Student;

public interface CourseService {
    Course getCourse(Long id) throws CourseNotFoundException;
    Course saveCourse(Course course) throws CourseNotFoundException;
    void deleteCourse(Long id) throws CourseNotFoundException;
    List<Course> getCourses() throws CourseNotFoundException;
    void updateCourse(int index, Course newCourse) throws CourseNotFoundException;

    Course addStudentToCourse(Long studentId, Long courseId);
    Set<Student> getEnrolledStudents(Long id);
}
