package com.ltp.gradesubmission.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ltp.gradesubmission.entity.Course;

public interface CourseRepository extends CrudRepository <Course, Long> {

}

// @Repository
// public class CourseRepository {

//     List<Course> courses = new ArrayList();
    
//     public List<Course> getCourses(){
//         return courses;
//     }

//     public Course getCourse(int index) {
//         return courses.get(index);
//     }

//     public void addCourse(Course course) {
//         course.setId((long) courses.size() + 1);
//         courses.add(course);
//     }

//     public void deleteCourse(int index) {
//         courses.remove(index);
//     }

//     public void updateCourse(int index, Course course){
//         courses.set(index, course);
//     }
// }

