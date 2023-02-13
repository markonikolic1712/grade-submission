package com.ltp.gradesubmission.service;

import java.util.List;

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.exception.GradeNotFoundException;

public interface GradeService {
    Grade getGrade(Long studentId, Long courseId) throws GradeNotFoundException;
    Grade saveGrade(Grade grade, Long studentId, Long courseId) throws GradeNotFoundException;
    Grade updateGrade(String score, Long studentId, Long courseId) throws GradeNotFoundException;
    void deleteGrade(Long studentId, Long courseId) throws GradeNotFoundException;
    List<Grade> getStudentGrades(Long studentId) throws GradeNotFoundException;
    List<Grade> getCourseGrades(Long courseId) throws GradeNotFoundException;
    List<Grade> getAllGrades() throws GradeNotFoundException;
}
